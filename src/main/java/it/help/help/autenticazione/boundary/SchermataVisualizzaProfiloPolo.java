package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreProfilo;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataVisualizzaProfiloPolo {
    public Button buttonHome;
    public Button buttonModificaDati;
    public Label labelNome;
    public Label labelCognome;
    public Label labelEmail;
    public Label labelCellulare;
    public Label labelIndirizzo;
    public Label labelNomePolo;

    public void inizialize(String nome, String cognome, String email, int cellulare, String indirizzo, String nomePolo) {
        labelEmail.setText(email);
        labelNome.setText(nome);
        labelCognome.setText(cognome);
        if(cellulare != 0) {
            labelCellulare.setText("" + cellulare);
        }
        labelIndirizzo.setText(indirizzo);
        labelNomePolo.setText(nomePolo);
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }

    public void clickModificaDati(ActionEvent actionEvent) throws Exception {
        GestoreProfilo gestoreProfilo = new GestoreProfilo();
        gestoreProfilo.schermataModificaDatiPolo((Stage) buttonModificaDati.getScene().getWindow());
    }
}
