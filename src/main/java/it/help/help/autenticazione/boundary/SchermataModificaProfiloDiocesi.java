package it.help.help.autenticazione.boundary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataModificaProfiloDiocesi extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataModificaProfiloDiocesi.class.getResource("/it/help/help/diocesi/SchermataModificaProfiloDiocesi.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Scgermata modifica profilo");
    }

    public static void main(String[] args) {
        launch();
    }
}
