package it.help.help.autenticazione.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataListaNuclei extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataListaNuclei.class.getResource("/it/help/help/polo/SchermataListaNuclei.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Schermata lista nuclei");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
