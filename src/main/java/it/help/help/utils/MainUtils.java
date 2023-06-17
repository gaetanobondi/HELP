package it.help.help.utils;
import it.help.help.entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import it.help.help.utils.DBMS;
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
    public static List<Stage> boundaryStack = new ArrayList<>(); // Inizializza la lista delle boundary precedenti

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
}
