// Oscar Avina
// 08/16/2026
// Module 1.3 Assignment
// This program displays four random playing cards and uses a button to display new cards.

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Avina_Mod1_3 extends Application {

    @Override
    public void start(Stage primaryStage) {
        // creates a row to hold the four card images
        HBox cardDisplay = new HBox(10);

        Button refreshButton = new Button("Refresh");

        // uses a lambda expression to display new cards when the button is clicked
        refreshButton.setOnAction(event -> displayRandomCards(cardDisplay));

        // places the card row above the Refresh button
        VBox rootPane = new VBox(10, cardDisplay, refreshButton);

        // displays four cards when the program first opens
        displayRandomCards(cardDisplay);

        Scene scene = new Scene(rootPane);
        primaryStage.setTitle("Random Playing Cards");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void displayRandomCards(HBox cardDisplay) {
        // clears the current cards before adding the next four
        cardDisplay.getChildren().clear();

        // stores the numbers of the cards that are currently displayed
        ArrayList<Integer> displayedCardNumbers = new ArrayList<Integer>();

        // continues choosing random numbers until four different cards are selected
        while (displayedCardNumbers.size() < 4) {
            int randomCardNumber = (int)(Math.random() * 52) + 1;

            // makes sure the same card is not selected twice
            if (!displayedCardNumbers.contains(randomCardNumber)) {
                displayedCardNumbers.add(randomCardNumber);
                Image cardImage = new Image("file:cards/" + randomCardNumber + ".png");
                ImageView cardImageView = new ImageView(cardImage);
                cardDisplay.getChildren().add(cardImageView);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}