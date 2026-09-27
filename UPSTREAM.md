# Источники расширений и авторского формата

Курс использует один текущий формат учебных данных. Его настройки находятся в
YAML; отдельный номер схемы и переключатели совместимости не задаются.
Расширения включены в репозиторий, поэтому сборка не требует их скачивания.

| Расширение | Репозиторий | Проверенный коммит |
|---|---|---|
| `course-core`, `course-presentation` | [Ядро и представление курса](https://github.com/AfonenkoA/programming-course-core-specification) | [d5e31f2](https://github.com/AfonenkoA/programming-course-core-specification/tree/d5e31f204eadf17ba5a4477ccc30aecbaee574ec) |
| `reference-catalog` | [Каталог перекрёстных ссылок](https://github.com/AfonenkoA/quarto-reference-catalog) | [6b19450](https://github.com/AfonenkoA/quarto-reference-catalog/tree/6b19450fd31c28c5aae17c658a01123c67cc4f7b) |
| `course-prairielearn` | [Адаптер PrairieLearn](https://github.com/AfonenkoA/programming-course-prairielearn-specification) | [e38d4f0](https://github.com/AfonenkoA/programming-course-prairielearn-specification/tree/e38d4f0b7965cc09ae3d09153d4cb4855efe3429) |

Исходные файлы установленных расширений совпадают с указанными коммитами.
Книга подключает `course-core`; рефераты — также `course-presentation` и
`course-prairielearn`. Корневой проект использует `reference-catalog` для общей
сборки и связывания страниц. Тема HTML — стандартная `cosmo`.

Проверка покрытия авторского формата запускается из
[шаблона курса](https://github.com/BSU-RFCT-Afonenko-Cources/programming-course-template).
Проверенный коммит [13f7c92](https://github.com/BSU-RFCT-Afonenko-Cources/programming-course-template/tree/13f7c9205029b97001d56c9655fffe7a5a2f25b7) закреплён в `.github/workflows/check.yml`. Проверка читает
действующие примеры шаблона и документы Java, включая включаемые фрагменты и
самостоятельный PDF-проект; отдельной копии перечня возможностей в Java нет.

Изменения реализации вносятся в исходный репозиторий расширения. При обновлении
заменяйте его установленную копию целиком, затем проверяйте покрытие шаблоном и
собирайте студенческий и полный сайты. Коммиты в этой таблице фиксируют состав
проверенной поставки, а не варианты поддерживаемых схем.
