package geometry;

/**
 * Service class for squares with useful methods helping to generate a tree.
 */
class SquareManager {

    /**
     * Constructor of service class.
     */
    SquareManager() {}

    /**
     * Returns the square to side b of a triangle.
     * @param triangle A triangle.
     * @return Square lying on side b of the triangle.
     */
    Square returnLeftSquareOnTriangle(Triangle triangle) {

        double absoluteX = Math.abs(triangle.getVertexA().getX() - triangle.getVertexC().getX());
        double absoluteY = Math.abs(triangle.getVertexA().getY() - triangle.getVertexC().getY());

        if(triangle.getVertexA().getX() <= triangle.getVertexC().getX() && triangle.getVertexA().getY() < triangle.getVertexC().getY()) {
            //1st quadrant
            Tuple vertexD = new Tuple(triangle.getVertexC().getX() - absoluteY, triangle.getVertexC().getY() + absoluteX);
            Tuple vertexE = new Tuple(triangle.getVertexA().getX() - absoluteY, triangle.getVertexA().getY() + absoluteX);
            return new Square(triangle.getVertexA(), triangle.getVertexC(), vertexD, vertexE);

        } else if(triangle.getVertexA().getX() > triangle.getVertexC().getX() && triangle.getVertexA().getY() <= triangle.getVertexC().getY()) {
            //2nd quadrant
            Tuple vertexD = new Tuple(triangle.getVertexC().getX() - absoluteY, triangle.getVertexC().getY() - absoluteX);
            Tuple vertexE = new Tuple(triangle.getVertexA().getX() - absoluteY, triangle.getVertexA().getY() - absoluteX);
            return new Square(triangle.getVertexA(), triangle.getVertexC(), vertexD, vertexE);

        } else if(triangle.getVertexA().getX() >= triangle.getVertexC().getX() && triangle.getVertexA().getY() > triangle.getVertexC().getY()) {
            //3rd quadrant
            Tuple vertexD = new Tuple(triangle.getVertexC().getX() + absoluteY, triangle.getVertexC().getY() - absoluteX);
            Tuple vertexE = new Tuple(triangle.getVertexA().getX() + absoluteY, triangle.getVertexA().getY() - absoluteX);
            return new Square(triangle.getVertexA(), triangle.getVertexC(), vertexD, vertexE);

        } else if(triangle.getVertexA().getX() < triangle.getVertexC().getX() && triangle.getVertexA().getY() >= triangle.getVertexC().getY()) {
            //4th quadrant
            Tuple vertexD = new Tuple(triangle.getVertexC().getX() + absoluteY, triangle.getVertexC().getY() + absoluteX);
            Tuple vertexE = new Tuple(triangle.getVertexA().getX() + absoluteY, triangle.getVertexA().getY() + absoluteX);
            return new Square(triangle.getVertexA(), triangle.getVertexC(), vertexD, vertexE);
        } else {
            throw new RuntimeException("Something went wrong with the left square generation.");
        }
    }

    /**
     * Returns the square to side a of a triangle.
     * @param triangle A triangle.
     * @return Square lying on side a of the triangle.
     */
    Square returnRightSquareOnTriangle(Triangle triangle) {

        double absoluteX = Math.abs(triangle.getVertexB().getX() - triangle.getVertexC().getX());
        double absoluteY = Math.abs(triangle.getVertexB().getY() - triangle.getVertexC().getY());

        if(triangle.getVertexB().getX() <= triangle.getVertexC().getX() && triangle.getVertexB().getY() < triangle.getVertexC().getY()) {
            //1st quadrant
            Tuple vertexD = new Tuple(triangle.getVertexB().getX() + absoluteY, triangle.getVertexB().getY() - absoluteX);
            Tuple vertexE = new Tuple(triangle.getVertexC().getX() + absoluteY, triangle.getVertexC().getY() - absoluteX);
            return new Square(triangle.getVertexC(), triangle.getVertexB(), vertexD, vertexE);

        } else if(triangle.getVertexB().getX() > triangle.getVertexC().getX() && triangle.getVertexB().getY() <= triangle.getVertexC().getY()) {
            //2nd quadrant
            Tuple vertexD = new Tuple(triangle.getVertexB().getX() + absoluteY, triangle.getVertexB().getY() + absoluteX);
            Tuple vertexE = new Tuple(triangle.getVertexC().getX() + absoluteY, triangle.getVertexC().getY() + absoluteX);
            return new Square(triangle.getVertexC(), triangle.getVertexB(), vertexD, vertexE);

        } else if(triangle.getVertexB().getX() >= triangle.getVertexC().getX() && triangle.getVertexB().getY() > triangle.getVertexC().getY()) {
            //3rd quadrant
            Tuple vertexD = new Tuple(triangle.getVertexB().getX() - absoluteY, triangle.getVertexB().getY() + absoluteX);
            Tuple vertexE = new Tuple(triangle.getVertexC().getX() - absoluteY, triangle.getVertexC().getY() + absoluteX);
            return new Square(triangle.getVertexC(), triangle.getVertexB(), vertexD, vertexE);

        } else if(triangle.getVertexB().getX() < triangle.getVertexC().getX() && triangle.getVertexB().getY() >= triangle.getVertexC().getY()) {
            //4th quadrant
            Tuple vertexD = new Tuple(triangle.getVertexB().getX() - absoluteY, triangle.getVertexB().getY() - absoluteX);
            Tuple vertexE = new Tuple(triangle.getVertexC().getX() - absoluteY, triangle.getVertexC().getY() - absoluteX);
            return new Square(triangle.getVertexC(), triangle.getVertexB(), vertexD, vertexE);
        } else {
            throw new RuntimeException("Something went wrong with the left square generation.");
        }
    }

    /**
     * Returns the square under the initial triangle.
     * @param vertexStart Point A of the initial triangle.
     * @param vertexEnd Point B of the initial triangle.
     * @return Square below the initial triangle
     */
    Square drawBaseSquare(Tuple vertexStart, Tuple vertexEnd) {
        double absoluteX = Math.abs(vertexEnd.getX() - vertexStart.getX());
        Tuple vertexD = new Tuple(vertexStart.getX(), vertexStart.getY() - absoluteX);
        Tuple vertexE = new Tuple(vertexEnd.getX(), vertexEnd.getY() - absoluteX);
        return new Square(vertexStart, vertexD, vertexE, vertexEnd);
    }
}