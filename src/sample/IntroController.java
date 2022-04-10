package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.*;

public class IntroController implements Initializable {
    @FXML
    TextField playerName;
    @FXML
    ListView<String> scoreList;
    static BufferedWriter bout;

    public void startGame() {
        try {
            bout = new BufferedWriter(new FileWriter("score.txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller.shotCounter = 0;
        Main.playerName = playerName.getText();
        Main.mojeOkno.setRoot(Main.root[1]);
        playerName.clear();
    }

    public static void readPlayers(ListView<String> scoreList) {
        scoreList.getItems().clear();
        try {
            BufferedReader bin = new BufferedReader(new FileReader("score.txt"));
            HashMap<String, Double> players = new HashMap<>();
            while (bin.ready()) {
                String [] line = bin.readLine().split(",");
                players.put(line[0], Double.parseDouble(line[1]));
            }

            List<Map.Entry<String, Double>> list = new ArrayList<>(players.entrySet());
            list.sort(Map.Entry.comparingByValue());

            try {
                for (int i = 0; i < 10; i++) {
                    scoreList.getItems().add(0, (10-i)+". "+list.get(i).getKey()+": "+list.get(i).getValue());
                }
            }
            catch (Exception e) {}

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                readPlayers(scoreList);
            }
        }.start();
    }
}
