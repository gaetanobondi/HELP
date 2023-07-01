package it.help.help.azienda_partner.boundary;

import it.help.help.azienda_partner.controll.GestoreAziendaPartner;
import it.help.help.utils.MainUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class SchermataEffettuaDonazione {

    public Button buttonHome;
    public MenuButton selectAlimenti;
    public TextField fieldMenuSelected;
    public TextField fieldQuantità;
    public DatePicker pickerDataScadenza;
    public Button buttonEffettuaDonazione;
    public CheckBox checkBoxSenzaGlutine;
    public CheckBox checkBoxSenzaZuccheri;
    public CheckBox checkBoxSenzaLattosio;
    public GestoreAziendaPartner gestoreAziendaPartner;

    public SchermataEffettuaDonazione(GestoreAziendaPartner gestoreAziendaPartner) {
        this.gestoreAziendaPartner = gestoreAziendaPartner;
    }
    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }

    public void clickEffettuaDonazione(ActionEvent actionEvent) throws Exception {
        String codice_prodotto = fieldMenuSelected.getText();
        String quantità = fieldQuantità.getText();
        LocalDate data_scadenza = pickerDataScadenza.getValue();

        gestoreAziendaPartner.effettuaDonazioneSpontanea((Stage) buttonEffettuaDonazione.getScene().getWindow(), codice_prodotto, quantità, data_scadenza);
    }
}
