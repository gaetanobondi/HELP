package it.help.help.autenticazione.controll;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import it.help.help.entity.*;

import it.help.help.utils.DBMS;
import javafx.stage.Stage;

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

    private final Responsabile responsabile;
    private final Help help;

    public GestoreProfilo(Responsabile responsabile, Help help) {
        this.responsabile = responsabile;
        this.help = help;
    }

    public GestoreProfilo() {
        this.help = null;
        this.responsabile = null;
    }



    public void clickModificaDati(ActionEvent actionEvent) {

    }

    public void clickVisualizzaProfiloHelp(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataProfiloPersonaleHelp.fxml"));
        Stage window = (Stage) buttonVisualizzaProfiloHelp.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickVisualizzaPrevisioneDiDistribuzione(ActionEvent actionEvent) {
    }

    public void clickRichiesteDiocesi(ActionEvent actionEvent) {
    }

    public void clickRichiesteAziendePartner(ActionEvent actionEvent) {
    }

    public void clickListaDonazioniRicevute(ActionEvent actionEvent) {
    }

    public void clickGestione(ActionEvent actionEvent) {
    }

    public void clickDonazioneAziendaPartner(ActionEvent actionEvent) {
    }

    public void clickLogout(ActionEvent actionEvent) {
    }
}