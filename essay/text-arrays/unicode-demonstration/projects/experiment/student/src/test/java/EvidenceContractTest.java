import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvidenceContractTest {
    @Test void supplementalExampleStartsWithSupplementaryCodePoint() {
        var first = UnicodeEvidence.examples()[0];
        assertTrue(first.text().codePointAt(0) >= Character.MIN_SUPPLEMENTARY_CODE_POINT);
        assertTrue(first.utf16Units() > first.codePoints());
        assertEquals(2, first.firstCodePoint().length());
    }

    @Test void controlContainsOnlyPrintableAscii() {
        String control = UnicodeEvidence.examples()[1].text();
        assertFalse(control.isEmpty());
        assertTrue(control.chars().allMatch(c -> c >= 0x20 && c <= 0x7e));
    }

    @Test void allTextsContainOnlyPairedSurrogates() {
        for (var example : UnicodeEvidence.examples()) {
            String s = example.text();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (Character.isHighSurrogate(c)) {
                    assertTrue(i + 1 < s.length());
                    assertTrue(Character.isLowSurrogate(s.charAt(++i)));
                } else {
                    assertFalse(Character.isLowSurrogate(c));
                }
            }
        }
    }
}
