public class Rectangle implements RectangleInterface{
    // instance variables to store the minimum x, y and maximum x, y coordinates of the rectangle
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    // constructor to initialize the minimum and maximum x, y coordinates of the rectangle
    public Rectangle(int xmin, int ymin, int xmax, int ymax) {
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }

    // method to return the minimum x-coordinate of the rectangle
    public int xmin() {
        return xmin;
    }

    // method to return the minimum y-coordinate of the rectangle
    public int ymin() {
        return ymin;
    }

    // method to return the maximum x-coordinate of the rectangle
    public int xmax() {
        return xmax;
    }

    // method to return the maximum y-coordinate of the rectangle
    public int ymax() {
        return ymax;
    }

    // method to determine if the point p is inside or on the sides of the rectangle
    public boolean contains(Point p) {
        return p.x() >= xmin && p.x() <= xmax && p.y() >= ymin && p.y() <= ymax;
    }

    // method to determine if the two rectangles intersect
    public boolean intersects(Rectangle that) {
        return this.xmax >= that.xmin && this.xmin <= that.xmax &&
               this.ymax >= that.ymin && this.ymin <= that.ymax;
    }

    // method to calculate the square of the Euclidean distance from a point p to the closest point in the rectangle
    public int squareDistanceTo(Point p) {
        int x = Math.max(Math.min(p.x(), xmax), xmin);
        int y = Math.max(Math.min(p.y(), ymax), ymin);
        int xDiff = p.x() - x;
        int yDiff = p.y() - y;
        return xDiff * xDiff + yDiff * yDiff;
    }

    public double distanceTo(Point p){
        int x = Math.max(Math.min(p.x(), xmax), xmin);
        int y = Math.max(Math.min(p.y(), ymax), ymin);
        int xDiff = p.x() - x;
        int yDiff = p.y() - y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    // method to return a string representation of the rectangle in the format [xmin, xmax] x [ymin, ymax]
    public String toString() {
        return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";
    }
}
