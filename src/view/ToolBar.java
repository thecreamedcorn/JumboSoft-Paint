package view;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.PaintCanvas;
import model.tools.Line;
import model.tools.Pen;
import model.tools.Rect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ToolBar extends ScrollPane {
    private PaintCanvas canvas;
    private FlowPane tools;
    private VBox layout;

    public ToolBar(PaintCanvas canvas, Stage primaryStage) {
        this.canvas = canvas;

        Button pen = new Button("Pen");
        Button line = new Button("Line");
        Button rect = new Button("Rect");
        tools = new FlowPane();
        tools.getChildren().addAll(pen, line, rect);

        Button save = new Button("Save");
        layout = new VBox();

        layout.setSpacing(20);
        layout.getChildren().addAll(tools, save);

        save.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
            File file = fileChooser.showSaveDialog(primaryStage);
            BufferedImage bi = SwingFXUtils.fromFXImage(canvas.saveCanvas(), null);
            try {
                ImageIO.write(bi, "png", file);
            } catch (IOException e) {
                System.out.println("IO Error. Whoopsy Daisy");
                e.printStackTrace();
            }
        });

        pen.setOnAction(event -> canvas.loadNewTool(new Pen()));
        line.setOnAction(event -> canvas.loadNewTool(new Line()));
        rect.setOnAction(event -> canvas.loadNewTool(new Rect()));

        this.setContent(layout);
    }

    public VBox getLayout() {
        return layout;
    }
}
