public interface PointInterface  {
    public int x(); // return the x-coordinate

    public int y(); // return the y-coordinate

    public double distanceTo(Point z); // Euclidean distance between two points

    public int squareDistanceTo(Point z); // square of the Euclidean distance

    // between two points
    public String toString(); // string representation: (x, y)        
}