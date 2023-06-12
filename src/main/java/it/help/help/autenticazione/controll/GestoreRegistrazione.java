package it.help.help.autenticazione.controll;

import it.help.help.entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.util.Random;
import it.help.help.utils.DBMS;

import javafx.scene.control.Alert.AlertType;
public class GestoreRegistrazione {

    public RadioButton radioButtonDiocesi;
    public RadioButton radioButtonAziendaPartner;
    public TextField fieldEmail;
    public PasswordField fieldPassword;
    public PasswordField fieldRipetiPassword;
    public Button buttonRegistrati;

    private boolean isValidEmail(String email) {
        // Definisci la regex per il formato dell'email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // Verifica se la stringa email corrisponde alla regex
        return email.matches(emailRegex);
    }

    private static boolean validatePassword(String password) {
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

    public void clickRegistrati(ActionEvent actionEvent) throws Exception {
        Boolean radioDiocesi = radioButtonDiocesi.isSelected();
        Boolean radioAzienda = radioButtonAziendaPartner.isSelected();
        String email = fieldEmail.getText();
        String password = fieldPassword.getText();
        String repeatPassword = fieldRipetiPassword.getText();
        Boolean showErrorAlert = false;
        String error = "";
        int type = 0;

        if((radioAzienda || radioDiocesi) && !email.isEmpty() && !password.isEmpty() && !repeatPassword.isEmpty()) {
            if(password.equals(repeatPassword)) {
                if(isValidEmail(email) && validatePassword(password)) {
                    // verifico che l'email non sia già presente nel DBMS
                    if(!DBMS.queryControllaEsistenzaEmail(email)) {
                        // registro l'utente nel DBMS
                        if(radioAzienda) {
                            type = 3;
                        } else if(radioDiocesi) {
                            type = 1;
                        }
                        ResponsabileCompleto responsabileCompleto = DBMS.queryRegistraResponsabile(email, password, type);
                        if(responsabileCompleto != null) {
                            String nomeSchermata = "";
                            switch (responsabileCompleto.getResponsabile().getType()) {
                                case 0:
                                    // HELP
                                    nomeSchermata = "/it/help/help/SchermataHomeResponsabileHelp.fxml";
                                    break;
                                case 1:
                                    // DIOCESI
                                    if(responsabileCompleto.getDiocesi().getStato_account()) {
                                        nomeSchermata = "/it/help/help/SchermataHomeResponsabileDiocesi.fxml";
                                    } else {
                                       // account non ancora attivo
                                        showErrorAlert = true;
                                        error = "Il tuo account non è ancora attivo.";
                                    }
                                    break;
                                case 2:
                                    // POLO
                                    nomeSchermata = "/it/help/help/SchermataHomeResponsabilePolo.fxml";
                                    break;
                                case 3:
                                    // AZIENDA PARTNER
                                    if(responsabileCompleto.getAziendaPartner().getStatoAccount()) {
                                        nomeSchermata = "/it/help/help/SchermataHomeResponsabileAziendaPartner.fxml";
                                    } else {
                                        // account non ancora attivo
                                        showErrorAlert = true;
                                        error = "Il tuo account non è ancora attivo.";
                                    }
                                    break;
                            }
                            if(!showErrorAlert) {
                                Parent root = FXMLLoader.load(getClass().getResource(nomeSchermata));
                                Stage window = (Stage) buttonRegistrati.getScene().getWindow();
                                window.setScene(new Scene(root));
                            }
                        }
                    } else {
                        showErrorAlert = true;
                        error = "Email già esistente";
                    }
                } else {
                    showErrorAlert = true;
                    error = "I dati inseriti non sono corretti";
                }
            } else {
                showErrorAlert = true;
                error = "Le password inserite non coincidono";
            }
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi";
        }

        if(showErrorAlert) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText(error);
            alert.showAndWait();
        }
    }

    public void clickIndietro(ActionEvent actionEvent) {
    }
}
