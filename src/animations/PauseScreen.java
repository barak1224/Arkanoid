package animations;
import java.awt.Color;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The class charges the pause screen.
 * @author Barak Talmor
 */
public class PauseScreen implements Animation {
//    private KeyboardSensor keyboard;
//    private boolean stop;

    /**
     * The constructor of the pause screen object.
     * @param k - the pressed key
     */
    public PauseScreen(KeyboardSensor k) {
//        this.keyboard = k;
////        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        // Paused announce
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.BLACK);
        d.drawText(260, d.getHeight() / 3, "PAUSED", 60);
        d.setColor(Color.BLUE);
        d.drawText(264, d.getHeight() / 3, "PAUSED", 60);
        d.setColor(Color.BLACK);
        d.drawText(268, d.getHeight() / 3, "PAUSED", 60);
        // Guidance to continue
        d.drawText(111, d.getHeight() * 2 / 3, "PRESS SPACE TO CONTINUE", 40);
        d.setColor(Color.RED);
        d.drawText(114, d.getHeight() * 2 / 3, "PRESS SPACE TO CONTINUE", 40);
        d.setColor(Color.BLACK);
        d.drawText(117, d.getHeight() * 2 / 3, "PRESS SPACE TO CONTINUE", 40);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}