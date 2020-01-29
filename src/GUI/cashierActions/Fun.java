package GUI.cashierActions;

import javafx.animation.PathTransition;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Fun {
    public Fun(Pane drawPane, VBox vb){
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.millis(350));
        pt.setPath(new Circle(drawPane.getWidth()/2, drawPane.getHeight()/2, 1));
        pt.setNode(drawPane);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.setCycleCount(7);
        pt.setAutoReverse(true);
        pt.play();

        PathTransition pt1 = new PathTransition();
        pt1.setDuration(Duration.millis(350));
        pt1.setPath(new Circle(vb.getWidth()/2, vb.getHeight()/2, 100));
        pt1.setNode(vb);
        pt1.setOrientation(PathTransition.OrientationType.NONE);
        pt1.setCycleCount(7);
        pt1.setAutoReverse(true);
        pt1.play();
    }
}
