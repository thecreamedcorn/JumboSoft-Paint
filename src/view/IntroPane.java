package view;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.PaintCanvas;
import utils.Ref;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class IntroPane {

    private static class NewProjectInfo {
        private int height;
        private int width;

        public NewProjectInfo(int height, int width) {
            this.height = height;
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }

    public static PaintCanvas introPane() {

        Stage stage = new Stage();
        stage.setTitle("JumboSoft Paint");

        final Ref<PaintCanvas> result = new Ref<>(null);

        VBox root = new VBox();

        Button newProject = new Button("New Project");
        Button openImage = new Button("Open Image");

        root.getChildren().addAll(new Label("JumboSoft Paint Program"), newProject, openImage);
        root.setSpacing(10);

        newProject.setOnAction(event -> {
            Dialog<NewProjectInfo> dialog = new Dialog<>();
            dialog.setTitle("New Project");
            dialog.setHeaderText("Create A New Project");

            ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField heightFeild = new TextField();
            heightFeild.setPromptText("height");
            TextField widthFeild = new TextField();
            widthFeild.setPromptText("width");

            grid.add(new Label("Height:"), 0, 0);
            grid.add(heightFeild, 1, 0);
            grid.add(new Label("Width:"), 0, 1);
            grid.add(widthFeild, 1, 1);

            final Ref<Boolean> isHeightNotValid = new Ref<>(true);
            final Ref<Boolean> isWidthNotValid = new Ref<>(true);

            Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
            createButton.setDisable(true);

            heightFeild.textProperty().addListener((observable, oldValue, newValue) -> {
                isHeightNotValid.set(!newValue.matches("\\d+"));
                createButton.setDisable(isHeightNotValid.get() || isWidthNotValid.get());
            });

            widthFeild.textProperty().addListener((observable, oldValue, newValue) -> {
                isWidthNotValid.set(!newValue.matches("\\d+"));
                createButton.setDisable(isWidthNotValid.get() || isWidthNotValid.get());
            });

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == createButtonType) {
                    return new NewProjectInfo(Integer.parseInt(heightFeild.getText()), Integer.parseInt(widthFeild.getText()));
                }
                return null;
            });

            Optional<NewProjectInfo> dialogResult = dialog.showAndWait();

            dialogResult.ifPresentOrElse(info -> {
                result.set(new PaintCanvas(info.getWidth(), info.getHeight()));
            }, () -> {
                System.exit(1);
            });

            stage.close();
        });

        openImage.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPEG Format", "*.jpg", "*.jpeg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                    new FileChooser.ExtensionFilter("WBMP", "*.wbmp"),
                    new FileChooser.ExtensionFilter("GIF", "*.gif")
            );
            File file = fileChooser.showOpenDialog(stage);
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(2);
            }
            result.set(new PaintCanvas(image.getWidth(), image.getHeight()));
            result.get().loadCanvas(SwingFXUtils.toFXImage(image, null));

            stage.close();
        });

        stage.setScene(new Scene(root, 450, 450));
        stage.showAndWait();

        return result.get();
    }
}
