package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreProfilo;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import it.help.help.utils.MainUtils;
import java.io.IOException;

public class SchermataModificaProfiloHelp {

    public Button buttonHome;
    public Button buttonSalvaModifiche;
    public TextField fieldIndirizzo;
    public TextField fieldCellulare;
    public TextField fieldEmail;
    public PasswordField fieldVecchiaPassword;
    public PasswordField fieldNuovaPassword;
    public TextField fieldCognome;
    public TextField fieldNome;
    public GestoreProfilo gestoreProfilo;

    public SchermataModificaProfiloHelp(GestoreProfilo gestoreProfilo) {
        this.gestoreProfilo = gestoreProfilo;
    }
    public void inizialize(String nome, String cognome, String email, int cellulare, String indirizzo) {
        fieldEmail.setText(email);
        fieldNome.setText(nome);
        fieldCognome.setText(cognome);
        if(cellulare != 0) {
            fieldCellulare.setText("" + cellulare);
        }
        fieldIndirizzo.setText(indirizzo);
    }

    public void clickSalvaModifiche(ActionEvent actionEvent) throws Exception {
        String cellulare = fieldCellulare.getText() != null ? fieldCellulare.getText() : "";
        String nome = fieldNome.getText() != null ? fieldNome.getText() : "";
        String cognome = fieldCognome.getText() != null ? fieldCognome.getText() : "";
        String indirizzo = fieldIndirizzo.getText() != null ? fieldIndirizzo.getText() : "";
        String email = fieldEmail.getText();
        String password = fieldVecchiaPassword.getText();
        String new_password = fieldNuovaPassword.getText();
        gestoreProfilo.salvaModificheHelp((Stage) buttonSalvaModifiche.getScene().getWindow(), nome, cognome, email, indirizzo, cellulare, password, new_password);
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }

}
