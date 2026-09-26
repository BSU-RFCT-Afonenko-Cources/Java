import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvidenceExamplesTest {
    @Test void returnsExactlyTwoExamples() {
        assertEquals(2, UnicodeEvidence.examples().length);
    }

    @Test void reportedMeasurementsMatchTheTexts() {
        for (var example : UnicodeEvidence.examples()) {
            assertNotNull(example);
            String text = example.text();
            assertNotNull(text);
            assertFalse(text.isEmpty());
            assertEquals(text.length(), example.utf16Units());
            assertEquals(text.codePointCount(0, text.length()), example.codePoints());
            assertEquals(text.substring(0, text.offsetByCodePoints(0, 1)),
                         example.firstCodePoint());
        }
    }
}
