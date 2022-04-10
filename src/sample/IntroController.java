package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class IntroController {
    @FXML
    TextField playerName;
    @FXML
    ListView scoreList;

    public void startGame() {
        Main.playerName = playerName.getText();
        Main.mojeOkno.setRoot(Main.root[1]);
    }
}
