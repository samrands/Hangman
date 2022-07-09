import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class HangmanTest {

    private static final String WORD_TO_GUESS = "onomatopoeia";
    private Word word;
    private InputStream sysInBackup;
    private PrintStream sysOutBackup;

    @Before
    public void setUp() {
        word = new Word(WORD_TO_GUESS);
        sysInBackup = System.in;
        sysOutBackup = System.out;
    }

    @After
    public void tearDown() {
        System.setIn(sysInBackup);
        System.setOut(sysOutBackup);
    }

    @Test
    public void testPrintCorrectUnderscores() {
        String underScores = word.gameView();

        assertNotNull(underScores);
        assertTrue(underScores.length() == WORD_TO_GUESS.length());

        for (char c : underScores.toCharArray()) {
            if (c != '_') fail();
        }
    }

    @Test
    public void testGuessCharacter() {
        word.guess("o");

        String gameView = word.gameView();

        assertTrue(gameView.equals("o_o___o_o___"));
    }

    @Test
    public void testGetUserInput() {
        ByteArrayInputStream in = new ByteArrayInputStream("a".getBytes());
        System.setIn(in);
        Game gameWithMockedInput = new Game(WORD_TO_GUESS);

        String guess = gameWithMockedInput.getGuess();

        assertEquals(guess, "a");
    }

    @Test
    public void testGuessCharacterInActualWord() {
        ByteArrayInputStream in = new ByteArrayInputStream("o".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));

        Game gameWithMockedInput = new Game(WORD_TO_GUESS);
        gameWithMockedInput.guess();

        gameWithMockedInput.printUserView();
        String systemOut = StringUtils.trim(out.toString());
        String secondPart = systemOut.split("\n")[1];

        assertTrue(secondPart.equals("o_o___o_o___"));
    }

    @Test
    public void testUsersEventualDeath() {
        StringBuilder multiLineBStringBuilder = new StringBuilder("b");
        for (int i = 0; i < 100; i++) {
            multiLineBStringBuilder.append("b\n");
        }
        String multiLineBs = multiLineBStringBuilder.toString();
        ByteArrayInputStream in = new ByteArrayInputStream(multiLineBs.getBytes());
        System.setIn(in);

        Game gameWithMockedInput = new Game(WORD_TO_GUESS);

        for (int i = 0; i < Game.ATTEMPT_COUNT; i++) {
            gameWithMockedInput.guess();
        }

        assertTrue(gameWithMockedInput.isOver());
    }

}
