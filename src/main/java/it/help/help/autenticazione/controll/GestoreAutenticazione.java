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

public class GestoreAutenticazione {

    public Button buttonSignIn;
    public Button buttonLogin;
    public Label labelBenvenuto;
    public PasswordField fieldPassword;
    public TextField fieldEmail;
    public Button buttonRecuperaPassword; //schermata login
    public Button buttonAccedi;
    public Button buttonIndietro;
    public RadioButton radioButtonDiocesi;
    public RadioButton radioButtonAziendaPartner;
    public PasswordField fieldRipetiPassword;
    public Button buttonRegistrati;
    public PasswordField fieldNuovaPassword;




    //Scherma Home Responsabile Diocesi
    public Button buttonVisualizzaSchemaDiDistribuzioneDiocesi;
    public Button buttonVisualizzaListaPoli;
    public Button buttonRegistrazionePolo;
    public Button buttonVisualizzaCarichiInviati;
    public Button buttonVisualizzaProfiloDiocesi;

    @FXML
    private AnchorPane contentPane;



    public void clickIndietro(ActionEvent actionEvent) {
        System.out.println("ciao autenticazione");
    }




    //per la SCHERMATA HOME RESPONSABILE DIOCESI
    public void clickVisualizzaProfiloDiocesi(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataProfiloPersonaleDiocesi.fxml"));
        Stage window = (Stage) buttonVisualizzaProfiloDiocesi.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickVisualizzaListaPoli(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataVisualizzazioneListaPoli.fxml"));
        Stage window = (Stage) buttonVisualizzaListaPoli.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickRegistrazionePolo(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataRegistrazionePolo.fxml"));
        Stage window = (Stage) buttonRegistrazionePolo.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickVisualizzaCarichiInviati(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataVisualizzazioneCarichi.fxml"));
        Stage window = (Stage) buttonVisualizzaCarichiInviati.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickVisualizzaSchemaDiDistribuzioneDiocesi(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataSchemaDiDistribuzioneDellaDiocesi.fxml"));
        Stage window = (Stage) buttonVisualizzaSchemaDiDistribuzioneDiocesi.getScene().getWindow();
        window.setScene(new Scene(root));
    }






    //per la SCHERMATA INIZIALE
    public void clickSignIn(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataSignin.fxml"));
        Stage window = (Stage) buttonSignIn.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Sign-In");
    }

    public void clickLogin(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataLogin.fxml"));
        Stage window = (Stage) buttonLogin.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Login");
    }





    //per la SCHERMATA LOGIN
    public void clickAccedi(ActionEvent actionEvent) throws Exception {
        String email = fieldEmail.getText();
        String password = fieldPassword.getText();
        Boolean showErrorAlert = false;
        String error = "";

        if(!email.isEmpty() && !password.isEmpty()) {
            Responsabile responsabile = DBMS.queryControllaCredenzialiResponsabile(email, password);
            System.out.println(responsabile.getEmail());
            System.out.println(responsabile.getType());
            if(responsabile != null) {
                String nomeSchermata = "";
                switch (responsabile.getType()) {
                    case 0:
                        // HELP
                        nomeSchermata = "/it/help/help/SchermataHomeResponsabileHelp.fxml";
                        GestoreProfilo gestoreProfilo = new GestoreProfilo();
                        break;
                    case 1:
                        // DIOCESI
                        Diocesi diocesi = DBMS.getDiocesi(responsabile.getId());
                        if(diocesi.getStato_account()) {
                            nomeSchermata = "/it/help/help/SchermataHomeResponsabileDiocesi.fxml";
                        } else {
                            // account non ancora attivo
                            showErrorAlert = true;
                            error = "Il tuo account non è ancora attivo.";
                        }
                        break;
                    case 2:
                        // POLO
                        System.out.println("OK1");
                        Polo polo = DBMS.getPolo(responsabile.getId());
                        if(polo.getStato_sospensione()) {
                            // POLO SOSPESO
                            nomeSchermata = "/it/help/help/SchermataSospensionePolo.fxml";
                        } else {
                            nomeSchermata = "/it/help/help/SchermataHomeResponsabilePolo.fxml";
                        }
                        break;
                    case 3:
                        // AZIENDA PARTNER
                        System.out.println("OK2");
                        AziendaPartner aziendaPartner = DBMS.getAziendaPartner(responsabile.getId());
                        if(aziendaPartner.getStatoAccount()) {
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
                    Stage window = (Stage) buttonAccedi.getScene().getWindow();
                    window.setScene(new Scene(root));
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

    public void clickRecuperaPassword(ActionEvent actionEvent) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataRecuperoPassword.fxml"));
        Stage window = (Stage) buttonRecuperaPassword.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Recupero Password");
    }





    //per la schermata SIGN-IN

    public boolean isValidEmail(String email) {
        // Definisci la regex per il formato dell'email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // Verifica se la stringa email corrisponde alla regex
        return email.matches(emailRegex);
    }

    public static boolean validatePassword(String password) {
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



    //per la schermata HOME RESPONSABILE POLO

    public Button buttonVisualizzaProfiloPolo;
    public Button buttonVisualizzaNucleoFamiliare;
    public Button buttonInserimentoNucleo;
    public Button buttonVisualizzaSchemaDiDistribuzionePolo;
    public Button buttonVisualizzaSchemaDiDistribuzioneFamiglie;
    public Button buttonSegnalazioneErrori;
    public Button buttonReport;
    public Button buttonSospendiPolo;
    public Button buttonAggiungiViveriMagazzino;


    public void clickVisualizzaProfiloPolo(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataProfiloPersonalePolo.fxml"));
        Stage window = (Stage) buttonVisualizzaProfiloPolo.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickVisualizzaNucleoFamiliare(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataSchemaVisualizzaListaNuclei.fxml"));
        Stage window = (Stage) buttonVisualizzaNucleoFamiliare.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickInserimentoNucleo(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataRegistrazioneNucleoFamiliare.fxml"));
        Stage window = (Stage) buttonInserimentoNucleo.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickVisualizzaSchemaDiDistribuzionePolo(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataSchemaDiDistribuzioneDelPolo.fxml"));
        Stage window = (Stage) buttonVisualizzaSchemaDiDistribuzionePolo.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    public void clickVisualizzaSchemaDiDistribuzioneFamiglie(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataSchemaDiDistribuzioneDelNucleo.fxml"));
        Stage window = (Stage) buttonVisualizzaSchemaDiDistribuzioneFamiglie.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    public void clickSegnalazioneErrori(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataSegnalazioneErrori.fxml"));
        Stage window = (Stage) buttonSegnalazioneErrori.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickReport(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataScaricamentoReport.fxml"));
        Stage window = (Stage) buttonReport.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    public void clickSospendiPolo(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataSospensionePolo.fxml"));
        Stage window = (Stage) buttonSospendiPolo.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    public void clickAggiungiViveriMagazzino(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataCaricamentoViveri.fxml"));
        Stage window = (Stage) buttonAggiungiViveriMagazzino.getScene().getWindow();
        window.setScene(new Scene(root));
    }





    //per la SCHERMATA HOME RESPONSABILE HELP

    public Button buttonVisualizzaProfiloHelp;
    public Button buttonVisualizzaPrevisioneDistribuzione;
    public Button buttonRichiesteDiocesi;
    public Button buttonRichiesteAziendePartner;
    public Button buttonListaDonazioniRicevute;
    public Button buttonGestione;
    public Button buttonVisualizzaReport;
    public Button buttonDonazioneAziendaPartner;

    public void clickVisualizzaProfiloHelp(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataProfiloPersonaleHelp.fxml"));
        Stage window = (Stage) buttonVisualizzaProfiloHelp.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    public void clickVisualizzaPrevisioneDiDistribuzione(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataVisualizzazionePrevisioneDiDistribuzione.fxml"));
        Stage window = (Stage) buttonVisualizzaProfiloHelp.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    public void clickRichiesteDiocesi(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataVisualizzaRichiesteDiocesi.fxml"));
        Stage window = (Stage) buttonRichiesteDiocesi.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickRichiesteAziendePartner(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataVisualizzaRichiesteAziendePartner.fxml"));
        Stage window = (Stage) buttonRichiesteAziendePartner.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickListaDonazioniRicevute(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataListaDonaizoni.fxml"));
        Stage window = (Stage) buttonListaDonazioniRicevute.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickGestione(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataGestione.fxml"));
        Stage window = (Stage) buttonGestione.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickDonazioneAziendaPartner(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataDonazioneAzienda.fxml"));
        Stage window = (Stage) buttonDonazioneAziendaPartner.getScene().getWindow();
        window.setScene(new Scene(root));
    }





    //per la schermata HOME RESPONSABILE AZIENDA PARTNER

    public Button buttonVisualizzaProfiloAziendaPartner;
    public Button buttonVisualizzaDonazioniEffettuate;
    public Button buttonEffettuaDonazioneAdHoc;
    public Button buttonLogout;
    public Button buttonEffettuaDonazioneSpontanea;

    public void clickVisualizzaProfiloAziendaPartner(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataProfiloPersonaleAziendaPartner.fxml"));
        Stage window = (Stage) buttonVisualizzaProfiloAziendaPartner.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickVisualizzaDonazioniEffettuate(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataVisualizzaDonazioniEffettuate.fxml"));
        Stage window = (Stage) buttonVisualizzaDonazioniEffettuate.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickEffettuaDonazioneAdHoc(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataEffettuaDonazioneAdHoc.fxml"));
        Stage window = (Stage) buttonEffettuaDonazioneAdHoc.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickLogout(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataLogin.fxml"));
        Stage window = (Stage) buttonLogout.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickEffettuaDonazioneSpontanea(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataEffettuaDonazione.fxml"));
        Stage window = (Stage) buttonEffettuaDonazioneSpontanea.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}
