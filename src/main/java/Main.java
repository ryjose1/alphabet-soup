import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || !Files.isRegularFile(Paths.get(args[0]))){
            printUsage();
            System.exit(1);
        }

        // Transform the input text file into expected format
        List<List<String>> inputSections = Parser.parseInput(args[0]);
        List<String> gridDimensions = inputSections.get(0);
        List<String> gridCharacters = inputSections.get(1);
        List<String> embeddedWords = inputSections.get(2);

        // Set up the game object
        int rows = Integer.parseInt(gridDimensions.get(0));
        int cols = Integer.parseInt(gridDimensions.get(1));
        Game g = new Game(rows, cols, gridCharacters, embeddedWords);

        // Print out the answers
        for (String answer: g.getAnswerKey()) {
            System.out.println(answer);
        }
        System.exit(0);
    }

    private static void printUsage() {
        System.out.println("Expected Usage: java -jar /path/to/alphabet-soup.jar /path/to/input/file.txt");
    }
}