package it.help.help.diocesi.controll;

import it.help.help.autenticazione.boundary.SchermataHomeResponsabileDiocesi;
import it.help.help.autenticazione.boundary.SchermataLogin;
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

public class GestoreDiocesi {

    public Button buttonRegistraPolo;
    public TextField fieldEmail;
    public TextField fieldNome;
    public PasswordField fieldPassword;
    public PasswordField fieldRipetiPassword;

    public void clickRegistraPolo(ActionEvent actionEvent) throws Exception {
        String nome = fieldNome.getText();
        String email = fieldEmail.getText();
        String password = fieldPassword.getText();
        String repeatPassword = fieldRipetiPassword.getText();
        Boolean showErrorAlert = false;
        String error = "";
        int type = 0;

        if(!nome.isEmpty() && !email.isEmpty() && !password.isEmpty() && !repeatPassword.isEmpty()) {
            if(password.equals(repeatPassword)) {
                if(MainUtils.isValidEmail(email) && MainUtils.isValidPassword(password)) {
                    // verifico che l'email non sia già presente nel DBMS
                    if(!DBMS.queryControllaEsistenzaEmail(email)) {
                        // registro l'utente nel DBMS
                        String encryptPassword = MainUtils.encryptPassword(password);
                        DBMS.queryRegistraPolo(MainUtils.responsabileLoggato.getId(), nome, email, encryptPassword);

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
                error = "Le password inserite non coincidono";
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
