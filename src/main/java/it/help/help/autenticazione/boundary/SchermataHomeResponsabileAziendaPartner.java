package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreAutenticazione;
import it.help.help.autenticazione.controll.GestoreProfilo;
import it.help.help.azienda_partner.controll.GestoreAziendaPartner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataHomeResponsabileAziendaPartner {

    public Button buttonLogout;
    public Button buttonVisualizzaDonazioniEffettuate;
    public Button buttonEffettuaDonazioneSpontanea;
    public Button buttonListaDonazioniAdHoc;
    public Button buttonVisualizzaProfiloAziendaPartner;

    public void clickLogout(ActionEvent actionEvent) throws Exception {
        GestoreAutenticazione gestoreAutenticazione = new GestoreAutenticazione();
        gestoreAutenticazione.logout((Stage) buttonLogout.getScene().getWindow());
    }

    public void clickEffettuaDonazioneSpontanea(ActionEvent actionEvent) throws Exception {
        GestoreAziendaPartner gestoreAziendaPartner = new GestoreAziendaPartner();
        gestoreAziendaPartner.schermataEffettuaDonazioneSpontanea((Stage) buttonEffettuaDonazioneSpontanea.getScene().getWindow());
    }

    public void clickListaDonazioniAdHoc(ActionEvent actionEvent) throws Exception {
        GestoreAziendaPartner gestoreAziendaPartner = new GestoreAziendaPartner();
        gestoreAziendaPartner.schermataListaDonazioniAdHoc((Stage) buttonListaDonazioniAdHoc.getScene().getWindow());
    }

    public void clickVisualizzaDonazioniEffettuate(ActionEvent actionEvent) throws Exception {
        GestoreAziendaPartner gestoreAziendaPartner = new GestoreAziendaPartner();
        gestoreAziendaPartner.schermataVisualizzaDonazioniEffettuate((Stage) buttonVisualizzaDonazioniEffettuate.getScene().getWindow());
    }
    public void clickVisualizzaProfiloAziendaPartner(ActionEvent actionEvent) throws Exception {
        GestoreProfilo gestoreProfilo = new GestoreProfilo();
        gestoreProfilo.visualizzaProfiloAziendaPartner((Stage) buttonVisualizzaProfiloAziendaPartner.getScene().getWindow());
    }
}
