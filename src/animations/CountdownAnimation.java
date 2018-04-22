package animations;
import java.awt.Color;
import sprites.SpriteCollection;
import biuoop.DrawSurface;

/**
 * The class which charges on the countdown on the screen.
 * @author Barak Talmor
 */
public class CountdownAnimation implements Animation {
    private boolean stop;
    private boolean firsTime;
    private double numOfSeconds;
    private long starTime;
    //private int originCountFrom;
    private int countFrom;
    private SpriteCollection gameScreen;
    private double sleepTime;

    /**
     * The constructor of the countdownAnimation object.
     * @param numOfSeconds -
     * @param countFrom - prints from countFrom back to 1
     * @param gameScreen - sprite collection
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.sleepTime = this.numOfSeconds / this.countFrom;
        this.stop = false;
        this.firsTime = true;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.gameScreen.drawAllOn(d);
        if (this.firsTime) {
            this.starTime = System.currentTimeMillis();
            this.firsTime = false;
        }
        int time = (int) Math.ceil(countFrom
                - (System.currentTimeMillis() - this.starTime) / (this.sleepTime * 1000.0));
        if (System.currentTimeMillis() < (numOfSeconds * 1000) + this.starTime) {
            d.setColor(Color.BLUE);
            String strCount = "" + time;
            d.drawText(d.getWidth() / 2, d.getHeight() / 2 + 100, strCount, 40);
        } else {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
