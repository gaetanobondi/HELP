package it.help.help.help.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataGestione extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataGestione.class.getResource("/it/help/help/help/SchermataGestione.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Schermata gestione");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

