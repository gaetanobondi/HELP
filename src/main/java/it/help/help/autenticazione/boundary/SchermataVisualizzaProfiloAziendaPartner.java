package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreProfilo;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class SchermataVisualizzaProfiloAziendaPartner {

    @FXML
    public Button buttonModificaDati;
    @FXML
    public Label labelNome;
    @FXML
    public Label labelCognome;
    @FXML
    public Label labelEmail;
    @FXML
    public Label labelPassword;
    @FXML
    public Label labelCellulare;
    @FXML
    public Label labelIndirizzo;
    @FXML
    public Label labelNomeAzienda;
    public Button buttonHome;


    public void inizialize(String nome, String cognome, String email, int cellulare, String indirizzo, String nomeAzienda) {
        labelEmail.setText(email);
        labelNome.setText(nome);
        labelCognome.setText(cognome);
        if(cellulare != 0) {
            labelCellulare.setText("" + cellulare);
        }
        labelIndirizzo.setText(indirizzo);
        labelNomeAzienda.setText(nomeAzienda);
    }

    public void clickModificaDati(ActionEvent actionEvent) throws Exception {
        GestoreProfilo gestoreProfilo = new GestoreProfilo();
        gestoreProfilo.schermataModificaDatiAzienda((Stage) buttonModificaDati.getScene().getWindow());
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }
}

