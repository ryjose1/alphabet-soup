import java.util.List;

public class Main {
    public static void main(String[] args) {
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
    }
}