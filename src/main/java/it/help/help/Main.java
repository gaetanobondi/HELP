package it.help.help;

import it.help.help.autenticazione.boundary.SchermataIniziale;
import it.help.help.tempo.GestoreSistema;
import it.help.help.utils.MainUtils;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

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
    public static void main(String[] args) throws Exception {
        LocalDate currentDate = LocalDate.now();

        // se è il primo del mese controllo se devo sospendere dei poli
        if (currentDate.getDayOfMonth() == 1) {
            GestoreSistema gestoreSistema = new GestoreSistema();
            gestoreSistema.checkSospensionePoli();
        }

        // se è il 25 del mese genero lo schema di distribuzione
        if (currentDate.getDayOfMonth() == 25) {
            GestoreSistema gestoreSistema = new GestoreSistema();
            // elimino gli schemi di distribuzione dei mesi precedenti
            gestoreSistema.eliminaSchemiDistribuzione();
            if(!gestoreSistema.checkSchemiDistribuzione()) {
                gestoreSistema.generazioneSchemiDistribuzione();
            }
        }

        // se è il 2 del mese genero la previsione
        if (currentDate.getDayOfMonth() == 2) {
            GestoreSistema gestoreSistema = new GestoreSistema();
            gestoreSistema.previsioneDistribuzione();
        }
        launch();
    }
}
