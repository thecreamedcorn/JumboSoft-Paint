package com.jumbosoft.paint.model;

import javafx.scene.Group;
import javafx.scene.Node;

public class Tool {
    private PaintCanvas canvas;

    protected PaintCanvas getCanvas() {
        return canvas;
    }

    public Tool() {}

    public void init(PaintCanvas canvas) {
        this.canvas = canvas;
    }
    public void kill() {}

    public Node getControlsUI() {
        return new Group();
    }
}
