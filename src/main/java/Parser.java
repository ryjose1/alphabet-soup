import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    public static List<List<String>> parseInput(String filename) {
        List<List<String>> parsedInput = new ArrayList<>();

        List<String> dimensions = new ArrayList<>();
        List<String> gridLines = new ArrayList<>();
        List<String> wordLines = new ArrayList<>();
        int rows = 0;
        int cols = 0;

        try {
            BufferedReader in = Files.newBufferedReader(Paths.get(filename));

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

        parsedInput.add(dimensions);
        parsedInput.add(parseGridLines(gridLines, rows, cols));
        parsedInput.add(parseWordLines(wordLines));

        return parsedInput;
    }

    private static List<String> parseSizeLine(String line) {
        String[] dimensions = line.split("x");
        // Would add more validation here for checking rows/cols are ints, and that there's only 2 entries
        return Arrays.asList(dimensions);
    }

    private static List<String> parseGridLines(List<String> lines, int rows, int cols) {
        List<String> characters = new ArrayList<>();

        // Doing minimal row/column validation to add confidence that size line is accurate
        // Would add validation for grids that are smaller than specified size
        if (lines.size() == rows) {
            for (String line: lines) {
                List<String> lineChars = Arrays.asList(line.split(" "));
                characters.addAll(lineChars.subList(0, cols));
            }
        }
        return characters;
    }

    private static List<String> parseWordLines(List<String> lines) {
        // Adding extra enforcement that the words are upper-case
        return lines.stream()
                .map(s -> s.toUpperCase())
                .collect(Collectors.toList());
    }
}
