package it.help.help.magazzino.boundary;

import it.help.help.magazzino.controll.GestoreMagazzino;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class SchermataCaricamentoViveri {
    public GestoreMagazzino gestoreMagazzino;
    public Button buttonHome;
    public MenuButton selectAlimenti;
    public TextField fieldMenuSelected;
    public TextField fieldQuantità;
    public DatePicker pickerDataScadenza;
    public CheckBox checkBoxSenzaGlutine;
    public CheckBox checkBoxSenzaZuccheri;
    public CheckBox checkBoxSenzaLattosio;
    public Button buttonCaricaViveri;
    public int type;

    public SchermataCaricamentoViveri(GestoreMagazzino gestoreMagazzino, int type) {
        this.gestoreMagazzino = gestoreMagazzino;
        this.type = type;
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }

    public void clickCaricaViveri(ActionEvent actionEvent) throws Exception {
        String codice_prodotto = fieldMenuSelected.getText();
        String quantità = fieldQuantità.getText();
        LocalDate data_scadenza = pickerDataScadenza.getValue();
        gestoreMagazzino.caricaViveri((Stage) buttonCaricaViveri.getScene().getWindow(), type, codice_prodotto, quantità, data_scadenza);
    }
}
