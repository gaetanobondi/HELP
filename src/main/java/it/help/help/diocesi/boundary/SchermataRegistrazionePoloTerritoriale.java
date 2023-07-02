package it.help.help.diocesi.boundary;

import it.help.help.diocesi.controll.GestoreDiocesi;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataRegistrazionePoloTerritoriale {
    public Button buttonHome;
    public Button buttonRegistraPolo;
    public TextField fieldNome;
    public TextField fieldCognome;
    public TextField fieldIndirizzo;
    public TextField fieldCellulare;
    public TextField fieldEmail;
    public PasswordField fieldPassword;
    public GestoreDiocesi gestoreDiocesi;

    public SchermataRegistrazionePoloTerritoriale(GestoreDiocesi gestoreDiocesi) {
        this.gestoreDiocesi = gestoreDiocesi;
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }

    public void clickRegistraPolo(ActionEvent actionEvent) throws Exception {
        String nome = fieldNome.getText();
        String cognome = fieldCognome.getText();
        String indirizzo = fieldIndirizzo.getText();
        String cellulare = fieldCellulare.getText();
        String email = fieldEmail.getText();
        String password = fieldPassword.getText();
        gestoreDiocesi.registraPolo((Stage) buttonRegistraPolo.getScene().getWindow(), nome, cognome, indirizzo, cellulare, email, password);
    }
}
