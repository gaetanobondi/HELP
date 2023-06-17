package it.help.help.autenticazione.controll;

import it.help.help.autenticazione.boundary.*;
import it.help.help.utils.MainUtils;
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

// import javax.swing.text.html.ImageView;

//
public class GestoreProfilo {


    public Label labelNomeResponsabile;
    public Label labelCognomeResponsabile;
    public Label labelEmail;
    public Label labelPassword;
    public Label labelNome;
    public Label labelCellulare;
    public Label labelIndirizzo;
    public Label labelNomePrete;


    public TextField fieldEmail;
    public TextField fieldNome;
    public TextField fieldCellulare;
    public TextField fieldIndirizzo;
    public PasswordField fieldVecchiaPassword;
    public PasswordField fieldNuovaPassword;
    public TextField fieldNomeResponsabile;
    public TextField fieldCognomeResponsabile;
    public TextField fieldNomePolo;
    public TextField fieldViveriProdotto;

    @FXML
    private AnchorPane contentPane;




    //per la schermata MODIFICA PROFILO HELP
    public Button buttonModificaDatiHelp;

    public void clickModificaDatiHelp(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/SchermataModificaProfilo.fxml"));
        Stage window = (Stage) buttonModificaDatiHelp.getScene().getWindow();
        MainUtils.previousScene = window.getScene();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Modifica Profilo Help");

        // Recupera le label dal file FXML utilizzando gli ID specificati nel file FXML
        TextField fieldNome = (TextField) root.lookup("#fieldNome");
        // TextField fieldCognomeResponsabile = (TextField) root.lookup("#fieldCognomeResponsabile");
        TextField fieldEmail = (TextField) root.lookup("#fieldEmail");

        // Imposta il testo delle label utilizzando i valori delle variabili

        fieldNome.setText(MainUtils.responsabileLoggato.getNome());
        // fieldCognomeResponsabile.setText(MainUtils.helpLoggato.getCognome());
        fieldEmail.setText(MainUtils.responsabileLoggato.getEmail());
    }

