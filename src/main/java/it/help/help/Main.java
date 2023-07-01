package it.help.help;

import it.help.help.autenticazione.boundary.SchermataIniziale;
import it.help.help.autenticazione.controll.GestoreAutenticazione;
import it.help.help.utils.MainUtils;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class Main extends Application {
    public static Stage mainStage = null;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        // stage.setTitle("Help");
        stage.setOnCloseRequest(c -> {
            System.exit(0);
        });
        MainUtils.cambiaInterfaccia("Schermata Iniziale", "/it/help/help/SchermataIniziale.fxml", stage, c -> {
            return new SchermataIniziale(stage);
        });
    }
    public static void main(String[] args) {
        launch();
    }
}
