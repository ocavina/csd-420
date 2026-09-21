// Oscar Avina
// 09/20/2026
// Module 7.2 Assignment
// This program displays four circles using an external CSS file and tests their colors.

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Avina_Mod7_2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        // creates four circles with a radius of 30 pixels
        Circle firstWhiteCircle = new Circle(30);
        Circle secondWhiteCircle = new Circle(30);
        Circle redCircle = new Circle(30);
        Circle greenCircle = new Circle(30);

        // uses the same style class for both white circles
        firstWhiteCircle.getStyleClass().add("plaincircle");
        secondWhiteCircle.getStyleClass().add("plaincircle");

        // uses separate IDs to apply the red and green styles
        redCircle.setId("redcircle");
        greenCircle.setId("greencircle");

        // places the first circle inside a pane with a black border
        StackPane borderedPane = new StackPane();
        borderedPane.getChildren().add(firstWhiteCircle);
        borderedPane.getStyleClass().add("border");

        // places the pane and remaining circles in a centered row with 5 pixels between them
        HBox circleRow = new HBox(5);
        circleRow.setAlignment(Pos.CENTER);
        circleRow.getChildren().addAll(
            borderedPane, secondWhiteCircle, redCircle, greenCircle);

        // loads the colors and border from the external CSS file
        Scene scene = new Scene(circleRow, 300, 250);
        scene.getStylesheets().add("mystyle.css");

        // sets the window title and displays the scene
        primaryStage.setTitle("M7.2 - Four Circles");
        primaryStage.setScene(scene);
        primaryStage.show();

        // checks each circle's fill and outline against the expected colors
        testCircle("First white circle", firstWhiteCircle, Color.WHITE, Color.BLACK);
        testCircle("Second white circle", secondWhiteCircle, Color.WHITE, Color.BLACK);
        testCircle("Red circle", redCircle, Color.RED, Color.RED);
        testCircle("Green circle", greenCircle, Color.GREEN, Color.GREEN);
    }

    // displays whether both colors match the expected values
    private void testCircle(String testName, Circle circle, Color expectedFill, Color expectedStroke) {
        if (expectedFill.equals(circle.getFill()) && expectedStroke.equals(circle.getStroke())) {
            System.out.println(testName + ": PASS");
        } else {
            System.out.println(testName + ": FAIL");
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}