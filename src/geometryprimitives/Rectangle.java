package geometryprimitives;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that includes methods and constructor for Rectangle object.
 * @author Barak Talmor
 */

public class Rectangle {
    private Point upperLeft;
    private Point lowerRight;
    private Point upperRight;
    private Point lowerLeft;
    private double width;
    private double height;
    // Rectangle lines
    private Line upper;
    private Line lower;
    private Line right;
    private Line left;

    /**
     * Create a new rectangle with location and width/height.
     * @param upperLeft - the start location of the rectangle
     * @param width - the width of the rectangle
     * @param height - the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.lowerLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
        this.lowerRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);
        this.upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        this.width = width;
        this.height = height;
        // Constructor for the lines of the rectangles
        this.left = new Line(this.upperLeft, this.lowerLeft);
        this.upper = new Line(this.upperLeft, this.upperRight);
        this.right = new Line(this.upperRight, this.lowerRight);
        this.lower = new Line(this.lowerLeft, this.lowerRight);
    }

    /**
     * Create a new rectangle with location and width/height.
     * @param upperLeftX - the x coordinate of start location of the rectangle
     * @param upperLeftY - the y coordinate of start location of the rectangle
     * @param width - the width of the rectangle
     * @param height - the height of the rectangle
     */
    public Rectangle(double upperLeftX, double upperLeftY, double width, double height) {
        this.upperLeft = new Point(upperLeftX, upperLeftY);
        this.lowerLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
        this.lowerRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);
        this.upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        this.width = width;
        this.height = height;
        // Constructor for the lines of the rectangles
        this.left = new Line(this.upperLeft, this.lowerLeft);
        this.upper = new Line(this.upperLeft, this.upperRight);
        this.right = new Line(this.upperRight, this.lowerRight);
        this.lower = new Line(this.lowerLeft, this.lowerRight);
    }

    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     * @param line - a specific line
     * @return interPoints - the intersection points of the line with the rectangle
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> interPoints = new ArrayList<Point>();
        Line[] rectLines = {this.upper, this.lower, this.right, this.left};
        int count = 0;
        for (int i = 0; i < rectLines.length; i++) {
            if (line.isIntersecting(rectLines[i]) && count < 2) {
                interPoints.add(rectLines[i].intersectionWith(line));
                count++;
            }
        }
        // List must contain till 2 intersection points
        if (interPoints.size() > 2) {
            return null;
        } else {
            return interPoints;
        }
    }

    /**
     * Return the width of the rectangle.
     * @return this.width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Return the height of the rectangle.
     * @return this.height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the upper-left point of the rectangle.
     * @return this.upperLeft
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Returns the lower-right point of the rectangle.
     * @return this.lowerRight
     */
    public Point getLowerRight() {
        return this.lowerRight;
    }

    /**
     * Returns the lower-left point of the rectangle.
     * @return this.lowerRight
     */
    public Point getLowerLeft() {
        return this.lowerLeft;
    }

    /**
     * Returns the upper-right point of the rectangle.
     * @return this.upperRight
     */
    public Point getUppeRight() {
        return this.upperRight;
    }

    /**
     * Returns the upper line of the rectangle.
     * @return this.upper
     */
    public Line getUpperLine() {
        return this.upper;
    }

    /**
     * Returns the lower line of the rectangle.
     * @return this.lower
     */
    public Line getLowerLine() {
        return this.lower;
    }

    /**
     * Returns the left line of the rectangle.
     * @return this.left
     */
    public Line getLeftLine() {
        return this.left;
    }

    /**
     * Returns the right line of the rectangle.
     * @return this.lowerRight
     */
    public Line getRightLine() {
        return this.right;
    }
}