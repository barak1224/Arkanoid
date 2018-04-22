package io;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;

import animations.Fill;

/**
 * The class of BlocksDefinitionReader.
 * @author Barak Talmor
 */
public class BlocksDefinitionReader {
    /**
     * The method read the file and builds BlocksFromSymbolsFactory.
     * @param reader - file
     * @return BlocksFromSymbolsFactory
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        Map<String, String> defaultDef = new TreeMap<>();
        Map<String, Integer> spacersDef = new TreeMap<>();
        Map<String, BlockCreator> blocks = new TreeMap<>();
        Map<String, String> fillsDef = new TreeMap<>();
        BufferedReader bReader = null;
        String line;
        bReader = new BufferedReader(reader);
        try {
            while ((line = bReader.readLine()) != null) {
                if (line.startsWith("#") || line.equals("")) {
                    continue;
                }
                if (line.startsWith("default")) {
                    String[] split = line.split(" ");
                    for (int i = 1; i < split.length; i++) {
                        String[] secondSplit = split[i].split(":");
                        if (secondSplit[0].startsWith("fill")) {
                            fillsDef.put(secondSplit[0], secondSplit[1]);
                        } else {
                            defaultDef.put(secondSplit[0], secondSplit[1]);
                        }
                    }
                }
                if (line.startsWith("bdef")) {
                    String[] split = line.split(" ");
                    if (split[1].split(":")[0].equals("symbol")) {
                        BlockCreator b = buildsBlock(split, defaultDef, fillsDef);
                        if (b != null) {
                            blocks.put(split[1].split(":")[1], b);
                        }
                    }
                }
                if (line.startsWith("sdef")) {
                    String[] split = line.split(" ");
                    String symbol = split[1].split(":")[1];
                    int width = Integer.parseInt(split[2].split(":")[1]);
                    spacersDef.put(symbol, width);
                }
            }
        } catch (IOException e) {
            return null;
        }
        return new BlocksFromSymbolsFactory(blocks, spacersDef);
    }

    /**
     * The method builds the block creator Map.
     * @param split - string of bdef that was split
     * @param defaultDef - the default defs
     * @param fillsDef - fills definition
     * @return BlockCreator
     */
    public static BlockCreator buildsBlock(String[] split, Map<String, String> defaultDef,
            Map<String, String> fillsDef) {
        BlockFactory block = new BlockFactory();
        int hitPoints = -1;
        boolean height = false, width = false, stroke = false, fill = false;
        Map<String, String> fills = new TreeMap<>();
        for (int i = 2; i < split.length; i++) {
            String[] secondSplit = split[i].split(":");
            if (secondSplit[0].equals("hit_points")) {
                block.setHitPoints(Integer.parseInt(secondSplit[1]));
                hitPoints = Integer.parseInt(secondSplit[1]);
                continue;
            }
            if (secondSplit[0].equals("height")) {
                block.setHeight(Integer.parseInt(secondSplit[1]));
                height = true;
                continue;
            }
            if (secondSplit[0].equals("width")) {
                block.setWidth(Integer.parseInt(secondSplit[1]));
                width = true;
                continue;
            }
            if (secondSplit[0].equals("stroke")) {
                ColorsParser pcolor = new ColorsParser();
                block.setStroke(pcolor.colorFromString(secondSplit[1]));
                stroke = true;
                continue;
            }
            if (secondSplit[0].startsWith("fill")) {
                fills.put(secondSplit[0], secondSplit[1]);
                fill = true;
                continue;
            }
        }
        // Adding default definition if not found in bdef
        Fill f = null;
        try {
            if (hitPoints == -1) {
                block.setHitPoints(Integer.parseInt(defaultDef.get("hit_points")));
                hitPoints = Integer.parseInt(defaultDef.get("hit_points"));
            }
            if (!height) {
                block.setHeight(Integer.parseInt(defaultDef.get("height")));
            }
            if (!width) {
                block.setWidth(Integer.parseInt(defaultDef.get("width")));
            }
            if (!stroke) {
                block.setStroke(new ColorsParser().colorFromString(defaultDef.get("stroke")));
            }
            if (!fill) {
                f = createFill(fillsDef, hitPoints);
            } else {
                Map<String, String> newFill = unionMaps(fillsDef, fills);
                f = createFill(newFill, hitPoints);
            }
        } catch (Exception e) {
            return null;
        }
        if (f != null) {
            block.setFill(f);
        } else {
            return null;
        }
        return block;
    }

    /**
     * The method return valid fill object.
     * @param fills - map
     * @param hitPoints - number of hitpoints
     * @return Fill
     */
    public static Fill createFill(Map<String, String> fills, int hitPoints) {
        String[] fillArray = new String[hitPoints + 1];
        String defaultColor = "color(RGB(154,157,84))";
        if (fills.containsKey("fill")) {
            if (checksValidFill(fills.get("fill"))) {
                defaultColor = fills.get("fill");
                fillArray[0] = "";
            }
        } else {
            defaultColor = "color(RGB(154,157,84))";
        }
        for (int i = 1; i < hitPoints + 1; i++) {
            if (fills.containsKey("fill-" + i)) {
                String s = fills.get("fill-" + i);
                if (!checksValidFill(s)) {
                    return null;
                } else {
                    fillArray[i] = s;
                }
            } else {
                fillArray[i] = defaultColor;
            }
        }
        Fill f = new Fill(fillArray);
        return f;
    }

    /**
     * The method checks if the fill is valid or not.
     * @param s - image or color
     * @return boolean
     */
    public static boolean checksValidFill(String s) {
        try {
            if (s.startsWith("image(")) {
                // Checking that we can open it.
                String path = s.substring(6, s.length() - 1);
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
                Image img = ImageIO.read(is);
                if (img == null) {
                    return false;
                }
            } else {
                ColorsParser cp = new ColorsParser();
                Color c = cp.colorFromString(s);
                if (c == null) {
                    return false;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * The method Union default map and other map and union them together.
     * @param fillsDef - default map
     * @param fills - other map
     * @return new map
     */
    public static Map<String, String> unionMaps(Map<String, String> fillsDef, Map<String, String> fills) {
        Map<String, String> newMap = new TreeMap<String, String>();
        for (int i = 1; i < Math.max(fillsDef.size(), fills.size()) + 2; i++) {
            if (fills.containsKey("fill")) {
                newMap.put("fill", fills.get("fill"));
            } else if (fillsDef.containsKey("fill")) {
                newMap.put("fill", fillsDef.get("fill"));
            }
            if (fills.containsKey("fill-" + String.valueOf(i))) {
                newMap.put("fill-" + String.valueOf(i), fills.get("fill-" + String.valueOf(i)));
            } else if (fillsDef.containsKey("fill-" + String.valueOf(i))) {
                newMap.put("fill-" + String.valueOf(i), fillsDef.get("fill-" + String.valueOf(i)));
            }
        }
        return newMap;
    }
}