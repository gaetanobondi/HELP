package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreProfilo;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataVisualizzaProfiloDiocesi {
    public Button buttonHome;
    public Button buttonModificaDati;
    public Label labelEmail;
    public Label labelCellulare;
    public Label labelIndirizzo;
    public Label labelNomeDiocesi;
    public Label labelNome;
    public Label labelCognome;

    public void inizialize(String nome, String cognome, String email, int cellulare, String indirizzo, String nomeDiocesi) {
        labelEmail.setText(email);
        labelNome.setText(nome);
        labelCognome.setText(cognome);
        if(cellulare != 0) {
            labelCellulare.setText("" + cellulare);
        }
        labelIndirizzo.setText(indirizzo);
        labelNomeDiocesi.setText(nomeDiocesi);
    }

    public void clickModificaDati(ActionEvent actionEvent) throws Exception {
        GestoreProfilo gestoreProfilo = new GestoreProfilo();
        gestoreProfilo.schermataModificaDatiDiocesi((Stage) buttonModificaDati.getScene().getWindow());
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }
}
