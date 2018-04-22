package animations;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * The class which is to run the animation of the class.
 * @author Barak Talmor
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**
     * The constructor of the AnimationRunner with default framesPerSecond.
     * @param gui the screen
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        this.framesPerSecond = 60;
        this.sleeper = new biuoop.Sleeper();
    }

    /**
     * The constructor of the AnimationRunner.
     * @param framesPerSecond - int
     * @param gui the screen
     */
    public AnimationRunner(int framesPerSecond, GUI gui) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        this.sleeper = new biuoop.Sleeper();
    }

    /**
     * The constrSuctor return the gui.
     * @return gui
     */
    public GUI getGui() {
        return this.gui;
    }

    /**
     * The method which runs the animation loop.
     * @param animation - an animation object
     */
    public void run(Animation animation) {
        double dt = (double) (1.0 / this.framesPerSecond);
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d, dt);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}