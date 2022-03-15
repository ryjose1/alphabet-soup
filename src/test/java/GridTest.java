import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GridTest {

    @Test
    public void ProcessCells_Basic2x2_LinksCellsCorrectly() {
        // The test grid
        String[] characters = new String[] {
                "A", "B",
                "C", "D"
        };

        // Set up the expected adjacent values
        Map<String, Map<String, String>> expected = new HashMap<>();

        Map<String, String> expectedA = new HashMap<>();
        expectedA.put(Grid.Direction.HORIZ_RIGHT.getDirection(), "B");
        expectedA.put(Grid.Direction.DIAG_DOWN_RIGHT.getDirection(), "D");
        expectedA.put(Grid.Direction.VERT_DOWN.getDirection(), "C");

        Map<String, String> expectedD = new HashMap<>();
        expectedD.put(Grid.Direction.HORIZ_LEFT.getDirection(), "C");
        expectedD.put(Grid.Direction.DIAG_UP_LEFT.getDirection(), "A");
        expectedD.put(Grid.Direction.VERT_UP.getDirection(), "B");

        expected.put("A", expectedA);
        expected.put("D", expectedD);

        // Set up the actual adjacent values
        int rows = 2;
        int cols = 2;
        Grid g = new Grid(rows, cols, Arrays.asList(characters));
        g.processGrid();

        // Compare the adjacent values for the cells with expected values
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell c = g.getCell(i, j);
                Map<String, String> expectedMap = expected.get(c.getLetter());
                if (expectedMap != null) {
                    expectedMap.forEach((direction, value) -> {
                        String actual = c.getAdjacentCell(direction).getLetter();
                        Assert.assertEquals(value, actual);
                    });
                }
            }
        }
    }

    @Test
    public void FindWord_WordIsInGrid() {
        String expected = "FED 1:2 1:0";
        // The test grid
        String[] characters = new String[] {
                "A", "B", "C",
                "D", "E", "F",
                "G", "H", "I"
        };

        int rows = 3;
        int cols = 3;
        Grid g = new Grid(rows, cols, Arrays.asList(characters));
        g.processGrid();

        String actual = g.findWord("FED");
        Assert.assertEquals(expected, actual);
    }
}
