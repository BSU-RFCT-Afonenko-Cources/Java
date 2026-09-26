import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SliceContractTest {
    @Test void allRangesForBoundaryExamples() {
        for (String text : new String[] {"", "ABC", "\uD83D\uDE80", "A\uD83D\uDE80B",
                "\uD800\uDC00\uDBFF\uDFFF", "e\u0301", "\u0000\uFFFF"}) {
            checkAllRanges(text);
        }
    }

    @Test void allRangesForDeterministicRandomStrings() {
        Random random = new Random(20260926L);
        int[] alphabet = {0, 0x41, 0x301, 0x7ff, 0xd7ff, 0xe000, 0xffff, 0x10000, 0x1f680, 0x10ffff};
        for (int sample = 0; sample < 40; sample++) {
            StringBuilder text = new StringBuilder();
            int size = random.nextInt(12);
            for (int i = 0; i < size; i++) text.appendCodePoint(alphabet[random.nextInt(alphabet.length)]);
            checkAllRanges(text.toString());
        }
    }

    private static void checkAllRanges(String text) {
        int[] points = text.codePoints().toArray();
        for (int from = 0; from <= points.length; from++) {
            for (int to = from; to <= points.length; to++) {
                assertEquals(new String(points, from, to - from), UnicodeSlice.slice(text, from, to));
            }
        }
    }

}
