package sample;


import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    Random rn = new Random();
    double mouseX, mouseY, currentAimOffsetY, currentAimOffsetX;
    boolean offsetAscendingY, offsetAscendingX;
    int tiredness = 0;
    int maxOffsetY, maxOffsetX;
    int windAngle = 0, windSpeed = 0;
    double windOffsetX;
    double windOffsetY;
    double finalScore = 0;
    BufferedWriter bout;
    int shotCounter = 0;

    public void onMouseMove(MouseEvent event) {
        mouseX = event.getX() - aim.getFitWidth()/2;
        mouseY = event.getY() - aim.getFitHeight()/2;

    }

    public void onMouseClick() throws MalformedURLException {
        ImageView hole = new ImageView();
        Path imageFile = Paths.get("res/hole.png");
        hole.setImage(new Image(imageFile.toUri().toURL().toExternalForm()));
        hole.setFitWidth(5);
        hole.setFitHeight(17);
        double shotX = getAimCenter()[0] + windOffsetX;
        double shotY = getAimCenter()[1] + windOffsetY;

        hole.setX( shotX  - hole.getFitHeight()/2 );
        hole.setY( shotY  - hole.getFitHeight()/2 );

        hole.setFitWidth(20);
        pane.getChildren().add(hole);

        double centerX = target.getFitWidth() / 2;
        double centerY = target.getFitHeight() / 2;

        double distance = distanceBetweenTwoPoints(centerX, centerY, shotX, shotY);
        double score = 10 - (distance/250*10);
        finalScore += score;
        shotCounter++;
        System.out.println(distance + " " + score);
        listView.getItems().add(String.valueOf(Math.round(score*10.0)/10.0));

        // TODO: koniec hry
        if (shotCounter == 10)
            gameOver();


    }
    public void showCursor() {
        pane.getScene().setCursor(Cursor.DEFAULT);
    }
    public void hideCursor() {
        pane.getScene().setCursor(Cursor.NONE);
    }

    private double[] getAimCenter() {
        double x = aim.getX() + aim.getFitWidth()/2;
        double y = aim.getY() + aim.getFitHeight()/2;
        return new double[] {x,y};

    }
    private void gameOver() {
        try {

            bout.newLine();
            bout.write(Main.playerName + ": " + Math.round(finalScore*100.0)/100.0);
            bout.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double distanceBetweenTwoPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            bout = new BufferedWriter(new FileWriter("score.txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tiredLabel.setText(""+(int)tiredSlider.getValue());
        tiredSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                tiredLabel.setText(String.format("%.0f", t1));
                tiredness = t1.intValue();

            }
        });

        windLabel.setText(""+(int)windSlider.getValue());
        windSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                windLabel.setText(String.format("%.0f", t1));
                windSpeed = t1.intValue();
            }
        });

        windAngle = rn.nextInt(360);
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                aim.setX((mouseX + currentAimOffsetX) * 0.1 + aim.getX() * 0.9);
                aim.setY((mouseY + currentAimOffsetY) * 0.1 + aim.getY() * 0.9);
                if (mouseX > 500)
                    aim.setX(460);

                windAngle += rn.nextInt(3) -1;
                arrow.setRotate(windAngle);
                windOffsetX = 10 * windSpeed * Math.cos((Math.toRadians(windAngle) % 360));
                windOffsetY = 10 * windSpeed * Math.sin((Math.toRadians(windAngle) % 360));
                //System.out.println(windAngle + " " + windOffsetX + " " + windOffsetY);

                if (offsetAscendingX) {
                    currentAimOffsetX += tiredness/(rn.nextDouble()*2 + 3);
                    if (currentAimOffsetX > tiredness * maxOffsetX + windOffsetX) {
                        offsetAscendingX = false;
                        maxOffsetX = rn.nextInt(tiredness);
                    }
                }
                else {
                    currentAimOffsetX -= tiredness/(rn.nextDouble()*1.5 + 3);
                    if (currentAimOffsetX < tiredness * -maxOffsetX + windOffsetX) {
                        offsetAscendingX = true;
                        maxOffsetX = rn.nextInt(tiredness);
                    }
                }

                if (offsetAscendingY) {
                    currentAimOffsetY += tiredness/(rn.nextDouble()*2 + 2);
                    if (currentAimOffsetY > tiredness * maxOffsetY + windOffsetY) {
                        offsetAscendingY = false;
                        maxOffsetY = rn.nextInt(tiredness * 2);
                    }
                }
                else {
                    currentAimOffsetY -= tiredness/(rn.nextDouble()*1.5 + 2);
                    if (currentAimOffsetY < tiredness * -maxOffsetY + windOffsetY) {
                        offsetAscendingY = true;
                        maxOffsetY = rn.nextInt(tiredness * 2);
                    }
                }
            }
        }.start();
    }
}

