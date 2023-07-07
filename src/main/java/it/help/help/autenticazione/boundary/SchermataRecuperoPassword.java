package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreAutenticazione;
import it.help.help.autenticazione.controll.GestorePassword;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SchermataRecuperoPassword {

    public Button buttonIndietro;
    public TextField fieldEmail;
    public Button buttonRecupera;
    public GestoreAutenticazione gestoreAutenticazione;
    public SchermataRecuperoPassword(GestoreAutenticazione gestoreAutenticazione) {
        this.gestoreAutenticazione = gestoreAutenticazione;
    }

    public void clickIndietro(ActionEvent actionEvent) {
        GestorePassword gestorePassword = new GestorePassword(gestoreAutenticazione);
        gestorePassword.schermataLogin((Stage) buttonIndietro.getScene().getWindow());
    }

    public void clickRecupera(ActionEvent actionEvent) throws Exception {
        String email = fieldEmail.getText();
        GestorePassword gestorePassword = new GestorePassword(gestoreAutenticazione);
        gestorePassword.recuperaPassword((Stage) buttonRecupera.getScene().getWindow(), email);
    }
}
