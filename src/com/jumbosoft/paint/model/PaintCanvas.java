package com.jumbosoft.paint.model;

import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import com.jumbosoft.paint.view.ToolBar;

public class PaintCanvas {
    private Tool currentTool;
    private Canvas canvas;
    private VBox toolBar;
    private Node toolControlUI;

    public PaintCanvas(double width, double height) {
        this.canvas = new Canvas(width, height);
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, width, height);
        currentTool = new Tool();
        toolControlUI = currentTool.getControlsUI();
    }

    public void initToolBar(ToolBar toolBar) {
        this.toolBar = toolBar.getLayout();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Image saveCanvas() {
        SnapshotParameters sp = new SnapshotParameters();
        return canvas.snapshot(sp, null);
    }

    public void loadCanvas(Image image) {
        canvas.getGraphicsContext2D().drawImage(image, 0, 0);
    }

    public void loadNewTool(Tool newTool) {
        toolBar.getChildren().remove(toolControlUI);
        currentTool.kill();
        currentTool = newTool;
        currentTool.init(this);
        toolBar.getChildren().add(toolControlUI = newTool.getControlsUI());
    }
}
