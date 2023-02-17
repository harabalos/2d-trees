public interface RectangleInterface {
    /**
     * Returns the minimum x-coordinate of the rectangle.
     */
    int xmin();

    /**
     * Returns the minimum y-coordinate of the rectangle.
     */
    int ymin();

    /**
     * Returns the maximum x-coordinate of the rectangle.
     */
    int xmax();

    /**
     * Returns the maximum y-coordinate of the rectangle.
     */
    int ymax();

    /**
     * Returns true if the rectangle contains the given point, false otherwise.
     */
    boolean contains(Point p);

    /**
     * Returns true if this rectangle intersects the given rectangle, false otherwise.
     */
    boolean intersects(Rectangle that);

    /**
     * Returns the square of the Euclidean distance from the given point to the closest point in the rectangle.
     */
    int squareDistanceTo(Point p);

    /**
     * Returns the Euclidean distance from the given point to the closest point in the rectangle.
     */
    double distanceTo(Point p);

    /**
     * Returns a string representation of the rectangle in the format [xmin, xmax] x [ymin, ymax].
     */
    String toString();
}
