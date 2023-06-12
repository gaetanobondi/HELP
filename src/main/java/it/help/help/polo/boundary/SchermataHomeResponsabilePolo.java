package it.help.help.polo.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataHomeResponsabilePolo extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataHomeResponsabilePolo.class.getResource("/it/help/help/schermataHomeResponsabilePolo.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Schermata Home Responsabile Polo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

