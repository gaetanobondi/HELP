package it.help.help.autenticazione.controll;

import it.help.help.autenticazione.boundary.*;
import it.help.help.entity.AziendaPartner;
import it.help.help.entity.Help;
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

import java.io.IOException;
import java.util.*;

// import javax.swing.text.html.ImageView;

//
public class GestoreProfilo {
    public Button buttonVisualizzaProfiloHelp;

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
    public TextField fieldCognome;
    public Button buttonVisualizzaProfiloAziendaPartner;
    public TextField fieldNomeAziendaPartner;
    public Label labelNomeDiocesi;
    public Button buttonVisualizzaProfiloDiocesi;
    public TextField fieldNomeDiocesi;

    @FXML
    private AnchorPane contentPane;




    //per la schermata MODIFICA PROFILO HELP
    public Button buttonModificaDatiHelp;

    @FXML
    public void visualizzaProfiloHelp(Stage stage) throws Exception {
        MainUtils.helpLoggato = DBMS.queryGetHelp(MainUtils.responsabileLoggato.getIdLavoro());
        String nome = MainUtils.responsabileLoggato.getNome();
        String cognome = MainUtils.responsabileLoggato.getCognome();
        String email = MainUtils.responsabileLoggato.getEmail();
        String indirizzo = MainUtils.helpLoggato.getIndirizzo();
        int cellulare = MainUtils.helpLoggato.getCellulare();

        SchermataVisualizzaProfiloHelp p = new SchermataVisualizzaProfiloHelp();
        MainUtils.cambiaInterfaccia("Schermata profilo help", "/it/help/help/help/SchermataProfiloPersonaleHelp.fxml", stage, c -> {
            return p;
        });
        p.inizialize(nome, cognome, email, cellulare, indirizzo);
    }

    public void schermataModificaDatiHelp(Stage stage) throws Exception {
        String nome = MainUtils.responsabileLoggato.getNome();
        String cognome = MainUtils.responsabileLoggato.getCognome();
        String email = MainUtils.responsabileLoggato.getEmail();
        String indirizzo = MainUtils.helpLoggato.getIndirizzo();
        int cellulare = MainUtils.helpLoggato.getCellulare();

        SchermataModificaProfiloHelp p = new SchermataModificaProfiloHelp(this);
        MainUtils.cambiaInterfaccia("Schermata modifica profilo help", "/it/help/help/help/SchermataModificaProfiloHelp.fxml", stage, c -> {
            return p;
        });
        p.inizialize(nome, cognome, email, cellulare, indirizzo);
    }

