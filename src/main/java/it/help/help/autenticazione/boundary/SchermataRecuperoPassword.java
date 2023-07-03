package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestorePassword;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SchermataRecuperoPassword {

    public Button buttonIndietro;
    public TextField fieldEmail;
    public Button buttonRecupera;

    public void clickIndietro(ActionEvent actionEvent) {
        GestorePassword gestorePassword = new GestorePassword();
        gestorePassword.schermataLogin((Stage) buttonIndietro.getScene().getWindow());
    }

    public void clickRecupera(ActionEvent actionEvent) throws Exception {
        String email = fieldEmail.getText();
        GestorePassword gestorePassword = new GestorePassword();
        gestorePassword.recuperaPassword((Stage) buttonRecupera.getScene().getWindow(), email);
    }
}
