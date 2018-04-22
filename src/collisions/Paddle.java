package collisions;

import java.awt.Color;

import animations.GameLevel;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import sprites.Sprite;

/**
 * Class that includes methods and constructor for Paddle object.
 * @author Barak Talmor
 */
public class Paddle implements Sprite, Collidable {
    private KeyboardSensor keyboard;
    private Rectangle rect;
    private Color color;
    private int speedPaddle;

    /**
     * Constructor of paddle.
     * @param rect - the rectangle of the paddle
     * @param color - the color of the paddle
     * @param gui - the gui of the game
     * @param speedPaddle - the move speed for the paddle
     */
    public Paddle(Rectangle rect, Color color, GUI gui, int speedPaddle) {
        this.rect = rect;
        this.color = color;
        this.keyboard = gui.getKeyboardSensor();
        this.speedPaddle = speedPaddle;
    }

    /**
     * The method move the paddle to the right.
     * @param dt - amount of seconds passed since the last frame
     */
    public void moveRight(double dt) {
        int speed = (int) (this.speedPaddle * dt);
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)
                && (this.rect.getLowerRight().getX() < 775 - speed)) {
            this.rect = new Rectangle(this.rect.getUpperLeft().getX() + speed,
                    this.rect.getUpperLeft().getY(), this.rect.getWidth(), this.rect.getHeight());
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)
                && this.rect.getLowerRight().getX() >= 775 - speed) {
            this.rect = new Rectangle(775 - this.rect.getWidth(), this.rect.getUpperLeft().getY(), this.rect.getWidth(),
                    this.rect.getHeight());
        }
    }

    /**
     * The method move the paddle to the left.
     * @param dt - amount of seconds passed since the last frame
     */
    public void moveLeft(double dt) {
        int speed = (int) (this.speedPaddle * dt);
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)
                && this.rect.getUpperLeft().getX() > speed + 25) {
            this.rect = new Rectangle(this.rect.getUpperLeft().getX() - speed,
                    this.rect.getUpperLeft().getY(), this.rect.getWidth(), this.rect.getHeight());
        }
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)
                && this.rect.getUpperLeft().getX() <= speed + 25) {
            this.rect = new Rectangle(25, this.rect.getUpperLeft().getY(), this.rect.getWidth(), this.rect.getHeight());
        }
    }

    /**
     * The method return the rectangle of the paddle.
     * @return rectangle
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * The method operating the move of the paddle.
     * @param dt - amount of seconds passed since the last frame
     */
    public void timePassed(double dt) {
        this.moveLeft(dt);
        this.moveRight(dt);
    }

    /**
     * The method draws the ball.
     * @param d - the DrawSurface for drawing the paddle
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth(), (int) this.rect.getHeight());
        // Draws the frame of the paddle
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth(), (int) this.rect.getHeight());
    }

    /**
     * the method return new velocity to the object, depends where was the hit.
     * @param hitter - the ball that hits
     * @param collisionPoint - the location where was the hit
     * @param currentVelocity - velocity of the object
     * @return velocity - new velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (collisionPoint.isPointOnLine(this.rect.getUpperLine())) {
            return hitOnUpperLine(collisionPoint, currentVelocity);
        } else if (collisionPoint.isPointOnLine(this.rect.getLowerLine())) {
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        if (collisionPoint.isPointOnLine(this.rect.getRightLine())
                || collisionPoint.isPointOnLine(this.rect.getLeftLine())) {
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        return currentVelocity;
    }

    /**
     * The method dividing the upper line to 5 parts and return different
     * angles.
     * depends where the collision point was
     * @param collisionPoint - the location where was the hit
     * @param currentVelocity - velocity of the object
     * @return velocity - new one with another angle
     */
    public Velocity hitOnUpperLine(Point collisionPoint, Velocity currentVelocity) {
        Point leftCorner = this.rect.getUpperLeft();
        int paddleLength = (int) this.rect.getUpperLeft().distance(this.rect.getUppeRight());
        if (collisionPoint.distance(leftCorner) < paddleLength * ((1) / 5.0)) {
            return Velocity.fromAngleAndSpeed(300, currentVelocity.getSpeed());
        } else if (collisionPoint.distance(leftCorner) < paddleLength * ((2) / 5.0)) {
            return Velocity.fromAngleAndSpeed(330, currentVelocity.getSpeed());
        } else if (collisionPoint.distance(leftCorner) < paddleLength * ((3) / 5.0)) {
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        } else if (collisionPoint.distance(leftCorner) < paddleLength * ((4) / 5.0)) {
            return Velocity.fromAngleAndSpeed(30, currentVelocity.getSpeed());
        } else {
            return Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());
        }
    }

    /**
     * Add this paddle to the game.
     * @param g - the game object
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}