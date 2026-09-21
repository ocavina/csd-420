// Oscar Avina
// 09/20/2026
// Module 8.2 Assignment
// This program uses three threads to display random letters, digits, and symbols and tests the output.

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Avina_Mod8_2 extends Application {

    // sets the number of characters for each thread and the characters it can use
    private static final int CHARACTERS_PER_THREAD = 10000;
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%&*";

    private final TextArea outputArea = new TextArea();
    private int completedThreads = 0;

    @Override
    public void start(Stage primaryStage) {
        // wraps the output in the text area and prevents the user from changing it
        outputArea.setEditable(false);
        outputArea.setWrapText(true);

        // sets the window title and displays the text area
        primaryStage.setTitle("Oscar Three Threads");
        primaryStage.setScene(new Scene(outputArea, 600, 400));
        primaryStage.show();

        // creates a separate thread for letters, digits, and symbols
        Thread letterThread = new Thread(new CharacterTask(LETTERS));
        Thread digitThread = new Thread(new CharacterTask(DIGITS));
        Thread symbolThread = new Thread(new CharacterTask(SYMBOLS));

        // starts all three threads so their character output can mix together
        letterThread.start();
        digitThread.start();
        symbolThread.start();
    }

    // uses the same task to generate characters from each thread's assigned group
    private class CharacterTask implements Runnable {
        private final String allowedCharacters;

        public CharacterTask(String allowedCharacters) {
            // stores the characters this task can choose from
            this.allowedCharacters = allowedCharacters;
        }

        @Override
        public void run() {
            try {
                // generates 10,000 characters for this thread
                for (int count = 0; count < CHARACTERS_PER_THREAD; count++) {
                    // chooses a random position from the allowed characters
                    int randomIndex =
                            (int) (Math.random() * allowedCharacters.length());
                    char character = allowedCharacters.charAt(randomIndex);

                    // schedules one character to be added
                    Platform.runLater(() ->
                            outputArea.appendText(String.valueOf(character)));

                    // pauses briefly between characters to pace the output
                    Thread.sleep(1);
                }
            } catch (InterruptedException exception) {
                // restores the interrupt flag after leaving the generation loop
                Thread.currentThread().interrupt();
            }

            // counts this task as finished after Java handles its character updates
            Platform.runLater(() -> {
                completedThreads++;
                // checks the displayed text after all three tasks have finished
                if (completedThreads == 3) {
                    testOutput(outputArea.getText());
                }
            });
        }
    }

    // checks the character counts and whether the output is mixed
    private void testOutput(String text) {
        int letterCount = 0;
        int digitCount = 0;
        int symbolCount = 0;
        int unexpectedCount = 0;
        int typeChanges = 0;
        char previousType = ' ';

        for (int index = 0; index < text.length(); index++) {
            char character = text.charAt(index);
            char currentType;

            // counts each character in its group and records any unexpected characters
            if (LETTERS.indexOf(character) >= 0) {
                letterCount++;
                currentType = 'L';
            } else if (DIGITS.indexOf(character) >= 0) {
                digitCount++;
                currentType = 'D';
            } else if (SYMBOLS.indexOf(character) >= 0) {
                symbolCount++;
                currentType = 'S';
            } else {
                unexpectedCount++;
                currentType = '?';
            }

            // counts changes between letters, digits, and symbols
            if (index > 0 && currentType != previousType) {
                typeChanges++;
            }
            previousType = currentType;
        }

        // checks for more than the two type changes that three separate groups would produce
        boolean mixedOutput = typeChanges > 2;
        // confirms that each group has 10,000 characters and the total is 30,000
        boolean correctCounts = letterCount == CHARACTERS_PER_THREAD
                && digitCount == CHARACTERS_PER_THREAD
                && symbolCount == CHARACTERS_PER_THREAD
                && unexpectedCount == 0
                && text.length() == CHARACTERS_PER_THREAD * 3;

        // displays the character counts and mixing result in the console
        System.out.println("Letters: " + letterCount);
        System.out.println("Digits: " + digitCount);
        System.out.println("Symbols: " + symbolCount);
        System.out.println("Unexpected characters: " + unexpectedCount);
        System.out.println("Total characters: " + text.length());
        System.out.println("Mixed output: " + mixedOutput);

        // displays whether both the character counts and mixed output pass the tests
        if (correctCounts && mixedOutput) {
            System.out.println("All output tests: PASS");
        } else {
            System.out.println("Output tests: FAIL");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
