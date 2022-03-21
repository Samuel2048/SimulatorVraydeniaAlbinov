package sample;


import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    ImageView target, aim;
    @FXML
    AnchorPane pane;

    double mouseX, mouseY;

    public void onMouseMove(MouseEvent event){
        mouseX = event.getX() - aim.getFitWidth()/2;
        mouseY = event.getY() - aim.getFitHeight()/2;

    }

    public void onMouseClick(MouseEvent event){
        ImageView hole = new ImageView("hole.png");
        hole.setX(aim.getX());
        hole.setY(aim.getY());
        hole.setFitWidth(20);
        pane.getChildren().add(hole);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                aim.setX(mouseX * 0.1 + aim.getX() * 0.9);
                aim.setY(mouseY * 0.1 + aim.getY() * 0.9);

            }
        }.start();
    }
}

