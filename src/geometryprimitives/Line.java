package geometryprimitives;
import java.util.List;

/**
 * The class includes methods for Line object.
 * @author Barak Talmor
 */
public class Line {
    private Point start;
    private Point end;
    private double aX;
    private double bY;
    private double c;

    /**
     * Constructor to build the line by two Point variables as params.
     * @param start - the point, where the line starts
     * @param end - the point, where the line ends
     */
    public Line(Point start, Point end) {
        // Checking that the points were sent in the right order
        if (start.getX() < end.getX()) {
            this.start = new Point(start.getX(), start.getY());
            this.end = new Point(end.getX(), end.getY());
        } else {
            this.start = new Point(end.getX(), end.getY());
            this.end = new Point(start.getX(), start.getY());
        }
        lineEquationParams();
    }

    /**
     * Constructor to build the line by getting only coordinates as params.
     * @param x1 - the x coordinate for the start point
     * @param y1 - the y coordinate for the start point
     * @param x2 - the x coordinate for the end point
     * @param y2 - the y coordinate for the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        // Checking that the points were sent in the right order
        if (x1 < x2) {
            this.start = new Point(x1, y1);
            this.end = new Point(x2, y2);
        } else {
            this.start = new Point(x2, y2);
            this.end = new Point(x1, y1);
        }
        lineEquationParams();
    }

    /**
     * The method calculating the coefficient for the equation line params.
     * by the format aX + bY = c
     * params - none
     */
    public void lineEquationParams() {
        this.aX = this.end.getY() - this.start.getY();
        this.bY = this.start.getX() - this.end.getX();
        this.c = this.aX * this.start.getX() + this.bY * this.start.getY();
    }

    /**
     * @return return the aX param of the equation line
     */
    public double getAX() {
        return this.aX;
    }

    /**
     * @return return the bY param of the equation line
     */
    public double getBY() {
        return this.bY;
    }

    /**
     * @return return the c param of the equation line
     */
    public double getC() {
        return this.c;
    }

    /**
     * @return return the length of the line
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * The method finds the middle point of the line segment and returns it.
     * @return middle - point that is in the middle of the line segment
     */
    public Point middle() {
        double middleX, middleY;
        middleX = (this.start.getX() + this.end.getX()) / 2;
        middleY = (this.start.getY() + this.end.getY()) / 2;
        Point middle = new Point(middleX, middleY);
        return middle;
    }

    /**
     * The method returns the end line segment point.
     * @return start - the point of the start line segment
     */
    public Point start() {
        return this.start;
    }

    /**
     * The method returns the end point of the end line segment.
     * @return end - the point of the end line segment
     */
    public Point end() {
        return this.end;
    }

    /**
     * The method checks if there is an intersection point between two lines.
     * @param other line variable
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return (this.intersectionWith(other) != null);
    }

    /**
     * The method finds the intersection point with determinant equation.
     * @param other line variable
     * @return the intersection point if the lines intersect, otherwise null
     */
    public Point intersectionWith(Line other) {
        double det, interX, interY;
        Point interPoint;
        // Calculating the determinant of the equation system for the both lines
        // A1 + B1 = C1
        // A2 + B2 = C2
        det = (this.aX * other.getBY()) - (other.getAX() * this.bY);
        // if determinant is 0, it means that there is not an intersection point
        if (det == 0) {
            return null;
        } else {
            // The X coordinate of intersection point is (B2*C1 - B1*C2)/det
            interX = ((other.getBY() * this.c) - (this.bY * other.getC())) / det;
            // The Y coordinate of intersection point is (A1*C2 - A2*C1)/det
            interY = ((this.getAX() * other.getC()) - (other.getAX() * this.c)) / det;
            // Checking if the intersection point is out of range of both lines
            interPoint = new Point(interX, interY);
            if (interPoint.isPointOnLine(this) && interPoint.isPointOnLine(other)) {
                return new Point(interX, interY);
            } else {
                return null;
            }
        }
    }

    /**
     * The method checks if the two lines are equal.
     * @param other line variable
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return this.start.equals(other.start) && this.end.equals(other.end);
    }

    /**
     * The method finds the closet intersection point to the start of the line.
     * @param rect - the rectangle of the object
     * @return Point
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> interPoints = rect.intersectionPoints(this);
        if (interPoints.isEmpty()) {
            return null;
        } else if (interPoints.size() == 1) {
            return interPoints.get(0);
        } else if (this.start.distance(interPoints.get(0))
                < this.start.distance(interPoints.get(1))) {
            return interPoints.get(0);
        } else {
            return interPoints.get(1);
        }
    }
}
