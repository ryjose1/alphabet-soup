import java.util.ArrayList;
import java.util.List;

public class Game {
    private Grid grid;
    private List<String> words;

    public Game() {
        this.grid = new Grid(0, 0);
        this.words = new ArrayList<String>();
    }

    public Game(int rows, int cols, List<String> characters, List<String> words) {
        this.grid = new Grid(rows, cols, characters);
        this.words = words;

        this.grid.processGrid();
    }

    public List<String> getAnswerKey() {
        List<String> answers = new ArrayList<>();
        for (String word: this.words) {
            answers.add(this.grid.findWord(word));
        }
        return answers;
    }
}
