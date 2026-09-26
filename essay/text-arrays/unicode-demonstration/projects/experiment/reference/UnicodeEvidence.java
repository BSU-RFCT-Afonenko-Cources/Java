public final class UnicodeEvidence {
    private UnicodeEvidence() {}

    public record Example(String text, int utf16Units, int codePoints,
                          String firstCodePoint) {}

    public static Example[] examples() {
        return new Example[] {
            new Example("\uD83D\uDE80A", 3, 2, "\uD83D\uDE80"),
            new Example("Java", 4, 4, "J")
        };
    }
}
