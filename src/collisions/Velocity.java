package collisions;
import geometryprimitives.Point;

/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 * @author Barak Talmor
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructor to build Velocity object.
     * @param dx - the change by x
     * @param dy - the change by y
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * The method return the dx of the object.
     * @return dx
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * The method return the dy of the object.
     * @return dy
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * The method return the speed of the object.
     * @return speed
     */
    public double getSpeed() {
        return Math.sqrt((this.getDx() * this.getDx()) + (this.getDy() * this.getDy()));
    }

    /**
     * The method add to the point dx and dy to change the position.
     * @param p a point object.
     * @return new point with new position
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * The method change the angle and the speed of the velocity.
     * @param angle of the velocity
     * @param speed of the velocity
     * @return new velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.cos(Math.toRadians(angle - 90.0)) * speed;
        double dy = Math.sin(Math.toRadians(angle - 90.0)) * speed;
        return new Velocity(dx, dy);
    }
}