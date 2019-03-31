/*
 * ChainProof
 * Tsiounis Konstantinos 2630
 * Polyzos Alexios 2338
 * Copyright (c) 2019.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/main_view.fxml"));
        primaryStage.setTitle("ChainProof Graph Builder");
        primaryStage.setScene(new Scene(root, 860, 805));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
