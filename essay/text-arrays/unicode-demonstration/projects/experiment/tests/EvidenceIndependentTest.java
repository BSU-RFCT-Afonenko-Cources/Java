import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvidenceIndependentTest {
    @Test void observationsAgreeWithCodePointArray() {
        for (var example : UnicodeEvidence.examples()) {
            int[] points = example.text().codePoints().toArray();
            assertEquals(points.length, example.codePoints());
            assertEquals(new String(points, 0, 1), example.firstCodePoint());
        }
    }
}
