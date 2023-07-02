package it.help.help.polo.boundary;

import it.help.help.polo.controll.GestoreMembro;
import it.help.help.polo.controll.GestoreNucleo;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import it.help.help.utils.MainUtils;

import java.io.IOException;
import java.time.LocalDate;

public class SchermataRegistraMembro {

    public Button buttonHome;
    public Button buttonSalvaRegistraMembro;
    public TextField fieldNome;
    public TextField fieldCognome;
    public TextField fieldCodFiscale;
    public DatePicker pickerDataNascita;
    public TextField fieldIndirizzo;
    public CheckBox checkBoxCeliachia;
    public CheckBox checkBoxDiabete;
    public CheckBox checkBoxLattosio;

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }

    public void clickSalvaRegistraMembro(ActionEvent actionEvent) throws Exception {
        String nome = fieldNome.getText() != null ? fieldNome.getText() : "";
        String cognome = fieldCognome.getText() != null ? fieldCognome.getText() : "";
        String codice_fiscale = fieldCodFiscale.getText() != null ? fieldCodFiscale.getText() : "";
        String indirizzo = fieldIndirizzo.getText() != null ? fieldIndirizzo.getText() : "";
        Boolean checkCeliachia = checkBoxCeliachia.isSelected();
        Boolean checkLattosio = checkBoxLattosio.isSelected();
        Boolean checkDiabete = checkBoxDiabete.isSelected();
        LocalDate dataNascita = pickerDataNascita.getValue();
        GestoreMembro gestoreMembro = new GestoreMembro();
        gestoreMembro.registraMembro((Stage) buttonSalvaRegistraMembro.getScene().getWindow(), nome, cognome, codice_fiscale, indirizzo, checkCeliachia, checkLattosio, checkDiabete, dataNascita);
    }
}
