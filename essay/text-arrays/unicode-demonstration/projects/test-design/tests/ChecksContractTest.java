import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChecksContractTest {
    @Test void acceptsIndependentCorrectImplementation() {
        assertDoesNotThrow(() -> SliceChecks.verify((text, from, to) -> {
            int[] points = text.codePoints().toArray();
            if (from < 0 || to < from || to > points.length)
                throw new IndexOutOfBoundsException();
            return new String(points, from, to - from);
        }));
    }
}
