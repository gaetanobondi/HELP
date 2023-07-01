package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreAutenticazione;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataLogin {
    public Button buttonIndietro;
    public PasswordField fieldPassword;
    public TextField fieldEmail;
    public Button buttonRecuperaPassword;
    public Button buttonAccedi;
    public GestoreAutenticazione gestoreAutenticazione;

    public SchermataLogin(GestoreAutenticazione gestoreAutenticazione) {
        this.gestoreAutenticazione = gestoreAutenticazione;
    }


    public void clickAccedi(ActionEvent actionEvent) throws Exception {
        String email = fieldEmail.getText();
        String password = fieldPassword.getText();

        gestoreAutenticazione.controllaCredenziali((Stage) fieldEmail.getScene().getWindow(), email, password);
    }

    public void clickIndietro(ActionEvent actionEvent) {
    }

    public void clickRecuperaPassword(ActionEvent actionEvent) {
    }
}
