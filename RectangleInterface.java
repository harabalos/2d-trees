public interface RectangleInterface{
    public int xmin(); // minimum x-coordinate of rectangle

    public int ymin() ;// minimum y-coordinate of rectangle

    public int xmax() ;// maximum x-coordinate of rectangle

    public int ymax(); // maximum y-coordinate of rectangle

    public boolean contains(Point p); //does p belong to the rectangle?

    public boolean intersects(Rectangle that); // do the two rectangles intersect?

    public double distanceTo(Point p); // Euclidean distance from p to closest point in rectangle

    public int squareDistanceTo(Point p); // square of Euclidean distance from p to closest point in rectangle

    public String toString(); // string representation: [xmin, xmax] × [ymin, ymax]

}