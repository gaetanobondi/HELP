package it.help.help.autenticazione.boundary;

import it.help.help.tempo.GestoreSistema;
import it.help.help.utils.MainUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataIniziale extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataIniziale.class.getResource("/it/help/help/schermataIniziale.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Schermata Iniziale");
        stage.setScene(scene);
        stage.show();
        MainUtils.boundaryStack.add((Stage) scene.getWindow());
        GestoreSistema g = new GestoreSistema();
        // g.generazioneSchemiDistribuzione();
        g.previsioneDistribuzione();
    }

    public static void main(String[] args) {
        launch();
    }
}
