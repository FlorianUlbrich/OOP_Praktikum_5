package geometry;

/**
 * Tuple used as a 2D Point
 */
class Tuple {

    /**
     * X coordinate.
     */
    private double x;

    /**
     * y coordinate.
     */
    private double y;

    /**
     * Constructor.
     * @param x X coordinate.
     * @param y Y coordinate.
     */
    Tuple(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }
}
