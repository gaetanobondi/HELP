package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreAutenticazione;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataLogin extends Application {
    public Button buttonIndietro;
    public PasswordField fieldPassword;
    public TextField fieldEmail;
    public Button buttonRecuperaPassword;
    public Button buttonAccedi;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataIniziale.class.getResource("/it/help/help/schermataLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void clickAccedi(ActionEvent actionEvent) throws Exception {
        String email = fieldEmail.getText();
        String password = fieldPassword.getText();

        GestoreAutenticazione g = new GestoreAutenticazione();
        g.controllaCredenziali(email, password);
    }

    public void clickIndietro(ActionEvent actionEvent) {
    }

    public void clickRecuperaPassword(ActionEvent actionEvent) {
    }
}
