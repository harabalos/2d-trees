public class Point{
    // instance variables to store the x and y coordinates of the point
    private int x;
    private int y;

    // constructor to initialize the x and y coordinates of the point
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // method to return the x-coordinate of the point
    public int x() {
        return x;
    }

    // method to return the y-coordinate of the point
    public int y() {
        return y;
    }

    // method to calculate the Euclidean distance between this point and another point
    public double distanceTo(Point z) {
        // calculate the difference between the x coordinates of the two points
        int xDiff = x - z.x();
        // calculate the difference between the y coordinates of the two points
        int yDiff = y - z.y();
        // return the square root of the sum of the squares of the differences
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    // method to calculate the square of the Euclidean distance between this point and another point
    public int squareDistanceTo(Point z) {
        // calculate the difference between the x coordinates of the two points
        int xDiff = x - z.x();
        // calculate the difference between the y coordinates of the two points
        int yDiff = y - z.y();
        // return the sum of the squares of the differences
        return xDiff * xDiff + yDiff * yDiff;
    }

    // method to return a string representation of the point in the format (x, y)
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}