package org.icesi.implementacionjuegopacman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.icesi.implementacionjuegopacman.Controllers.GameViewController;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/icesi/implementacionjuegopacman/GameView.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Juego de Pac-Man con Grafos");
        primaryStage.show();

        scene.setOnKeyPressed(event -> {
            GameViewController controller = loader.getController();
            controller.manejarTeclas(event.getCode());
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
