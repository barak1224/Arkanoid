package animations;

import java.io.Serializable;

/**
 * The class of the scoreInfo object.
 * @author Barak Talmor
 *
 */
public class ScoreInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int score;

    /**
     * Constructor of the scoreInfo object.
     * @param name - the name of the player with high score.
     * @param score - the score of the player.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * The method return the name of scoreInfo.
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * The method return the score of scoreInfo.
     * @return score
     */
    public int getScore() {
        return this.score;
    }
}
