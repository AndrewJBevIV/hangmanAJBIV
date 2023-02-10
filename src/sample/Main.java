package sample;


import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Locale;

public class HelloApplication extends Application {
    private String wordToGuess = "Washington";
    private SimpleIntegerProperty numberOfStrikes = new SimpleIntegerProperty(this, "numberOfStrikes", -1){
        public String toString() {return String.valueOf(Math.max(get(), 0));}
    };
    private Label guessedWordLabel = new Label();
    private Label numberOfGuessesLabel = new Label();
    private TextField guessTextBox = new TextField();
    private ImageView stickmanImage = new ImageView();

    @Override
    public void start(Stage primaryStage) {
        guessedWordLabel.setText(hideWord(wordToGuess));
        numberOfGuessesLabel.setText("Number of strikes: " + numberOfStrikes.toString());
        //stickmanImage.setImage(new Image("/images/stickman0.png"));
        Button guessButton = new Button("Guess");
        guessButton.setOnAction(e -> {
            String guessedLetter = guessTextBox.getText(); // replace this with input from user
            if (wordToGuess.toLowerCase(Locale.ROOT).contains(guessedLetter.toLowerCase(Locale.ROOT))) {
                guessedWordLabel.setText(showLetter(guessedLetter, wordToGuess, guessedWordLabel.getText()));
            } else {
                numberOfStrikes.set(numberOfStrikes.get() + 1);
                numberOfGuessesLabel.setText("Number of strikes: " + numberOfStrikes.get());
                //stickmanImage.setImage(new Image("/images/stickman" + numberOfGuesses + ".png"));
            }
        });

        HBox guessBox = new HBox(10, new Label("Guess a letter: "), guessTextBox, guessButton);
        guessBox.setPadding(new Insets(10));

        Group stickMan = drawStickMan(150,100,30);
        VBox root = new VBox(10, stickMan,guessedWordLabel, numberOfGuessesLabel, guessBox);
        numberOfStrikes.addListener(observable -> {
            for (int i=0; i<stickMan.getChildren().size(); i++) {
                stickMan.getChildren().get(i).setVisible(i<numberOfStrikes.get());
            }
        });
        numberOfStrikes.set(0);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root);
        root.prefHeightProperty().bind(scene.heightProperty());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hangman"); 1
        primaryStage.show();
    }

    private Group drawStickMan(double x0, double y0, double l0) {
        Group root = new Group();

        // Head
        Circle head = new Circle(x0, y0, l0);
        root.getChildren().add(head);

        // Body
        Line body = new Line(x0, y0+l0, x0, y0+3*l0);
        root.getChildren().add(body);

        // Arms
        Line arm1 = new Line(x0, y0+3/2*l0, x0-l0, y0+2*l0);
        Line arm2 = new Line(x0, y0+3/2*l0, x0+l0, y0+2*l0);
        root.getChildren().add(arm1);
        root.getChildren().add(arm2);

        // Legs
        Line leg1 = new Line(x0, y0+3*l0, x0-l0, y0+4*l0);
        Line leg2 = new Line(x0, y0+3*l0, x0+l0, y0+4*l0);
        root.getChildren().add(leg1);
        root.getChildren().add(leg2);

        return root;
    }

    private String hideWord(String word) {
        StringBuilder hiddenWord = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            hiddenWord.append("_");
        }
        return hiddenWord.toString();
    }

    private String showLetter(String letter, String word, String currentGuessedWord) {
        StringBuilder newGuessedWord = new StringBuilder(currentGuessedWord);
        for (int i = 0; i < word.length(); i++) {
            if (word.toLowerCase(Locale.ROOT).charAt(i) == letter.toLowerCase(Locale.ROOT).charAt(0)) {
                newGuessedWord.setCharAt(i, word.charAt(i));
            }
        }
        return newGuessedWord.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }
}