package it.help.help.help.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataLista extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataLista.class.getResource("/it/help/help/help/SchermataLista.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Schermata lista");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

