package it.help.help.autenticazione.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataRegistraNucleo extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataRegistraNucleo.class.getResource("/it/help/help/polo/SchermataRegistraNucleo.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Schermata registra nucleo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
