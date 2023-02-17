import java.util.List;
import java.util.Stack;

public interface TwoDTreeInterface {
    /**
     * Returns true if the tree is empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * Returns the number of points in the tree.
     */
    int size();

    /**
     * Inserts the given point into the tree.
     */
    void insert(Point p);

    /**
     * Returns true if the given point is in the tree, false otherwise.
     */
    boolean search(Point p);

    /**
     * Returns the point in the tree that is closest to the given point,
     * or null if the tree is empty.
     */
    Point nearestNeighbor(Point p);

    /**
     * Returns a list of points in the tree that are contained within the
     * given rectangle.
     */
    Stack<Point> rangeSearch(Rectangle rect);
}
