package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestorePassword;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SchermataCambioPassword {

    public TextField fieldEmail;
    public Button buttonSalvaNuovaPassword;
    public PasswordField fieldNuovaPassword;
    public PasswordField fieldRipetiPassword;
    public GestorePassword gestorePassword;
    public String email;
    public SchermataCambioPassword(GestorePassword gestorePassword, String email) {
        this.gestorePassword = gestorePassword;
        this.email = email;
    }

    public void clickSalvaPassword(ActionEvent actionEvent) throws Exception {
        String password = fieldNuovaPassword.getText();
        String repeat_password = fieldRipetiPassword.getText();
        gestorePassword.salvaPassword((Stage) buttonSalvaNuovaPassword.getScene().getWindow(), email, password, repeat_password);
    }
}
