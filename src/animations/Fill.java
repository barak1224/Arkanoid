package animations;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import biuoop.DrawSurface;
import io.ColorsParser;

/**
 * The class of fill.
 * @author Barak Talmor
 */
public class Fill {
    private Map<Integer, Image> images;
    private Map<Integer, Color> colors;

    /**
     * The constructor of fills.
     * @param fills array of string
     */
    public Fill(String[] fills) {
        this.images = new TreeMap<Integer, Image>();
        this.colors = new TreeMap<Integer, Color>();
        this.buildsMap(fills);
    }

    /**
     * The method takes the strings to colors.
     * @param fills - array of strings with colors or images as string
     */
    public void buildsMap(String[] fills) {
        for (int i = 1; i < fills.length; i++) {
            if (fills[i].startsWith("image(")) {
                Image img = null;
                try {
                    String path = fills[i].substring(6, fills[i].length() - 1);
                    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
                    img = ImageIO.read(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.images.put(i, img);
            } else {
                ColorsParser cp = new ColorsParser();
                this.colors.put(i, cp.colorFromString(fills[i]));
            }
        }
    }

    /**
     * The method draw the fill of the block.
     * @param d - surface
     * @param hitPoints - the current hit points
     * @param x position
     * @param y position
     * @param width size
     * @param height size
     */
    public void drawon(DrawSurface d, int hitPoints, int x, int y, int width, int height) {
        int index = hitPoints;
        if (hitPoints <= 0) {
            index = 1;
        }
        if (this.images.containsKey(index)) {
            d.drawImage(x, y, this.images.get(index));
        } else {
            d.setColor(this.colors.get(index));
            d.fillRectangle(x, y, width, height);
        }
    }
}