package it.help.help.autenticazione.controll;

import it.help.help.autenticazione.boundary.SchermataLogin;
import it.help.help.entity.*;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.util.Random;
import it.help.help.utils.DBMS;

import javafx.scene.control.Alert.AlertType;
public class GestoreRegistrazione {

    public RadioButton radioButtonDiocesi;
    public RadioButton radioButtonAziendaPartner;
    public TextField fieldEmail;
    public PasswordField fieldPassword;
    public PasswordField fieldRipetiPassword;
    public Button buttonRegistrati;

    public void clickRegistrati(ActionEvent actionEvent) throws Exception {
        Boolean radioDiocesi = radioButtonDiocesi.isSelected();
        Boolean radioAzienda = radioButtonAziendaPartner.isSelected();
        String email = fieldEmail.getText();
        String password = fieldPassword.getText();
        String repeatPassword = fieldRipetiPassword.getText();
        Boolean showErrorAlert = false;
        String error = "";
        int type = 0;

        if((radioAzienda || radioDiocesi) && !email.isEmpty() && !password.isEmpty() && !repeatPassword.isEmpty()) {
            if(password.equals(repeatPassword)) {
                if(MainUtils.isValidEmail(email) && MainUtils.isValidPassword(password)) {
                    // verifico che l'email non sia già presente nel DBMS
                    if(!DBMS.queryControllaEsistenzaEmail(email)) {
                        // registro l'utente nel DBMS
                        if(radioAzienda) {
                            type = 3;
                        } else if(radioDiocesi) {
                            type = 1;
                        }
                        String encryptPassword = MainUtils.encryptPassword(password);
                        DBMS.queryRegistraResponsabile(email, encryptPassword, type);

                        // rimando alla schermata di login
                        SchermataLogin l = new SchermataLogin();
                        Stage window = (Stage) buttonRegistrati.getScene().getWindow();
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
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText(error);
            alert.showAndWait();
        }
    }

    public void clickIndietro(ActionEvent actionEvent) {
    }
}
