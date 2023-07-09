package it.help.help.autenticazione.controll;

import it.help.help.autenticazione.boundary.*;
import it.help.help.utils.MainUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import it.help.help.utils.DBMS;
import java.util.*;

public class GestoreProfilo {
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
        //per precompilare i campi si utilizza il metodo inizialize
        p.inizialize(nome, cognome, email, cellulare, indirizzo);
    }

    public void salvaModificheHelp(Stage stage, String nome, String cognome, String email, String indirizzo, String cellulare, String password, String new_password) throws Exception {
        Boolean showErrorAlert = false;
        String error = "";

        // controllo riempimento campi
        if(!nome.isEmpty() && !cognome.isEmpty() && !indirizzo.isEmpty() && !cellulare.isEmpty() && !email.isEmpty()) {
            if(MainUtils.contieneSoloLettere(nome) && MainUtils.contieneSoloLettere(cognome) && MainUtils.contieneSoloNumeri(cellulare)) {
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
                error = "Inserisci i dati nel giusto formato";
            }
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi obbligatori";
        }

        //stessi controlli per modificare la password visto che il responsabile potrebbe decidere di non modificarla
        if(!password.isEmpty() && !new_password.isEmpty()) {
            if(MainUtils.isValidPassword(new_password)) {
                HashMap<String, Object> datiAggiornati = new HashMap<>();
                String encryptPassword = MainUtils.encryptPassword(new_password);
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

    public void salvaModificheAzienda(Stage stage, String nome, String cognome, String email, String indirizzo, String cellulare, String nome_azienda, String password, String new_password) throws Exception {
        Boolean showErrorAlert = false;
        String error = "";

        // controllo riempimento campi
        if(!nome_azienda.isEmpty() && !email.isEmpty() && !cellulare.isEmpty() && !nome.isEmpty() && !cognome.isEmpty() && !indirizzo.isEmpty()) {
            if(MainUtils.contieneSoloLettere(nome_azienda) && MainUtils.contieneSoloLettere(nome) && MainUtils.contieneSoloLettere(cognome) && MainUtils.contieneSoloNumeri(cellulare)) {
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
                error = "Inserisci i dati nel giusto formato";
            }
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi obbligatori";
        }

        if(!password.isEmpty() && !new_password.isEmpty()) {
            if(MainUtils.isValidPassword(new_password)) {
                HashMap<String, Object> datiAggiornati = new HashMap<>();
                String encryptPassword = MainUtils.encryptPassword(new_password);
                datiAggiornati.put("password", encryptPassword);
                DBMS.queryModificaDati(MainUtils.responsabileLoggato.getId(), "responsabile", datiAggiornati);
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
    public void schermataModificaDatiPolo(Stage stage) throws Exception {
        String nome = MainUtils.responsabileLoggato.getNome();
        String cognome = MainUtils.responsabileLoggato.getCognome();
        String email = MainUtils.responsabileLoggato.getEmail();
        String indirizzo = MainUtils.poloLoggato.getIndirizzo();
        int cellulare = MainUtils.poloLoggato.getCellulare();
        String nomePolo = MainUtils.poloLoggato.getNome();

        SchermataModificaProfiloPolo p = new SchermataModificaProfiloPolo(this);
        MainUtils.cambiaInterfaccia("Schermata modifica profilo polo", "/it/help/help/polo/SchermataModificaProfiloPolo.fxml", stage, c -> {
            return p;
        });
        p.inizialize(nome, cognome, email, cellulare, indirizzo, nomePolo);
    }
    public Button buttonSalvaModifichePolo;

    public void salvaModifichePolo(Stage stage, String nome, String cognome, String email, String indirizzo, String cellulare, String nome_polo, String password, String new_password) throws Exception {
        Boolean showErrorAlert = false;
        String error = "";

        // controllo riempimento campi
        if(!nome_polo.isEmpty() && !email.isEmpty() && !cellulare.isEmpty() && !nome.isEmpty() && !cognome.isEmpty() && !indirizzo.isEmpty()) {
            if(MainUtils.contieneSoloLettere(nome_polo) && MainUtils.contieneSoloLettere(nome) && MainUtils.contieneSoloLettere(cognome) && MainUtils.contieneSoloNumeri(cellulare)) {
                if(email.equals(MainUtils.responsabileLoggato.getEmail()) || (MainUtils.isValidEmail(email) && !DBMS.queryControllaEsistenzaEmail(email))) {
                    // aggiorno la tabella azienda
                    HashMap<String, Object> datiAggiornati = new HashMap<>();
                    datiAggiornati.put("indirizzo", indirizzo);
                    datiAggiornati.put("cellulare", cellulare);
                    datiAggiornati.put("nome", nome_polo);
                    DBMS.queryModificaDati(MainUtils.responsabileLoggato.getIdLavoro(), "polo", datiAggiornati);
                    // ricarico la entity azienda
                    MainUtils.aziendaPartnerLoggata = DBMS.queryGetAziendaPartner(MainUtils.responsabileLoggato.getIdLavoro());

                    // aggiorno la tabella responsabile
                    HashMap<String, Object> datiAggiornatiResponsabile = new HashMap<>();
                    datiAggiornatiResponsabile.put("nome", nome);
                    datiAggiornatiResponsabile.put("cognome", cognome);
                    datiAggiornatiResponsabile.put("email", email);
                    DBMS.queryModificaDati(MainUtils.responsabileLoggato.getId(), "responsabile", datiAggiornatiResponsabile);
                    // ricarico la entity responsabile
                    MainUtils.responsabileLoggato = DBMS.getResponsabile(2, MainUtils.responsabileLoggato.getIdLavoro());
                } else {
                    showErrorAlert = true;
                    error = "Non puoi usare questa email";
                }
            } else {
                showErrorAlert = true;
                error = "Inserisci i dati nel giusto formato";
            }
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi obbligatori";
        }

        if(!password.isEmpty() && !new_password.isEmpty()) {
            if(MainUtils.isValidPassword(new_password)) {
                HashMap<String, Object> datiAggiornati = new HashMap<>();
                String encryptPassword = MainUtils.encryptPassword(new_password);
                datiAggiornati.put("password", encryptPassword);
                DBMS.queryModificaDati(MainUtils.responsabileLoggato.getId(), "responsabile", datiAggiornati);
                // ricarico la entity responsabile
                MainUtils.responsabileLoggato = DBMS.getResponsabile(2, MainUtils.responsabileLoggato.getIdLavoro());
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
            visualizzaProfiloPolo(stage);
        }
    }





    //per la schermata MODIFICA PROFILO PERSONALE DIOCESI
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
            if(MainUtils.contieneSoloLettere(nome_diocesi) && MainUtils.contieneSoloLettere(nome) && MainUtils.contieneSoloLettere(cognome) && MainUtils.contieneSoloNumeri(cellulare)) {
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
                    datiAggiornatiResponsabile.put("nome", nome);
                    datiAggiornatiResponsabile.put("cognome", cognome);
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
                error = "Inserisci i dati nel giusto formato";
            }
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi obbligatori";
        }

        if(!password.isEmpty() && !new_password.isEmpty()) {
            if(MainUtils.isValidPassword(new_password)) {
                HashMap<String, Object> datiAggiornati = new HashMap<>();
                String encryptPassword = MainUtils.encryptPassword(new_password);
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
    public void visualizzaProfiloPolo(Stage stage) throws Exception {
        MainUtils.poloLoggato = DBMS.queryGetPolo(MainUtils.responsabileLoggato.getIdLavoro());
        String nome = MainUtils.responsabileLoggato.getNome();
        String cognome = MainUtils.responsabileLoggato.getCognome();
        String email = MainUtils.responsabileLoggato.getEmail();
        String indirizzo = MainUtils.poloLoggato.getIndirizzo();
        int cellulare = MainUtils.poloLoggato.getCellulare();
        String nomePolo = MainUtils.poloLoggato.getNome();

        SchermataVisualizzaProfiloPolo p = new SchermataVisualizzaProfiloPolo();
        MainUtils.cambiaInterfaccia("Schermata profilo polo", "/it/help/help/polo/SchermataProfiloPersonalePolo.fxml", stage, c -> {
            return p;
        });
        p.inizialize(nome, cognome, email, cellulare, indirizzo, nomePolo);
    }
}