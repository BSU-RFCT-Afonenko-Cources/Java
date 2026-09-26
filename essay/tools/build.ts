/** Generic sequential view build; no platform export. */
const root = Deno.cwd();
const quarto = Deno.env.get("QUARTO") ?? "quarto";
await Deno.stat(`${root}/_quarto.yml`);
await Deno.mkdir(`${root}/_generated/models`, { recursive: true });
for (const view of ["student", "full"]) {
  const result = await new Deno.Command(quarto, {
    args: ["render", ".", "--profile", view, "--to", "html", "--fail-if-warnings"],
    cwd: root, stdout: "inherit", stderr: "inherit",
  }).output();
  if (!result.success) throw new Error(`${view} render failed (${result.code})`);
  const text = await Deno.readTextFile(`${root}/_generated/course-spec/course.json`);
  const model = JSON.parse(text);
  if (model.course.view !== view) throw new Error(`Wrong course view: ${model.course.view}`);
  await Deno.writeTextFile(`${root}/_generated/models/${view}.json`, text);
  if (view === "full") {
    const rows = ["assessment\ttitle\tstudent-label"];
    for (const a of model.assessments) {
      const label = a.extensions.prairielearn?.assignment?.["student-label"];
      if (label) rows.push([a.id, a.title, label].map(x => String(x).replace(/[\t\r\n]/g, " ")).join("\t"));
    }
    await Deno.writeTextFile(`${root}/_generated/assignments.tsv`, rows.join("\n") + "\n");
  }
  console.log(`${view}: ${model.exercises.length} exercises, ${model.assessments.length} assessments`);
}
console.log("Both views built; full model and derived assignment catalogue saved.");
