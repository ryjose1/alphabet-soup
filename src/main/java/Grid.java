import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid {
    public enum Direction {
        VERT_UP(0, -1, "N"),
        DIAG_UP_RIGHT(1, -1, "NE"),
        HORIZ_RIGHT(1, 0, "E"),
        DIAG_DOWN_RIGHT(1, 1, "SE"),
        VERT_DOWN(0, 1, "S"),
        DIAG_DOWN_LEFT(-1, 1, "SW"),
        HORIZ_LEFT(-1, 0, "W"),
        DIAG_UP_LEFT(-1, -1, "NW");


        private final int dx;
        private final int dy;
        private final String direction;

        Direction(int dx, int dy, String direction) {
            this.dx = dx;
            this.dy = dy;
            this.direction = direction;
        }

        public String getDirection(){
            return this.direction;
        }
    }

    private final int rows;
    private final int cols;
    private List<List<Cell>> grid;
    private Map<String, List<Cell>> locationMap;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new ArrayList<List<Cell>>();
        this.locationMap = new HashMap<String, List<Cell>>();
    }

    public Grid(int rows, int cols, List<String> characters) {
        this(rows, cols);

        if (!isValidGridInput(rows, cols, characters)) {
            return;
        }

        // Fill out the data structures with the passed in characters
        for (int i = 0; i < rows; i++) {
            List<Cell> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                String letter = characters.get(i * cols + j);
                Cell c = new Cell(i, j, letter);

                // Add cell to the 2D array
                row.add(c);
                // Add cell to the lookup map
                if (this.locationMap.containsKey(letter)) {
                    this.locationMap.get(letter).add(c);
                } else {
                    List<Cell> bucket = new ArrayList<>();
                    bucket.add(c);
                    this.locationMap.put(letter, bucket);
                }
            }
            this.grid.add(row);
        }
    }

    private boolean isValidGridInput(int rows, int cols, List<String> characters) {
        if (rows < 0 || cols < 0) {
            return false;
        } else if (characters.size() != rows * cols) {
            return false;
        } else {
            return true;
        }
    }

    // One-time up-front processing to fill out each cell's adjacent cells table
    // Could speed up with parallel processing
    public void processGrid() {
        for (List<Cell> row : this.grid) {
            for (Cell c : row) {
                addAdjacentCells(c);
            }
        }
    }

    private void addAdjacentCells(Cell c) {
        for (Direction d: Direction.values()) {
            int adjRow = c.getRowPos() + d.dy;
            int adjCol = c.getColPos() + d.dx;
            if (inBounds(adjRow, adjCol)) {
                c.addAdjacentCellIfAbsent(d.direction, this.getCell(adjRow, adjCol));
            }
        }
    }

    private boolean inBounds(int rowPos, int colPos) {
        return rowPos >= 0 && rowPos < this.rows && colPos >= 0 && colPos < this.cols;
    }

    public Cell getCell(int row, int col) {
        if (!inBounds(row, col)) {
            return null;
        }

        return this.grid.get(row).get(col);
    }

    public String findWord(String word) {
        if (!word.matches(Parser.DISPLAYABLE_WORD_REGEX_PATTERN)) {
            return "";
        }

        // Transform human-readable terms into searchable terms
        String searchTerm = word.replace(" ", "");

        Cell start = null;
        Cell end = null;

        // Check all positions that match the starting letter for the word
        // Exit early if the word is found
        String firstLetter = Character.toString(searchTerm.charAt(0));
        for (Cell c : locationMap.get(firstLetter)) {
            start = c;
            if (searchTerm.length() == 1) {
                end = c;
                break;
            }

            String nextLetter = Character.toString(searchTerm.substring(1).charAt(0));
            List<String> searchDirections = start.isAdjacentTo(nextLetter);
            for (String direction : searchDirections) {
                if (Cell.matchesString(start, searchTerm, direction)) {
                    end = Cell.getInlineCell(start, direction, searchTerm.length());
                    break;
                }
            }

            if (end != null) {
                break;
            }
        }

        // If end position is not found, return empty string
        if (start != null && end != null) {
            return formatOutput(word, start, end);
        } else {
            return "";
        }

    }

    private String formatOutput (String word, Cell start, Cell end){
        return String.join(" ", word, start.printPosition(), end.printPosition());
    }
}
