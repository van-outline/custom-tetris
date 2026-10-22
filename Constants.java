package tetris;

import javafx.scene.paint.Color;

public class Constants {

    // width of each square
    public static final int SQUARE_WIDTH = (int)(Constants.SCENE_HEIGHT+45)/24;

    // coordinates for squares in each tetris piece
    public static final int[][] I_PIECE_COORDS = {
            {0, 0},
            {0, SQUARE_WIDTH},
            {0, 2 * SQUARE_WIDTH},
            {0, 3 * SQUARE_WIDTH}};
    public static final int[][] T_PIECE_COORDS = {
            {-1 * SQUARE_WIDTH, 0},
            {-1 * SQUARE_WIDTH, 2 * SQUARE_WIDTH},
            {-1 * SQUARE_WIDTH, SQUARE_WIDTH},
            {0, SQUARE_WIDTH}};
    public static final int[][] O_PIECE_COORDS = {
            {-1 * SQUARE_WIDTH, 0},
            {-1 * SQUARE_WIDTH, SQUARE_WIDTH},
            {0, SQUARE_WIDTH},
            {0, 0}};
    public static final int[][] L_PIECE_COORDS = {
            {-1 * SQUARE_WIDTH, 0},
            {0, 0},
            {0, SQUARE_WIDTH},
            {0, 2 * SQUARE_WIDTH}};
    public static final int[][] L_TWO_PIECE_COORDS = {
            {-1 * SQUARE_WIDTH, 2 * SQUARE_WIDTH},
            {-1 * SQUARE_WIDTH, 0},
            {-1 * SQUARE_WIDTH, SQUARE_WIDTH},
            {0, 0}};
    public static final int[][] S_PIECE_COORDS = {
            {-1 * SQUARE_WIDTH, 0},
            {-1 * SQUARE_WIDTH, SQUARE_WIDTH},
            {0, SQUARE_WIDTH},
            {0, 2 * SQUARE_WIDTH}};
    public static final int[][] S_TWO_PIECE_COORDS = {
            {-1 * SQUARE_WIDTH, 2 * SQUARE_WIDTH},
            {-1 * SQUARE_WIDTH, SQUARE_WIDTH},
            {0, SQUARE_WIDTH},
            {0, 0}};
    public static final int T_PIECE = 0;
    public static final int O_PIECE = 1;
    public static final int L_PIECE = 2;
    public static final int L_TWO_PIECE = 3;
    public static final int S_PIECE = 4;
    public static final int S_TWO_PIECE = 5;
    public static final Color T_PIECE_COLOR = Color.MEDIUMSPRINGGREEN;
    public static final Color O_PIECE_COLOR = Color.LIGHTSKYBLUE;
    public static final Color L_PIECE_COLOR = Color.PLUM;
    public static final Color L_TWO_PIECE_COLOR = Color.SALMON;
    public static final Color S_PIECE_COLOR = Color.KHAKI;
    public static final Color S_TWO_PIECE_COLOR = Color.ORANGE;
    public static final Color I_PIECE_COLOR = Color.TURQUOISE;
    public static final Color BORDER_COLOR = Color.RED;
    public static final Color INSIDE_COLOR = Color.BLACK;
    public static final double SCENE_WIDTH = (Constants.SCENE_HEIGHT+45)/2;
    public static final double SCENE_HEIGHT = 1035;
    public static final int BUTTONPANE_HEIGHT = Constants.SQUARE_WIDTH;
    public static final int BOARD_HEIGHT =
            (int)(Constants.SCENE_HEIGHT+45)/Constants.SQUARE_WIDTH;
    public static final int BOARD_WIDTH =
            (int)Constants.SCENE_WIDTH/Constants.SQUARE_WIDTH;
    public static final double BASE_SPEED_SLOW = 1.0;
    public static final double BASE_SPEED_MEDIUM = 0.5;
    public static final double BASE_SPEED_FAST = 0.2;
    public static final double BASE_SECONDS = 0.15;
    public static final double LEVEL_DIVIDER = 5.0;
    public static final int ONE_LINE = 1;
    public static final int TWO_LINE = 2;
    public static final int THREE_LINE = 3;
    public static final int FOUR_LINE = 4;
    public static final int ONE_LINE_SCORE = 40;
    public static final int TWO_LINE_SCORE = 100;
    public static final int THREE_LINE_SCORE = 300;
    public static final int FOUR_LINE_SCORE = 1200;
    public static final String CONTAINER_COLOR = "-fx-background-color: #FFFFFF";
    public static final String BTN_PANE_COLOR = "-fx-background-color: #778899";
    public static final String LABEL_WHITE_COLOR =
            "-fx-text-fill: #ffffff; -fx-background-color: transparent;";
    public static final String LABEL_BLACK_COLOR =
            "-fx-text-fill: #000000; -fx-background-color: transparent;";
    public static final int PAUSE_QUIT_SCALE = 4;
    public static final int LABEL_Y_OFFSET = 10;
    public static final int LABEL_X_OFFSET = 300;
    public static final double LABEL_SCALE = 1.2;
    public static final int BORDER_HEIGHT = 20;
    public static final int BORDER_WIDTH = 10;
    public static final int BORDER_SHADOW = 30;
    public static final int PIECE_AMOUNT = 7;
    public static final int BOARD_OFFSET = 4;
    public static final int COORDS_OFFSET = 270;
    public static final int ROTATION_ARRAY_ROWS = 4;
    public static final int ROTATION_ARRAY_COLS = 2;
    public static final int ROTATION_COORD = 2;
    public static final int SHADOW_LEVEL = 30;
}
