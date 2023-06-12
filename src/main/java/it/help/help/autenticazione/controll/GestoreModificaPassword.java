package it.help.help.autenticazione.controll;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class GestoreModificaPassword {

    public Button buttonIndietro;
    public Button buttonConferma;
    public PasswordField fieldNuovaPassword;
    public PasswordField filedRipetiPassword;

    public void clickIndietro(ActionEvent actionEvent) {
        System.out.println("ciao cambio Password");
    }

    public void clickConferma(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataLogin.fxml"));
        Stage window = (Stage) buttonConferma.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Login");
    }


    //CREAZIONE DEI POP-UP DI ERRORE


}
