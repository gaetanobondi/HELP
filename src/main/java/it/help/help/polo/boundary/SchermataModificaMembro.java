package it.help.help.polo.boundary;

import it.help.help.polo.controll.GestoreMembro;
import it.help.help.polo.controll.GestoreNucleo;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class SchermataModificaMembro {

    public Button buttonHome;
    public TextField fieldNome;
    public TextField fieldCognome;
    public TextField fieldCodFiscale;
    public DatePicker pickerDataNascita;
    public TextField fieldCellulare;
    public TextField fieldIndirizzo;
    public CheckBox checkBoxCeliachia;
    public CheckBox checkBoxDiabete;
    public CheckBox checkBoxLattosio;
    public GestoreMembro gestoreMembro;
    public Button buttonModificaMembro;

    public SchermataModificaMembro(GestoreMembro gestoreMembro) {
        this.gestoreMembro = gestoreMembro;
    }
    public void inizialize(String nome, String cognome, String codice_fiscale, String indirizzo, Boolean checkCeliachia, Boolean checkLattosio, Boolean checkDiabete, LocalDate dataNascita) {
        MainUtils.codice_fiscale = codice_fiscale;
        fieldNome.setText(nome);
        fieldCognome.setText(cognome);
        fieldCodFiscale.setText(codice_fiscale);
        fieldIndirizzo.setText(indirizzo);
        checkBoxCeliachia.setSelected(checkCeliachia);
        checkBoxLattosio.setSelected(checkLattosio);
        checkBoxDiabete.setSelected(checkDiabete);
        pickerDataNascita.setValue(dataNascita);
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }

    public void clickModificaMembro(ActionEvent actionEvent) throws Exception {
        String nome = fieldNome.getText() != null ? fieldNome.getText() : "";
        String cognome = fieldCognome.getText() != null ? fieldCognome.getText() : "";
        String codice_fiscale = fieldCodFiscale.getText() != null ? fieldCodFiscale.getText() : "";
        String indirizzo = fieldIndirizzo.getText() != null ? fieldIndirizzo.getText() : "";
        Boolean checkCeliachia = checkBoxCeliachia.isSelected();
        Boolean checkLattosio = checkBoxLattosio.isSelected();
        Boolean checkDiabete = checkBoxDiabete.isSelected();
        LocalDate dataNascita = pickerDataNascita.getValue();
        gestoreMembro.modificaMembro((Stage) buttonModificaMembro.getScene().getWindow(), nome, cognome, codice_fiscale, indirizzo, checkCeliachia, checkLattosio, checkDiabete, dataNascita);
    }
}
