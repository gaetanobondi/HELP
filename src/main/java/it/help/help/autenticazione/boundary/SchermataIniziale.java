package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreAutenticazione;
import it.help.help.autenticazione.controll.GestoreRegistrazione;
import it.help.help.tempo.GestoreSistema;
import it.help.help.utils.MainUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SchermataIniziale {
    public Button buttonSignIn;
    public Button buttonLogin;
    public Stage stage;

    public SchermataIniziale(Stage stage) {
        this.stage = stage;
    }


    public void clickSignIn(ActionEvent actionEvent) throws Exception {
        GestoreRegistrazione gestoreRegistrazione = new GestoreRegistrazione();
        gestoreRegistrazione.schermataSignin((Stage) buttonSignIn.getScene().getWindow());
    }

    public void clickLogin(ActionEvent actionEvent) throws Exception {
        GestoreAutenticazione gestoreAutenticazione = new GestoreAutenticazione();
        gestoreAutenticazione.schermataLogin(stage);
    }
}
