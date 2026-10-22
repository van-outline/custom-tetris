package tetris;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * This class handles all the game logic. It has instance variables
 * for the gamePane, board, current piece, timeline, labels, etc.
 *
 * These instance variables allow the class to update all the different
 * logical elements of the game.
 *
 * As a top-level class, Game is responsible for key input, the timeline,
 * generating tetris pieces, as well as game over, restart, pause, difficulty,
 * and score logic.
 */
public class Game {
    private final BorderPane gamePane;
    private Board board;
    private Piece currentPiece;
    private boolean paused;
    public boolean gameOver;
    private double baseSpeed;
    private int scoreValue;
    private int level;
    private Timeline timeline;
    private Label difficulty;
    private Label pauseScreen;
    private Label score;
    private Label levelLabel;
    private Label gameOverLabel;
    private HBox labelPane;

    /**
     * The constructor sets up all the instance variables, and takes in a
     * parameter for the main gamePane of the project, which is used for
     * all the pieces and labels.
     *
     * The constructor also allows the game to take key input for controlling
     * the pieces by configuring GamePane. As well as creates the board, timeline,
     * labels, and first piece.
     */
    public Game(BorderPane gamePane){
        // gamePane instantiation and configuration
        this.gamePane = gamePane;
        this.gamePane.requestFocus();
        this.gamePane.setFocusTraversable(true);
        this.gamePane.setOnKeyPressed((KeyEvent e) -> this.handleKeyPress(e));

        // Misc. instantiation
        this.gameOver = false;
        this.level = 0;
        this.baseSpeed = Constants.BASE_SPEED_SLOW;
        this.board = new Board(this, this.gamePane);
        this.startTimeline();
        this.createLabels();
        this.generatePiece();
    }

