package main.pythagoras;

import geometry.TreeManager;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Main extends Application {

    /**
     * Main method, creates the user interface, and connects the buttons with parameters to the service classes.
     * @param primaryStage Primary Stage.
     */
    @Override
    public void start(Stage primaryStage) {

        TreeManager treeManager = new TreeManager();

        TextField inputX = new TextField();
        TextField inputY = new TextField();
        TextField stopCondition = new TextField();
        TextField colorOne = new TextField();
        TextField colorTwo = new TextField();
        TextField colorThree = new TextField();

        Label labelX = new Label("Length of site a: ");
        labelX.setLabelFor(inputX);

        Label labelY = new Label("Length of site b: ");
        labelY.setLabelFor(inputY);

        Label stopConditionLabel = new Label("Minimal length: ");
        stopConditionLabel.setLabelFor(stopCondition);

        Label labelColorOne = new Label("color: ");
        labelColorOne.setLabelFor(colorOne);

        Label labelColorTwo = new Label("color: ");
        labelColorTwo.setLabelFor(colorTwo);

        Label labelColorThree = new Label("color: ");
        labelColorThree.setLabelFor(colorThree);

        Label validationHexColors = new Label("The color input is not correct.");
        validationHexColors.setTextFill(Color.web("#bc1414"));
        validationHexColors.setVisible(false);

        Label validationNumerical = new Label("The numerical input is not correct");
        validationNumerical.setTextFill(Color.web("#bc1414"));
        validationNumerical.setVisible(false);

        ChoiceBox choiceBoxBranching = new ChoiceBox();
        choiceBoxBranching.getItems().addAll("similar branching", "alternately branching");
        choiceBoxBranching.setValue("similar branching");

        ChoiceBox choiceBoxColor = new ChoiceBox();
        choiceBoxColor.getItems().addAll("color branches", "color level");
        choiceBoxColor.setValue("color level");

        Button calculate = new Button();
        calculate.setText("Calculate");
        calculate.setAlignment(Pos.TOP_CENTER);

        Button erase = new Button();
        erase.setText("Erase");
        erase.setAlignment(Pos.TOP_CENTER);

        VBox root = new VBox();
        HBox controls = new HBox();
        VBox numericalInput = new VBox();
        HBox inputXBox = new HBox();
        HBox inputYBox = new HBox();
        HBox inputStopBox = new HBox();
        VBox colorInput = new VBox();
        HBox inputColorOne = new HBox();
        HBox inputColorTwo = new HBox();
        HBox inputColorThree = new HBox();
        HBox treeShape = new HBox();
        HBox buttons = new HBox();
        HBox treeBox = new HBox();

        inputXBox.getChildren().addAll(labelX, inputX);
        inputYBox.getChildren().addAll(labelY, inputY);
        inputStopBox.getChildren().addAll(stopConditionLabel, stopCondition);
        numericalInput.getChildren().addAll(inputXBox, inputYBox, inputStopBox);

        inputColorOne.getChildren().addAll(labelColorOne, colorOne);
        inputColorTwo.getChildren().addAll(labelColorTwo, colorTwo);
        inputColorThree.getChildren().addAll(labelColorThree, colorThree);
        colorInput.getChildren().addAll(inputColorOne, inputColorTwo, inputColorThree);

        controls.getChildren().addAll(numericalInput, colorInput);

        treeShape.getChildren().addAll(choiceBoxBranching, choiceBoxColor);

        buttons.getChildren().addAll(calculate);

        root.getChildren().addAll(controls, treeShape, buttons, treeBox, validationHexColors, validationNumerical);

        primaryStage.setTitle("Pythagoras Trees");
        Scene scene = new Scene(root, 1000, 1000);
        scene.setFill(Color.rgb(252, 155, 27));
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        // button for generating tree with given input
        calculate.setOnAction(event -> {
            // clean up for next input
            treeManager.cleanupTree();

            // booleans for validation
            boolean areHexColorsValid = false;
            boolean isNumericalInputValid = false;
            validationHexColors.setVisible(false);
            validationNumerical.setVisible(false);

            // nop validation required because of choice box
            ArrayList<String> colors = new ArrayList<>();
            String branchingStyle = (String) choiceBoxBranching.getValue();
            String colorStyle = (String) choiceBoxColor.getValue();

            // has to be positive and larger then 0
            int x = tryInt(inputX);
            int y = tryInt(inputY);
            int stop = tryInt(stopCondition);

            if(x > 0 && y > 0 &&  stop > 0) {
                isNumericalInputValid = true;
            } else {
                validationNumerical.setVisible(true);
            }

            // hex colors have to be validated, too.
            if(Pattern.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", colorOne.getText()) &&
                    Pattern.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", colorTwo.getText()) &&
                    Pattern.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", colorThree.getText())) {
                areHexColorsValid = true;
            } else {
                validationHexColors.setVisible(true);
            }

            //if validation is successful, tree is calculated
            if(isNumericalInputValid && areHexColorsValid) {

                colors.add(colorOne.getText());
                colors.add(colorTwo.getText());
                colors.add(colorThree.getText());
                treeManager.setColors(colors);
                treeManager.setMinimumSquareLength(stop);
                treeManager.setBranchingStyle(branchingStyle);
                treeManager.setColorStyle(colorStyle);
                treeManager.printTreeFromInput(x, y);

                WritableImage image = treeManager.getTree().snapshot(new SnapshotParameters(), null);
                File file = new File("/Users/florian/Desktop/tree.png");

                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                } catch (IOException e) {
                    throw new RuntimeException("Image export failed.");
                }
                try {
                    treeBox.getChildren().addAll(treeManager.getTree());
                    treeBox.setAlignment(Pos.CENTER);
                } catch(Exception e) {
                    System.out.println("lol");
                }
            }


        });
    }

    private int tryInt(TextField input) {
        try {
            return Integer.parseInt(input.getText());
        } catch(NumberFormatException e) {
            System.out.print("This is not a number on " + input.getId() + "\n");
            return 0;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}