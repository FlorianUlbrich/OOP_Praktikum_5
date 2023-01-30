package geometry;

/**
 * Triangle consisting of three points.
 */
class Triangle {

    /**
     * Point A.
     */
    private Tuple vertexA;

    /**
     * Point B.
     */
    private Tuple vertexB;

    /**
     * Point C.
     */
    private Tuple vertexC;

    /**
     * Color of the triangle.
     */
    private String color = "#FFFFFF";

    /**
     * Constructor.
     * @param vertexA Point A.
     * @param vertexB Point B.
     * @param vertexC Point C.
     */
    Triangle(Tuple vertexA, Tuple vertexB, Tuple vertexC) {
        this.vertexA = vertexA;
        this.vertexB = vertexB;
        this.vertexC = vertexC;
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

    String getColor() {
        return color;
    }

    void setVertexA(Tuple vertexA) {
        this.vertexA = vertexA;
    }

    void setVertexB(Tuple vertexB) {
        this.vertexB = vertexB;
    }

    void setVertexC(Tuple vertexC) {
        this.vertexC = vertexC;
    }

    void setColor(String color) {
        this.color = color;
    }
}