package collisions;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;

/**
 * The interface which contains declaration of the colliadable object.
 * @author barak
 */
public interface Collidable {
   /**
    * The method the "collision shape" of the object.
    * @return rect - rectangle object
    */
   Rectangle getCollisionRectangle();

   /**
    * The method returns new velocity expected after the hit (based on
    * the force the object inflicted on us).
    * @param hitter - the ball that hits
    * @param collisionPoint - the point where the collision happened
    * @param currentVelocity - the current velocity of the object
    * @return velocity - new velocity
    */
   Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}