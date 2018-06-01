package com.jumbosoft.paint.model.tools;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import com.jumbosoft.paint.model.PaintCanvas;
import com.jumbosoft.paint.model.Tool;

public class Line extends Tool {
    private Image originalCanvas;
    private double diameter;
    private Color color;

    double startX;
    double startY;

    public Line() {
        super();
        this.diameter = 25;
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
            drawLine(event.getX(), event.getY());
        });

    }

    public void kill() {
        Canvas c = this.getCanvas().getCanvas();
        c.setOnMousePressed(event -> {});
        c.setOnMouseDragged(event -> {});
    }

    private void drawLine(double endX, double endY) {
        getCanvas().getCanvas().getGraphicsContext2D().setFill(color);
        getCanvas().getCanvas().getGraphicsContext2D().fillOval(endX - (diameter / 2), endY - (diameter / 2), diameter, diameter);
        getCanvas().getCanvas().getGraphicsContext2D().fillOval(startX - (diameter / 2), startY - (diameter / 2), diameter, diameter);

        double x;
        double y;
        double dx;
        double dy;
        double step;
        int i;

        dx = startX - endX;
        dy = startY - endY;
        if (Math.abs(dx) >= Math.abs(dy)) {
            step = Math.abs(dx);
        } else {
            step = Math.abs(dy);
        }
        dx = dx / step;
        dy = dy / step;
        x = endX;
        y = endY;
        i = 1;
        while (i <= step) {
            getCanvas().getCanvas().getGraphicsContext2D().fillOval(x - (diameter / 2), y - (diameter / 2), diameter, diameter);
            x += dx;
            y += dy;
            i++;
        }
    }

    @Override
    public Node getControlsUI() {
        VBox parent = new VBox();

        Slider slider = new Slider(1, 100, 25);
        slider.setShowTickMarks(false);
        slider.setBlockIncrement(1);

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        colorPicker.setOnAction(event -> this.color = colorPicker.getValue());

        slider.valueProperty().addListener((observable, oldValue, newValue) -> this.diameter = newValue.doubleValue());

        parent.getChildren().addAll(slider, colorPicker);

        return parent;
    }
}
