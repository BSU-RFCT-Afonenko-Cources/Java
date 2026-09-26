public final class UnicodeSlice {
    private UnicodeSlice() {}

    public static String slice(String text, int from, int to) {
        int size = text.codePointCount(0, text.length());
        if (from < 0 || to < from || to > size) {
            throw new IndexOutOfBoundsException();
        }
        int begin = text.offsetByCodePoints(0, from);
        int end = text.offsetByCodePoints(begin, to - from);
        return text.substring(begin, end);
    }
}
