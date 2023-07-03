package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreAutenticazione;
import it.help.help.magazzino.controll.GestoreMagazzino;
import it.help.help.utils.DBMS;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class SchermataRipristinoPolo {
    public MenuButton selectAlimenti;
    public TextField fieldMenuSelected;
    public TextField fieldQuantità;
    public DatePicker pickerDataScadenza;
    public CheckBox checkBoxSenzaGlutine;
    public CheckBox checkBoxSenzaZuccheri;
    public CheckBox checkBoxSenzaLattosio;
    public Button buttonAggiornaMagazzino;
    public Button buttonLogout;


    public void clickAggiornaMagazzino(ActionEvent actionEvent) throws Exception {
        String codice_prodotto = fieldMenuSelected.getText();
        String quantità = fieldQuantità.getText();
        LocalDate data_scadenza = pickerDataScadenza.getValue();
        GestoreMagazzino gestoreMagazzino = new GestoreMagazzino();
        gestoreMagazzino.caricaViveri((Stage) buttonAggiornaMagazzino.getScene().getWindow(), 2, codice_prodotto, quantità, data_scadenza);
        DBMS.queryRipristinaPolo(MainUtils.responsabileLoggato.getIdLavoro());
    }

    public void clickLogout(ActionEvent actionEvent) throws Exception {
        GestoreAutenticazione gestoreAutenticazione = new GestoreAutenticazione();
        gestoreAutenticazione.logout((Stage) buttonLogout.getScene().getWindow());
    }
}
