package it.help.help.diocesi.controll;

import it.help.help.common.SchermataVisualizzaSchemaDistribuzione;
import it.help.help.diocesi.boundary.SchermataRegistrazionePoloTerritoriale;
import it.help.help.diocesi.boundary.SchermataVisualizzaPoliIscritti;
import it.help.help.entity.Polo;
import it.help.help.entity.Prodotto;
import it.help.help.entity.Responsabile;
import it.help.help.entity.SchemaDistribuzione;
import it.help.help.utils.DBMS;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class GestoreDiocesi {

    public Button buttonRegistraPolo;
    public TextField fieldEmail;
    public TextField fieldNome;
    public PasswordField fieldPassword;
    public PasswordField fieldRipetiPassword;
    public TextField fieldCognome;
    public TextField fieldIndirizzo;
    public TextField fieldCellulare;
    public Button buttonVisualizzaSchemaDiDistribuzioneDiocesi;

    public void registraPolo(Stage stage, String nome, String cognome, String indirizzo, String cellulare, String email, String password) throws Exception {
        Boolean showErrorAlert = false;
        String error = "";
        int type = 0;

        if(!nome.isEmpty() && !cognome.isEmpty() && !indirizzo.isEmpty() && !cellulare.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            if(MainUtils.isValidEmail(email) && MainUtils.isValidPassword(password)) {
                // verifico che l'email non sia già presente nel DBMS
                if(!DBMS.queryControllaEsistenzaEmail(email)) {
                    // registro l'utente nel DBMS
                    String encryptPassword = MainUtils.encryptPassword(password);
                    int id_polo = DBMS.queryRegistraPolo(MainUtils.responsabileLoggato.getIdLavoro(), nome, indirizzo, Integer.parseInt(cellulare));
                    DBMS.queryRegistraResponsabile(email, encryptPassword, 2, id_polo);
                    // aggiorno la tabella responsabile
                    HashMap<String, Object> datiAggiornati = new HashMap<>();
                    datiAggiornati.put("nome", nome);
                    datiAggiornati.put("cognome", cognome);
                    Responsabile responsabile = DBMS.getResponsabile(2, id_polo);
                    DBMS.queryModificaDati(responsabile.getId(), "responsabile", datiAggiornati);

                    // rimando alla schermata precedente
                    MainUtils.tornaAllaHome(stage);
                } else {
                    showErrorAlert = true;
                    error = "Email già esistente";
                }
            } else {
                showErrorAlert = true;
                error = "I dati inseriti non sono corretti";
            }
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi";
        }

        if(showErrorAlert) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText(error);
            alert.showAndWait();
        }
    }

    public void schermataRegistraPolo(Stage stage) {
        MainUtils.cambiaInterfaccia("Schermata registra polo", "/it/help/help/diocesi/SchermataRegistrazionePoloTerritoriale.fxml", stage, c -> {
            return new SchermataRegistrazionePoloTerritoriale(this);
        });
    }

    public void schermataVisualizzaSchemaDiDistribuzioneDiocesi(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata visualizza schema di distribuzione", "/it/help/help/diocesi/SchermataVisualizzaSchemaDistribuzioneDiocesi.fxml", stage, c -> {
            return new SchermataVisualizzaSchemaDistribuzione();
        });

        SchemaDistribuzione[] schemiDistribuzione = DBMS.queryGetSchemiDistribuzione(0, MainUtils.responsabileLoggato.getIdLavoro());

        Parent root = stage.getScene().getRoot();
        VBox lista = (VBox) stage.getScene().getRoot().lookup("#lista");

        double layoutY = 140;
        double spacing = 40.0; // Spazio verticale tra i componenti
        double layoutX = 20.0; // Spazio laterale

        // Ottieni il nome del mese corrente in italiano
        LocalDate currentDate = LocalDate.now();
        String nomeMeseCorrente = currentDate.format(DateTimeFormatter.ofPattern("MMMM", new Locale("it")));

        // Aggiungi il titolo
        Label titoloLabel = new Label("Schema di distribuzione di " + nomeMeseCorrente);
        titoloLabel.setLayoutX(layoutX);
        titoloLabel.setLayoutY(80);
        titoloLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        lista.getChildren().add(titoloLabel);

        for (SchemaDistribuzione schemaDistribuzione : schemiDistribuzione) {
            Prodotto prodotto = DBMS.queryGetProdotto(schemaDistribuzione.getCodiceProdotto());

            Label label = new Label(schemaDistribuzione.getQuantità() + " di " + prodotto.getTipo());
            label.setLayoutX(layoutX);
            label.setLayoutY(layoutY);
            layoutY += spacing;

            lista.getChildren().add(label);
        }

        if(schemiDistribuzione.length == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText("Non ci sono schemi per questo mese");
            alert.showAndWait();
            MainUtils.tornaAllaHome(stage);
        }
    }

    public void schermataVisualizzaPoliIscritti(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata visualizza lista poli", "/it/help/help/diocesi/SchermataVisualizzaPoliIscritti.fxml", stage, c -> {
            return new SchermataVisualizzaPoliIscritti();
        });

        Polo[] listaPoli = DBMS.queryGetAllPoli(MainUtils.responsabileLoggato.getIdLavoro());

        Parent root = stage.getScene().getRoot();
        VBox lista = (VBox) stage.getScene().getRoot().lookup("#lista");

        double layoutY = 140;
        double spacing = 40.0; // Spazio verticale tra i componenti
        double layoutX = 20.0; // Spazio laterale

        for (Polo polo : listaPoli) {

            Label label = new Label(polo.getNome());
            label.setLayoutX(layoutX);
            label.setLayoutY(layoutY);
            layoutY += spacing;

            lista.getChildren().add(label);
        }

        if(listaPoli.length == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText("Nessun polo iscritto");
            alert.showAndWait();
            MainUtils.tornaAllaHome(stage);
        }
    }




}
