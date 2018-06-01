package com.jumbosoft.paint.model.tools;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import com.jumbosoft.paint.model.PaintCanvas;
import com.jumbosoft.paint.model.Tool;

public class Pen extends Tool {
    private double diameter;
    private Color color;
    private double lastX;
    private double lastY;

    public Pen() {
        super();
        diameter = 25;
        color = Color.BLACK;
    }

    @Override
    public void init(PaintCanvas canvas) {
        super.init(canvas);

        canvas.getCanvas().setOnMousePressed(event -> {
            lastX = event.getX();
            lastY = event.getY();
        });

        canvas.getCanvas().setOnMouseDragged(event -> {
            drawLine(event.getX(), event.getY());
            lastX = event.getX();
            lastY = event.getY();
        });

        canvas.getCanvas().setOnMouseReleased(event -> {
            drawLine(event.getX(), event.getY());
        });
    }

    @Override
    public void kill() {
        this.getCanvas().getCanvas().setOnMousePressed(event -> {});
        this.getCanvas().getCanvas().setOnMouseDragged(event -> {});
        this.getCanvas().getCanvas().setOnMouseReleased(event -> {});
    }

    private void drawLine(double newX, double newY) {
        getCanvas().getCanvas().getGraphicsContext2D().setFill(color);
        getCanvas().getCanvas().getGraphicsContext2D().fillOval(lastX - (diameter / 2), lastY - (diameter / 2), diameter, diameter);
        getCanvas().getCanvas().getGraphicsContext2D().fillOval(newX - (diameter / 2), newY - (diameter / 2), diameter, diameter);

        double x;
        double y;
        double dx;
        double dy;
        double step;
        int i;

        dx = newX - lastX;
        dy = newY - lastY;
        if (Math.abs(dx) >= Math.abs(dy)) {
            step = Math.abs(dx);
        } else {
            step = Math.abs(dy);
        }
        dx = dx / step;
        dy = dy / step;
        x = lastX;
        y = lastY;
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
