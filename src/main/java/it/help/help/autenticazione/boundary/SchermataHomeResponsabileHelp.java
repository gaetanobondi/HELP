package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreAccettazioneEsiti;
import it.help.help.autenticazione.controll.GestoreAutenticazione;
import it.help.help.autenticazione.controll.GestoreProfilo;
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

    public void clickVisualizzaProfiloHelp(ActionEvent actionEvent) throws Exception {
        GestoreProfilo gestoreProfilo = new GestoreProfilo();
        gestoreProfilo.visualizzaProfiloHelp((Stage) buttonVisualizzaProfiloHelp.getScene().getWindow());
    }

    public void clickRichiesteDiocesi(ActionEvent actionEvent) throws Exception {
        GestoreAccettazioneEsiti gestoreAccettazioneEsiti = new GestoreAccettazioneEsiti();
        gestoreAccettazioneEsiti.schermataRichiesteDiocesi((Stage) buttonRichiesteDiocesi.getScene().getWindow());
    }

    public void clickRichiesteAziendePartner(ActionEvent actionEvent) throws Exception {
        GestoreAccettazioneEsiti gestoreAccettazioneEsiti = new GestoreAccettazioneEsiti();
        gestoreAccettazioneEsiti.schermataRichiesteAziende((Stage) buttonRichiesteAziendePartner.getScene().getWindow());
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

