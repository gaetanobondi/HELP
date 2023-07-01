package it.help.help.autenticazione.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataRecuperoPassword extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataIniziale.class.getResource("/it/help/help/SchermataRecuperoPassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Schermata Recupero Password");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
