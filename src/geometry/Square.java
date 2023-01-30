package geometry;

/**
 * A square used in the tree consisting of four points.
 */
class Square {

    /**
     * Point A.
     */
    private Tuple vertexA;

    /**
     * Point B.
     */
    private Tuple vertexB;

    /**
     * Point C
     */
    private Tuple vertexC;

    /**
     * Point C
     */
    private Tuple vertexD;

    /**
     * Color of this square.
     */
    private String color = "#FFFFFF";

    /**
     * Constructor.
     * @param vertexA Point A.
     * @param vertexB Point B.
     * @param vertexC Point C.
     * @param vertexD Point D.
     */
    Square(Tuple vertexA, Tuple vertexB, Tuple vertexC, Tuple vertexD) {
        this.vertexA = vertexA;
        this.vertexB = vertexB;
        this.vertexC = vertexC;
        this.vertexD = vertexD;
    }


    /**
     * Returns the length of the square.
     */
    double getSquareLength() {
        return Math.sqrt(Math.pow(this.vertexA.getX() - this.vertexB.getX(), 2) +
                Math.pow(this.vertexA.getY() - this.vertexB.getY(), 2));
    }

    Tuple getVertexA() {
        return vertexA;
    }

    Tuple getVertexB() {
        return vertexB;
    }

    Tuple getVertexC() {
        return vertexC;
    }

    Tuple getVertexD() {
        return vertexD;
    }

    String getColor() {
        return color;
    }

    void setColor(String color) {
        this.color = color;
    }
}