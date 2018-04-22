package io;


import collisions.Block;

/**
 * The interface that create blocks.
 * @author Barak Talmor
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     * @param xpos - x position
     * @param ypos - y position
     * @return Block
     */
    Block create(int xpos, int ypos);
}