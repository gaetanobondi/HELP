package it.help.help.magazzino.controll;

import it.help.help.entity.Magazzino;
import it.help.help.utils.DBMS;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

public class GestoreMagazzino {
    public TextField fieldMenuSelected;
    public TextField fieldQuantità;
    public DatePicker pickerDataScadenza;
    public Button buttonEffettuaDonazione;
    public Button buttonCaricaViveri;

    public void clickCaricaViveri(ActionEvent actionEvent) throws Exception {
        String codice_prodotto = fieldMenuSelected.getText();
        String quantità = fieldQuantità.getText();
        LocalDate data_scadenza = pickerDataScadenza.getValue();
        boolean showErrorAlert = false;
        String error = "";

        if(!codice_prodotto.isEmpty() && !quantità.isEmpty() && data_scadenza != null) {
            // Conversione da LocalDate a java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(data_scadenza);
            // ottengo tutti i magazzini disponibili
            Magazzino[] listaMagazzini = DBMS.queryGetMagazzini(0, MainUtils.responsabileLoggato.getIdLavoro());
            boolean prodottoAggiunto = false;
            for(Magazzino magazzino : listaMagazzini) {
                int nuova_capienza = magazzino.getCapienzaAttuale() + Integer.parseInt(quantità);
                if(nuova_capienza <= magazzino.getCapienzaMax()) {
                    // c'è spazio per aggiungere il prodotto
                    DBMS.queryCaricaViveri(Integer.parseInt(codice_prodotto), magazzino.getId(), Integer.parseInt(quantità), sqlDate);

                    // aggiorno la tabella magazzino
                    HashMap<String, Object> datiAggiornati = new HashMap<>();
                    datiAggiornati.put("capienza_attuale", nuova_capienza);
                    DBMS.queryModificaDati(magazzino.getId(), "magazzino", datiAggiornati);

                    prodottoAggiunto = true;
                }
            }

            if(!prodottoAggiunto) {
                showErrorAlert = true;
                error = "Spazio nei magazzini insufficiente.";
            }
            // DBMS.querySalvaDonazione(MainUtils.responsabileLoggato.getIdLavoro(), Integer.parseInt(codice_prodotto), Integer.parseInt(quantità), sqlDate);
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
            MainUtils.tornaAllaHome(buttonCaricaViveri);
        }
    }
}
