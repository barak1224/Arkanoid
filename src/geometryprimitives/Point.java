package geometryprimitives;
/**
 * The class includes methods for Point object.
 * @author Barak Talmor
 */
public class Point {
    private double x;
    private double y;

    /**
     * Constructor of class Point.
     * @param x - coordinate x for the ball position
     * @param y - coordinate y for the ball position
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The method set new x to the point.
     * @param newx - coordinate
     */
    public void setX(double newx) {
        this.x = newx;
    }

    /**
     * The method set new y to the point.
     * @param newY - coordinate
     */
    public void setY(double newY) {
        this.y = newY;
    }

    /**
     * The method return the coordinate x of the point.
     * @return x - coordinate
     */
    public double getX() {
        return this.x;
    }

    /**
     * The method return the coordinate y of the point.
     * @return y - coordinate
     */
    public double getY() {
        return this.y;
    }

    /**
     * return the distance between this point and the other point.
     * @param other - a Point object
     * @return distance between points
     */
    public double distance(Point other) {
        double dx, dy;
        dx = (this.x - other.getX()) * (this.x - other.getX());
        dy = (this.y - other.getY()) * (this.y - other.getY());
        return Math.sqrt(dx + dy);
    }

    /**
     * The method return true if the points are equal, false otherwise.
     * @param other - a Point object
     * @return boolean
     */
    public boolean equals(Point other) {
        return (Math.abs(this.x - other.getX()) < 0.00000001
                && (Math.abs(this.y - other.getY()) < 0.00000001));
    }

    /**
     * The method checks if the point is on the line segment by using triangle inequality.
     * @param line - object
     * @return boolean
     */
    public boolean isPointOnLine(Line line) {
        double d1, d2, d3;
        d1 = this.distance(line.start());
        d2 = this.distance(line.end());
        d3 = line.start().distance(line.end());
        return (Math.abs((d1 + d2) - d3) < 0.00000001);
    }
}