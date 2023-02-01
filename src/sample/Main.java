package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Locale;

public class Main extends Application {
    private String wordToGuess = "Washington";
    private int numberOfGuesses = 0;
    private Label guessedWordLabel = new Label();
    private Label numberOfGuessesLabel = new Label();
    private TextField guessTextBox = new TextField();
    private ImageView stickmanImage = new ImageView();

    @Override
    public void start(Stage primaryStage) {
        guessedWordLabel.setText(hideWord(wordToGuess));
        numberOfGuessesLabel.setText("Number of strikes: " + numberOfGuesses);
        //stickmanImage.setImage(new Image("/images/stickman0.png"));
        Button guessButton = new Button("Guess");
        guessButton.setOnAction(e -> {
            String guessedLetter = guessTextBox.getText(); // replace this with input from user
            if (wordToGuess.toLowerCase(Locale.ROOT).contains(guessedLetter.toLowerCase(Locale.ROOT))) {
                guessedWordLabel.setText(showLetter(guessedLetter, wordToGuess, guessedWordLabel.getText()));
            } else {
                numberOfGuesses++;
                numberOfGuessesLabel.setText("Number of strikes: " + numberOfGuesses);
                //stickmanImage.setImage(new Image("/images/stickman" + numberOfGuesses + ".png"));
            }
        });

        HBox guessBox = new HBox(10, new Label("Guess a letter: "), guessTextBox, guessButton);
        guessBox.setPadding(new Insets(10));

        VBox root = new VBox(10, drawStickMan(),guessedWordLabel, numberOfGuessesLabel, guessBox);
        root.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Hangman");
        primaryStage.show();
    }

    private Group drawStickMan() {
        Group root = new Group();

        // Head
        Circle head = new Circle(150, 100, 50);
        root.getChildren().add(head);

        // Body
        Line body = new Line(150, 150, 150, 250);
        root.getChildren().add(body);

        // Arms
        Line arm1 = new Line(150, 175, 100, 200);
        Line arm2 = new Line(150, 175, 200, 200);
        root.getChildren().add(arm1);
        root.getChildren().add(arm2);

        // Legs
        Line leg1 = new Line(150, 250, 100, 300);
        Line leg2 = new Line(150, 250, 200, 300);
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