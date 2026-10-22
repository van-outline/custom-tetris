package tetris;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * This is a class that represents each tetris piece.
 *
 * It instantiates gamePane, board, and game, so that each piece
 * can be added both logically and graphically. This also allows
 * important line clearing messages to be called by piece when
 * it becomes stationary. Piece also has an ArrayList that stores
 * each tetris block in it.
 */
public class Piece {

    private final ArrayList<Block> blocks;
    private final Color color;
    private final BorderPane gamePane;
    private final Board board;
    private final Game game;

    /**
     * This is the piece constructor, it instantiates each piece using
     * passed in parameters that make each piece unique. Namely, the color
     * and the double array responsible for each block's position within
     * the piece.
     */
    public Piece(BorderPane gamePane, int[][] coords,
                 Board board, Color color, Game game){
        this.game = game;
        this.gamePane = gamePane;
        this.blocks = new ArrayList<>();
        this.color = color;
        this.board = board;
        this.generateBlocks(coords, this.color);
    }

    /**
     * generatePlatform is called in the constructor and instantiates the wrapper
     * class block that the piece is made up from. It adds each block to the
     * Arraylist and sets its coordinates to be the x and y components of
     * the 2D array. It also sets each blocks color.
     */
    private void generateBlocks(int[][] coords, Color color) {
        for (int[] coord : coords) {
            this.blocks.add(new Block(
                this.gamePane,
                coord[0] + Constants.COORDS_OFFSET,
                coord[1] + Constants.SQUARE_WIDTH,
                color));
        }
    }

    /**
     * Instant down is the method called when the player presses the space bar.
     * This method determines the minimum distance required for a block within
     * piece to reach collision and then drops the entire piece that amount.
     *
     * This is done using a while loop that scans the distance from the block
     * to the first occupied space, and returns that distance by assigning it to
     * MinDrop. This is done for each block due to a for each loop.
     */
    public void instantDown() {
        int minDrop = (int)(Constants.SCENE_HEIGHT / Constants.SQUARE_WIDTH);
        for(Block block : this.blocks) {
            // Converts the block's location into coordinates
            int row = (int)(block.getY() / Constants.SQUARE_WIDTH);
            int col = (int)(block.getX() / Constants.SQUARE_WIDTH);
            int drop = 0;
            // scans from the block's location to the bottom row
            while(!this.board.isOccupied(row + drop + 1,col)
                    && row + drop + 1
                    < (int)(Constants.SCENE_HEIGHT / Constants.SQUARE_WIDTH)){
                drop++;
            }
            // Updates the least distance from a collision
            if(drop < minDrop){
                minDrop = drop;
            }
        }
        // sets each block to that distance to make the piece move down.
        for(Block block : this.blocks){
            block.setY(block.getY() + minDrop * Constants.SQUARE_WIDTH);
        }
        // Marks the newly occupied space and generates a new piece.
        this.board.markOccupiedSpaces(this.blocks);
        this.game.generatePiece();
    }

    /**
     * moveDown is a method that is called every keyframe duration
     * whenever the game is not paused or over. This causes each
     * piece to continuously move down a block amount. Because
     * the keyframe duration decreases with difficulty, the pieces
     * fall faster and faster.
     *
     * If the next square below is occupied, the piece becomes stationary
     * and a new one is generated.
     */
    public void moveDown() {
        // Checks if the next space is occupied
        if (this.board.checkNextSquare(this.blocks,0,1)) {
            for(Block block : this.blocks){
                block.setY(block.getY() + Constants.SQUARE_WIDTH);
            }
        }
        else{
            // Marks the newly occupied space and generates a new piece.
            this.board.markOccupiedSpaces(this.blocks);
            this.game.generatePiece();
        }
    }

    /**
     * rotate is a method called whenever the up arrow is pressed.
     * It causes a piece to rotated about a predetermined axis
     * of rotate.
     *
     * Each blocks new coordinates are stored in a local 2D array,
     * and then each block is moved to those newly calculated coordinates.
     *
     * If a space where the blocks want to rotate is occupied, the rotation
     * will not happen. This is done by predetermining the new position
     * and checking if it is occupied by calling board's isOccupied.
     */
    public void rotate() {
        // Checks if the piece is an O piece, in which case it can not rotate.
        if(this.color != Constants.O_PIECE_COLOR){
            // Newly calculated coordinates will be assigned to this local 2D array.
            double[][] rotation = new double
                    [Constants.ROTATION_ARRAY_ROWS]
                    [Constants.ROTATION_ARRAY_COLS];
            boolean canRotate = true;
            // The axis of rotation is the third x and y in each piece's original coordinates.
            double centerX = this.blocks.get(Constants.ROTATION_COORD).getX();
            double centerY = this.blocks.get(Constants.ROTATION_COORD).getY();
                for(int i = 0; i < this.blocks.size(); i++){
                    Block block = this.blocks.get(i);
                    rotation[i][0] = centerX
                            - centerY
                            + block.getY();
                    rotation[i][1] = centerY
                            + centerX
                            - block.getX();
                    int y = (int)(rotation[i][1] / Constants.SQUARE_WIDTH);
                    int x = (int)(rotation[i][0] / Constants.SQUARE_WIDTH);
                    if(this.board.isOccupied(y,x)){
                        canRotate = false;
                    }
                }
                // If a piece can rotate, each block is set to the new coordinates.
                if(canRotate){
                    for(int i = 0; i < this.blocks.size(); i++){
                        Block block = this.blocks.get(i);
                        block.setX((int)rotation[i][0]);
                        block.setY((int)rotation[i][1]);
                    }
                }
        }
    }

    /**
     * moveX is responsible for moving the block with right and left,
     * the direction is determined by parameter dir, which then determines
     * which square will be checked for collision and where the piece will
     * actually move when it has the opportunity to.
     */
    public void moveX(int dir){
        // After checking for collision each block is moved a square length.
        if(this.board.checkNextSquare(this.blocks, dir, 0)){
            for(Block block : this.blocks){
                block.setX((int)(block.getX() + dir * Constants.SQUARE_WIDTH));
            }
        }
    }

    /**
     * This method removes each block within a piece and is
     * called by game to remove the current piece when the
     * game needs to restart.
     */
    public void remove() {
        for(Block block : this.blocks){
            block.remove();
        }
    }
}
