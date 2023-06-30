package it.help.help.azienda_partner.controll;

import it.help.help.azienda_partner.boundary.*;
import it.help.help.entity.AziendaPartner;
import it.help.help.entity.Donazione;
import it.help.help.entity.Prodotto;
import it.help.help.entity.RichiestaAdHoc;
import it.help.help.help.boundary.SchermataListaDonazioni;
import it.help.help.utils.DBMS;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class GestoreAziendaPartner {

    public TextField fieldQuantità;
    public DatePicker pickerDataScadenza;
    public Button buttonEffettuaDonazione;
    public MenuButton selectAlimenti;
    public TextField fieldMenuSelected;
    public Button buttonEffettuaDonazioneAdHoc;
    public Button buttonVisualizzaDonazioniEffettuate;
    public Button buttonEffettuaDonazioneSpontanea;
    public Button buttonListaDonazioniAdHoc;
    public VBox listaDonazioniAdHoc;
    public TextField fieldIdRichiesta;
    public VBox lista;

    public void clickEffettuaDonazioneSpontanea(ActionEvent actionEvent) throws Exception {
        SchermataEffettuaDonazione l = new SchermataEffettuaDonazione();
        Stage window = (Stage) buttonEffettuaDonazioneSpontanea.getScene().getWindow();
        l.start(window);

        Prodotto[] listaProdotti = DBMS.queryGetProdotti();
        Parent root = window.getScene().getRoot();
        TextField fieldMenuSelected = (TextField) root.lookup("#fieldMenuSelected");
        MenuButton selectAlimenti = (MenuButton) root.lookup("#selectAlimenti");
        CheckBox checkBoxSenzaGlutine = (CheckBox) root.lookup("#checkBoxSenzaGlutine");
        CheckBox checkBoxSenzaLattosio = (CheckBox) root.lookup("#checkBoxSenzaLattosio");
        CheckBox checkBoxSenzaZuccheri = (CheckBox) root.lookup("#checkBoxSenzaZuccheri");

        for (Prodotto prodotto : listaProdotti) {
            MenuItem menuItem = new MenuItem(prodotto.getTipo());
            menuItem.setUserData(prodotto.getCodice());
            menuItem.setOnAction(event -> {
                String selectedProductName = ((MenuItem) event.getSource()).getText();
                selectAlimenti.setText(selectedProductName);
                fieldMenuSelected.setText("" + prodotto.getCodice());

                if(prodotto.getSenzaGlutine()) {
                    checkBoxSenzaGlutine.setSelected(true);
                } else {
                    checkBoxSenzaGlutine.setSelected(false);
                }

                if(prodotto.getSenzaLattosio()) {
                    checkBoxSenzaLattosio.setSelected(true);
                } else {
                    checkBoxSenzaLattosio.setSelected(false);
                }

                if(prodotto.getSenzaZucchero()) {
                    checkBoxSenzaZuccheri.setSelected(true);
                } else {
                    checkBoxSenzaZuccheri.setSelected(false);
                }
            });

            selectAlimenti.getItems().add(menuItem);
        }

    }

    public void clickEffettuaDonazione(ActionEvent actionEvent) throws Exception {
        String codice_prodotto = fieldMenuSelected.getText();
        String quantità = fieldQuantità.getText();
        LocalDate data_scadenza = pickerDataScadenza.getValue();
        boolean showErrorAlert = false;
        String error = "";

        if(!codice_prodotto.isEmpty() && !quantità.isEmpty() && data_scadenza != null) {
            // Conversione da LocalDate a java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(data_scadenza);
            DBMS.querySalvaDonazione(MainUtils.responsabileLoggato.getIdLavoro(), Integer.parseInt(codice_prodotto), Integer.parseInt(quantità), sqlDate);
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
            MainUtils.tornaAllaHome(buttonEffettuaDonazione);
        }
    }

    public void effettuaDonazioneAdHoc(Button button, RichiestaAdHoc richiestaAdHoc) throws Exception {
        SchermataEffettuaDonazioneAdHoc l = new SchermataEffettuaDonazioneAdHoc();
        Stage window = (Stage) button.getScene().getWindow();
        l.start(window);

        Prodotto prodotto = DBMS.queryGetProdotto(richiestaAdHoc.getCodiceProdotto());
        MenuButton selectAlimenti = (MenuButton) window.getScene().lookup("#selectAlimenti");
        selectAlimenti.setText(prodotto.getTipo());
        TextField fieldQuantità = (TextField) window.getScene().lookup("#fieldQuantità");
        TextField fieldIdRichiesta = (TextField) window.getScene().lookup("#fieldIdRichiesta");
        TextField fieldMenuSelected = (TextField) window.getScene().lookup("#fieldMenuSelected");
        fieldQuantità.setText(String.valueOf(richiestaAdHoc.getQuantità()));
        fieldIdRichiesta.setText(String.valueOf(richiestaAdHoc.getId()));
        fieldMenuSelected.setText(String.valueOf(richiestaAdHoc.getCodiceProdotto()));

        CheckBox checkBoxSenzaGlutine = (CheckBox) window.getScene().lookup("#checkBoxSenzaGlutine");
        CheckBox checkBoxSenzaLattosio = (CheckBox) window.getScene().lookup("#checkBoxSenzaLattosio");
        CheckBox checkBoxSenzaZuccheri = (CheckBox) window.getScene().lookup("#checkBoxSenzaZuccheri");
        if(prodotto.getSenzaGlutine()) {
            checkBoxSenzaGlutine.setSelected(true);
        } else {
            checkBoxSenzaGlutine.setSelected(false);
        }

        if(prodotto.getSenzaLattosio()) {
            checkBoxSenzaLattosio.setSelected(true);
        } else {
            checkBoxSenzaLattosio.setSelected(false);
        }

        if(prodotto.getSenzaZucchero()) {
            checkBoxSenzaZuccheri.setSelected(true);
        } else {
            checkBoxSenzaZuccheri.setSelected(false);
        }
    }

    public void clickVisualizzaDonazioniEffettuate(ActionEvent actionEvent) throws Exception {
        SchermataVisualizzaDonazioniEffettuate l = new SchermataVisualizzaDonazioniEffettuate();
        Stage window = (Stage) buttonVisualizzaDonazioniEffettuate.getScene().getWindow();
        l.start(window);

        Parent root = window.getScene().getRoot();

        Donazione[] listaDonazioni = DBMS.queryGetAllDonazioni(MainUtils.responsabileLoggato.getIdLavoro());
        lista = (VBox) window.getScene().getRoot().lookup("#lista");
        for (Donazione donazione : listaDonazioni) {
            Prodotto prodotto = DBMS.queryGetProdotto(donazione.getCodiceProdotto());
            AziendaPartner aziendaPartner = DBMS.queryGetAziendaPartner(donazione.getIdAzienda());

            Label label = new Label("Prodotto: " + prodotto.getTipo() + "\n"
                    + "Quantità: " + donazione.getQuantità() + "\n"
                    + "Data di scadenza: " + donazione.getScadenzaProdotto() + "\n");
            lista.getChildren().add(label);
        }
    }

    public void clickListaDonazioniAdHoc(ActionEvent actionEvent) throws Exception {
        SchermataListaDonazioniAdHoc l = new SchermataListaDonazioniAdHoc();
        Stage window = (Stage) buttonListaDonazioniAdHoc.getScene().getWindow();
        l.start(window);

        RichiestaAdHoc[] listaRichieste = DBMS.queryGetRichiesteAdHoc();
        Parent root = window.getScene().getRoot();

        VBox container = new VBox(); // Contenitore verticale per i singoli elementi orizzontali
        container.setSpacing(10); // Spaziatura tra gli elementi all'interno del contenitore

        for (RichiestaAdHoc richiestaAdHoc : listaRichieste) {
            Prodotto prodotto = DBMS.queryGetProdotto(richiestaAdHoc.getCodiceProdotto());

            HBox itemBox = new HBox(); // Contenitore orizzontale per i singoli elementi (Label e Button)
            itemBox.setSpacing(10); // Spaziatura tra gli elementi all'interno del contenitore orizzontale

            Label nomeProdottoLabel = new Label(prodotto.getTipo()); // Label per il nome del prodotto
            Label quantitaLabel = new Label("Quantità: " + richiestaAdHoc.getQuantità()); // Label per la quantità
            Button donaButton = new Button("Dona"); // Pulsante "Dona"

            // Azione da eseguire quando viene premuto il pulsante "Dona"
            donaButton.setOnAction(e -> {
                // Puoi aggiungere qui il codice per gestire l'evento del pulsante "Dona"
                try {
                    effettuaDonazioneAdHoc(donaButton, richiestaAdHoc);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            itemBox.getChildren().addAll(nomeProdottoLabel, quantitaLabel, donaButton);
            container.getChildren().add(itemBox);
        }

        // Rimuovi il contenuto esistente e aggiungi il contenitore radice
        Pane rootPane = (Pane) root;
        rootPane.getChildren().add(container);
    }

    public void clickEffettuaDonazioneAdHoc(ActionEvent actionEvent) throws Exception {
        String id_richiesta = fieldIdRichiesta.getText();
        String codice_prodotto = fieldMenuSelected.getText();
        String quantità = fieldQuantità.getText();
        LocalDate data_scadenza = pickerDataScadenza.getValue();
        boolean showErrorAlert = false;
        String error = "";

        if(!codice_prodotto.isEmpty() && !quantità.isEmpty() && data_scadenza != null) {
            // Conversione da LocalDate a java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(data_scadenza);
            DBMS.querySalvaDonazione(MainUtils.responsabileLoggato.getIdLavoro(), Integer.parseInt(codice_prodotto), Integer.parseInt(quantità), sqlDate);
            // elimino la richiesta ad-hoc perché l'ho soddisfatta
            DBMS.queryEliminaRichiestaAdHoc(Integer.parseInt(id_richiesta));
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
            MainUtils.tornaAllaHome(buttonEffettuaDonazioneAdHoc);
        }
    }
}
