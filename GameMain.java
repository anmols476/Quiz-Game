import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * For the Quiz Game application, this class serves as the view and controller.
 * It extends the JavaFX Application class and provides the main entry point
 * for the JavaFX application.
 *
 * @author Anmol Singh, created on 7 July 2024.
 */
public class GameMain extends Application {

    /**
     * The quiz game model
     */
    private KnowledgeGame game;

    /**
     * Label to display the current question
     */
    private Label questionLabel;

    /**
     * Array of radio buttons for answer options
     */
    private RadioButton[] choiceButtons;

    /**
     * Toggle group for answer options
     */
    private ToggleGroup optionsGroup;

    /**
     * TextField for entering player name
     */
    private TextField playerNameField;

    /**
     * Label to offer commentary on the respondent's response.
     */
    private Label resultLabel;

    /**
     * Label to display the score text
     */
    private Label scoreLabelText;

    /**
     * TextField to display consumer points.
     */
    private TextField scoreField;

    /**
     * Label display correct answer count
     */
    private Label correctAnswersLabel;

    /**
     * Label to display the incorrect answer count
     */
    private Label incorrectAnswersLabel;

    /**
     * Gives elapsed time
     */
    private Label timerLabel;

    /**
     * Timeline for the timer
     */
    private Timeline timeline;

    /**
     * Elapsed time in seconds
     */
    private int elapsedSeconds;

    /**
     * Here is where you build your elements, incorporate the model,
     * and attach event handlers.
     *
     * @param stage The main stage
     * @throws Exception if an error occurs during application start
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Set up model
        game = new KnowledgeGame();

        // Establish and set up the primary GridPane.
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #E3F2FD;");

        // Set the stage's title and establish the scenario.
        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Knowledge Game");

        // Create and configure the TextField for player name
        playerNameField = new TextField();
        playerNameField.setPromptText("Enter your name...");
        playerNameField.setStyle("-fx-font-size: 14px; -fx-prompt-text-fill: gray;");

        // Make the question label and set it up.
        questionLabel = new Label(game.getCurrentQuestion());
        questionLabel.setStyle("-fx-font-size: 18px;");

        // Create the ToggleGroup and RadioButton array for answer options
        optionsGroup = new ToggleGroup();
        choiceButtons = new RadioButton[4];
        for (int i = 0; i < choiceButtons.length; i++) {
            choiceButtons[i] = new RadioButton();
            choiceButtons[i].setToggleGroup(optionsGroup);
            choiceButtons[i].setUserData(i);
        }
        // Update answer options for the current question
        updateOptions();

        // Create and configure the submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> handleSubmitAnswer());
        submitButton.setStyle("-fx-background-color: lightgreen;");

        // Create and configure the reset button
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> resetGame());
        resetButton.setStyle("-fx-background-color: lightcoral;");

        // Create and configure feedback and score labels
        resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");

        // Create and configure the label and TextField for score
        scoreLabelText = new Label("Score:");
        scoreLabelText.setStyle("-fx-font-size: 20px;");
        scoreField = new TextField("0");
        scoreField.setEditable(false);
        scoreField.setStyle("-fx-font-size: 20px; -fx-background-color: lightgray;");

        correctAnswersLabel = new Label("Correct Answers: 0");
        correctAnswersLabel.setStyle("-fx-font-size: 20px;");

        incorrectAnswersLabel = new Label("Incorrect Answers: 0");
        incorrectAnswersLabel.setStyle("-fx-font-size: 20px;");

        // Create and configure the timer label
        timerLabel = new Label("Time: 0:00");
        timerLabel.setStyle("-fx-font-size: 20px;");

        // Add player name field to the root GridPane
        root.add(playerNameField, 0, 0, 2, 1);
        GridPane.setMargin(playerNameField, new Insets(10, 0, 10, 0));

        // Add all components to the root GridPane
        root.getChildren().addAll(questionLabel, choiceButtons[0], choiceButtons[1],
                choiceButtons[2], choiceButtons[3],
                submitButton, resetButton, resultLabel, scoreLabelText, scoreField,
                correctAnswersLabel, incorrectAnswersLabel, timerLabel);

        // Position the elements in the GridPane
        GridPane.setConstraints(questionLabel, 0, 1, 2, 1);
        for (int i = 0; i < choiceButtons.length; i++) {
            GridPane.setConstraints(choiceButtons[i], 0, i + 2, 2, 1);
        }
        GridPane.setConstraints(submitButton, 0, 6);
        GridPane.setConstraints(resetButton, 1, 6);
        GridPane.setConstraints(resultLabel, 0, 7, 2, 1);
        GridPane.setConstraints(scoreLabelText, 0, 8);
        GridPane.setConstraints(scoreField, 1, 8);
        GridPane.setConstraints(correctAnswersLabel, 0, 9, 2, 1);
        GridPane.setConstraints(incorrectAnswersLabel, 0, 10, 2, 1);
        GridPane.setConstraints(timerLabel, 2, 11, 2, 1);

        // Start the game timer
        startTimer();

        // Set the scene and show the stage
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Updates the answer options for the current question.
     */
    private void updateOptions() {
        // Retrieve the current options from the game model
        String[] options = game.getCurrentOptions();
        // Update the text of each RadioButton with the corresponding option
        for (int i = 0; i < choiceButtons.length; i++) {
            choiceButtons[i].setText(options[i]);
        }
    }

