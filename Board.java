package tetris;

import javafx.scene.layout.BorderPane;
import java.util.ArrayList;

/**
 * This class is in charge of most piece mechanics. It's main
 * purpose is to remove and drop down all the pieces when a line
 * clear occurs. This is done through the use of a double array
 * that stores information on occupied and non-occupied squares
 * on the board.
 */
public class Board {

    private final BorderPane gamePane;
    private final Block[][] occupiedSpace;
    private final Game game;

    /**
     * This constructor takes in this game, as well as the gamePane. It instantiates
     * game as an instance variable since several methods in this class need to
     * access it, and gamePane is needed for the line clearing. The constructor
     * also creates the occupiedSpace 2D array, which is the main array where
     * board occupancy information will be stored.
     */
    public Board(Game game, BorderPane gamePane){
        this.gamePane = gamePane;
        this.occupiedSpace = new Block[Constants.BOARD_HEIGHT][Constants.BOARD_WIDTH];
        this.createBorder();
        this.game = game;
    }

    /**
     * This method is called in the constructor to create the border of the tetris
     * game. Using for loops, it creates a block in every space on the screen
     * specified by SQUARE_WIDTH. The border is assigned a color, and the inside
     * is assigned black. Similarly, the sides are added to the occupied-space array
     * to signify their presence, while the inside is marked as null.
     */
    private void createBorder() {
        for(int i = 0; i < Constants.BOARD_HEIGHT; i++){
            for(int j = 0; j < Constants.BOARD_WIDTH; j++){
                if((i < 1 || i > Constants.BORDER_HEIGHT)
                        || (j < 1 || j > Constants.BORDER_WIDTH)){
                    // Creates border blocks
                    Block block = new Block(
                            this.gamePane,
                            j*Constants.SQUARE_WIDTH,
                            i*Constants.SQUARE_WIDTH,
                            Constants.BORDER_COLOR);
                    block.setShadow(Constants.BORDER_SHADOW);
                    this.occupiedSpace[i][j] = block;
                }
                else{
                    // Creates center null blocks
                    Block block = new Block(
                            this.gamePane,
                            j*Constants.SQUARE_WIDTH,
                            i*Constants.SQUARE_WIDTH,
                            Constants.INSIDE_COLOR);
                    // Shadow set to 0, so they don't emit a glow.
                    block.setShadow(0);
                    this.occupiedSpace[i][j] = null;
                }
            }
        }
    }

    /**
     * A method is called in the piece class to check whether a given space
     * is occupied or not. The method checks if the coordinates passed through
     * the parameter are null or not, returning a boolean. This method is useful
     * for movement and rotation logic.
     */
    public boolean isOccupied(int row, int col){
        return this.occupiedSpace[row][col] != null;
    }

    /**
     * This method was created to cut down on repetition and checks
     * if the next square in a movement sequence is occupied.
     *
     * After converting the block's position into rows and cols,
     * the method isOccupied to see if the
     * space is occupied in the 2D array containing all the blocks,
     * the method returns true or false depending on that condition.
     */
    public boolean checkNextSquare(ArrayList<Block> blocks, int xOffset, int yOffset) {
        boolean canMove = true;
        for(Block block : blocks){
            int y = (int)(block.getY() / Constants.SQUARE_WIDTH) + yOffset;
            int x = (int)(block.getX() / Constants.SQUARE_WIDTH) + xOffset;
            if (this.isOccupied(y,x)) {
                canMove = false;
            }
        }
        return canMove;
    }

    /**
     * When a piece reaches a place where it can move no more, this method is
     * called, by using a for each loop every block in the piece is
     * converted into coordinates and the 2D array space assigned to those
     * coordinates is filled with the block. The method then calls
     * clearRows to clear and move down the rows.
     */
    public void markOccupiedSpaces(ArrayList<Block> blocks) {
        for(Block block : blocks){
            int row = ((int)(block.getY() / Constants.SQUARE_WIDTH));
            int col = ((int)(block.getX() / Constants.SQUARE_WIDTH));
            this.occupiedSpace[row][col] = block;
        }
        this.clearRows();
        this.checkGameOver();
    }

    /**
     * checkGameOver is called at the end of markOccupiedSpaces to see if
     * a game over condition is met. The for loop goes through the top row
     * to see if any spaces are occupied, if they are gameOver is called.
     */
    public void checkGameOver() {
        for (int col = 1; col < Constants.BOARD_WIDTH - 1; col++) {
            if(this.occupiedSpace[1][col] != null){
                this.game.gameOver();
                break;
            }
        }
    }

    /**
     * Clear rows is a large method that checks and clears rows.
     * It also calls the moveRowsDown method which moves each block above
     * the cleared row down. Using a while loop, the method checks every
     * space within a row (from bottom to top rows). If a full row has
     * a single null space, a boolean determining if that row is full
     * is set to false. This boolean then determines if moveRowsDown
     * should be called.
     */
    private void clearRows() {
        int i = Constants.BOARD_HEIGHT-Constants.BOARD_OFFSET;
        int lineCount = 0;
        while (i > 0){
            boolean rowFull = true;
            for (int j = 0; j < Constants.BOARD_WIDTH; j++) {
                if (this.occupiedSpace[i][j] == null) {
                    rowFull = false;
                    break;
                }
            }
            if(rowFull){
                lineCount++;
                this.moveRowsDown(i);
            }
            else{
                i--;
            }
        }
        // The amount of lines cleared is passed into game so that a score can be determined.
        this.game.determineScore(lineCount);
    }

    /**
     * Move rows down is called only when a full row is detected.
     * This method uses several nested for loops to move all blocks
     * above the cleared row(s) down. The first for loop clears the
     * full row. The next set of nested for loops moves each block on
     * the screen above the row down by the number of rows the block
     * is above the cleared row.
     *
     * There is also an additional for loop that marks all the lines
     * on the top row to be null.
     */
    private void moveRowsDown(int i) {
        // Removes the blocks in the cleared row, both graphically and logically
        for (int j = 1; j < Constants.BOARD_WIDTH-1; j++) {
            this.occupiedSpace[i][j].remove();
            this.occupiedSpace[i][j] = null;
        }
        // Moves all the blocks down by their distance from the cleared row.
        for(int row = i; row > 1; row--){
            for (int col = 1; col < Constants.BOARD_WIDTH - 1; col++) {
                this.occupiedSpace[row][col] = this.occupiedSpace[row - 1][col];
                if(this.occupiedSpace[row][col] != null){
                    this.occupiedSpace[row][col].setY(row * Constants.SQUARE_WIDTH);
                }
            }
        }
        // Causes the top row to be null.
        for (int col = 1; col < Constants.BOARD_WIDTH - 1; col++) {
            this.occupiedSpace[0][col] = null;
        }
    }
}