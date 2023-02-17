public interface PointInterface {

    /**
     * Returns the x-coordinate of the point.
     */
    public int x();

    /**
     * Returns the y-coordinate of the point.
     */
    public int y();

    /**
     * Calculates the Euclidean distance between this point and another point.
     */
    public double distanceTo(Point z);

    /**
     * Calculates the square of the Euclidean distance between this point and another point.
     */
    public int squareDistanceTo(Point z);

    /**
     * Returns a string representation of the point in the format (x, y).
     */
    public String toString();
}
