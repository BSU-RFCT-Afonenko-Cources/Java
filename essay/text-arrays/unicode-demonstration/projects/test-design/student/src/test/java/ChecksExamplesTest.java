import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChecksExamplesTest {
    static String correct(String text, int from, int to) {
        int size = text.codePointCount(0, text.length());
        if (from < 0 || to < from || to > size) throw new IndexOutOfBoundsException();
        return text.substring(text.offsetByCodePoints(0, from),
                              text.offsetByCodePoints(0, to));
    }

    @Test void acceptsCorrectOperation() {
        assertDoesNotThrow(() -> SliceChecks.verify(ChecksExamplesTest::correct));
    }

    @Test void rejectsUtf16Indices() {
        assertThrows(AssertionError.class,
                     () -> SliceChecks.verify(String::substring));
    }

    @Test void rejectsIncludedRightBoundary() {
        assertThrows(AssertionError.class, () -> SliceChecks.verify((s, from, to) -> {
            int size = s.codePointCount(0, s.length());
            if (from < 0 || to < from || to > size) throw new IndexOutOfBoundsException();
            return correct(s, from, to < size ? to + 1 : to);
        }));
    }

    @Test void rejectsEmptyRangeFailure() {
        assertThrows(AssertionError.class, () -> SliceChecks.verify((s, from, to) -> {
            if (from == to) throw new IllegalArgumentException("Пустой диапазон");
            return correct(s, from, to);
        }));
    }
    @Test void checksAtLeastOneInvalidRange() {
        int[] invalidCalls = {0};
        SliceChecks.verify((text, from, to) -> {
            int[] points = text.codePoints().toArray();
            if (from < 0 || to < from || to > points.length) {
                invalidCalls[0]++;
                throw new IndexOutOfBoundsException();
            }
            return new String(points, from, to - from);
        });
        assertTrue(invalidCalls[0] > 0, "Добавьте проверку недопустимого диапазона");
    }

}
