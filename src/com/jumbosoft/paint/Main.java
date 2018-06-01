package com.jumbosoft.paint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.jumbosoft.paint.view.WorkPane;
import com.jumbosoft.paint.view.IntroPane;
import com.jumbosoft.paint.model.PaintCanvas;

public class Main extends Application {
    private Stage stage;
    private WorkPane workPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        // workPane = new WorkPane(IntroPane.introPane(), primaryStage);
        workPane = new WorkPane(new PaintCanvas(500, 500), primaryStage);
        Scene scene = new Scene(workPane, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JumboSoft Paint");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}