package it.help.help.utils;
import it.help.help.autenticazione.boundary.SchermataHomeResponsabileAziendaPartner;
import it.help.help.autenticazione.boundary.SchermataHomeResponsabileDiocesi;
import it.help.help.autenticazione.boundary.SchermataHomeResponsabileHelp;
import it.help.help.autenticazione.boundary.SchermataHomeResponsabilePolo;
import it.help.help.entity.*;
import it.help.help.polo.boundary.SchermataComponentiNucleo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import it.help.help.utils.DBMS;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MainUtils {
    public static Scene previousScene;
    public static Responsabile responsabileLoggato;
    public static Polo poloLoggato;
    public static AziendaPartner aziendaPartnerLoggata;
    public static Diocesi diocesiLoggata;
    public static Help helpLoggato;
    public static Nucleo nucleo;
    public static List<Stage> boundaryStack = new ArrayList<>(); // Inizializza la lista delle boundary precedenti
    public Button buttonIndietro;

    public static String encryptPassword(String password) {
        try {
            // Crea un oggetto MessageDigest con l'algoritmo SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Calcola l'hash della password
            byte[] hash = md.digest(password.getBytes());

            // Converti l'array di byte in una stringa Base64 per salvarla nel database
            String encryptedPassword = Base64.getEncoder().encodeToString(hash);

            return encryptedPassword;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void tornaAllaHome(Button button) throws IOException {
        switch (MainUtils.responsabileLoggato.getType()) {
            case 0:
                // HELP
                // nomeSchermata = "/it/help/help/SchermataHomeResponsabileHelp.fxml";
                SchermataHomeResponsabileHelp l = new SchermataHomeResponsabileHelp();
                Stage window = (Stage) button.getScene().getWindow();
                l.start(window);
                break;
            case 1:
                // DIOCESI
                // nomeSchermata = "/it/help/help/SchermataHomeResponsabileDiocesi.fxml";
                SchermataHomeResponsabileDiocesi l1 = new SchermataHomeResponsabileDiocesi();
                Stage window1 = (Stage) button.getScene().getWindow();
                l1.start(window1);
                break;
            case 2:
                // POLO
                // nomeSchermata = "/it/help/help/SchermataHomeResponsabilePolo.fxml";
                SchermataHomeResponsabilePolo l2 = new SchermataHomeResponsabilePolo();
                Stage window2 = (Stage) button.getScene().getWindow();
                l2.start(window2);
                break;
            case 3:
                // AZIENDA PARTNER
                // nomeSchermata = "/it/help/help/SchermataHomeResponsabileAziendaPartner.fxml";
                SchermataHomeResponsabileAziendaPartner l3 = new SchermataHomeResponsabileAziendaPartner();
                Stage window3 = (Stage) button.getScene().getWindow();
                l3.start(window3);
                break;
        }
    }

    public static boolean isValidPassword(String password) {
        // Controlla se la password ha almeno 8 caratteri
        if (password.length() < 8) {
            return false;
        }

        // Controlla se la password contiene almeno una lettera maiuscola
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Controlla se la password contiene almeno un numero
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // Controlla se la password contiene almeno un carattere speciale
        if (!password.matches(".*[!@#$%^&*()].*")) {
            return false;
        }

        // La password soddisfa tutti i requisiti
        return true;
    }

    public static boolean isValidEmail(String email) {
        // Definisci la regex per il formato dell'email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // Verifica se la stringa email corrisponde alla regex
        return email.matches(emailRegex);
    }

    public void clickIndietro(ActionEvent actionEvent) {
    }




    public void clickHome(ActionEvent actionEvent) {
    }
}
