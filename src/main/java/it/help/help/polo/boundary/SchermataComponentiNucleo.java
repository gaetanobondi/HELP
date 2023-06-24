package it.help.help.polo.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataComponentiNucleo extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataComponentiNucleo.class.getResource("/it/help/help/polo/SchermataComponentiNucleo.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Schermata componenti nucleo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
