package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreProfilo;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataModificaProfiloPolo {

    public Button buttonHome;
    public Button buttonSalvaModifiche;
    public TextField fieldEmail;
    public TextField fieldCellulare;
    public TextField fieldIndirizzo;
    public PasswordField fieldVecchiaPassword;
    public PasswordField fieldNuovaPassword;
    public TextField filedNomePolo;
    public TextField fieldNome;
    public TextField fieldCognome;
    public GestoreProfilo gestoreProfilo;

    public SchermataModificaProfiloPolo(GestoreProfilo gestoreProfilo) {
        this.gestoreProfilo = gestoreProfilo;
    }
    public void inizialize(String nome, String cognome, String email, int cellulare, String indirizzo, String nomePolo) {
        fieldEmail.setText(email);
        fieldNome.setText(nome);
        fieldCognome.setText(cognome);
        if(cellulare != 0) {
            fieldCellulare.setText("" + cellulare);
        }
        fieldIndirizzo.setText(indirizzo);
        filedNomePolo.setText(nomePolo);
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }

    public void clickSalvaModifiche(ActionEvent actionEvent) throws Exception {
        String nome_polo = filedNomePolo.getText() != null ? filedNomePolo.getText() : "";
        String cellulare = fieldCellulare.getText() != null ? fieldCellulare.getText() : "";
        String nome = fieldNome.getText() != null ? fieldNome.getText() : "";
        String cognome = fieldCognome.getText() != null ? fieldCognome.getText() : "";
        String indirizzo = fieldIndirizzo.getText() != null ? fieldIndirizzo.getText() : "";
        String email = fieldEmail.getText();
        String password = fieldVecchiaPassword.getText();
        String new_password = fieldNuovaPassword.getText();
        gestoreProfilo.salvaModifichePolo((Stage) buttonSalvaModifiche.getScene().getWindow(), nome, cognome, email, indirizzo, cellulare, nome_polo, password, new_password);
    }
}
