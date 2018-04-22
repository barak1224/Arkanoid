package collisions;
import geometryprimitives.Point;

/**
 * The calss which contains method for the collision info of the collidable object.
 * @author barak
 *
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

   /**
     * The constructor of the CollisionInfo object.
     * @param collisionPoint - the point where was the collision
     * @param collisionObject - the object where was the collision on
     */
   public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
       this.collisionPoint = collisionPoint;
       this.collisionObject = collisionObject;
   }

   /**
    * The method return the point at which the collision occurs.
    * @return collisionPoint
    */
   public Point collisionPoint() {
       return this.collisionPoint;
   }

   /**
    * The method return the collidable object involved in the collision.
    * @return collisionObject
    */
   public Collidable collisionObject() {
       return this.collisionObject;
   }
}