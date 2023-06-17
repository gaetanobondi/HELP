package it.help.help.autenticazione.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataRegistrazionePolo extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataRegistrazionePolo.class.getResource("/it/help/help/SchermataRegistrazionePolo.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Registrazione Polo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
