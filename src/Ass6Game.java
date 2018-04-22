

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import animations.AnimationRunner;
import animations.HighScoresAnimation;
import animations.Menu;
import animations.MenuAnimation;
import animations.QuitTask;
import animations.ShowHiScoresTask;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import io.HighScoresTable;
import io.LevelsTask;
import io.Task;

/**
 * The ass3 assignment starter.
 * @author Barak Talmor
 */
public class Ass6Game {
    static final int LIVES = 7;

    /**
     * The method start the game.
     * @param args none
     */
    public static void main(String[] args) {
        String levelSetsDirection;
        if (args.length > 0) {
            levelSetsDirection = args[0];
        } else {
            levelSetsDirection = "level_sets.txt";
        }
        GUI gui = new GUI("Arknoid", 800, 600);
        AnimationRunner ar = new AnimationRunner(gui);
        KeyboardSensor ks = ar.getGui().getKeyboardSensor();
        Menu<Task<Void>> menu = new MenuAnimation<>("Arknoid", ar, ks);
        Menu<Task<Void>> subMenu = new MenuAnimation<>("Arknoid", ar, ks);
        try {
            buildSubMenu(subMenu, ar, ks, levelSetsDirection);
        } catch (IOException e) {
            ;
        }
        menu.addSubMenu("s", "Start Game", subMenu);
        HighScoresTable hST = new HighScoresTable(5);
        menu.addSelection("h", "High Scores", new ShowHiScoresTask(ar, new HighScoresAnimation(hST)));
        menu.addSelection("q", "Quit", new QuitTask());
        while (true) {
            ar.run(menu);
            Task<Void> status = menu.getStatus();
            if (status == null) {
                ar.run(subMenu);
                Task<Void> status2 = subMenu.getStatus();
                status2.run();
                subMenu.resetStop();
            } else {
                status.run();
            }
            menu.resetStop();
        }
    }

    /**
     * The method extracting the level sets file and them to the sub menu.
     * @param subMenu - menu animation
     * @param ar animation runner
     * @param ks keyboard sensor
     * @param path - where to locate the file
     * @throws IOException problem in reading file
     */
    public static void buildSubMenu(Menu<Task<Void>> subMenu, AnimationRunner ar, KeyboardSensor ks, String path)
            throws IOException {
        LineNumberReader bReader = null;
        String key = null, message = null;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
            bReader = new LineNumberReader(new InputStreamReader(is));
            String line;
            while ((line = bReader.readLine()) != null) {
                // level set's name
                if (bReader.getLineNumber() % 2 == 1) {
                    String[] split = line.split(":");
                    key = split[0];
                    message = split[1];
                    // class path
                } else {
                    subMenu.addSelection(key, message, new LevelsTask(ar, ks, LIVES, line));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.print("File Couldn't open");
        } finally {
            if (bReader != null) {
                bReader.close();
            }
        }
    }
}
