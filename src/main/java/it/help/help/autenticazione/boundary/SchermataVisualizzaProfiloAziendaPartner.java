package it.help.help.autenticazione.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataVisualizzaProfiloAziendaPartner extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataVisualizzaProfiloAziendaPartner.class.getResource("/it/help/help/SchermataProfiloPersonale.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Profilo Personale Azienda Partner");
    }

    public static void main(String[] args) {
        launch();
    }
}

