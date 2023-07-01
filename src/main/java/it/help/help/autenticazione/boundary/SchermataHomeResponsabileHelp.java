package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreAccettazioneAccount;
import it.help.help.autenticazione.controll.GestoreAutenticazione;
import it.help.help.autenticazione.controll.GestoreProfilo;
import it.help.help.help.controll.GestoreHelp;
import it.help.help.magazzino.controll.GestoreMagazzino;
import javafx.event.ActionEvent;
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
        GestoreAccettazioneAccount gestoreAccettazioneAccount = new GestoreAccettazioneAccount();
        gestoreAccettazioneAccount.schermataRichiesteDiocesi((Stage) buttonRichiesteDiocesi.getScene().getWindow());
    }

    public void clickRichiesteAziendePartner(ActionEvent actionEvent) throws Exception {
        GestoreAccettazioneAccount gestoreAccettazioneAccount = new GestoreAccettazioneAccount();
        gestoreAccettazioneAccount.schermataRichiesteAziende((Stage) buttonRichiesteAziendePartner.getScene().getWindow());
    }

    public void clickVisualizzaPrevisioneDiDistribuzione(ActionEvent actionEvent) throws Exception {
        GestoreHelp gestoreHelp = new GestoreHelp();
        gestoreHelp.visualizzaPrevisioneDiDistribuzione((Stage) buttonVisualizzaPrevisioneDistribuzione.getScene().getWindow());
    }

    public void clickListaDonazioniRicevute(ActionEvent actionEvent) throws Exception {
        GestoreHelp gestoreHelp = new GestoreHelp();
        gestoreHelp.schermataListaDonazioniRicevute((Stage) buttonListaDonazioniRicevute.getScene().getWindow());
    }

    public void clickAggiungiViveriMagazzino(ActionEvent actionEvent) throws Exception {
        GestoreMagazzino gestoreMagazzino = new GestoreMagazzino();
        gestoreMagazzino.aggiungiViveriMagazzino((Stage) buttonAggiungiViveriMagazzino.getScene().getWindow());
    }
    public void clickGestione(ActionEvent actionEvent) throws IOException {
        GestoreHelp gestoreHelp = new GestoreHelp();
        gestoreHelp.schermataGestione((Stage) buttonGestione.getScene().getWindow());
    }
}

