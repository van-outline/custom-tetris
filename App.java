package tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the main class where your Tetris game will start.
 * The main method of this application calls launch, a JavaFX method
 * which eventually calls the start method below. You will need to fill
 * in the start method to start your game!
 *
 * This is the Tetris App class. This class is run to start the game.
 */

public class App extends Application {

    /**
     * This method is eventually called by the given launch method. It sets up the scene
     * of the game and the PaneOrganizer, which starts the game up.
     */
    @Override
    public void start(Stage stage) {
        // Create top-level object, set up the scene, and show the stage here.
        CpuTimeTracker tracker = new CpuTimeTracker();
        tracker.start();
        stage.setTitle("Tetris");
        PaneOrganizer organizer = new PaneOrganizer();
        Scene scene = new Scene(
                organizer.getRoot(),
                Constants.SCENE_WIDTH,
                Constants.SCENE_HEIGHT);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> tracker.outputEstimate());
        stage.show();
    }

    /*
     * Here is the mainline! No need to change this.
     */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
