package collisions;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import animations.Fill;
import animations.GameLevel;
import biuoop.DrawSurface;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import listeners.HitListener;
import sprites.Sprite;

/**
 * The class which contains the methods of the Block object.
 * @author Barak Talmor
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private Fill fillColor;
    private Color frameColor;
    private int hitPoints;
    private ArrayList<HitListener> hitListeners;

    /**
     * Constructor of the black with rectangle, color and default hitPoints.
     * @param rect - the rectangle of the block
     * @param fillColor - the color of the block
     * @param frameColor - the frame color of the block
     */
    public Block(Rectangle rect, Fill fillColor, Color frameColor) {
        this.rect = rect;
        this.fillColor = fillColor;
        this.frameColor = frameColor;
        this.hitPoints = 0;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * Constructor of the black with rectangle, color and hitPoints.
     * @param rect - the rectangle of the block
     * @param fillColor - the color of the block
     * @param frameColor - the frame color of the block
     * @param hitPoints - the number of the block can be hit
     */
    public Block(Rectangle rect, Fill fillColor, Color frameColor, int hitPoints) {
        this.rect = rect;
        this.fillColor = fillColor;
        this.frameColor = frameColor;
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * Constructor of tthe black with rectangle, color, hitPoints and
     * blockRemover..
     * @param rect - the rectangle of the block
     * @param fillColor - the color of the block
     * @param frameColor - the frame color of the block
     * @param hitPoints - the number of the block can be hit
     * @param hL - hitListener
     */
    public Block(Rectangle rect, Fill fillColor, Color frameColor, int hitPoints, HitListener hL) {
        this.rect = rect;
        this.fillColor = fillColor;
        this.frameColor = frameColor;
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
        hitListeners.add(hL);
    }

    /**
     * Constructor of tthe black with rectangle, color, hitPoints and
     * blockRemover..
     * @param rect - the rectangle of the block
     * @param fillColor - the color of the block
     * @param frameColor - the frame color of the block
     * @param hitPoints - the number of the block can be hit
     * @param listHL - list of listener
     */
    public Block(Rectangle rect, Fill fillColor, Color frameColor, int hitPoints, List<HitListener> listHL) {
        this.rect = rect;
        this.fillColor = fillColor;
        this.frameColor = frameColor;
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>(listHL);
    }

    /**
     * The method return the number of hit points of the block.
     * @return hitPoints
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Return the "collision shape" of the object.
     * @return rect - the rectangle of the block
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * The method return new velocity by checking where the collisionPoint was
     * hit and decrease the hitPoints.
     * @param hitter - the ball that hits
     * @param collisionPoint - the location where was the collision
     * @param currentVelocity - of the ball
     * @return Velocity - the new velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx(), dy = currentVelocity.getDy();
        this.hitPoints--;
        this.notifyHit(hitter);
        if (collisionPoint.isPointOnLine(this.rect.getUpperLine())
                || collisionPoint.isPointOnLine(this.rect.getLowerLine())) {
            dy *= -1;
        }
        if (collisionPoint.isPointOnLine(this.rect.getLeftLine())
                || collisionPoint.isPointOnLine(this.rect.getRightLine())) {
            dx *= -1;
        }
        // Changing velocity by where it was hit.
        return new Velocity(dx, dy);
    }

    /**
     * The method draw the ball on the board.
     * @param surface - helps to draw on the board
     */
    public void drawOn(DrawSurface surface) {
        if (this.hitPoints < 0) {
            this.hitPoints = 1;
        }
        this.fillColor.drawon(surface, this.hitPoints, (int) this.rect.getUpperLeft().getX(),
                (int) this.rect.getUpperLeft().getY(), (int) this.rect.getWidth(), (int) this.rect.getHeight());
        if (this.frameColor != null) {
            surface.setColor(this.frameColor);
            surface.drawRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY(),
                    (int) this.rect.getWidth(), (int) this.rect.getHeight());
        }
    }

    /**
     * Notify hit to the block.
     * @param hitter - the ball that hits
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Method that notifies the block that time has passed.
     * @param dt - amount of seconds passed since the last frame
     */
    public void timePassed(double dt) {
    }

    /**
     * Add list of listeners to hit events.
     * @param hList - list of hitListener object
     */
    public void addHitListener(List<HitListener> hList) {
        this.hitListeners.addAll(hList);
    }

    /**
     * Add hl as a listener to hit events.
     * @param hl - hitListener object
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl - hitListener object
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Method adds the block object to the game spirte list and to the
     * gameEnvironment.
     * @param game - the game object
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
        game.addCollidable(this);
    }

    /**
     * Method removes the block object from the game spirte list and from the
     * gameEnvironment.
     * @param game - the game object
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }
}
