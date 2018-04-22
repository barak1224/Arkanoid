package animations;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import biuoop.KeyboardSensor;
import collisions.Ball;
import collisions.Block;
import collisions.Collidable;
import collisions.GameEnvironment;
import collisions.Paddle;
import collisions.Velocity;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import levels.LevelInformation;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.Counter;
import listeners.HitListener;
import listeners.ScoreTrackingListener;
import sprites.LivesIndicator;
import sprites.NameIndicator;
import sprites.ScoreIndicator;
import sprites.Sprite;
import sprites.SpriteCollection;
import biuoop.DrawSurface;

/**
 * The class for creating the game for assignment 3.
 * @author Barak Talmor
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;
    private Counter lives;
    private AnimationRunner runner;
    private boolean running;
    private Paddle paddle;
    private KeyboardSensor keyboard;
    private LevelInformation levelInfo;

    /**
     * Constructor of the game.
     * @param levelInfo - an level object
     * @param k - keyboard sensor
     * @param aR - animation runner
     * @param score - the counter of the score
     * @param lives - the counter of the remaining lives
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor k, AnimationRunner aR, Counter score, Counter lives) {
        this.levelInfo = levelInfo;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter(this.levelInfo.numberOfBlocksToRemove());
        this.remainingBalls = new Counter(0);
        this.score = score;
        this.lives = lives;
        this.running = true;
        this.runner = aR;
        this.keyboard = k;
    }

    /**
     * The method gets collidable object and add it to the spriteCollection.
     * @param c - collidable object
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * The method gets collidable object and remove it to the list.
     * @param c - collidable object
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * The method gets sprite object and add it to the spriteCollection.
     * @param s - sprite object
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * The method gets sprite object and remove it to the spriteCol.
     * @param s - sprite object
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * The methods remove the paddle from the game.
     */
    public void removePaddle() {
        this.removeCollidable(this.paddle);
        this.removeSprite(this.paddle);
    }

    /**
     * The method return the number of remaining blocks in the level.
     * @return remainingBlocks.getValue()
     */
    public int getNumberOfRemainingBlocks() {
        return this.remainingBlocks.getValue();
    }

    /**
     * The method organize all indicator sprites and add it to the
     * game.
     */
    public void addIndicatorSprites() {
        Sprite scoreI = new ScoreIndicator(this.score);
        Sprite livesRemain = new LivesIndicator(this.lives);
        Sprite nameLevel = new NameIndicator(this.levelInfo.levelName());
        this.sprites.addSprite(scoreI);
        this.sprites.addSprite(livesRemain);
        this.sprites.addSprite(nameLevel);
    }

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle)
     * and add them to the game.
     */
    public void initialize() {
        this.sprites.addSprite(this.levelInfo.getBackground());
        this.buildsFrame();
        this.addIndicatorSprites();
        this.buildsBlocks();
    }

    /**
     * The method builds the frames and add them to the game.
     */
    public void buildsFrame() {
        HitListener ballRemover = new BallRemover(this, this.remainingBalls);
        String[] s = new String[2];
        s[0] = "";
        s[1] = "color(gray)";
        Fill f = new Fill(s);
        Block frameBlock1 = new Block(new Rectangle(new Point(0, 20), 780, 25), f, Color.GRAY, 0);
        Block frameBlock2 = new Block(new Rectangle(new Point(0, 20), 25, 650), f, Color.GRAY, 0);
        Block frameBlock4 = new Block(new Rectangle(new Point(775, 20), 25, 650), f, Color.GRAY, 0);
        // Death region
        Block frameBlock3 = new Block(new Rectangle(new Point(-50, 610), 850, 20), f, Color.GRAY, 0,
                ballRemover);
        frameBlock1.addToGame(this);
        frameBlock2.addToGame(this);
        frameBlock3.addToGame(this);
        frameBlock4.addToGame(this);
    }

    /**
     * The method builds the blocks and add them to the game.
     */
    public void buildsBlocks() {
        HitListener bR = new BlockRemover(this, this.remainingBlocks);
        HitListener scoreTracking = new ScoreTrackingListener(this.score);
        List<HitListener> listHL = new ArrayList<HitListener>(Arrays.asList(bR, scoreTracking));
        for (Block block : this.levelInfo.blocks()) {
            block.addHitListener(listHL);
            block.addToGame(this);
        }
    }

    /**
     * The method builds the balls and add them to the game.
     */
    public void buildsBallsAndPuddle() {
        for (int i = 0; i < this.levelInfo.numberOfBalls(); i++) {
            Ball ball = new Ball(new Point(400, 550), 5, Color.LIGHT_GRAY);
            Velocity v = this.levelInfo.initialBallVelocities().get(i);
            ball.setVelocity(new Velocity(v.getDx(), v.getDy()));
            ball.addToGame(this);
            ball.setGameEnviornment(this.environment);
        }
        this.paddle = new Paddle(
                new Rectangle(400 - this.levelInfo.paddleWidth() / 2, 565, this.levelInfo.paddleWidth(), 15),
                Color.ORANGE, runner.getGui(), this.levelInfo.paddleSpeed());
        this.remainingBalls.increase(this.levelInfo.numberOfBalls());
        paddle.addToGame(this);
    }

    /**
     * The method choose the exact color by the index of the row.
     * @param index - the row number of the blocks
     * @return random color
     */
    public Color chooseColor(int index) {
        Color color;
        switch (index) {
        case 0:
            color = Color.YELLOW;
            break;
        case 1:
            color = Color.RED;
            break;
        case 2:
            color = Color.ORANGE;
            break;
        case 3:
            color = Color.GREEN;
            break;
        case 4:
            color = Color.CYAN;
            break;
        case 5:
            color = Color.MAGENTA;
            break;
        default:
            color = Color.WHITE;
        }
        return color;
    }

    /**
     * The method returns true if the game should stop, otherwise false.
     * @return boolean
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * The method charges on the logic of the game.
     * @param d - DrawSurface
     * @param dt - amount of seconds passed since the last frame
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        if (this.keyboard.isPressed("p") || this.keyboard.isPressed("P")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space",
                    new PauseScreen(this.keyboard)));
            this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        }
        if (this.remainingBlocks.getValue() == 0) {
            this.score.increase(100);
            this.running = false;
        }
        if (this.remainingBalls.getValue() == 0) {
            this.lives.decrease(1);
            this.running = false;
        }
    }

    /**
     * The method runs the game and start the animation loop.
     */
    public void playOneTurn() {
        this.buildsBallsAndPuddle();
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        this.runner.run(this);
        this.removePaddle();
    }
}