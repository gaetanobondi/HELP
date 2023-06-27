package it.help.help.diocesi.controll;

import it.help.help.autenticazione.boundary.SchermataHomeResponsabileDiocesi;
import it.help.help.common.SchermataVisualizzaSchemaDistribuzione;
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

    public void clickRegistraPolo(ActionEvent actionEvent) throws Exception {
        String nome = fieldNome.getText();
        String cognome = fieldCognome.getText();
        String indirizzo = fieldIndirizzo.getText();
        String cellulare = fieldCellulare.getText();
        String email = fieldEmail.getText();
        String password = fieldPassword.getText();
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
                    Responsabile responsabile = DBMS.getResponsabile(id_polo);
                    DBMS.queryModificaDati(responsabile.getId(), "responsabile", datiAggiornati);

                    // rimando alla schermata precedente
                    SchermataHomeResponsabileDiocesi l = new SchermataHomeResponsabileDiocesi();
                    Stage window = (Stage) buttonRegistraPolo.getScene().getWindow();
                    l.start(window);
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

    public void clickVisualizzaSchemaDiDistribuzioneDiocesi(ActionEvent actionEvent) throws Exception {
        SchermataVisualizzaSchemaDistribuzione l = new SchermataVisualizzaSchemaDistribuzione();
        Stage window = (Stage) buttonVisualizzaSchemaDiDistribuzioneDiocesi.getScene().getWindow();
        l.start(window);

        SchemaDistribuzione[] schemiDistribuzione = DBMS.queryGetSchemiDistribuzione(0, MainUtils.responsabileLoggato.getIdLavoro());

        Parent root = window.getScene().getRoot();

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
        ((Pane) root).getChildren().add(titoloLabel);

        for (SchemaDistribuzione schemaDistribuzione : schemiDistribuzione) {
            Prodotto prodotto = DBMS.queryGetProdotto(schemaDistribuzione.getCodiceProdotto());

            Label label = new Label(schemaDistribuzione.getQuantità() + " di " + prodotto.getTipo());
            label.setLayoutX(layoutX);
            label.setLayoutY(layoutY);
            layoutY += spacing;

            ((Pane) root).getChildren().add(label);
        }
    }




}
