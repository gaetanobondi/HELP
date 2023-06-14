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
    public TextField fieldCellulare;
    public TextField fieldIndirizzo;
    public PasswordField fieldVecchiaPassword;
    public PasswordField fieldNuovaPassword;
    public TextField fieldNomeResponsabile;
    public TextField fieldCognomeResponsabile;
    public TextField fieldNomePolo;

    @FXML
    private AnchorPane contentPane;


    public Button buttonIndietro;
    public void clickIndietro(ActionEvent actionEvent) {

    }





    //per la schermata MODIFICA PROFILO HELP
    public Button buttonModificaDatiHelp;

    public void clickModificaDatiHelp(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataModificaProfiloHelp.fxml"));
        Stage window = (Stage) buttonModificaDatiHelp.getScene().getWindow();
        MainUtils.previousScene = window.getScene();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Modifica Profilo Help");

        // Recupera le label dal file FXML utilizzando gli ID specificati nel file FXML
        TextField fieldNomeResponsabile = (TextField) root.lookup("#fieldNome");
        TextField fieldCognomeResponsabile = (TextField) root.lookup("#fieldCognome");
        TextField fieldEmail = (TextField) root.lookup("#fieldEmail");

        // Imposta il testo delle label utilizzando i valori delle variabili

        fieldNomeResponsabile.setText(Help.getNome());
        fieldCognomeResponsabile.setText(Help.getCognome());
        fieldEmail.setText(Responsabile.getEmail());
    }

    public Button buttonSalvaModificheHelp;
    public void clickSalvaModificheHelp(ActionEvent actionEvent) throws Exception {
        String nome = fieldNomeResponsabile.getText();
        String cognome = fieldCognomeResponsabile.getText();
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
            Stage window = (Stage) buttonSalvaModificheHelp.getScene().getWindow();
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
        TextField fieldNomeResponsabile = (TextField) root.lookup("#fieldNomeResponsabile");
        TextField fieldCognomeResponsabile = (TextField) root.lookup("#fieldCognomeResponsabile");
        TextField fieldEmail = (TextField) root.lookup("#fieldEmail");
        TextField fieldIndirizzo = (TextField) root.lookup("#fieldIndirizzo");
        TextField fieldCellulare = (TextField) root.lookup("#fieldCellulare");

        // Imposta il testo delle label utilizzando i valori delle variabili
        fieldNome.setText(AziendaPartner.getNome());
        fieldNomeResponsabile.setText(AziendaPartner.getNomeResponsabile());
        fieldCognomeResponsabile.setText(AziendaPartner.getCognomeResponsabile());
        fieldEmail.setText(Responsabile.getEmail());
        fieldIndirizzo.setText(AziendaPartner.getIndirizzo());
        fieldCellulare.setText("" + AziendaPartner.getCellulare());
    }
    public Button buttonSalvaModificheAzienda;
    public void clickSalvaModificheAzienda(ActionEvent actionEvent) {
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
        fieldNome.setText(Polo.getNome());
        fieldNomeResponsabile.setText(Polo.getNome_responsabile());
        fieldCognomeResponsabile.setText(Polo.getCognome_responsabile());
        fieldEmail.setText(Responsabile.getEmail());
        fieldIndirizzo.setText(Polo.getIndirizzo());
        fieldCellulare.setText("" + Polo.getCellulare());
    }
    public Button buttonSalvaModifichePolo;

    public void clickSalvaModifichePolo(ActionEvent actionEvent) {
    }





    //per la schermata MODIFICA PROFILO PERSONALE DIOCESI
    public TextField fieldNomePrete;
    public Button buttonSalvaModificheDiocesi;

    public Button buttonModificaDatiDiocesi;
    public void clickModificaDatiDiocesi(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/SchermataModificaProfiloDiocesi.fxml"));
        Stage window = (Stage) buttonModificaDatiDiocesi.getScene().getWindow();
        MainUtils.previousScene = window.getScene();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Modifica Profilo Diocesi");

        // Recupera le label dal file FXML utilizzando gli ID specificati nel file FXML
        TextField fieldNome = (TextField) root.lookup("#fieldNome");
        TextField fieldNomeResponsabile = (TextField) root.lookup("#fieldNomeResponsabile");
        TextField fieldCognomeResponsabile = (TextField) root.lookup("#fieldCognomeResponsabile");
        TextField fieldEmail = (TextField) root.lookup("#fieldEmail");
        TextField fieldIndirizzo = (TextField) root.lookup("#fieldIndirizzo");
        TextField fieldCellulare = (TextField) root.lookup("#fieldCellulare");
        TextField fieldNomePrete = (TextField) root.lookup("#fieldNomePrete");

        // Imposta il testo delle label utilizzando i valori delle variabili
        fieldNome.setText(Diocesi.getNome());
        fieldNomeResponsabile.setText(Diocesi.getNome_responsabile());
        fieldCognomeResponsabile.setText(Diocesi.getCognome_responsabile());
        fieldEmail.setText(Responsabile.getEmail());
        fieldIndirizzo.setText(Diocesi.getIndirizzo());
        fieldCellulare.setText("" + Diocesi.getCellulare());
        fieldNomePrete.setText(Diocesi.getPrete());
    }

    public void clickSalvaModificheDiocesi(ActionEvent actionEvent) {
    }



}