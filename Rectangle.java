public class Rectangle {
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    public Rectangle(int xmin, int ymin, int xmax, int ymax) {
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }

    public int xmin() {
        return xmin;
    }

    public int ymin() {
        return ymin;
    }

    public int xmax() {
        return xmax;
    }

    public int ymax() {
        return ymax;
    }

    public boolean contains(Point p) {
        return p.x() >= xmin && p.x() <= xmax && p.y() >= ymin && p.y() <= ymax;
    }

    public boolean intersects(Rectangle that) {
        return this.xmax >= that.xmin && this.xmin <= that.xmax &&
               this.ymax >= that.ymin && this.ymin <= that.ymax;
    }

    public int squareDistanceTo(Point p) {
        int x = Math.max(Math.min(p.x(), xmax), xmin);
        int y = Math.max(Math.min(p.y(), ymax), ymin);
        int xDiff = p.x() - x;
        int yDiff = p.y() - y;
        return xDiff * xDiff + yDiff * yDiff;
    }

    public String toString() {
        return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";
    }
}
