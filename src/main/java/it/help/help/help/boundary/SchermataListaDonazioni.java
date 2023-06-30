package it.help.help.help.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataListaDonazioni extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataListaDonazioni.class.getResource("/it/help/help/help/SchermataListaDonazioni.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Schermata lista donazioni");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