    /**
     * This sets up the timeline and sets the pause status to false.
     * It then starts playing the animation loop indefinitely. The original
     * duration of the keyframe is one second, however, as the difficulty
     * increases, this duration will be altered to decrease.
     */
    private void startTimeline() {
        this.paused = false;
        this.timeline = new Timeline();
        this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
                ((ActionEvent e) -> this.currentPiece.moveDown())));
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    /**
     * This method randomizes the piece generation of the game.
     * It generates a random integer using Math.Random and passes it
     * into a switch statement where each int creates a different piece.
     * Because only color and block coordinates set each piece apart,
     * each of those local variables is assigned in the switch body,
     * and then passed into a piece instantiation at the end of the method.
     */

    public void generatePiece() {
        int randInt = (int)(Math.random()*Constants.PIECE_AMOUNT);

        // Local variables responsible for coordinates and color
        int[][] coords;
        Color color;

        switch (randInt) {
            case Constants.T_PIECE:
                coords = Constants.T_PIECE_COORDS;
                color = Constants.T_PIECE_COLOR;
                break;
            case Constants.O_PIECE:
                coords = Constants.O_PIECE_COORDS;
                color = Constants.O_PIECE_COLOR;
                break;
            case Constants.L_PIECE:
                coords = Constants.L_PIECE_COORDS;
                color = Constants.L_PIECE_COLOR;
                break;
            case Constants.L_TWO_PIECE:
                coords = Constants.L_TWO_PIECE_COORDS;
                color = Constants.L_TWO_PIECE_COLOR;
                break;
            case Constants.S_PIECE:
                coords = Constants.S_PIECE_COORDS;
                color = Constants.S_PIECE_COLOR;
                break;
            case Constants.S_TWO_PIECE:
                coords = Constants.S_TWO_PIECE_COORDS;
                color = Constants.S_TWO_PIECE_COLOR;
                break;
            default:
                coords = Constants.I_PIECE_COORDS;
                color = Constants.I_PIECE_COLOR;
                break;
        }
        this.currentPiece = new Piece(this.gamePane, coords,this.board, color, this);
    }

    /**
     * This method takes a parameter of a KeyEvent and gets the keycode.
     * If the KeyCode is either right or left arrow keys, the piece will
     * move right or left. The up KeyCode is responsible for the piece's
     * rotation, the down for an accelerated drop, space causes the piece to
     * drop instantly, r calls a method to restart the game, and each of the
     * 1 2 3 digits increase the difficulty.
     *
     * At the beginning of the method there is a separate check to see if
     * P was pressed to pause the game. This ensures that the Pause logic
     * does not interfere with game over logic.
     *
     * There is also a consume method call on the KeyEvent
     * at the end to avoid triggering other parts of the program.
     */
    private void handleKeyPress(KeyEvent e) {
        KeyCode keyPressed = e.getCode();
        // This if statement ensures there is no pause and game over logic interference.
        if (keyPressed == KeyCode.P && !this.gameOver) {
            this.pauseGame();
            e.consume();
            return;
        }
        if(!this.gameOver && !this.paused){
            switch (keyPressed) {
                case LEFT:
                    this.currentPiece.moveX(-1);
                    break;
                case RIGHT:
                    this.currentPiece.moveX(1);
                    break;
                case UP:
                    this.currentPiece.rotate();
                    break;
                case DOWN:
                    this.currentPiece.moveDown();
                    break;
                case SPACE:
                    this.currentPiece.instantDown();
                    break;
                case R:
                    this.restart();
                    break;
                case DIGIT1:
                    this.baseSpeed = Constants.BASE_SPEED_SLOW;
                    this.applySpeed();
                    this.difficulty.setText("Difficulty - Easy");
                    break;
                case DIGIT2:
                    this.baseSpeed = Constants.BASE_SPEED_MEDIUM;
                    this.applySpeed();
                    this.difficulty.setText("Difficulty - Medium");
                    break;
                case DIGIT3:
                    this.baseSpeed = Constants.BASE_SPEED_FAST;
                    this.applySpeed();
                    this.difficulty.setText("Difficulty - Hard");
                    break;
                default:
                    break;
            }
        }
        e.consume();
        // Allows for a restart even when the game is over
        if (keyPressed == KeyCode.R) {
            this.restart();
            e.consume();
        }
    }

    /**
     * This is a top-class restart method that calls other methods
     * to restart their respective logic. It removes labels, pieces,
     * resets timelines, and reinstates initial variables.
     */
    private void restart() {
        this.timeline.stop();
        this.currentPiece.remove();
        this.gameOver = false;
        if(this.paused){
            this.paused = false;
        }
        this.gamePane.getChildren().clear();
        this.board = new Board(this, this.gamePane);
        this.labelPane.getChildren().clear();
        this.createLabels();
        this.generatePiece();
        this.baseSpeed = Constants.BASE_SPEED_SLOW;
        this.applySpeed();
        this.timeline.playFromStart();
    }

    /**
     * This is a top-class gameOver method that stops the timeline,
     * sets gameOver to true, and creates the game over screen, which displays
     * "GAME OVER" in the middle of the screen. It is called by the board
     * when it detects a block in the top row.
     *
     * The method also shows the number of lines the player cleared
     * by using the level variable.
     */
    public void gameOver() {
        this.timeline.stop();
        this.gameOver = true;
        this.gameOverLabel = new Label("  GAME OVER \nLines Cleared " + this.level);
        this.stylizeLabel(
                this.gameOverLabel,
                Constants.LABEL_WHITE_COLOR,
                0,
                0,
                Constants.PAUSE_QUIT_SCALE);
        this.gamePane.setCenter(this.gameOverLabel);
        this.gameOverLabel.toFront();
    }

    /**
     * This method updates the score based on how many lines were cleared.
     * This is amount is passed in through as an int and then used within
     * a switch statement to determine what score needs to be added.
     * This method also increases the level of the game depending on the
     * line clearance, which increases the difficulty of the game.
     */
    public void determineScore(int lineCount) {
        switch (lineCount) {
            case Constants.ONE_LINE:
                this.updateLevelScore(lineCount, Constants.ONE_LINE_SCORE);
                break;
            case Constants.TWO_LINE:
                this.updateLevelScore(lineCount, Constants.TWO_LINE_SCORE);
                break;
            case Constants.THREE_LINE:
                this.updateLevelScore(lineCount, Constants.THREE_LINE_SCORE);
                break;
            case Constants.FOUR_LINE:
                this.updateLevelScore(lineCount, Constants.FOUR_LINE_SCORE);
                break;
            default:
                break;
        }
    }

    /**
     * By taking in determineScore's assigned score and level values,
     * this method actually changes the labels corresponding to each
     * statistic, and calls applySpeed to speed the game up based on
     * the new level.
     */
    private void updateLevelScore(int lineCount, int scoreAddition) {

        // New level gets updated:
        this.level += lineCount;
        this.applySpeed();
        this.levelLabel.setText("Level " + this.level);

       // New score gets updated:
        this.scoreValue = this.scoreValue + scoreAddition;
        this.score.setText("Score " + this.scoreValue);
    }

    /**
     * This method gradually increases the speed of the falling pieces.
     * It does this by calling method setSpeed and passing through a
     * newly calculated secondsPerDrop which outputs a lower and lower
     * value depending on what level the game is on.
     *
     * Based on the level, this method gets called to update the timeline's
     * duration, as the levels increase the duration gets smaller making the pieces
     * fall faster which ultimately makes the game more challenging.
     */
    private void applySpeed() {
        // Math.max is used to cap the speed so that the pieces do not fall down instantly.
        double secondsPerDrop = Math.max(Constants.BASE_SECONDS,
                this.baseSpeed - Constants.BASE_SECONDS
                        * (this.level / Constants.LEVEL_DIVIDER));
        this.timeline.stop();
        this.timeline.getKeyFrames().clear();
        this.timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(secondsPerDrop),
                        e -> this.currentPiece.moveDown()));
        this.timeline.play();
    }

    /**
     * This method is called when the game is paused.
     * It pauses the timeline and unpauses it if P is pressed
     * once again. This is done by inverting a boolean each call.
     * It displays the pause screen and removes it when the game
     * is unpaused.
     */
    private void pauseGame() {
        this.paused = !this.paused;
        if(this.paused){
            this.timeline.pause();
            this.gamePane.setCenter(this.pauseScreen);
        }
        else{
            this.timeline.play();
            this.gamePane.getChildren().remove(this.pauseScreen);
        }
    }

    /**
     * This method resets all the labels and
     * variables associated with labels as well as
     * removes a gameOver screen if there is one.
     */
    private void resetLabels() {
        this.scoreValue = 0;
        this.score.setText("Score 0");
        this.level = 0;
        this.levelLabel.setText("Level 0");
        this.difficulty.setText("Choose 1,2,3 for difficulty");

        // Removes the label only when it is on the pane
        if(this.gameOver){
            this.gamePane.getChildren().remove(this.gameOverLabel);
        }
        else if(this.paused){
            this.gamePane.getChildren().remove(this.pauseScreen);
        }
    }

    /**
     * This method creates all the labels except the game over label.
     * It adds all these labels to a newly instantiated HBox at the top of the
     * screen. The labels include the pause screen, the score, the level, and the
     * difficulty setting.
     */
    private void createLabels(){

        // Making a label pane allows the labels to overlay when the game restarts
        // Label Pane
        this.labelPane = new HBox();
        this.gamePane.setTop(this.labelPane);
        this.labelPane.setAlignment(Pos.TOP_CENTER);

        // Pause Label
        this.pauseScreen = new Label("PAUSED");
        this.stylizeLabel(
                this.pauseScreen,
                Constants.LABEL_WHITE_COLOR,
                0,
                0,
                Constants.PAUSE_QUIT_SCALE);

        // Score Label
        this.score = new Label("Score 0");
        this.stylizeLabel(
                this.score,
                Constants.LABEL_BLACK_COLOR,
                Constants.LABEL_X_OFFSET,
                Constants.LABEL_Y_OFFSET,
                Constants.LABEL_SCALE);

        // Difficulty Label
        this.difficulty = new Label("Choose 1,2,3 for difficulty");
        this.stylizeLabel(
                this.difficulty,
                Constants.LABEL_BLACK_COLOR,
                0,
                Constants.LABEL_Y_OFFSET,
                Constants.LABEL_SCALE);

        // Level Label
        this.levelLabel = new Label("Level 0");
        this.stylizeLabel(
                this.levelLabel,
                Constants.LABEL_BLACK_COLOR,
                -Constants.LABEL_X_OFFSET,
                Constants.LABEL_Y_OFFSET,
                Constants.LABEL_SCALE);

        this.labelPane.getChildren().addAll(this.score, this.difficulty, this.levelLabel);
        this.resetLabels();
    }

    /**
     * This method reduces the repetition caused by creating multiple labels
     * by taking in a lot of parameters accounting for each label's color,
     * position, and scale. All labels call this method to configure their
     * respective appearances.
     */
    private void stylizeLabel(
            Label label,
            String color,
            int offsetX,
            int offsetY,
            double scale){
        label.setStyle(color);
        label.setTranslateY(offsetY);
        label.setTranslateX(offsetX);
        label.setScaleX(scale);
        label.setScaleY(scale);
        label.setAlignment(Pos.CENTER);
    }
}
