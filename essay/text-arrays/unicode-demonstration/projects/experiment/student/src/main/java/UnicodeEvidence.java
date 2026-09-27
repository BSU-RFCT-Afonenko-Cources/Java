public final class UnicodeEvidence {
    private UnicodeEvidence() {}

    public record Example(String text, int utf16Units, int codePoints,
                          String firstCodePoint) {}

    public static Example[] examples() {
        throw new UnsupportedOperationException("Подготовьте данные для эксперимента");
    }
}
