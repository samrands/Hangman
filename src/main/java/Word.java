import org.apache.commons.lang3.StringUtils;

public class Word {

    private String wordToGuess;
    private String userView;
    private StringBuilder existingGuesses = new StringBuilder();

    public Word(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.userView = StringUtils.repeat('_', wordToGuess.length());
    }

    public String gameView() {
        return userView;
    }

    /*
    * guess
    * @parameter guess: guess string, user input, should be one character
    * @returns boolean: true if guess was in word, false if invalid or not in word
    * */
    public boolean guess(String guess) {
        if (guess == null || guess.length() != 1) {
            System.out.println("Please provide one character for your guess");
            return false;
        }

        if (wordToGuess.contains(guess)){
            existingGuesses.append(guess);

            // Below replaces all chars that match the regex with _
            // regex: brackets mean character class of all that don't match the guess
            // (so we can match on chars instead of the whole word)
            userView = wordToGuess.replaceAll(String.format("[^%s]", existingGuesses.toString()), "_");
            return true;
        } else {
            System.out.println("You guessed wrong, son!");
            return false;
        }
    }

    public boolean isGuessed() {
        return wordToGuess.equals(userView);
    }
}
