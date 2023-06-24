package it.help.help.diocesi.controll;

import it.help.help.autenticazione.boundary.SchermataHomeResponsabileDiocesi;
import it.help.help.autenticazione.boundary.SchermataLogin;
import it.help.help.entity.Responsabile;
import it.help.help.utils.DBMS;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;

public class GestoreDiocesi {

    public Button buttonRegistraPolo;
    public TextField fieldEmail;
    public TextField fieldNome;
    public PasswordField fieldPassword;
    public PasswordField fieldRipetiPassword;
    public TextField fieldCognome;
    public TextField fieldIndirizzo;
    public TextField fieldCellulare;

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
}
