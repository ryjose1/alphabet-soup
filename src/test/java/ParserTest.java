import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserTest {
    private final Path TEST_CASES_PATH = Paths.get("src", "test", "resources", "ParserTests");

    @Test
    public void ParseInput_OneCellInput_MatchesExpected() {
        String input = TEST_CASES_PATH.resolve("OneCellInput.txt").toFile().getAbsolutePath();
        String[] expectedSize = new String[] {"1","1"};
        String[] expectedChars = new String[] {"A"};
        String[] expectedWords = new String[] {"A"};

        List<List<String>> expected = new ArrayList<>();
        expected.add(Arrays.asList(expectedSize));
        expected.add(Arrays.asList(expectedChars));
        expected.add(Arrays.asList(expectedWords));

        List<List<String>> actual = Parser.parseInput(input);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void ParseInput_RectangularInput_MatchesExpected() {
        String input = TEST_CASES_PATH.resolve("RectangularInput.txt").toFile().getAbsolutePath();
        String[] expectedSize = new String[] {"3","4"};
        String[] expectedChars = new String[] {
                "A", "B", "C", "D",
                "E", "F", "G", "H",
                "I", "J", "K", "L"
        };
        String[] expectedWords = new String[] {"AFK", "DHL", "LKJI"};

        List<List<String>> expected = new ArrayList<>();
        expected.add(Arrays.asList(expectedSize));
        expected.add(Arrays.asList(expectedChars));
        expected.add(Arrays.asList(expectedWords));

        List<List<String>> actual = Parser.parseInput(input);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void ParseInput_SquareInput_MatchesExpected() {
        String input = TEST_CASES_PATH.resolve("SquareInput.txt").toFile().getAbsolutePath();
        String[] expectedSize = new String[] {"3","3"};
        String[] expectedChars = new String[] {
                "A", "B", "C",
                "D", "E", "F",
                "G", "H", "I"
        };
        String[] expectedWords = new String[] {"AEI", "DEF", "IFC"};

        List<List<String>> expected = new ArrayList<>();
        expected.add(Arrays.asList(expectedSize));
        expected.add(Arrays.asList(expectedChars));
        expected.add(Arrays.asList(expectedWords));

        List<List<String>> actual = Parser.parseInput(input);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void ParseInput_WhitespacedInput_KeepsSpacing() {
        String input = TEST_CASES_PATH.resolve("WhitespacedInput.txt").toFile().getAbsolutePath();
        String[] expectedSize = new String[] {"4","4"};
        String[] expectedChars = new String[] {
                "B", "A", "L", "I",
                "D", "O", "O", "F",
                "G", "I", "R", "M",
                "E", "X", "E", "P"
        };
        String[] expectedWords = new String[] {"FO OD", "LOR E", "R I G"};

        List<List<String>> expected = new ArrayList<>();
        expected.add(Arrays.asList(expectedSize));
        expected.add(Arrays.asList(expectedChars));
        expected.add(Arrays.asList(expectedWords));

        List<List<String>> actual = Parser.parseInput(input);
        Assert.assertEquals(expected,actual);
    }
}
