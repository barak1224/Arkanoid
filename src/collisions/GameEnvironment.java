package collisions;
import java.util.ArrayList;
import java.util.List;

import geometryprimitives.Line;
import geometryprimitives.Point;

/**
 * The class which contains the methods of the GameEnvironment object.
 * @author Barak Talmor
 */
public class GameEnvironment {
    private ArrayList<Collidable> colliList;

    /**
     * The constructor for the gameEnvironment.
     */
    public GameEnvironment() {
        this.colliList = new ArrayList<Collidable>();
    }

    /**
     * The method gets collidable object and add it to the list.
     * @param c - collidable object
     */
    public void addCollidable(Collidable c) {
        this.colliList.add(c);
    }

    /**
     * The method gets collidable object and remove it to the list.
     * @param c - collidable object
     */
    public void removeCollidable(Collidable c) {
        this.colliList.remove(c);
    }

    /**
     * The method returns the collidable list which contain the objects in the
     * game.
     * @return colliList
     */
    public ArrayList<Collidable> getColliList() {
        return this.colliList;
    }

    /**
     * The method checks if there is an collision with the trajectory.
     * @param trajectory - the path of the moving object
     * @return true if there is collision, otherwise, returns false
     */
    public boolean isCollidingWith(Line trajectory) {
        return (this.getClosestCollision(trajectory) != null);
    }

    /**
     * The method returns the closest collision info to the line.
     * @param trajectory - the direction of the moving object
     * @return closetInfo
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closestInfo = null;
        Point closest;
        double dstClose, dsTemp;
        List<CollisionInfo> collisions = new ArrayList<CollisionInfo>();
        // Adding all collisions info with the trajectory into a list
        for (Collidable block : this.colliList) {
            if (!block.getCollisionRectangle().intersectionPoints(trajectory).isEmpty()) {
                closest = trajectory.closestIntersectionToStartOfLine(block.getCollisionRectangle());
                collisions.add(new CollisionInfo(closest, block));
            }
        }
        // Checking which is the closest collision
        if (!collisions.isEmpty()) {
            closestInfo = collisions.get(0);
            dstClose = closestInfo.collisionPoint().distance(trajectory.start());
            for (int i = 1; i < collisions.size(); i++) {
                dsTemp = collisions.get(i).collisionPoint().distance(trajectory.start());
                if (dsTemp < dstClose) {
                    closestInfo = collisions.get(i);
                }
            }
        }
        return closestInfo;
    }
}