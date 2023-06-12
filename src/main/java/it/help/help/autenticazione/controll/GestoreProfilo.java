package it.help.help.autenticazione.controll;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import it.help.help.entity.*;

import it.help.help.utils.DBMS;

public class GestoreProfilo {

    public Button buttonModificaDati;
    public Label labelEmail;
    public Label labelPassword;
    public Label labelNome;
    public Label labelCognome;
    public Button buttonVisualizzaProfiloHelp;
    public Button buttonVisualizzaPrevisioneDistribuzione;
    public Button buttonRichiesteDiocesi;
    public Button buttonRichiesteAziendePartner;
    public Button buttonListaDonazioniRicevute;
    public Button buttonGestione;
    public Button buttonDonazioneAziendaPartner;

    public void clickModificaDati(ActionEvent actionEvent) {
    }

    public void clickVisualizzaProfiloHelp(ActionEvent actionEvent) throws Exception {
        Help help = DBMS.getHelp(1);
    }

    public void clickVisualizzaPrevisioneDiDistribuzione(ActionEvent actionEvent) {
    }

    public void clickRichiesteDiocesi(ActionEvent actionEvent) {
    }

    public void clickRichiesteAziendePartner(ActionEvent actionEvent) {
    }

    public void clickListaDonazioniRicevute(ActionEvent actionEvent) {
    }
}