package com.jumbosoft.paint.view;

import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import com.jumbosoft.paint.model.PaintCanvas;

public class WorkPane extends SplitPane {
    private PaintCanvas paintCanvas;
    private ToolBar toolbar;
    private ZoomableScrollPane zoomableScrollPane;

    public WorkPane(PaintCanvas paintCanvas, Stage primaryStage) {
        super();

        this.paintCanvas = paintCanvas;
        zoomableScrollPane = new ZoomableScrollPane(paintCanvas.getCanvas());
        toolbar = new ToolBar(paintCanvas, primaryStage);
        paintCanvas.initToolBar(toolbar);

        this.getItems().addAll(toolbar, zoomableScrollPane);
        this.setDividerPositions(0.2f, 0.8f);
    }

    public PaintCanvas getPaintCanvas() {
        return paintCanvas;
    }

    public ToolBar getToolbar() {
        return toolbar;
    }
}
