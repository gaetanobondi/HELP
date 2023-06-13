package it.help.help.autenticazione.controll;

import it.help.help.entity.*;
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
import java.io.IOException;

public class GestoreProfilo {

    public Button buttonModificaDati;
    public Label labelEmail;
    public Label labelPassword;
    public Label labelNome;
    public Label labelCognome;
    public Button buttonVisualizzaProfiloHelp;
    public Button buttonVisualizzaPrevisioneDistribuzione;
    public Button buttonRichiesteDiocesi;
    public Button buttonRichiesteAziendePartner;
    public Button buttonListaDonazioniRicevute;
    public Button buttonGestione;
    public Button buttonDonazioneAziendaPartner;
    public TextField fieldNome;
    public TextField fieldCognome;

    @FXML
    private AnchorPane contentPane;


    public void clickModificaDati(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataModificaProfiloHelp.fxml"));
        Stage window = (Stage) buttonModificaDati.getScene().getWindow();
        MainUtils.previousScene = window.getScene();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Modifica Profilo");

        // Recupera le label dal file FXML utilizzando gli ID specificati nel file FXML
        TextField fieldNome = (TextField) root.lookup("#fieldNome");
        TextField fieldCognome = (TextField) root.lookup("#fieldCognome");
        TextField fieldEmail = (TextField) root.lookup("#fieldEmail");

        // Imposta il testo delle label utilizzando i valori delle variabili

        fieldNome.setText(Help.getNome());
        fieldCognome.setText(Help.getCognome());
        fieldEmail.setText(Responsabile.getEmail());
    }


    //per la schermata MODIFICA PROFILO PERSONALE AZIENDA PARTNER
    public Button buttonIndietro;
    public Button buttonSalvaModifiche;

    // public ImageView home;
    public TextField fieldEmail;
    public TextField fieldCellulare;
    public TextField fieldIndirizzo;
    public PasswordField fieldVecchiaPassword;
    public PasswordField fieldNuovaPassword;
    public TextField filedNomeAziendaPartner;


    public void clickIndietro(ActionEvent actionEvent) {

    }

    public void clickSalvaModifiche(ActionEvent actionEvent) throws Exception {
        String nome = fieldNome.getText();
        String cognome = fieldCognome.getText();
        String email = fieldEmail.getText();
        String password = fieldVecchiaPassword.getText();
        String new_password = fieldNuovaPassword.getText();
        Boolean showErrorAlert = false;
        String error = "";

        // controllo riempimento campi
        if(!nome.isEmpty() && !cognome.isEmpty() && !email.isEmpty()) {
            HashMap<String, Object> datiAggiornati = new HashMap<>();
            datiAggiornati.put("nome", nome);
            datiAggiornati.put("cognome", cognome);
            DBMS.queryModificaDati(Responsabile.getId(), "help", datiAggiornati);
            DBMS.getHelp(Responsabile.getId());

            // torno alla schermata precedente
            Stage window = (Stage) buttonSalvaModifiche.getScene().getWindow();
            window.setScene(MainUtils.previousScene);
            window.setTitle("Schermata Profilo Personale");
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi obbligatori";
        }

        if(showErrorAlert) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText(error);
            alert.showAndWait();
        }
    }




    //per la schermata MODIFICA PROFILO PERSONALE DIOCESI
    public TextField filedNomeDiocesi;
    public TextField fieldNomePrete;

    public void fillInEmail(ActionEvent actionEvent) {
    }

    public void fillInCellulare(ActionEvent actionEvent) {
    }

    public void fillInIndirizzo(ActionEvent actionEvent) {
    }

    public void fillInVecchiaPassword(ActionEvent actionEvent) {
    }

    public void filInNuovaPassword(ActionEvent actionEvent) {
    }

    public void fillInNomeDiocesi(ActionEvent actionEvent) {
    }

    public void fillInNomePrete(ActionEvent actionEvent) {

    }


}