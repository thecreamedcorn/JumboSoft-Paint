package view;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;

public class ZoomableScrollPane extends ScrollPane {
    private Group zoomGroup;
    private Scale scaleTransform;
    private Node content;

    public ZoomableScrollPane(Node content) {

        this.content = content;
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(content);
        setContent(centeredNode(contentGroup));
        scaleTransform = new Scale(1, 1);
        zoomGroup.getTransforms().add(scaleTransform);

        this.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

        this.setOnScroll(event -> {
            if (event.isControlDown()) {
                if (event.getDeltaY() > 0.0) {
                    scaleTransform.setX(scaleTransform.getX() + 0.1);
                    scaleTransform.setY(scaleTransform.getY() + 0.1);
                } else {
                    scaleTransform.setX(scaleTransform.getX() - 0.1);
                    scaleTransform.setY(scaleTransform.getY() - 0.1);
                }
                event.consume();
            }
        });
    }

    private Node centeredNode(Node node) {
        VBox vBox = new VBox(node);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
}