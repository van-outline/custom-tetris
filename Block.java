package tetris;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * This is a class for the block which each piece is composed of.
 * Block is a wrapper for the JavaFX rectangle which allows greater
 * customization and disconnects the rest of the game from JavaFX.
 *
 * It has instance variables for its x and y position and also
 * for a Rectangle that graphically displays the block.
 *
 * The blocks have some effects to make them look unique and
 * beautiful.
 */
public class Block {

    private final Rectangle block;
    private final DropShadow shadow;
    private double x;
    private double y;
    private final BorderPane gamePane;

    /**
     * This is the block constructor, it instantiates parameters x and y
     * to be instance variables x and y, respectively. x and y are very
     * important position variables that determine the coordinates
     * of the actual Rectangle object. It also passes in the gamePane
     * so that the blocks can be added graphically.
     */
    public Block(BorderPane gamePane,
                 double x,
                 double y,
                 Color color){
        this.gamePane = gamePane;
        this.x = x;
        this.y = y;
        this.block = new Rectangle(
                this.x,
                this.y,
                Constants.SQUARE_WIDTH,
                Constants.SQUARE_WIDTH);
        this.block.setFill(color);
        this.gamePane.getChildren().add(this.block);
        this.shadow = new DropShadow();
        this.shadow.setColor(color);
        this.shadow.setRadius(Constants.SHADOW_LEVEL);
        this.block.setEffect(this.shadow);
    }

    /**
     * Sets the block's shadow strength. This ensures the background
     * black blocks have no shadow.
     */
    public void setShadow(int rad){
        this.shadow.setRadius(rad);
    }

    /**
     * A getter that returns the Rectangle object.
     * This is useful when the actual Rectangle needs to be accessed,
     * rather than the block object.
     */
    public void remove() {
        this.gamePane.getChildren().remove(this.block);
    }

    /**
     * Returns the y position of the block.
     */
    public double getY() {
        return this.block.getY();
    }
    
    /**
     * Returns the x position of the block.
     */
    public double getX() {
        return this.block.getX();
    }

    /**
     * Sets the block's y position, it is important
     * that the instance variable y is also set because it ensures
     * this custom variable is in sync with the Rectangle's position.
     */
    public void setY(double value) {
        this.block.setY(value);
        this.y = this.block.getY();
    }

    /**
     * Sets the block's x position, it is important
     * that the instance variable x is also set because it ensures
     * this custom variable is in sync with the Rectangle's position.
     */
    public void setX(double value) {
        this.block.setX(value);
        this.x = this.block.getX();
    }
}
