import { dirname, fromFileUrl, join, resolve } from "stdlib/path";
import { parse } from "stdlib/yaml";

const root = dirname(dirname(fromFileUrl(import.meta.url)));
function assert(value: unknown, message: string): asserts value {
  if (!value) throw new Error(message);
}
async function files(directory: string): Promise<string[]> {
  const result: string[] = [];
  for await (const item of Deno.readDir(directory)) {
    const path = join(directory, item.name);
    if (item.isDirectory) result.push(...await files(path));
    else if (item.isFile) result.push(path);
  }
  return result;
}
const config = parse(await Deno.readTextFile(join(root, "_quarto.yml"))) as Record<string, any>;
const essay = parse(await Deno.readTextFile(join(root, "essay/_quarto.yml"))) as Record<string, any>;
const exports = Object.entries(config["reference-catalog"].exports)
  .flatMap(([namespace, ids]) => (ids as string[]).map(id => `${namespace}:${id}`)).sort();
const hidden = new Set<string>();
for (const file of (await files(join(root, "essay"))).filter(path => path.endsWith("/_control.qmd"))) {
  const text = await Deno.readTextFile(file);
  if (!text.includes(".when-full")) continue;
  for (const match of text.matchAll(/\{#(exr-[\w-]+)/g)) hidden.add(match[1]);
}
let links = 0;
for (const profile of ["student", "full"]) {
  const output = join(root, `_site-${profile}`), paths = await files(output);
  const expectedArchives = Object.entries(essay["project-download"].resources)
    .filter(([, resource]) => !(resource as any).profiles || (resource as any).profiles.includes(profile))
    .map(([id]) => `${id}.zip`).sort();
  const actualArchives = paths.filter(path => path.endsWith(".zip")).map(path => path.split("/").at(-1)).sort();
  assert(JSON.stringify(actualArchives) === JSON.stringify(expectedArchives), `${profile}: состав архивов не соответствует явному реестру`);
  const catalog = JSON.parse(await Deno.readTextFile(join(output, "reference-catalog.json")));
  assert(JSON.stringify(Object.keys(catalog.targets).sort()) === JSON.stringify(exports), `${profile}: нарушен явный экспорт глав`);
  for (const path of paths) {
    assert(!/\.(?:qmd|java|gradle|tsv|ts|md)$/.test(path) && !path.includes("/_generated/") && !path.includes("/_extensions/"), `Опубликован служебный исходник: ${path}`);
    if (!path.endsWith(".html") && !path.endsWith("search.json")) continue;
    const text = await Deno.readTextFile(path);
    if (profile === "student") for (const id of hidden) {
      assert(!text.includes(id), `В студенческой публикации найден закрытый контроль ${id}: ${path}`);
    }
    if (!path.endsWith(".html")) continue;
    for (const match of text.matchAll(/\b(?:href|src)="([^"]+)"/g)) {
      const raw = match[1];
      if (/^(?:[A-Za-z][A-Za-z0-9+.-]*:|\/\/|#)/.test(raw)) continue;
      const url = decodeURIComponent(raw.split(/[?#]/)[0]);
      if (!url) continue;
      const target = url.startsWith("/") ? join(output, url.slice(1)) : resolve(dirname(path), url);
      try { await Deno.stat(target); } catch { throw new Error(`Неработающая ссылка ${raw}: ${path}`); }
      links++;
    }
  }
}
console.log(`Публикация Java проверена: оба профиля, явный экспорт, архивы, ${hidden.size} закрытых заданий и ${links} локальных ссылок.`);
