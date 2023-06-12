package it.help.help.autenticazione.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataHomeResponsabileHelp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataHomeResponsabileHelp.class.getResource("/it/help/help/schermataHomeResponsabileHelp.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Schermata Home Responsabile Help");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