    /**
     * Handles the submission of an answer.
     */
    private void handleSubmitAnswer() {
        // Get the selected answer option
        RadioButton selectedOption = (RadioButton) optionsGroup.getSelectedToggle();
        if (selectedOption == null) {
            resultLabel.setText("Please select an answer.");
            return;
        }

        // Check if the selected answer is correct
        String answer = selectedOption.getText();
        boolean isRight = game.verifySolution(answer);
        if (isRight) {
            resultLabel.setText("Correct!");
            resultLabel.setStyle("-fx-text-fill: green;");
        } else {
            resultLabel.setText("Incorrect. Try again.");
            resultLabel.setStyle("-fx-text-fill: red;");
        }

        // Update points and answer counts
        scoreField.setText(String.valueOf(game.getScore()));
        correctAnswersLabel.setText("Correct Answers: " + game.getCorrectAnswers());
        incorrectAnswersLabel.setText("Incorrect Answers: " + game.getIncorrectAnswers());

        // Display the next question and update options
        questionLabel.setText(game.getCurrentQuestion());
        updateOptions();
        // Clear the selected option
        optionsGroup.selectToggle(null);
    }

    /**
     * Resets the game to its initial state.
     */
    private void resetGame() {
        // Reinitialize the game model
        game = new KnowledgeGame();
        // Reset the UI components to their initial states
        questionLabel.setText(game.getCurrentQuestion());
        resultLabel.setText("");
        scoreField.setText("0");
        correctAnswersLabel.setText("Correct Answers: 0");
        incorrectAnswersLabel.setText("Incorrect Answers: 0");
        updateOptions();
        optionsGroup.selectToggle(null);
        resetTimer();
    }

    /**
     * Starts the game timer.
     */
    private void startTimer() {
        // Initialize the elapsed seconds and update the timer label
        elapsedSeconds = 0;
        updateTimerLabel();
        // Create and start the timeline for the timer
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            elapsedSeconds++;
            updateTimerLabel();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Updates the timer label with the current elapsed time.
     */
    private void updateTimerLabel() {
        // Calculate minutes and seconds from elapsed seconds
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;
        // Update the timer label text
        timerLabel.setText(String.format("Time: %d:%02d", minutes, seconds));
    }

    /**
     * Resets the game timer.
     */
    private void resetTimer() {
        // Stop the current timeline and start a new timer
        timeline.stop();
        startTimer();
    }

    /**
     * Main entry point for the JavaFX application.
     *
     * @param args Command line arguments (unused)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
