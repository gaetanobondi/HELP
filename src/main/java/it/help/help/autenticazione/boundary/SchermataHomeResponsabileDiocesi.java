package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreAutenticazione;
import it.help.help.autenticazione.controll.GestoreProfilo;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataHomeResponsabileDiocesi {

    public Button buttonVisualizzaProfiloDiocesi;
    public Button buttonVisualizzaSchemaDiDistribuzioneDiocesi;
    public Button buttonVisualizzaListaPoli;
    public Button buttonRegistrazionePolo;
    public Button buttonLogout;

    public void clickVisualizzaProfiloDiocesi(ActionEvent actionEvent) throws Exception {
        GestoreProfilo gestoreProfilo = new GestoreProfilo();
        gestoreProfilo.visualizzaProfiloDiocesi((Stage) buttonVisualizzaProfiloDiocesi.getScene().getWindow());
    }

    public void clickVisualizzaSchemaDiDistribuzioneDiocesi(ActionEvent actionEvent) {
    }

    public void clickVisualizzaListaPoli(ActionEvent actionEvent) {
    }

    public void clickRegistrazionePolo(ActionEvent actionEvent) {
    }

    public void clickLogout(ActionEvent actionEvent) throws Exception {
        GestoreAutenticazione gestoreAutenticazione = new GestoreAutenticazione();
        gestoreAutenticazione.logout((Stage) buttonLogout.getScene().getWindow());
    }
}
