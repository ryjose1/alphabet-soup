import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    public static final String SIZE_LINE_REGEX_PATTERN = "^(\\d{1,})[x](\\d{1,})$";
    public static final String GRID_ENTRY_REGEX_PATTERN = "^[A-Z]$";
    public static final String DISPLAYABLE_WORD_REGEX_PATTERN = "^[A-Z\\s]+$";

    public static List<List<String>> parseInput(String filename) {
        if (!Files.isRegularFile(Paths.get(filename))){
            return new ArrayList<>();
        }

        List<List<String>> parsedInput = new ArrayList<>();

        List<String> dimensions = new ArrayList<>();
        List<String> gridLines = new ArrayList<>();
        List<String> wordLines = new ArrayList<>();
        int rows = 0;
        int cols = 0;

        try (BufferedReader in = Files.newBufferedReader(Paths.get(filename))) {
            // The first line specifies the size of the grid
            String sizeLine = in.readLine();
            if (sizeLine != null) {
                dimensions = parseSizeLine(sizeLine);
                rows = Integer.parseInt(dimensions.get(0));
                cols = Integer.parseInt(dimensions.get(1));
            }

            // The character grid is represented by a number of lines equal to the rows in the first line
            for (int i = 0; i < rows; i++) {
                String line = in.readLine();
                if (line != null) {
                    gridLines.add(line);
                }
            }

            // The remaining lines are the words to find in the grid
            wordLines = in.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Only return results on valid input
        if (dimensions.isEmpty() || gridLines.isEmpty() || wordLines.isEmpty()) {
            return new ArrayList<>();
        }
        parsedInput.add(dimensions);
        parsedInput.add(parseGridLines(gridLines, rows, cols));
        parsedInput.add(parseWordLines(wordLines));

        return parsedInput;
    }

    private static List<String> parseSizeLine(String line) {
        // Validation for checking rows/cols are ints, that there's only 2 entries, and that the delimiter is 'x'
        if (!line.matches(SIZE_LINE_REGEX_PATTERN)) {
            return new ArrayList<>();
        }

        String[] dimensions = line.split("x");
        return Arrays.asList(dimensions);
    }

    private static List<String> parseGridLines(List<String> lines, int rows, int cols) {
        List<String> characters = new ArrayList<>();
        if (lines.size() == rows) {
            for (String line: lines) {
                List<String> lineChars = Arrays.asList(line.split(" ")).stream()
                        .filter(s -> s.matches(GRID_ENTRY_REGEX_PATTERN))
                        .collect(Collectors.toList());
                if (lineChars.size() == cols) {
                    characters.addAll(lineChars.subList(0, cols));
                }

            }
        }

        // Grid is only successful if the number of characters match the size
        if (characters.size() == rows * cols) {
            return characters;
        } else {
            return new ArrayList<>();
        }

    }

    private static List<String> parseWordLines(List<String> lines) {
        // Adding extra enforcement that the words are upper-case, and all letters
        return lines.stream()
                .map(s -> s.toUpperCase())
                .filter(s -> s.matches(DISPLAYABLE_WORD_REGEX_PATTERN))
                .collect(Collectors.toList());
    }
}
