package it.help.help.autenticazione.controll;

import it.help.help.autenticazione.boundary.SchermataIniziale;
import it.help.help.autenticazione.boundary.SchermataLogin;
import it.help.help.autenticazione.boundary.SchermataSignin;
import it.help.help.utils.MainUtils;
import javafx.scene.control.*;
import javafx.stage.Stage;
import it.help.help.utils.DBMS;

import javafx.scene.control.Alert.AlertType;
public class GestoreRegistrazione {
    public GestoreAutenticazione gestoreAutenticazione;

    public GestoreRegistrazione(GestoreAutenticazione gestoreAutenticazione) {
        this.gestoreAutenticazione = gestoreAutenticazione;
    }

    public void schermataIniziale(Stage stage) {
        MainUtils.cambiaInterfaccia("Help", "/it/help/help/SchermataIniziale.fxml", stage, c -> {
            return new SchermataIniziale(stage);
        });
    }

    public void schermataSignin(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata signin","/it/help/help/SchermataSignin.fxml", stage, c -> {
            return new SchermataSignin(this);
        });
    }

    public void registraResponsabile(Stage stage, Boolean radioDiocesi, Boolean radioAzienda, String email, String password, String repeatPassword) throws Exception {
        Boolean showErrorAlert = false;
        String error = "";
        int type = 0;
        int id_lavoro = 0;

        if((radioAzienda || radioDiocesi) && !email.isEmpty() && !password.isEmpty() && !repeatPassword.isEmpty()) {
            if(password.equals(repeatPassword)) {
                if(MainUtils.isValidEmail(email) && MainUtils.isValidPassword(password)) {
                    // verifico che l'email non sia già presente nel DBMS
                    if(!DBMS.queryControllaEsistenzaEmail(email)) {
                        String encryptPassword = MainUtils.encryptPassword(password);
                        // registro l'utente nel DBMS
                        if(radioAzienda) {
                            type = 3;
                            id_lavoro = DBMS.queryRegistraAziendaPartner();
                        } else if(radioDiocesi) {
                            type = 1;
                            id_lavoro = DBMS.queryRegistraDiocesi();
                            DBMS.queryCreaMagazzino(1, id_lavoro);
                        }

                        DBMS.queryRegistraResponsabile(email, encryptPassword, type, id_lavoro);
                        
                        gestoreAutenticazione.schermataLogin(stage);
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
            error = "Messaggio di Errore";
        }

        if(showErrorAlert) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText(error);
            alert.showAndWait();
        }
    }

}
