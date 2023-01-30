package geometry;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Main service class, generating the tree from the given input.
 */
public class TreeManager {

    /**
     * TriangleManager for additional useful methods to generate triangles.
     */
    private TriangleManager triangleManager = new TriangleManager();

    /**
     * SquareManager foradditional useful method to generate squares.
     */
    private SquareManager squareManager = new SquareManager();

    /**
     * Group of polylines representing the the output tree.
     */
    private Group tree = new Group();

    /**
     * List of all triangles of the tree, packaged for each level in an additional list.
     */
    private TreeMap<Integer, ArrayList<Triangle>> triangles = new TreeMap<>();

    /**
     * List of all squares of the tree, packaged for each level in an additional list.
     */
    private TreeMap<Integer, ArrayList<Square>> squares = new TreeMap<>();

    /**
     * The minimum square length after which the tree stops generating new levels.
     */
    private double minimumSquareLength;

    /**
     * The list of colors, which are used to color the tree after selected algorithms.
     */
    private ArrayList<String> colors = new ArrayList<>();

    /**
     * How the tree is branching. Similar branching means the triangle is always the same, just rotated.
     * Alternated branching means, the triangle is mirrored every second level.
     */
    private String branchingStyle;

    /**
     * How the tree is going to be colored. First type colors all levels under a many thresholds.
     */
    private String colorStyle;

    /**
     * Constructor.
     */
    public TreeManager() {}

    /**
     * Generates the tree from thew input by using provided functionality of the two service classes
     * TriangleManager and SquareManager.
     * @param lengthA Length of side a.
     * @param lengthB Length of side b.
     */
    public void printTreeFromInput(int lengthA, int lengthB) {

        Triangle initial = triangleManager.returnTriangleFromInput(lengthA, lengthB);
        Triangle mirroredInitial = triangleManager.mirrorTriangle(initial);
        Square baseSquare = squareManager.drawBaseSquare(initial.getVertexA(), initial.getVertexB());

        Square squareLeft = squareManager.returnLeftSquareOnTriangle(initial);
        Square squareRight = squareManager.returnRightSquareOnTriangle(initial);

        ArrayList<Triangle> initialT = new ArrayList<>();
        initialT.add(initial);

        ArrayList<Square> initialS = new ArrayList<>();
        initialS.add(squareLeft);
        initialS.add(squareRight);

        this.triangles.put(0, initialT);
        this.squares.put(0, initialS);

        while(isMinimumSquareLengthExceeded()) {
            generateNewLevel(initial, mirroredInitial);
        }

        // apply colorization
        if(colorStyle != null && colorStyle.equals("color branches")) {
            if(lengthA == lengthB) {
                colorBranchesSymmetrical();
            } else {
                colorBranchesAsymmetrical();
            }
        } else if (colorStyle != null) {
            colorLevel();
        }

        treeToPolyline();
        addSquareToPolyline(baseSquare);
    }

    /**
     * Generates a new level by using the List of triangles and squares. First for all squares of the
     * last level is a triangle generated, then from these triangles are squares generated.
     * Data structure for tree: TreeMap<Int level, ArrayList<Triangle triangle>> TreeMap<Int level, ArrayList<Square square>>
     * @param initial The initial triangle, generated from the two given sides.
     * @param mirroredInitial The mirrored version of the initial triangle used for alternately branching trees.
     */
    private void generateNewLevel(Triangle initial, Triangle mirroredInitial) {

        ArrayList<Triangle> newTriangles = new ArrayList<>();
        ArrayList<Square> newSquares = new ArrayList<>();
        int currentLevel = this.squares.lastKey();

        // mirroring the triangles every second level if alternately branching enabled
        if(branchingStyle.equals("alternately branching") && currentLevel % 2 == 1) {
            for(Square square : this.squares.get(currentLevel)) {
                newTriangles.add(triangleManager.returnTriangleFromSquare(mirroredInitial, square));
            }
        } else {
            for(Square square : this.squares.get(currentLevel)) {
                newTriangles.add(triangleManager.returnTriangleFromSquare(initial, square));
            }
        }

        for(Triangle triangle : newTriangles) {
            newSquares.add(squareManager.returnLeftSquareOnTriangle(triangle));
            newSquares.add(squareManager.returnRightSquareOnTriangle(triangle));
        }

        this.triangles.put(currentLevel+1, newTriangles);
        this.squares.put(currentLevel+1, newSquares);
    }

    /**
     * Colors a tree by dividing it into two main branches starting at the root and colorizing those differently.
     */
    private void colorBranchesAsymmetrical() {
        int maxLevelTriangles = this.triangles.lastKey();
        for(int i = 0; i <= maxLevelTriangles; i++) {
            // color all triangles
            for(int j = 0; j < this.triangles.get(i).size(); j++) {
                this.triangles.get(i).get(j).setColor(this.colors.get(0));
            }
            // color all squares
            for(int k = 0; k < this.squares.get(i).size(); k++) {
                if(k % 2 == 0) {
                    this.squares.get(i).get(k).setColor(this.colors.get(1));
                } else {
                    this.squares.get(i).get(k).setColor(this.colors.get(2));
                }
            }
        }
    }

