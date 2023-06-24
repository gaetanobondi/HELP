package it.help.help.polo.controll;

import it.help.help.polo.boundary.*;
import it.help.help.utils.DBMS;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import it.help.help.entity.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;


import java.io.IOException;
import java.time.LocalDate;

public class GestoreNucleo {
    public TextField fieldCognome;
    public Button buttonRegistraNucleo;
    public TextField fieldReddito;
    @FXML
    public VBox listaComponenti;
    public TextField fieldNome;
    public TextField fieldCodFiscale;
    public TextField fieldCellulare;
    public TextField fieldIndirizzo;
    public CheckBox checkBoxCeliachia;
    public CheckBox checkBoxDiabete;
    public CheckBox checkBoxLattosio;
    public Button buttonAggiungiNuovoMembro;
    public Button buttonSalvaRegistraMembro;
    public DatePicker pickerDataNascita;
    public Label hiddenLabelIdNucleo;

    public void clickRegistraNucleo(ActionEvent actionEvent) throws Exception {
        String cognome = fieldCognome.getText();
        String reddito = fieldReddito.getText();
        boolean showErrorAlert = false;
        String error = "";

        if(!cognome.isEmpty() && !reddito.isEmpty()) {
            DBMS.queryRegistraNucleo(MainUtils.responsabileLoggato.getIdLavoro(), cognome, Integer.parseInt(reddito));
            MainUtils.tornaAllaHome(buttonRegistraNucleo);
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi.";
        }

        if(showErrorAlert) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText(error);
            alert.showAndWait();
        }
    }

    public static void eliminaNucleo(int id_nucleo) throws Exception {
        DBMS.queryEliminaNucleo(id_nucleo);
    }

    public void clickAggiungiNuovoMembro(ActionEvent actionEvent) throws Exception {
        SchermataRegistraMembro l = new SchermataRegistraMembro();
        Stage window = (Stage) buttonAggiungiNuovoMembro.getScene().getWindow();
        l.start(window);
    }

    public void clickSchemaDistribuzioneNucleo(ActionEvent actionEvent) {
    }

    public void schermataComponentiNucleo(Button button, Nucleo nucleo) throws Exception {
        SchermataComponentiNucleo l = new SchermataComponentiNucleo();
        Stage window = (Stage) button.getScene().getWindow();
        l.start(window);

        MainUtils.nucleo = nucleo;

        // Recupera le label dal file FXML utilizzando gli ID specificati nel file FXML
        Label labelCognome = (Label) window.getScene().getRoot().lookup("#labelCognome");

        // Imposta il testo delle label utilizzando i valori delle variabili
        labelCognome.setText("Famiglia " + nucleo.getCognome());

        Membro[] listaMembri = DBMS.getMembri(nucleo.getId());

        Parent root = window.getScene().getRoot();

        listaComponenti = (VBox) window.getScene().getRoot().lookup("#listaComponenti");

        for(Membro membro : listaMembri) {
            Label labelNome = new Label(membro.getNome() + " " + membro.getCognome());
            Button buttonModifica = new Button("Modifica");
            Button buttonElimina = new Button("Elimina");
            buttonElimina.setOnAction(event -> {
                try {
                    DBMS.queryEliminaMembro(membro.getCodiceFiscale());
                    MainUtils.tornaAllaHome(buttonElimina);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            HBox hBox = new HBox(10.0);
            hBox.getChildren().addAll(labelNome, buttonModifica, buttonElimina);
            listaComponenti.getChildren().add(hBox);
        }
    }

    public void clickSalvaRegistraMembro(ActionEvent actionEvent) throws Exception {
        String nome = fieldNome.getText();
        String cognome = fieldCognome.getText();
        String codice_fiscale = fieldCodFiscale.getText();
        String cellulare = fieldCellulare.getText();
        String indirizzo = fieldIndirizzo.getText();
        Boolean checkCeliachia = checkBoxCeliachia.isSelected();
        Boolean checkLattosio = checkBoxLattosio.isSelected();
        Boolean checkDiabete = checkBoxDiabete.isSelected();
        LocalDate dataNascita = pickerDataNascita.getValue();

        boolean showErrorAlert = false;
        String error = "";

        if(!nome.isEmpty() && !cognome.isEmpty() && !codice_fiscale.isEmpty() && !cellulare.isEmpty() && !indirizzo.isEmpty() && dataNascita != null) {
            // controllo che il membro non sia già iscritto
            if(!DBMS.queryControllaEsistenzaMembro(codice_fiscale)) {
                // Conversione da LocalDate a java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(dataNascita);

                DBMS.queryRegistraMembro(codice_fiscale, MainUtils.nucleo.getId(), nome, cognome, sqlDate, indirizzo, checkCeliachia, checkLattosio, checkDiabete);
                MainUtils.tornaAllaHome(buttonSalvaRegistraMembro);
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
            MainUtils.tornaAllaHome(buttonSalvaRegistraMembro);
        }
    }
}
