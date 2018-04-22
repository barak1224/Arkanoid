package animations;

import java.io.File;
import java.io.IOException;

import biuoop.KeyboardSensor;
import io.HighScoresTable;
import io.Task;

/**
 * The class of ShowHiScoresTask.
 * @author Barak Talmor
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private HighScoresAnimation highScoresAnimation;
    private HighScoresTable hST;
    private KeyboardSensor keyboard;

    /**
     * The consturcor for the ShowHiScoresTask.
     * @param runner - animationRunner
     * @param highScoresAnimation - animation of HighScoresAnimation
     */
    public ShowHiScoresTask(AnimationRunner runner, HighScoresAnimation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
        this.hST = highScoresAnimation.getScores();
        this.keyboard = runner.getGui().getKeyboardSensor();
    }

    @Override
    public Void run() {
        loadTable(this.hST);
        this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", this.highScoresAnimation));
        return null;
    }

    /**
     * The method load the table.
     * @param hST - HighScoresTable object
     */
    public static void loadTable(HighScoresTable hST) {
        try {
            File f = new File("highscores.txt");
            if (!f.createNewFile()) {
                hST.load(f);
            }
        } catch (IOException e) {
            ;
        }
    }
}