package it.help.help.autenticazione.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataVisualizzaProfiloDiocesi extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataVisualizzaProfiloDiocesi.class.getResource("/it/help/help/diocesi/SchermataProfiloPersonaleDiocesi.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Profilo Personale");
    }

    public static void main(String[] args) {
        launch();
    }
}
