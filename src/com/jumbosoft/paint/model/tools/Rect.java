package com.jumbosoft.paint.model.tools;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import com.jumbosoft.paint.model.PaintCanvas;
import com.jumbosoft.paint.model.Tool;

public class Rect extends Tool {
    private Image originalCanvas;
    private Color color;

    double startX;
    double startY;

    public Rect() {
        super();
        this.color = Color.BLACK;
    }

    public void init(PaintCanvas canvas) {
        super.init(canvas);

        Canvas c =  this.getCanvas().getCanvas();

        c.setOnMousePressed(event -> {
            this.startX = event.getX();
            this.startY = event.getY();
            this.originalCanvas = this.getCanvas().saveCanvas();
        });

        c.setOnMouseDragged(event -> {
            this.getCanvas().loadCanvas(originalCanvas);
            GraphicsContext gc = this.getCanvas().getCanvas().getGraphicsContext2D();
            gc.setFill(color);
            double x, y, w, h;

            if (startX < event.getX()) {
                x = startX;
                w = event.getX() - startX;
            } else {
                x = event.getX();
                w = startX - event.getX();
            }

            if (startY < event.getY()) {
                y = startY;
                h = event.getY() - startY;
            } else {
                y = event.getY();
                h = startY - event.getY();
            }

            gc.fillRect(x, y, w, h);
        });

    }

    public void kill() {
        Canvas c = this.getCanvas().getCanvas();
        c.setOnMousePressed(event -> {});
        c.setOnMouseDragged(event -> {});
    }

    @Override
    public Node getControlsUI() {
        VBox parent = new VBox();

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        colorPicker.setOnAction(event -> this.color = colorPicker.getValue());

        parent.getChildren().addAll(colorPicker);

        return parent;
    }
}
