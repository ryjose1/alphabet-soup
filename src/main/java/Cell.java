import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cell {
    private int rowPos;
    private int colPos;
    private String letter;
    private Map<String, Cell> adjacentCells;

    public Cell() {
        this.rowPos = 0;
        this.colPos = 0;
        this.letter = "";
        this.adjacentCells = new HashMap<String, Cell>();
    }

    public Cell(int row, int col, String letter) {
        this.rowPos = row;
        this.colPos = col;
        this.letter = letter;
        this.adjacentCells = new HashMap<String, Cell>();
    }

    public int getRowPos(){
        return this.rowPos;
    }

    public int getColPos(){
        return this.colPos;
    }

    public String getLetter() {
        return this.letter;
    }

    public void addAdjacentCell(String direction, Cell cell) {
        if (!this.adjacentCells.containsKey(direction)) {
            this.adjacentCells.put(direction, cell);
        }
    }

    public Cell getAdjacentCell(String direction) {
        if (this.adjacentCells.containsKey(direction)) {
            return this.adjacentCells.get(direction);
        } else {
            return null;
        }
    }

    public List<String> isAdjacentTo(String character) {
        List<String> adjacentDirections = new ArrayList<>();
        this.adjacentCells.forEach((direction, cell) -> {
            if (cell.getLetter().equals(character)) {
                adjacentDirections.add(direction);
            }
        });
        return adjacentDirections;
    }

    public String printPosition(){
        return String.join(":", Integer.toString(this.getRowPos()), Integer.toString(this.getColPos()));
    }

    public static boolean matchesString(Cell c, String str, String direction) {
        if (c == null) {
            return false;
        }
        if (str.length() == 1) {
            return c.getLetter().equals(str);
        } else if (c.getLetter().equals(Character.toString(str.charAt(0)))) {
            return matchesString(c.getAdjacentCell(direction), str.substring(1), direction);
        } else {
            return false;
        }
    }

    public static Cell getInlineCell(Cell c, String direction, int distance) {
        // i = 0 is the passed in c, so start at 1
        for (int i = 1; i < distance; i++) {
            c = c.getAdjacentCell(direction);
        }
        return c;
    }
}
