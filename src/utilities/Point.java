package utilities;

/**
 * Maxwell Knight: 326905791
 * Dor Gabriel Israeli : 305092835
 *
 */
/**
 * Represents a point in a 2-dimensional coordinate system.
 */
public class Point {
    private double x;
    private double y;

    /**
     * Constructs a point with specified coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @throws RuntimeException if x or y are out of valid range (0 <= x <= 1000000 and 0 <= y <= 800).
     */
    public Point(double x, double y) {
        if((x < 0 || x > 1000000) || (y < 0 || y > 800))
            throw new RuntimeException("0 <= x <= 1000000 and 0 <= y <= 800");

        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a point with the same coordinates as another point.
     *
     * @param p The point to copy coordinates from.
     */
    public Point(Point p){
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Constructs a point at the origin (0, 0).
     */
    public Point(){
        this.x = 0;
        this.y = 0;
    }

    /**
     * Returns the x-coordinate of the point.
     *
     * @return The x-coordinate of the point.
     */
    public double getX(){
        return this.x;
    }

    /**
     * Returns the y-coordinate of the point.
     *
     * @return The y-coordinate of the point.
     */
    public double getY(){
        return this.y;
    }

    /**
     * Sets the x-coordinate of the point.
     *
     * @param x The new x-coordinate to set.
     */
    public void setX(double x){
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the point.
     *
     * @param y The new y-coordinate to set.
     */
    public void setY(double y){
        this.y = y;
    }

    /**
     * Returns a string representation of the point.
     *
     * @return A string representation of the point in the format "Point(x = x-coordinate, y = y-coordinate)".
     */
    @Override
    public String toString(){
        return String.format("Point(x = %.2f, y = %.2f)", this.x, this.y);
    }

    /**
     * Checks if this point is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal (same class and same coordinates), false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Point point = (Point) obj;
        return Double.compare(point.x, x) == 0 &&
               Double.compare(point.y, y) == 0;
    }
}

