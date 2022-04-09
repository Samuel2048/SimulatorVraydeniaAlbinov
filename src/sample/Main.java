package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    static int aktivneOkno = 0;
    static Parent[] root = new Parent[2];
    static Scene mojeOkno;
    static Stage primaryStage;
    static int pocetKol = 0;
    static FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        loader = new FXMLLoader();
        for (int i = 0; i < 2; i++)
            root[i] = loader.load(getClass().getResource("sample" + i + ".fxml"));
        this.primaryStage = primaryStage;
        mojeOkno = new Scene(root[aktivneOkno]);
        primaryStage.setTitle("Target Simulation");
        primaryStage.getIcons().add(new Image("file:res/targetLogo.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(mojeOkno);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