    /**
     * Colors a tree by providing a different color for the smaller squares and a different color
     * to the larger squares of all triangles of the tree. The triangles get an own color.
     */
    private void colorBranchesSymmetrical() {
        int maxLevelTriangles = this.triangles.lastKey();
        for(int i = 0; i <= maxLevelTriangles; i++) {
            int halfSizeOfTriangles = this.triangles.get(i).size() / 2;
            for(int j = 0; j < halfSizeOfTriangles; j++) {
                this.triangles.get(i).get(j).setColor(this.colors.get(0));
            }
            for(int k = halfSizeOfTriangles; k < this.triangles.get(i).size(); k++) {
                this.triangles.get(i).get(k).setColor(this.colors.get(1));
            }
        }
        int maxLevelSquares = this.squares.lastKey();
        for(int i = 0; i <= maxLevelSquares; i++) {
            int halfSizeOfSquares = this.squares.get(i).size() / 2;
            for(int j = 0; j < halfSizeOfSquares; j++) {
                this.squares.get(i).get(j).setColor(this.colors.get(0));
            }
            for(int k = halfSizeOfSquares; k < this.squares.get(i).size(); k++) {
                this.squares.get(i).get(k).setColor(this.colors.get(1));
            }
        }
    }

    /**
     * Colors the tree after levels. A level is a triangle with two squares on the catheti.
     */
    private void colorLevel() {
        int maxLevel = this.triangles.lastKey();
        for(int i = 0; i <= maxLevel; i++) {
            if(i < 3) {
                for(int j = 0; j < this.triangles.get(i).size(); j++) {
                    this.triangles.get(i).get(j).setColor(this.colors.get(0));
                }
                for(int k = 0; k < this.squares.get(i).size(); k++) {
                    this.squares.get(i).get(k).setColor(this.colors.get(0));
                }
            }
            if(i >= 3 && i < 10) {
                for(int j = 0; j < this.triangles.get(i).size(); j++) {
                    this.triangles.get(i).get(j).setColor(this.colors.get(1));
                }
                for(int k = 0; k < this.squares.get(i).size(); k++) {
                    this.squares.get(i).get(k).setColor(this.colors.get(1));
                }
            }
            if(i >= 7) {
                for(int j = 0; j < this.triangles.get(i).size(); j++) {
                    this.triangles.get(i).get(j).setColor(this.colors.get(2));
                }
                for(int k = 0; k < this.squares.get(i).size(); k++) {
                    this.squares.get(i).get(k).setColor(this.colors.get(2));
                }
            }
        }
    }

    /**
     * Generates a Group of polylines from the lists of squares and triangles.
     */
    private void treeToPolyline() {
        for(int i = 0; i <= this.triangles.lastKey(); i++) {
            for(Triangle triangle : this.triangles.get(i)) {
                addTriangleToPolyline(triangle);
            }
        }
        for(int i = 0; i <=this.squares.lastKey(); i++) {
            for(Square square: this.squares.get(i)) {
                addSquareToPolyline(square);
            }
        }
    }

    /**
     * Transforms a triangle to a polyline and uses the color to fill it. Then it gets added to the tree group.
     * @param triangle A triangle.
     */
    private void addTriangleToPolyline(Triangle triangle) {
        Polyline tri = new Polyline();
        tri.getPoints().addAll(
                triangle.getVertexA().getX(), triangle.getVertexA().getY(),
                triangle.getVertexB().getX(), triangle.getVertexB().getY(),
                triangle.getVertexC().getX(), triangle.getVertexC().getY(),
                triangle.getVertexA().getX(), triangle.getVertexA().getY()
        );
        tri.setFill(Color.web(triangle.getColor()));
        this.tree.getChildren().add(tri);
    }

    /**
     * Transforms a square to a polyline and uses the color to fill it. The it gets added to the tree group.
     * @param square A square.
     */
    private void addSquareToPolyline(Square square) {
        Polyline sq = new Polyline();
        sq.getPoints().addAll(
                square.getVertexA().getX(), square.getVertexA().getY(),
                square.getVertexB().getX(), square.getVertexB().getY(),
                square.getVertexC().getX(), square.getVertexC().getY(),
                square.getVertexD().getX(), square.getVertexD().getY(),
                square.getVertexA().getX(), square.getVertexA().getY()
        );
        sq.setFill(Color.web(square.getColor()));
        this.tree.getChildren().add(sq);
    }

    /**
     * Checks if one of the squares of the last generated level is violating the minimum length defined by the user.
     * @return false, if the minimum length is violated.
     */
    private boolean isMinimumSquareLengthExceeded() {
        for(Square square : this.squares.get(this.squares.lastKey())) {
            if(square.getSquareLength() < this.minimumSquareLength) {
                return false;
            }
        }
        return true;
    }

    /**
     * Cleans up properties of the tree for generating a new one.
     */
    public void cleanupTree() {
        this.tree.getChildren().clear();
        this.triangles.clear();
        this.squares.clear();
        this.minimumSquareLength = 0;
        this.colors.clear();
        this.colorStyle = "";
        this.branchingStyle = "";
    }

    public Group getTree() {
        this.tree.setRotate(180);
        return this.tree;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public void setColorStyle(String colorStyle) {
        this.colorStyle = colorStyle;
    }

    public void setMinimumSquareLength(double minimumSquareLength) {
        this.minimumSquareLength = minimumSquareLength;
    }

    public void setBranchingStyle(String branchingStyle) {
        this.branchingStyle = branchingStyle;
    }
}