    public Button buttonSalvaModificheHelp;
    public void clickSalvaModificheHelp(ActionEvent actionEvent) throws Exception {
        String nome = fieldNome.getText();
        // String cognome = fieldCognomeResponsabile.getText();
        String email = fieldEmail.getText();
        String password = fieldVecchiaPassword.getText();
        String new_password = fieldNuovaPassword.getText();
        Boolean showErrorAlert = false;
        String error = "";

        // controllo riempimento campi
        if(!nome.isEmpty() && !email.isEmpty()) {
            if(email.equals(MainUtils.responsabileLoggato.getEmail()) || (MainUtils.isValidEmail(email) && !DBMS.queryControllaEsistenzaEmail(email))) {
                // aggiorno la tabella help
                HashMap<String, Object> datiAggiornati = new HashMap<>();
                datiAggiornati.put("nome", nome);
                datiAggiornati.put("email", email);
                // datiAggiornati.put("cognome", cognome);
                DBMS.queryModificaDati(MainUtils.responsabileLoggato.getId(), "responsabile", datiAggiornati);
                // DBMS.getHelp(MainUtils.responsabileLoggato.getId());
                // aggiorno la tabella responsabile per l'email
                // HashMap<String, Object> datiAggiornatiResponsabile = new HashMap<>();
                // datiAggiornatiResponsabile.put("email", email);
                // DBMS.queryModificaDati(MainUtils.responsabileLoggato.getId(), "responsabile", datiAggiornatiResponsabile);
                MainUtils.responsabileLoggato = DBMS.getResponsabile(MainUtils.responsabileLoggato.getId());
            } else {
                showErrorAlert = true;
                error = "Non puoi usare questa email";
            }
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi obbligatori";
        }

        if(!password.isEmpty() && !new_password.isEmpty()) {
            if(MainUtils.isValidPassword(new_password)) {
                HashMap<String, Object> datiAggiornati = new HashMap<>();
                String encryptPassword = MainUtils.encryptPassword(password);
                datiAggiornati.put("password", encryptPassword);
                DBMS.queryModificaDati(MainUtils.responsabileLoggato.getId(), "responsabile", datiAggiornati);
            } else {
                showErrorAlert = true;
                error = "La nuova password deve essere lunga almeno 8 caratteri e contenere almeno una lettera maiuscola e un carattere speciale";
            }
        } else if(!password.isEmpty() && new_password.isEmpty()) {
            showErrorAlert = true;
            error = "Inserisci la nuova password";
        } else if(password.isEmpty() && !new_password.isEmpty()) {
            showErrorAlert = true;
            error = "Inserisci la tua password";
        }

        if(showErrorAlert) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText(error);
            alert.showAndWait();
        } else {
            // torno alla schermata precedente
            tornaAVisualizza();
        }
    }





    //per la schermata MODIFICA PROFILO PERSONALE AZIENDA PARTNER
    public Button buttonModificaDatiAzienda;

    public void clickModificaDatiAzienda(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/SchermataModificaProfiloAziendaPartner.fxml"));
        Stage window = (Stage) buttonModificaDatiAzienda.getScene().getWindow();
        MainUtils.previousScene = window.getScene();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Modifica Profilo Azienda Partner");

        // Recupera le label dal file FXML utilizzando gli ID specificati nel file FXML
        TextField fieldNome = (TextField) root.lookup("#fieldNome");
        // TextField fieldNomeResponsabile = (TextField) root.lookup("#fieldNomeResponsabile");
        // TextField fieldCognomeResponsabile = (TextField) root.lookup("#fieldCognomeResponsabile");
        TextField fieldEmail = (TextField) root.lookup("#fieldEmail");
        // TextField fieldIndirizzo = (TextField) root.lookup("#fieldIndirizzo");
        // TextField fieldCellulare = (TextField) root.lookup("#fieldCellulare");
        // TextField fieldViveriProdotto = (TextField) root.lookup("#fieldViveriProdotto");

        // Imposta il testo delle label utilizzando i valori delle variabili
        fieldNome.setText(MainUtils.responsabileLoggato.getNome());
        // fieldNomeResponsabile.setText(MainUtils.aziendaPartnerLoggata.getNomeResponsabile());
        // fieldCognomeResponsabile.setText(MainUtils.aziendaPartnerLoggata.getCognomeResponsabile());
        fieldEmail.setText(MainUtils.responsabileLoggato.getEmail());
        // fieldIndirizzo.setText(MainUtils.aziendaPartnerLoggata.getIndirizzo());
        // fieldViveriProdotto.setText(MainUtils.aziendaPartnerLoggata.getViveriProdotto());
        // if(MainUtils.aziendaPartnerLoggata.getCellulare() != 0) {
            // fieldCellulare.setText("" + MainUtils.aziendaPartnerLoggata.getCellulare());
        // }
    }
    public Button buttonSalvaModificheAzienda;
    public void clickSalvaModificheAzienda(ActionEvent actionEvent) throws Exception {
        String nome_azienda = fieldNome.getText() != null ? fieldNome.getText() : "";
        // String viveri_prodotto = fieldViveriProdotto.getText() != null ? fieldViveriProdotto.getText() : "";
        // String cellulare = fieldCellulare.getText() != null ? fieldCellulare.getText() : "";
        // String nome = fieldNomeResponsabile.getText() != null ? fieldNomeResponsabile.getText() : "";
        // String cognome = fieldCognomeResponsabile.getText() != null ? fieldCognomeResponsabile.getText() : "";
        // String indirizzo = fieldIndirizzo.getText() != null ? fieldIndirizzo.getText() : "";
        String email = fieldEmail.getText();
        String password = fieldVecchiaPassword.getText();
        String new_password = fieldNuovaPassword.getText();
        Boolean showErrorAlert = false;
        String error = "";

        // controllo riempimento campi
        if(!nome_azienda.isEmpty() && !email.isEmpty()) {
            if(email.equals(MainUtils.responsabileLoggato.getEmail()) || (MainUtils.isValidEmail(email) && !DBMS.queryControllaEsistenzaEmail(email))) {
                // aggiorno la tabella help
                HashMap<String, Object> datiAggiornati = new HashMap<>();
                datiAggiornati.put("nome", nome_azienda);
                datiAggiornati.put("email", email);
                DBMS.queryModificaDati(MainUtils.responsabileLoggato.getId(), "responsabile", datiAggiornati);
                MainUtils.responsabileLoggato = DBMS.getResponsabile(MainUtils.responsabileLoggato.getId());
            } else {
                showErrorAlert = true;
                error = "Non puoi usare questa email";
            }
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi obbligatori";
        }

        if(!password.isEmpty() && !new_password.isEmpty()) {
            if(MainUtils.isValidPassword(new_password)) {
                HashMap<String, Object> datiAggiornati = new HashMap<>();
                String encryptPassword = MainUtils.encryptPassword(password);
                datiAggiornati.put("password", encryptPassword);
                DBMS.queryModificaDati(MainUtils.responsabileLoggato.getId(), "responsabile", datiAggiornati);
            } else {
                showErrorAlert = true;
                error = "La nuova password deve essere lunga almeno 8 caratteri e contenere almeno una lettera maiuscola e un carattere speciale";
            }
        } else if(!password.isEmpty() && new_password.isEmpty()) {
            showErrorAlert = true;
            error = "Inserisci la nuova password";
        } else if(password.isEmpty() && !new_password.isEmpty()) {
            showErrorAlert = true;
            error = "Inserisci la tua password";
        }

        if(showErrorAlert) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText(error);
            alert.showAndWait();
        } else {
            // torno alla schermata precedente
            tornaAVisualizza();
        }
    }






    //per la schermata MODIFICA PROFILO PERSONALE POLO
    public Button buttonModificaDatiPolo;
    public void clickModificaDatiPolo(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/SchermataModificaProfiloPolo.fxml"));
        Stage window = (Stage) buttonModificaDatiPolo.getScene().getWindow();
        MainUtils.previousScene = window.getScene();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Modifica Profilo Polo");

        // Recupera le label dal file FXML utilizzando gli ID specificati nel file FXML
        TextField fieldNome = (TextField) root.lookup("#fieldNome");
        TextField fieldNomeResponsabile = (TextField) root.lookup("#fieldNomeResponsabile");
        TextField fieldCognomeResponsabile = (TextField) root.lookup("#fieldCognomeResponsabile");
        TextField fieldEmail = (TextField) root.lookup("#fieldEmail");
        TextField fieldIndirizzo = (TextField) root.lookup("#fieldIndirizzo");
        TextField fieldCellulare = (TextField) root.lookup("#fieldCellulare");

        // Imposta il testo delle label utilizzando i valori delle variabili
        fieldNome.setText(MainUtils.poloLoggato.getNome());
        fieldNomeResponsabile.setText(MainUtils.poloLoggato.getNome_responsabile());
        fieldCognomeResponsabile.setText(MainUtils.poloLoggato.getCognome_responsabile());
        fieldEmail.setText(MainUtils.responsabileLoggato.getEmail());
        fieldIndirizzo.setText(MainUtils.poloLoggato.getIndirizzo());
        fieldCellulare.setText("" + MainUtils.poloLoggato.getCellulare());
    }
    public Button buttonSalvaModifichePolo;

    public void clickSalvaModifichePolo(ActionEvent actionEvent) {
    }





    //per la schermata MODIFICA PROFILO PERSONALE DIOCESI
    public TextField fieldNomePrete;
    public Button buttonSalvaModificheDiocesi;

    public Button buttonModificaDatiDiocesi;
    public void clickModificaDatiDiocesi(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/SchermataModificaProfilo.fxml"));
        Stage window = (Stage) buttonModificaDatiDiocesi.getScene().getWindow();
        MainUtils.previousScene = window.getScene();
        window.setScene(new Scene(root));
        window.setTitle("Modifica Profilo Diocesi");

        // Recupera le label dal file FXML utilizzando gli ID specificati nel file FXML
        TextField fieldNome = (TextField) root.lookup("#fieldNome");
        // TextField fieldNomeResponsabile = (TextField) root.lookup("#fieldNomeResponsabile");
        // TextField fieldCognomeResponsabile = (TextField) root.lookup("#fieldCognomeResponsabile");
        TextField fieldEmail = (TextField) root.lookup("#fieldEmail");
        // TextField fieldIndirizzo = (TextField) root.lookup("#fieldIndirizzo");
        // TextField fieldCellulare = (TextField) root.lookup("#fieldCellulare");
        // TextField fieldNomePrete = (TextField) root.lookup("#fieldNomePrete");

        // Diocesi diocesi = DBMS.getDiocesi(MainUtils.responsabileLoggato.getId());

        // Imposta il testo delle label utilizzando i valori delle variabili
        fieldNome.setText(MainUtils.responsabileLoggato.getNome());
        fieldEmail.setText(MainUtils.responsabileLoggato.getEmail());
    }

    public void clickSalvaModificheDiocesi(ActionEvent actionEvent) throws Exception {
        String nome_diocesi = fieldNome.getText() != null ? fieldNome.getText() : "";
        String email = fieldEmail.getText();
        String password = fieldVecchiaPassword.getText();
        String new_password = fieldNuovaPassword.getText();
        Boolean showErrorAlert = false;
        String error = "";

        // controllo riempimento campi
        if(!nome_diocesi.isEmpty() && !email.isEmpty()) {
            if(email.equals(MainUtils.responsabileLoggato.getEmail()) || (MainUtils.isValidEmail(email) && !DBMS.queryControllaEsistenzaEmail(email))) {
                // aggiorno la tabella help
                HashMap<String, Object> datiAggiornati = new HashMap<>();
                datiAggiornati.put("nome", nome_diocesi);
                datiAggiornati.put("email", email);
                DBMS.queryModificaDati(MainUtils.responsabileLoggato.getId(), "responsabile", datiAggiornati);
                MainUtils.responsabileLoggato = DBMS.getResponsabile(MainUtils.responsabileLoggato.getId());
            } else {
                showErrorAlert = true;
                error = "Non puoi usare questa email";
            }
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi obbligatori";
        }

        if(!password.isEmpty() && !new_password.isEmpty()) {
            if(MainUtils.isValidPassword(new_password)) {
                HashMap<String, Object> datiAggiornati = new HashMap<>();
                String encryptPassword = MainUtils.encryptPassword(password);
                datiAggiornati.put("password", encryptPassword);
                DBMS.queryModificaDati(MainUtils.responsabileLoggato.getId(), "responsabile", datiAggiornati);
            } else {
                showErrorAlert = true;
                error = "La nuova password deve essere lunga almeno 8 caratteri e contenere almeno una lettera maiuscola e un carattere speciale";
            }
        } else if(!password.isEmpty() && new_password.isEmpty()) {
            showErrorAlert = true;
            error = "Inserisci la nuova password";
        } else if(password.isEmpty() && !new_password.isEmpty()) {
            showErrorAlert = true;
            error = "Inserisci la tua password";
        }

        if(showErrorAlert) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText(error);
            alert.showAndWait();
        } else {
            // torno alla schermata precedente
            tornaAVisualizza();
        }
    }

    private void tornaAVisualizza() throws Exception {
        SchermataVisualizzaProfilo l = new SchermataVisualizzaProfilo();
        Stage window = (Stage) buttonSalvaModificheHelp.getScene().getWindow();
        l.start(window);
        // Recupera le label dal file FXML utilizzando gli ID specificati nel file FXML
        Label labelEmail = (Label) window.getScene().lookup("#labelEmail");
        Label labelNome = (Label) window.getScene().lookup("#labelNome");
        // Label labelCognome = (Label) window.getScene().lookup("#labelCognomeResponsabile");

        // Imposta il testo delle label utilizzando i valori delle variabili

        labelEmail.setText(MainUtils.responsabileLoggato.getEmail());
        labelNome.setText(MainUtils.responsabileLoggato.getNome());
    }


}