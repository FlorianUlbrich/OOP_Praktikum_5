package geometry;

import java.awt.geom.AffineTransform;

/**
 * Service class with useful methods for generating triangles.
 */
class TriangleManager {

    /**
     * Constructor.
     */
    TriangleManager() {}

    /**
     * Returns the initial triangle from the input of the two sides.
     * @param lengthA Length of side a.
     * @param lengthB Length of side b.
     * @return Triangle from given length a and b.
     */
    Triangle returnTriangleFromInput(int lengthA, int lengthB) {
        double lengthC = Math.sqrt(Math.pow(lengthA, 2) + Math.pow(lengthB, 2));
        double heightOfTriangle = (lengthA * lengthB) / lengthC;
        double p = Math.sqrt(Math.pow(lengthA, 2) - Math.pow(heightOfTriangle, 2));
        Tuple vertexA = new Tuple(0,0);
        Tuple vertexB = new Tuple(lengthC, 0);
        Tuple vertexC = new Tuple(p, heightOfTriangle);
        return new Triangle(vertexA, vertexB, vertexC);
    }

    /**
     * Given the length of the square, the triangle size is adjusted first.
     * @param triangle The triangle to be resized.
     * @param lengthOfSquare Length of the square.
     * @return Triangle with side c resized to fit the square.
     */
    private Triangle resizeTriangle(Triangle triangle, double lengthOfSquare) {
        double resizeFactor = lengthOfSquare / (triangle.getVertexB().getX() - triangle.getVertexA().getX());
        return new Triangle(triangle.getVertexA(),
                new Tuple(triangle.getVertexB().getX() * resizeFactor,triangle.getVertexB().getY() * resizeFactor),
                new Tuple(triangle.getVertexC().getX() * resizeFactor,triangle.getVertexC().getY() * resizeFactor));
    }

    /**
     * Returns triangle mirrored at a line orthogonal to the mid of side c.
     * @param triangle A triangle.
     * @return The mirrored version of the triangle.
     */
    Triangle mirrorTriangle(Triangle triangle) {

        double ac = triangle.getVertexC().getX() - triangle.getVertexA().getX();
        double cb = triangle.getVertexB().getX() - triangle.getVertexC().getX();
        double newXofC;

        if(ac > cb) {
            newXofC = cb;
        } else {
            newXofC = ac;
        }

        return new Triangle(triangle.getVertexA(), triangle.getVertexB(),
                new Tuple(triangle.getVertexA().getX() + newXofC, triangle.getVertexC().getY()));
    }

    /**
     * Returns Triangle fitted to the two vertices of a square.
     * @param triangle The initial triangle.
     * @param square The square, the triangle is going to be generated from.
     * @return A resized version of the triangle, fitted to side D-C from the square.
     */
    Triangle returnTriangleFromSquare(Triangle triangle, Square square){

        // Resize Triangle for square.
        double length = Math.sqrt(Math.pow(square.getVertexD().getX()-square.getVertexC().getX(), 2)
                + Math.pow(square.getVertexD().getY()-square.getVertexC().getY(), 2));

        Triangle resized = resizeTriangle(triangle, length);

        // Fit A of Triangle to Point Start
        resized.setVertexA(new Tuple(resized.getVertexA().getX() + square.getVertexD().getX(),
                resized.getVertexA().getY() + square.getVertexD().getY()));
        resized.setVertexB(new Tuple(resized.getVertexB().getX() + square.getVertexD().getX(),
                resized.getVertexB().getY() + square.getVertexD().getY()));
        resized.setVertexC(new Tuple(resized.getVertexC().getX() + square.getVertexD().getX(),
                resized.getVertexC().getY() + square.getVertexD().getY()));

        double alpha = Math.toDegrees(Math.atan2((square.getVertexC().getY() - square.getVertexD().getY()),
                (square.getVertexC().getX() - square.getVertexD().getX())));
        double[] untransformedPoints = {
                resized.getVertexA().getX(), resized.getVertexA().getY(),
                resized.getVertexB().getX(), resized.getVertexB().getY(),
                resized.getVertexC().getX(), resized.getVertexC().getY()
        };
        float[] transformedPoints = new float[untransformedPoints.length];
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(alpha), square.getVertexD().getX(), square.getVertexD().getY());
        rotation.transform(untransformedPoints, 0, transformedPoints, 0, untransformedPoints.length / 2);

        Tuple vertexA = new Tuple(transformedPoints[0], transformedPoints[1]);
        Tuple vertexB = new Tuple(transformedPoints[2], transformedPoints[3]);
        Tuple vertexC = new Tuple(transformedPoints[4], transformedPoints[5]);

        return new Triangle(vertexA, vertexB, vertexC);
    }
}