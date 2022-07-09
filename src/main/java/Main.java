import org.apache.commons.io.FileUtils;
import sun.nio.cs.UTF_8;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Random;

public class Main {

    private static final String WORDS_FILE_NAME = "words.txt";

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Hangman!");

        String word = getRandomWordFromFile();
        Game game = new Game(word);

        while (!game.isOver()) {
            game.printUserView();

            game.guess();
        }

        game.printUserView();

        if (game.isWin()) {
            System.out.println("You won! Great job!");
        } else {
            System.out.println("You lost. Better luck next time...");
        }

    }

    private static String getRandomWordFromFile() throws IOException {
        URL url = Main.class.getResource(WORDS_FILE_NAME);
        File wordsFile = new File(url.getPath());
        String wordsFileText = FileUtils.readFileToString(wordsFile, Charset.defaultCharset());

        String[] words = wordsFileText.split("\r\n");
        Random rand = new Random();
        int randInt = rand.nextInt(words.length);

        return words[randInt];
    }
}