    public Button buttonSalvaModificheHelp;
    public void salvaModificheHelp(Stage stage, String nome, String cognome, String email, String indirizzo, String cellulare, String password, String new_password) throws Exception {
        Boolean showErrorAlert = false;
        String error = "";

        // controllo riempimento campi
        if(!nome.isEmpty() && !cognome.isEmpty() && !indirizzo.isEmpty() && !cellulare.isEmpty() && !email.isEmpty()) {
            if(email.equals(MainUtils.responsabileLoggato.getEmail()) || (MainUtils.isValidEmail(email) && !DBMS.queryControllaEsistenzaEmail(email))) {
                // NEL SEQUENCE C'è SOLO UNA QUERY, QUI CE NE SONO 2
                // aggiorno la tabella help
                HashMap<String, Object> datiAggiornati = new HashMap<>();
                datiAggiornati.put("indirizzo", indirizzo);
                datiAggiornati.put("cellulare", cellulare);
                DBMS.queryModificaDati(MainUtils.responsabileLoggato.getIdLavoro(), "help", datiAggiornati);
                // ricarico la entity help
                MainUtils.helpLoggato = DBMS.queryGetHelp(MainUtils.responsabileLoggato.getIdLavoro());

                // aggiorno la tabella responsabile
                HashMap<String, Object> datiAggiornatiResponsabile = new HashMap<>();
                datiAggiornatiResponsabile.put("email", email);
                datiAggiornatiResponsabile.put("nome", nome);
                datiAggiornatiResponsabile.put("cognome", cognome);
                DBMS.queryModificaDati(MainUtils.responsabileLoggato.getId(), "responsabile", datiAggiornatiResponsabile);
                // ricarico la entity responsabile
                MainUtils.responsabileLoggato = DBMS.getResponsabile(0, MainUtils.responsabileLoggato.getIdLavoro());
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
                // ricarico la entity responsabile
                MainUtils.responsabileLoggato = DBMS.getResponsabile(0, MainUtils.responsabileLoggato.getIdLavoro());
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
            visualizzaProfiloHelp(stage);
        }
    }





    //per la schermata MODIFICA PROFILO PERSONALE AZIENDA PARTNER
    public Button buttonModificaDatiAzienda;

    public void schermataModificaDatiAzienda(Stage stage) throws Exception {
        String nome = MainUtils.responsabileLoggato.getNome();
        String cognome = MainUtils.responsabileLoggato.getCognome();
        String email = MainUtils.responsabileLoggato.getEmail();
        String indirizzo = MainUtils.aziendaPartnerLoggata.getIndirizzo();
        int cellulare = MainUtils.aziendaPartnerLoggata.getCellulare();
        String nomeAzienda = MainUtils.aziendaPartnerLoggata.getNome();

        SchermataModificaProfiloAziendaPartner p = new SchermataModificaProfiloAziendaPartner(this);
        MainUtils.cambiaInterfaccia("Schermata modifica profilo azienda", "/it/help/help/azienda_partner/SchermataModificaProfiloAziendaPartner.fxml", stage, c -> {
            return p;
        });
        p.inizialize(nome, cognome, email, cellulare, indirizzo, nomeAzienda);
    }
    public Button buttonSalvaModificheAzienda;
    public void salvaModificheAzienda(Stage stage, String nome, String cognome, String email, String indirizzo, String cellulare, String nome_azienda, String password, String new_password) throws Exception {
        Boolean showErrorAlert = false;
        String error = "";

        // controllo riempimento campi
        if(!nome_azienda.isEmpty() && !email.isEmpty() && !cellulare.isEmpty() && !nome.isEmpty() && !cognome.isEmpty() && !indirizzo.isEmpty()) {
            if(email.equals(MainUtils.responsabileLoggato.getEmail()) || (MainUtils.isValidEmail(email) && !DBMS.queryControllaEsistenzaEmail(email))) {
                // aggiorno la tabella azienda
                HashMap<String, Object> datiAggiornati = new HashMap<>();
                datiAggiornati.put("indirizzo", indirizzo);
                datiAggiornati.put("cellulare", cellulare);
                datiAggiornati.put("nome", nome_azienda);
                DBMS.queryModificaDati(MainUtils.responsabileLoggato.getIdLavoro(), "azienda", datiAggiornati);
                // ricarico la entity azienda
                MainUtils.aziendaPartnerLoggata = DBMS.queryGetAziendaPartner(MainUtils.responsabileLoggato.getIdLavoro());

                // aggiorno la tabella responsabile
                HashMap<String, Object> datiAggiornatiResponsabile = new HashMap<>();
                datiAggiornatiResponsabile.put("nome", nome);
                datiAggiornatiResponsabile.put("cognome", cognome);
                datiAggiornatiResponsabile.put("email", email);
                DBMS.queryModificaDati(MainUtils.responsabileLoggato.getId(), "responsabile", datiAggiornatiResponsabile);
                // ricarico la entity responsabile
                MainUtils.responsabileLoggato = DBMS.getResponsabile(3, MainUtils.responsabileLoggato.getIdLavoro());
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
                MainUtils.responsabileLoggato = DBMS.getResponsabile(3, MainUtils.responsabileLoggato.getIdLavoro());
                // ricarico la entity responsabile
                MainUtils.responsabileLoggato = DBMS.getResponsabile(3, MainUtils.responsabileLoggato.getIdLavoro());
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
            visualizzaProfiloAziendaPartner(stage);
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
        fieldNomeResponsabile.setText(MainUtils.responsabileLoggato.getNome());
        fieldCognomeResponsabile.setText(MainUtils.responsabileLoggato.getCognome());
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
    public void schermataModificaDatiDiocesi(Stage stage) throws Exception {
        String nome = MainUtils.responsabileLoggato.getNome();
        String cognome = MainUtils.responsabileLoggato.getCognome();
        String email = MainUtils.responsabileLoggato.getEmail();
        String indirizzo = MainUtils.diocesiLoggata.getIndirizzo();
        int cellulare = MainUtils.diocesiLoggata.getCellulare();
        String nomeDiocesi = MainUtils.diocesiLoggata.getNome();

        SchermataModificaProfiloDiocesi p = new SchermataModificaProfiloDiocesi(this);
        MainUtils.cambiaInterfaccia("Schermata modifica profilo diocesi", "/it/help/help/diocesi/SchermataModificaProfiloDiocesi.fxml", stage, c -> {
            return p;
        });
        p.inizialize(nome, cognome, email, cellulare, indirizzo, nomeDiocesi);
    }

    public void salvaModificheDiocesi(Stage stage, String nome, String cognome, String email, String indirizzo, String cellulare, String nome_diocesi, String password, String new_password) throws Exception {
        Boolean showErrorAlert = false;
        String error = "";

        // controllo riempimento campi
        if(!nome_diocesi.isEmpty() && !email.isEmpty() && !cellulare.isEmpty() && !nome.isEmpty() && !cognome.isEmpty() && !indirizzo.isEmpty()) {
            if(email.equals(MainUtils.responsabileLoggato.getEmail()) || (MainUtils.isValidEmail(email) && !DBMS.queryControllaEsistenzaEmail(email))) {
                // aggiorno la tabella diocesi
                HashMap<String, Object> datiAggiornati = new HashMap<>();
                datiAggiornati.put("indirizzo", indirizzo);
                datiAggiornati.put("cellulare", cellulare);
                datiAggiornati.put("nome", nome_diocesi);
                DBMS.queryModificaDati(MainUtils.responsabileLoggato.getIdLavoro(), "diocesi", datiAggiornati);
                // ricarico la entity azienda
                MainUtils.diocesiLoggata = DBMS.queryGetDiocesi(MainUtils.responsabileLoggato.getIdLavoro());

                // aggiorno la tabella responsabile
                HashMap<String, Object> datiAggiornatiResponsabile = new HashMap<>();
                datiAggiornatiResponsabile.put("nome", nome_diocesi);
                datiAggiornatiResponsabile.put("email", email);
                DBMS.queryModificaDati(MainUtils.responsabileLoggato.getId(), "responsabile", datiAggiornatiResponsabile);
                // ricarico la entity responsabile
                MainUtils.responsabileLoggato = DBMS.getResponsabile(1, MainUtils.responsabileLoggato.getIdLavoro());
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
                // ricarico la entity responsabile
                MainUtils.responsabileLoggato = DBMS.getResponsabile(1, MainUtils.responsabileLoggato.getIdLavoro());
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
            visualizzaProfiloDiocesi(stage);
        }
    }


    public void visualizzaProfiloAziendaPartner(Stage stage) throws Exception {
        MainUtils.aziendaPartnerLoggata = DBMS.queryGetAziendaPartner(MainUtils.responsabileLoggato.getIdLavoro());
        String nome = MainUtils.responsabileLoggato.getNome();
        String cognome = MainUtils.responsabileLoggato.getCognome();
        String email = MainUtils.responsabileLoggato.getEmail();
        String indirizzo = MainUtils.aziendaPartnerLoggata.getIndirizzo();
        int cellulare = MainUtils.aziendaPartnerLoggata.getCellulare();
        String nomeAzienda = MainUtils.aziendaPartnerLoggata.getNome();

        SchermataVisualizzaProfiloAziendaPartner p = new SchermataVisualizzaProfiloAziendaPartner();
        MainUtils.cambiaInterfaccia("Schermata profilo azienda", "/it/help/help/azienda_partner/SchermataProfiloPersonaleAziendaPartner.fxml", stage, c -> {
            return p;
        });
        p.inizialize(nome, cognome, email, cellulare, indirizzo, nomeAzienda);
    }

    public void visualizzaProfiloDiocesi(Stage stage) throws Exception {
        MainUtils.diocesiLoggata = DBMS.queryGetDiocesi(MainUtils.responsabileLoggato.getIdLavoro());
        String nome = MainUtils.responsabileLoggato.getNome();
        String cognome = MainUtils.responsabileLoggato.getCognome();
        String email = MainUtils.responsabileLoggato.getEmail();
        String indirizzo = MainUtils.diocesiLoggata.getIndirizzo();
        int cellulare = MainUtils.diocesiLoggata.getCellulare();
        String nomeDiocesi = MainUtils.diocesiLoggata.getNome();

        SchermataVisualizzaProfiloDiocesi p = new SchermataVisualizzaProfiloDiocesi();
        MainUtils.cambiaInterfaccia("Schermata profilo diocesi", "/it/help/help/diocesi/SchermataProfiloPersonaleDiocesi.fxml", stage, c -> {
            return p;
        });
        p.inizialize(nome, cognome, email, cellulare, indirizzo, nomeDiocesi);
    }
}