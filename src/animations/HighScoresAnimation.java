package animations;

import java.awt.Color;
import java.util.List;

import biuoop.DrawSurface;
import io.HighScoresTable;

/**
 * The class of HighScoresAnimation.
 * @author Barak Talmor
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable scores;

    /**
     * The constructor of HighScoresAnimation.
     * @param scores - all scores table
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 800, 600);
        // Title "Hight Scores"
        d.setColor(Color.BLACK);
        d.drawText(250, 66, "High Scores", 50);
        d.setColor(Color.GREEN);
        d.drawText(254, 66, "High Scores", 50);
        d.setColor(Color.BLACK);
        d.drawText(258, 66, "High Scores", 50);
        // Title Player Name and Score
        d.setColor(Color.BLACK);
        d.drawText(120, 150, "Player Name", 30);
        d.setColor(Color.BLACK);
        d.drawText(520, 150, "Score", 30);
        // Line
        d.setColor(Color.BLACK);
        d.drawLine(120, 160, 600, 160);
        d.setColor(Color.BLUE);
        d.drawLine(120, 161, 600, 161);
        d.setColor(Color.BLUE);
        d.drawLine(120, 162, 600, 162);
        d.setColor(Color.BLACK);
        d.drawLine(120, 163, 600, 163);
        List<ScoreInfo> hs = this.scores.getHighScores();
        for (int i = 0; i < hs.size(); i++) {
            d.setColor(Color.BLACK);
            d.drawText(120, 190 + i * 40, hs.get(i).getName(), 30);
            d.setColor(Color.BLACK);
            d.drawText(520, 190 + i * 40, "" + hs.get(i).getScore(), 30);
        }
        d.setColor(Color.BLACK);
        d.drawText(111, 550, "PRESS SPACE TO CONTINUE", 40);
        d.setColor(Color.BLUE);
        d.drawText(114, 550, "PRESS SPACE TO CONTINUE", 40);
        d.setColor(Color.BLACK);
        d.drawText(117, 550, "PRESS SPACE TO CONTINUE", 40);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }

    /**
     * The method returns scores table.
     * @return scores
     */
    public HighScoresTable getScores() {
        return this.scores;
    }
}