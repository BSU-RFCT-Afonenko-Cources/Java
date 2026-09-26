import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SliceExamplesTest {
    @Test void asciiRangeHasExclusiveRightBoundary() {
        assertEquals("BC", UnicodeSlice.slice("ABCD", 1, 3));
    }
    @Test void supplementaryCodePointRemainsWhole() {
        assertEquals("\uD83D\uDE80", UnicodeSlice.slice("A\uD83D\uDE80B", 1, 2));
    }
    @Test void emptyInputAndEmptyRangesAreAccepted() {
        assertEquals("", UnicodeSlice.slice("", 0, 0));
        assertEquals("", UnicodeSlice.slice("AB", 1, 1));
    }
    @Test void invalidIndicesAlwaysFail() {
        for (int[] bounds : new int[][] {{-1, 0}, {0, -1}, {2, 1}, {0, 4}, {4, 4},
                                       {Integer.MIN_VALUE, 0}, {0, Integer.MAX_VALUE}}) {
            assertThrows(IndexOutOfBoundsException.class,
                () -> UnicodeSlice.slice("A\uD83D\uDE80B", bounds[0], bounds[1]));
        }
    }
}
