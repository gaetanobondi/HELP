package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreAutenticazione;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataHomeResponsabileHelp {

    public Button buttonLogout;
    public Button buttonVisualizzaProfiloHelp;
    public Button buttonRichiesteDiocesi;
    public Button buttonRichiesteAziendePartner;
    public Button buttonAggiungiViveriMagazzino;
    public Button buttonVisualizzaPrevisioneDistribuzione;
    public Button buttonGestione;
    public Button buttonListaDonazioniRicevute;

    public void clickLogout(ActionEvent actionEvent) throws Exception {
        GestoreAutenticazione gestoreAutenticazione = new GestoreAutenticazione();
        gestoreAutenticazione.logout((Stage) buttonLogout.getScene().getWindow());
    }

    public void clickVisualizzaProfiloHelp(ActionEvent actionEvent) {
    }

    public void clickRichiesteDiocesi(ActionEvent actionEvent) {
    }

    public void clickRichiesteAziendePartner(ActionEvent actionEvent) {
    }

    public void clickVisualizzaPrevisioneDiDistribuzione(ActionEvent actionEvent) {
    }

    public void clickListaDonazioniRicevute(ActionEvent actionEvent) {
    }

    public void clickAggiungiViveriMagazzino(ActionEvent actionEvent) {
    }

    public void clickGestione(ActionEvent actionEvent) {
    }
}

