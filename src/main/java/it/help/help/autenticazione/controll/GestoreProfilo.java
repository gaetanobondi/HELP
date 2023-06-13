package it.help.help.autenticazione.controll;

import it.help.help.entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import it.help.help.utils.DBMS;

import javax.swing.text.html.ImageView;

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




    //per la schermata MODIFICA PROFILO PERSONALE AZIENDA PARTNER
    public Button buttonIndietro;
    public Button buttonSalvaModifiche;

    public ImageView home;
    public TextField fieldEmail;
    public TextField fieldCellulare;
    public TextField fieldIndirizzo;
    public PasswordField fieldVecchiaPassword;
    public PasswordField fieldNuovaPassword;
    public TextField filedNomeAziendaPartner;


    public void clickIndietro(ActionEvent actionEvent) {

    }

    public void clickSalvaModifiche(ActionEvent actionEvent) {

    }




    //per la schermata MODIFICA PROFILO PERSONALE DIOCESI
    public TextField filedNomeDiocesi;
    public TextField fieldNomePrete;

    public void fillInEmail(ActionEvent actionEvent) {
    }

    public void fillInCellulare(ActionEvent actionEvent) {
    }

    public void fillInIndirizzo(ActionEvent actionEvent) {
    }

    public void fillInVecchiaPassword(ActionEvent actionEvent) {
    }

    public void filInNuovaPassword(ActionEvent actionEvent) {
    }

    public void fillInNomeDiocesi(ActionEvent actionEvent) {
    }

    public void fillInNomePrete(ActionEvent actionEvent) {

    }


}