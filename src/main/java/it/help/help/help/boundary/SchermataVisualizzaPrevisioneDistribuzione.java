package it.help.help.help.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataVisualizzaPrevisioneDistribuzione extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataVisualizzaPrevisioneDistribuzione.class.getResource("/it/help/help/help/SchermataVisualizzaPrevisioneDistribuzione.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Schermata visualizza previsione di distribuzione");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

