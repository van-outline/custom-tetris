package tetris;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * This is the main class for the graphics of the game. It has an instance
 * variable for a BorderPane that contains all the graphical elements of the game.
 * That is the main root node, added to it are the panes responsible for
 * the game and the quit button.
 */
public class PaneOrganizer {
    private final BorderPane container;
    private BorderPane gamePane;
    private HBox buttonPane;

    /**
     * This constructor sets up the main graphics of the game and also
     * instantiates a Game object. It passes the gamePane into game,
     * so that game can only have access to its respective pane.
     * The constructor also calls a method that creates the quit button.
     */
    public PaneOrganizer(){
        this.container = new BorderPane();
        this.createGamePane();
        this.container.setStyle(Constants.CONTAINER_COLOR);
        new Game(this.gamePane);
        this.createQuitPane();
    }

    /**
     * This method creates GamePane, which is the pane that game has
     * access to. Game adds labels and javafx elements only to this pane.
     */
    private void createGamePane() {
        this.gamePane = new BorderPane();
        this.gamePane.setStyle(Constants.CONTAINER_COLOR);
        this.gamePane.setFocusTraversable(true);
        this.container.setCenter(this.gamePane);
    }

    /**
     * This method creates the button pane for the quit button.
     * Using an HBox the button is positioned using javafx methods
     * and then the method to create the actual button is called.
     */
    private void createQuitPane(){
        this.buttonPane = new HBox();
        this.buttonPane.setPrefSize(Constants.SCENE_WIDTH,
                Constants.BUTTONPANE_HEIGHT);
        this.container.setBottom(this.buttonPane);
        this.buttonPane.setAlignment(Pos.CENTER);
        this.buttonPane.setStyle(Constants.BTN_PANE_COLOR);
        this.createQuitButton();
    }

    /**
     * The quit button is a HBox that goes at the bottom of the screen.
     * If click it calls System.exit(0) and closes the application window.
     */
    private void createQuitButton() {
        Button button = new Button("Quit");
        this.buttonPane.getChildren().add(button);
        button.setOnAction((ActionEvent e) -> System.exit(0));
        this.buttonPane.setFocusTraversable(false);
        button.setFocusTraversable(false);
    }

    /**
     * This method returns the root Borderpane.
     */
    public BorderPane getRoot() {
        return this.container;
    }
}
