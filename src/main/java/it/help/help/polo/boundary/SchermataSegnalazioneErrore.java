package it.help.help.polo.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataSegnalazioneErrore extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataSegnalazioneErrore.class.getResource("/it/help/help/polo/SchermataSegnalazioneErrore.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Schermata segnalazione errore");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
