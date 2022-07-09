import java.util.Scanner;

public class Game {

    public static final int ATTEMPT_COUNT = 10;

    private Scanner userInput = new Scanner(System.in).useDelimiter("\n");
    private Word word;
    private int attempts;

    public Game(String wordToGuess) {
        this.word = new Word(wordToGuess);
    }

    /*
    * guess
    * @purpose: get guess from user, apply guess to word, give user gameView of word
    * @parameters: none
    * @returns: userView after applying guess to word
    * */
    public void guess() {
        String userGuess = getGuess();
        boolean isCorrect = word.guess(userGuess);

        if (!isCorrect) attempts++;
    }

    public void printUserView() {
        System.out.println(word.gameView());
    }

    public String getGuess() {
        System.out.println("Make your guess please!");
        return userInput.next();
    }

    public boolean isOver() {
        return isWin() || attempts == ATTEMPT_COUNT;
    }

    public boolean isWin() {
        return word.isGuessed();
    }
}
