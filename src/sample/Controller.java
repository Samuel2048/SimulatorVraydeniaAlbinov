package sample;


import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    ImageView target, aim, arrow;
    @FXML
    Slider tiredSlider, windSlider;
    @FXML
    Label windLabel, tiredLabel;
    @FXML
    AnchorPane pane;
    @FXML
    ListView<String> listView;

    double mouseX, mouseY;
    int angle;


    Random rn = new Random();

    public void onMouseMove(MouseEvent event){
        mouseX = event.getX() - aim.getFitWidth()/2;
        mouseY = event.getY() - aim.getFitHeight()/2;

    }

    public void onMouseClick(MouseEvent event){
        ImageView hole = new ImageView("file:res/hole.png");
        hole.setX(aim.getX());
        hole.setY(aim.getY());
        hole.setFitWidth(20);
        pane.getChildren().add(hole);

        listView.getItems().add("test");
    }

    public void generateWindDirection() {
        angle = rn.nextInt(364) + 1;
        arrow.setRotate(angle);
    }


    //TODO: smer vetra + sila vetra
    public void windForceShot() {

    }

    public void tiredShot() {
        double exhaustionX = rn.nextInt((int) tiredSlider.getValue());
        double exhaustionY = rn.nextInt((int) tiredSlider.getValue());
        aim.setX(exhaustionX);
        aim.setY(exhaustionY);
    }

    public double distanceBetweenTwoPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public void shotDistanceFromCentre() {
        double x1 = target.getFitWidth() / 2;
        double y1 = target.getFitHeight() / 2;
        double x2 = aim.getX();
        double y2 = aim.getY();

        double distance = distanceBetweenTwoPoints(x1, y1, x2, y2);
    }

    //TODO: nech sú slidery v int a nie double typoch
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tiredLabel.setText(""+(int)tiredSlider.getValue());
        tiredSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                tiredLabel.setText(String.format("%.0f", t1));
            }
        });

        windLabel.setText(""+(int)windSlider.getValue());
        windSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                windLabel.setText(String.format("%.0f", t1));
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long l) {
                aim.setX(mouseX * 0.1 + aim.getX() * 0.9);
                aim.setY(mouseY * 0.1 + aim.getY() * 0.9);

            }
        }.start();
    }
}

