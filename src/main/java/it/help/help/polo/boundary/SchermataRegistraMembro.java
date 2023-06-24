package it.help.help.polo.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataRegistraMembro extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataRegistraMembro.class.getResource("/it/help/help/polo/SchermataRegistraMembro.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Schermata registra membro");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
