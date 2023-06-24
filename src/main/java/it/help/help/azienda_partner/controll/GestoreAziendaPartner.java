package it.help.help.azienda_partner.controll;

import it.help.help.utils.DBMS;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;

public class GestoreAziendaPartner {

    public TextField fieldQuantità;
    public DatePicker pickerDataScadenza;
    public Button buttonEffettuaDonazione;
    public MenuButton selectAlimenti;
    public TextField fieldMenuSelected;

    public void clickEffettuaDonazione(ActionEvent actionEvent) throws Exception {
        String codice_prodotto = fieldMenuSelected.getText();
        String quantità = fieldQuantità.getText();
        LocalDate data_scadenza = pickerDataScadenza.getValue();
        boolean showErrorAlert = false;
        String error = "";

        if(!codice_prodotto.isEmpty() && !quantità.isEmpty() && data_scadenza != null) {
            // Conversione da LocalDate a java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(data_scadenza);
            DBMS.querySalvaDonazione(MainUtils.responsabileLoggato.getIdLavoro(), Integer.parseInt(codice_prodotto), Integer.parseInt(quantità), sqlDate);
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi.";
        }

        if(showErrorAlert) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText(error);
            alert.showAndWait();
        } else {
            MainUtils.tornaAllaHome(buttonEffettuaDonazione);
        }
    }
}
