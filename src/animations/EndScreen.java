package animations;

import java.awt.Color;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import listeners.Counter;

/**
 * The class which charges on the end screen.
 * @author Barak Talmor
 */
public class EndScreen implements Animation {
    private Counter lives;
    private Counter score;
//    private KeyboardSensor keyboard;
//    private boolean stop;

    /**
     * The constructor of the end screen.
     * @param lives that remains from the game
     * @param score from the game
     * @param keyboard to check to get out
     */
    public EndScreen(Counter lives, Counter score, KeyboardSensor keyboard) {
        this.lives = lives;
        this.score = score;
//        this.stop = false;
//        this.keyboard = keyboard;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.BLACK);
        if (this.lives.getValue() > 0) {
            d.drawText(60, d.getHeight() / 3, "YOU WIN!", 60);
            d.setColor(Color.green);
            d.drawText(63, d.getHeight() / 3, "YOU WIN!", 60);
            d.setColor(Color.BLACK);
            d.drawText(66, d.getHeight() / 3, "YOU WIN!", 60);
        } else {
            d.drawText(60, d.getHeight() / 3, "GAME OVER", 60);
            d.setColor(Color.RED);
            d.drawText(63, d.getHeight() / 3, "GAME OVER", 60);
            d.setColor(Color.BLACK);
            d.drawText(66, d.getHeight() / 3, "GAME OVER", 60);

        }
        //Drawing score announcement
        d.setColor(Color.BLACK);
        d.drawText(60, d.getHeight() / 2, "YOUR SCORE IS:" + this.score.getValue(), 60);
        d.setColor(Color.blue);
        d.drawText(63, d.getHeight() / 2, "YOUR SCORE IS:" + this.score.getValue(), 60);
        d.setColor(Color.BLACK);
        d.drawText(66, d.getHeight() / 2, "YOUR SCORE IS:" + this.score.getValue(), 60);
        // Drawing the exit guidance
        d.setColor(Color.BLACK);
        d.drawText(60, d.getHeight() * 4 / 5, "PRESS SPACE TO EXIT", 40);
        d.setColor(Color.DARK_GRAY);
        d.drawText(63, d.getHeight() * 4 / 5, "PRESS SPACE TO EXIT", 40);
        d.setColor(Color.BLACK);
        d.drawText(66, d.getHeight() * 4 / 5, "PRESS SPACE TO EXIT", 40);


    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
