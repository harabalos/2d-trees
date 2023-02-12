public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public double distanceTo(Point z) {
        int xDiff = x - z.x();
        int yDiff = y - z.y();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public int squareDistanceTo(Point z) {
        int xDiff = x - z.x();
        int yDiff = y - z.y();
        return xDiff * xDiff + yDiff * yDiff;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
