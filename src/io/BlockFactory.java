package io;

import java.awt.Color;

import animations.Fill;
import collisions.Block;
import geometryprimitives.Rectangle;

/**
 * The class for creating block.
 * @author Barak Talmor
 */
public class BlockFactory implements BlockCreator {
    private int height;
    private int width;
    private Color stroke;
    private int hitPoints;
    private Fill fill;

    @Override
    public Block create(int xpos, int ypos) {
        return new Block(new Rectangle(xpos, ypos, width, height), this.fill, this.stroke, this.hitPoints);
    }

    /**
     * The method set new height.
     * @param newHeight - of block
     */
    public void setHeight(int newHeight) {
        this.height = newHeight;
    }

    /**
     * The method set new width.
     * @param newWidth - of block
     */
    public void setWidth(int newWidth) {
        this.width = newWidth;
    }

    /**
     * The method set new stroke.
     * @param newStroke - of block
     */
    public void setStroke(Color newStroke) {
        this.stroke = newStroke;
    }

    /**
     * The method set new fill.
     * @param newFill - of block
     */
    public void setFill(Fill newFill) {
        this.fill = newFill;
    }

    /**
     * The method set new hitPoints.
     * @param newHitPoints - of block
     */
    public void setHitPoints(int newHitPoints) {
        this.hitPoints = newHitPoints;
    }
}
