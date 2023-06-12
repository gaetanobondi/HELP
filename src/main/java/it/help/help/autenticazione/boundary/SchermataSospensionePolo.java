package it.help.help.autenticazione.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataSospensionePolo extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataSospensionePolo.class.getResource("/it/help/help/SchermataSospensionePolo.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Avviso sospensionePolo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

