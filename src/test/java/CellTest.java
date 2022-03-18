import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CellTest {
    @Test
    public void IsAdjacentTo_ReturnsSingleMatch() {
        List<String> expected = Arrays.asList(new String[] {"RIGHT"});

        Cell c = new Cell(1, 1, "C");

        c.addAdjacentCellIfAbsent("RIGHT", new Cell(1, 2, "D"));
        c.addAdjacentCellIfAbsent("DOWN", new Cell(2, 1, "E"));

        List<String> actual = c.isAdjacentTo("D");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void IsAdjacentTo_ReturnsMultiMatch() {
        List<String> expected = Arrays.asList(new String[] {"RIGHT", "LEFT"});

        Cell c = new Cell(1, 1, "C");

        c.addAdjacentCellIfAbsent("RIGHT", new Cell(1, 2, "D"));
        c.addAdjacentCellIfAbsent("LEFT", new Cell(1, 2, "D"));
        c.addAdjacentCellIfAbsent("DOWN", new Cell(2, 1, "E"));

        List<String> actual = c.isAdjacentTo("D");

        Assert.assertEquals(expected.size(), actual.size());
        Assert.assertTrue(actual.containsAll(expected));
    }

    @Test
    public void PrintPosition_BasicConstructor_ReturnsOrigin() {
        String expected = "0:0";
        Cell c = new Cell();
        Assert.assertEquals(expected, c.printPosition());
    }

    @Test
    public void PrintPosition_Constructor_ReturnsPosition() {
        String expected = "1:2";
        Cell c = new Cell(1,2, "A'");
        Assert.assertEquals(expected, c.printPosition());
    }

    @Test
    public void MatchesString_ValidMatch() {
        Cell nodeA = new Cell(1, 1, "F");
        Cell nodeB = new Cell(1, 2, "O");
        Cell nodeC = new Cell(1, 3, "O");
        Cell nodeD = new Cell(1, 4, "B");
        Cell nodeE = new Cell(1, 5, "A");
        Cell nodeF = new Cell(1, 6, "R");

        nodeA.addAdjacentCellIfAbsent("RIGHT", nodeB);
        nodeB.addAdjacentCellIfAbsent("RIGHT", nodeC);
        nodeC.addAdjacentCellIfAbsent("RIGHT", nodeD);
        nodeD.addAdjacentCellIfAbsent("RIGHT", nodeE);
        nodeE.addAdjacentCellIfAbsent("RIGHT", nodeF);

        Assert.assertTrue(Cell.matchesString(nodeA, "FOOBAR", "RIGHT"));
        Assert.assertFalse(Cell.matchesString(nodeA, "FOOBAR", "DOWN"));
    }

    @Test
    public void MatchesString_DoesNotMatch() {
        Cell nodeA = new Cell(1, 1, "F");
        Cell nodeB = new Cell(1, 2, "O");
        Cell nodeC = new Cell(1, 3, "O");

        nodeA.addAdjacentCellIfAbsent("RIGHT", nodeB);
        nodeB.addAdjacentCellIfAbsent("RIGHT", nodeC);

        Assert.assertFalse(Cell.matchesString(nodeA, "FOOBAR", "RIGHT"));
    }

    @Test
    public void GetInlineCell_ReturnsEndCell() {
        String expected = "3:3";

        Cell c = new Cell(1, 1, "C");
        Cell d = new Cell(2, 2, "D");
        Cell e = new Cell(3, 3, "E");

        c.addAdjacentCellIfAbsent("SW", d);
        d.addAdjacentCellIfAbsent("SW", e);

        String actual = Cell.getInlineCell(c, "SW", 3).printPosition();

        Assert.assertEquals(expected, actual);
    }
}
