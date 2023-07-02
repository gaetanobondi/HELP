package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreRegistrazione;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataSignin {

    public Button buttonIndietro;
    public RadioButton radioButtonDiocesi;
    public RadioButton radioButtonAziendaPartner;
    public TextField fieldEmail;
    public PasswordField fieldRipetiPassword;
    public PasswordField fieldPassword;
    public Button buttonRegistrati;
    public GestoreRegistrazione gestoreRegistrazione;
    public SchermataSignin(GestoreRegistrazione gestoreRegistrazione) {
        this.gestoreRegistrazione = gestoreRegistrazione;
    }

    public void clickIndietro(ActionEvent actionEvent) {
        gestoreRegistrazione.schermataIniziale((Stage) buttonIndietro.getScene().getWindow());
    }

    public void clickRegistrati(ActionEvent actionEvent) throws Exception {
        Boolean radioDiocesi = radioButtonDiocesi.isSelected();
        Boolean radioAzienda = radioButtonAziendaPartner.isSelected();
        String email = fieldEmail.getText();
        String password = fieldPassword.getText();
        String repeatPassword = fieldRipetiPassword.getText();

        gestoreRegistrazione.registraResponsabile((Stage) buttonRegistrati.getScene().getWindow(), radioDiocesi, radioAzienda, email, password, repeatPassword);
    }
}
