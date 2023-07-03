package it.help.help.autenticazione.controll;

import it.help.help.autenticazione.boundary.*;
import it.help.help.magazzino.controll.GestoreMagazzino;
import it.help.help.utils.MainUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import it.help.help.utils.DBMS;
import javafx.scene.control.Alert.AlertType;

public class GestoreAutenticazione {
    public TextField fieldEmail;
    public PasswordField fieldNuovaPassword;
    public Label labelEmail;
    public Label labelNome;
    public Label labelCognome;
    public Button buttonHome;
    public Button buttonLogout;

    public void schermataLogin(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata login","/it/help/help/SchermataLogin.fxml", stage, c -> {
            return new SchermataLogin(this);
        });
    }
    //per la SCHERMATA LOGIN
    public void controllaCredenziali(Stage stage, String email, String password) throws Exception {
        boolean showErrorAlert = false;
        String error = "";

        if(!email.isEmpty() && !password.isEmpty()) {
            String encryptPassword = MainUtils.encryptPassword(password);
            MainUtils.responsabileLoggato = DBMS.queryControllaCredenzialiResponsabile(email, encryptPassword);
            if(MainUtils.responsabileLoggato != null) {
                String nomeSchermata = "";
                switch (MainUtils.responsabileLoggato.getType()) {
                    case 0:
                        // HELP
                        nomeSchermata = "/it/help/help/help/SchermataHomeResponsabileHelp.fxml";
                        MainUtils.cambiaInterfaccia("Schermata responsabile help", nomeSchermata, stage, c -> {
                            return new SchermataHomeResponsabileHelp();
                        });
                        break;
                    case 1:
                        // DIOCESI
                        if(DBMS.queryGetStatoAccount("diocesi", MainUtils.responsabileLoggato.getIdLavoro())) {
                            nomeSchermata = "/it/help/help/diocesi/SchermataHomeResponsabileDiocesi.fxml";
                            MainUtils.cambiaInterfaccia("Schermata responsabile diocesi", nomeSchermata, stage, c -> {
                                return new SchermataHomeResponsabileDiocesi();
                            });
                        } else {
                            // account non ancora attivo
                            showErrorAlert = true;
                            error = "Il tuo account non è ancora attivo.";
                        }
                        break;
                    case 2:
                        // POLO
                        if(DBMS.queryGetStatoSospensione(MainUtils.responsabileLoggato.getIdLavoro())) {
                            // POLO SOSPESO
                            GestoreMagazzino gestoreMagazzino = new GestoreMagazzino();
                            gestoreMagazzino.schermataRipristinoPolo(stage);
                        } else {
                            nomeSchermata = "/it/help/help/polo/SchermataHomeResponsabilePolo.fxml";
                            MainUtils.cambiaInterfaccia("Schermata responsabile polo", nomeSchermata, stage, c -> {
                                return new SchermataHomeResponsabilePolo();
                            });
                        }
                        break;
                    case 3:
                        // AZIENDA PARTNER
                        if(DBMS.queryGetStatoAccount("azienda", MainUtils.responsabileLoggato.getIdLavoro())) {
                            nomeSchermata = "/it/help/help/azienda_partner/SchermataHomeResponsabileAziendaPartner.fxml";
                            MainUtils.cambiaInterfaccia("Schermata responsabile azienda", nomeSchermata, stage, c -> {
                                return new SchermataHomeResponsabileAziendaPartner();
                            });
                        } else {
                            // account non ancora attivo
                            showErrorAlert = true;
                            error = "Il tuo account non è ancora attivo.";
                        }
                        break;
                }
            } else {
                showErrorAlert = true;
                error = "Le credenziali non sono corrette";
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
    public void schermataIniziale(Stage stage) {
        MainUtils.cambiaInterfaccia("Help", "/it/help/help/SchermataIniziale.fxml", stage, c -> {
            return new SchermataIniziale(stage);
        });
    }
    public void schermataSospendiPolo(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata sospendi polo", "/it/help/help/polo/SchermataSospensionePolo.fxml", stage, c -> {
            return new SchermataSospensionePolo(this);
        });
    }
    public void confermaSospensionePolo(Stage stage) throws Exception {
        DBMS.querySospendiPolo(MainUtils.responsabileLoggato.getIdLavoro());
        logout(stage);
    }
    //per la schermata HOME RESPONSABILE AZIENDA PARTNER

    public void logout(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata login","/it/help/help/SchermataLogin.fxml", stage, c -> {
            return new SchermataLogin(this);
        });
    }
}
