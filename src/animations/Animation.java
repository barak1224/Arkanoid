package animations;
import biuoop.DrawSurface;

/**
 * The interface of Animation.
 * @author Barak Talmor
 */
public interface Animation {
    /**
     * The method which charge of the logic.
     * @param d - DrawSurface
     * @param dt - amount of seconds passed since the last frame
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * The method which charge of stopping condition.
     * @return boolean
     */
    boolean shouldStop();
}