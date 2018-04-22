package io;

import java.util.Map;

import collisions.Block;

/**
 * The class of BlocksFromSymbolsFactory.
 * @author Barak Talmor
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * The constructor of the blocksFromSymbolsFactory.
     * @param blockCreators - map
     * @param spacerWidths - map
     */
    public BlocksFromSymbolsFactory(Map<String, BlockCreator> blockCreators,
            Map<String, Integer> spacerWidths) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    /**
     * returns true if 's' is a valid space symbol.
     * @param s - symbol space
     * @return boolean
     */
    public boolean isSpaceSymbol(String s) {
        return (this.spacerWidths.containsKey(s));
    }

    /**
     * returns true if 's' is a valid block symbol.
     * @param s - symbol block
     * @return boolean
     */
    public boolean isBlockSymbol(String s) {
        return (this.blockCreators.containsKey(s));
    }

    /**
     * Return a block according to the definitions associated.
     * @param s - symbol
     * @param xpos - x position
     * @param ypos - y position
     * @return Block
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     * @param s symbol spacer.
     * @return width
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}
