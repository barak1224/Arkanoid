package collisions;

import biuoop.DrawSurface;
import geometryprimitives.Line;
import geometryprimitives.Point;
import sprites.Sprite;

import java.awt.Color;

import animations.GameLevel;

/**
 * Class that includes methods and constructor for Ball object.
 * @author Barak Talmor
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    private GameEnvironment gameEnv;

    /**
     * Constructor to build ball object by a point, radius and color.
     * @param center - the position of the ball
     * @param r - the radius of the ball
     * @param color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);
    }

    /**
     * Constructor to build ball object by coordinates, radius and color.
     * @param x - coordinate
     * @param y - coordinate
     * @param r - radius
     * @param color of the ball
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);
    }

    /**
     * Constructor builds ball object by coordinates, radius, color and
     * GameEnvironment.
     * @param x - coordinate
     * @param y - coordinate
     * @param r - radius
     * @param color of the ball
     * @param e - GameEnvironment of the game
     */
    public Ball(double x, double y, int r, Color color, GameEnvironment e) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
        this.gameEnv = e;
        this.velocity = new Velocity(0, 0);
    }

    /**
     * Constructor builds ball object by coordinates, radius, color and
     * GameEnvironment.
     * @param center - the point center of the ball
     * @param r - radius
     * @param color of the ball
     * @param e - GameEnvironment of the game
     */
    public Ball(Point center, int r, Color color, GameEnvironment e) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.gameEnv = e;
        this.velocity = new Velocity(0, 0);
    }

    /**
     * The constructor for the velocity by getting the velocity.
     * @param v - Velocity for the ball
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * The Constructor for the velocity by getting dx and dy.
     * @param dx - the angle by dx
     * @param dy - the speed by dy
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * The method set the gameEnvironment to the ball.
     * @param g - the gameEnvironment
     */
    public void setGameEnviornment(GameEnvironment g) {
        this.gameEnv = g;
    }

    /**
     * The method return the x coordinate of the center.
     * @return coordinate
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * The method return the y coordinate of the center.
     * @return coordinate
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * The method return size of the ball.
     * @return radius
     */
    public int getSize() {
        return radius;
    }

    /**
     * The method return center of the ball.
     * @return center
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * The method return the color of the ball.
     * @return color
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * The method return the color of the ball.
     * @return color
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * The method takes the balls to almost collision hit.
     * @param collision - where the ball should hit
     * @param partion - the partion of the path between the current ball and the
     *        collision
     */
    public void almostCollisionHit(Point collision, double partion) {
        double dx = collision.getX() - this.center.getX();
        double dy = collision.getY() - this.center.getY();
        double applayX = this.center.getX() + dx * partion;
        double applayY = this.center.getY() + dy * partion;
        this.center = new Point(applayX, applayY);
    }

    /**
     * The method help the ball make a move.
     * If the ball is hitting the bounds, it changes
     * his direction.
     * @param dt - amount of seconds passed since the last frame
     */
    public void moveOneStep(double dt) {
        Velocity v = new Velocity(this.velocity.getDx() * dt, this.velocity.getDy() * dt);
        Line trajectory = trajectory(dt, v);
        CollisionInfo colInfo = this.gameEnv.getClosestCollision(trajectory);
        if (colInfo == null) {
            this.center = v.applyToPoint(this.center);
        } else {
            almostCollisionHit(colInfo.collisionPoint(), 0.5);
            Velocity v1 = colInfo.collisionObject().hit(this, colInfo.collisionPoint(), this.velocity);
            this.setVelocity(v1);
            if (this.center.isPointOnLine(colInfo.collisionObject().getCollisionRectangle().getUpperLine())) {
                this.center.setY(this.center.getY() - 1);
            }
        }
    }

    /**
     * the method builds the trajectory line of the ball.
     * @param dt - amount of seconds passed since the last frame
     * @param v - velocity multiply by dt
     * @return trajectory line
     */
    public Line trajectory(double dt, Velocity v) {
        Point tempEndTraj = v.applyToPoint(this.center);
        return new Line(this.center, v.applyToPoint(tempEndTraj));
    }

    /**
     * Method that notifies the ball that time has passed.
     * @param dt - amount of seconds passed since the last frame
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * The method draw the ball on the board.
     * @param surface - helps to draw on the board
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), this.radius);
        surface.setColor(Color.BLACK);
    }

    /**
     * Method adds the ball object to the game spirte list.
     * @param game - the game
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * Method removes the ball object from the game spirte list.
     * @param game - the game
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }
}
