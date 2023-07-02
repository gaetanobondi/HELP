package it.help.help.polo.controll;

import it.help.help.entity.Membro;
import it.help.help.entity.Nucleo;
import it.help.help.polo.boundary.SchermataModificaMembro;
import it.help.help.polo.boundary.SchermataRegistraMembro;
import it.help.help.polo.boundary.SchermataVisualizzaComponentiNucleo;
import it.help.help.utils.DBMS;
import it.help.help.utils.MainUtils;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;

public class GestoreMembro {
    public void schermataAggiungiNuovoMembro(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata aggiungi nuovo membro", "/it/help/help/polo/SchermataRegistraMembro.fxml", stage, c -> {
            return new SchermataRegistraMembro();
        });
    }

    public void schermataModificaMembro(Stage stage, Membro membro) throws Exception {
        SchermataModificaMembro p = new SchermataModificaMembro(this);
        MainUtils.cambiaInterfaccia("Schermata modifica membro", "/it/help/help/polo/SchermataModificaMembro.fxml", stage, c -> {
            return p;
        });
        java.util.Date utilDate = new java.util.Date(membro.getDataNascita().getTime());
        p.inizialize(membro.getNome(), membro.getCognome(), membro.getCodiceFiscale(), membro.getIndirizzo(), membro.getCeliachia(), membro.getIntolleranzaLattosio(), membro.getDiabete(), utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
    public void schermataComponentiNucleo(Stage stage, Nucleo nucleo) throws Exception {
        MainUtils.nucleo = nucleo;
        MainUtils.cambiaInterfaccia("Schermata visualizza componenti nucleo", "/it/help/help/polo/SchermataVisualizzaComponentiNucleo.fxml", stage, c -> {
            return new SchermataVisualizzaComponentiNucleo();
        });

        // Recupera le label dal file FXML utilizzando gli ID specificati nel file FXML
        Label labelCognome = (Label) stage.getScene().getRoot().lookup("#labelCognome");

        // Imposta il testo delle label utilizzando i valori delle variabili
        labelCognome.setText("Famiglia " + nucleo.getCognome());

        Membro[] listaMembri = DBMS.getMembri(nucleo.getId());

        Parent root = stage.getScene().getRoot();

        VBox listaComponenti = (VBox) stage.getScene().getRoot().lookup("#listaComponenti");

        for(Membro membro : listaMembri) {
            Label labelNome = new Label(membro.getNome() + " " + membro.getCognome());
            Button buttonModifica = new Button("Modifica");
            buttonModifica.setOnAction(event -> {
                try {
                    schermataModificaMembro(stage, membro);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            Button buttonElimina = new Button("Elimina");
            buttonElimina.setOnAction(event -> {
                try {
                    DBMS.queryEliminaMembro(membro.getCodiceFiscale());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Pop-Up Errore");
                    alert.setHeaderText("Membro eliminato correttamente");
                    alert.showAndWait();
                    schermataComponentiNucleo(stage, nucleo);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            HBox hBox = new HBox(10.0);
            hBox.getChildren().addAll(labelNome, buttonModifica, buttonElimina);
            listaComponenti.getChildren().add(hBox);
        }
    }
    public void modificaMembro(Stage stage, String nome, String cognome, String codice_fiscale, String indirizzo, Boolean checkCeliachia, Boolean checkLattosio, Boolean checkDiabete, LocalDate dataNascita) throws Exception {
        boolean showErrorAlert = false;
        String error = "";

        if(!nome.isEmpty() && !cognome.isEmpty() && !codice_fiscale.isEmpty() && !indirizzo.isEmpty() && dataNascita != null) {
            // controllo che il membro non sia già iscritto
            if(codice_fiscale.equals(MainUtils.codice_fiscale) || !DBMS.queryControllaEsistenzaMembro(codice_fiscale)) {
                // aggiorno la tabella azienda
                HashMap<String, Object> datiAggiornati = new HashMap<>();
                datiAggiornati.put("nome", nome);
                datiAggiornati.put("cognome", cognome);
                datiAggiornati.put("codice_fiscale", codice_fiscale);
                datiAggiornati.put("indirizzo", indirizzo);
                datiAggiornati.put("celiachia", checkCeliachia);
                datiAggiornati.put("intolleranza_lattosio", checkLattosio);
                datiAggiornati.put("diabete", checkDiabete);
                // Conversione da LocalDate a java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(dataNascita);
                datiAggiornati.put("data_nascita", sqlDate);
                DBMS.queryModificaDati(codice_fiscale, datiAggiornati);
            } else {
                showErrorAlert = true;
                error = "Il membro risulta già iscritto al programma di aiuto.";
            }
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi.";
        }

        if(showErrorAlert) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText(error);
            alert.showAndWait();
        } else {
            schermataComponentiNucleo(stage, MainUtils.nucleo);
        }
    }
    public void registraMembro(Stage stage, String nome, String cognome, String codice_fiscale, String indirizzo, Boolean checkCeliachia, Boolean checkLattosio, Boolean checkDiabete, LocalDate dataNascita) throws Exception {
        boolean showErrorAlert = false;
        String error = "";

        if(!nome.isEmpty() && !cognome.isEmpty() && !codice_fiscale.isEmpty() && !indirizzo.isEmpty() && dataNascita != null) {
            // controllo che il membro non sia già iscritto
            if(!DBMS.queryControllaEsistenzaMembro(codice_fiscale)) {
                // Conversione da LocalDate a java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(dataNascita);

                DBMS.queryRegistraMembro(codice_fiscale, MainUtils.nucleo.getId(), nome, cognome, sqlDate, indirizzo, checkCeliachia, checkLattosio, checkDiabete);
            } else {
                showErrorAlert = true;
                error = "Il membro risulta già iscritto al programma di aiuto.";
            }
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi.";
        }

        if(showErrorAlert) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText(error);
            alert.showAndWait();
        } else {
            schermataComponentiNucleo(stage, MainUtils.nucleo);
        }
    }
}
