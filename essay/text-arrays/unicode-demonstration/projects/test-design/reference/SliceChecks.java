public final class SliceChecks {
    private SliceChecks() {}

    public static void verify(CodePointSlice operation) {
        expect(operation, "A\uD83D\uDE80B", 1, 2, "\uD83D\uDE80");
        expect(operation, "ABCD", 1, 2, "B");
        expect(operation, "ABCD", 2, 2, "");
        expect(operation, "", 0, 0, "");
        expect(operation, "e\u0301", 0, 1, "e");
        expectInvalid(operation, "A", -1, 0);
        expectInvalid(operation, "A", 0, 2);
        expectInvalid(operation, "AB", 2, 1);
    }

    private static void expect(CodePointSlice operation, String text,
                               int from, int to, String expected) {
        final String actual;
        try {
            actual = operation.slice(text, from, to);
        } catch (RuntimeException failure) {
            throw new AssertionError("A valid range was rejected", failure);
        }
        if (!expected.equals(actual)) {
            throw new AssertionError("Unexpected range content");
        }
    }

    private static void expectInvalid(CodePointSlice operation, String text,
                                      int from, int to) {
        try {
            operation.slice(text, from, to);
        } catch (IndexOutOfBoundsException expected) {
            return;
        } catch (RuntimeException failure) {
            throw new AssertionError("Unexpected exception type", failure);
        }
        throw new AssertionError("An invalid range was accepted");
    }
}
