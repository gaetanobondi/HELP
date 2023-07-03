package it.help.help.azienda_partner.controll;

import it.help.help.azienda_partner.boundary.*;
import it.help.help.entity.AziendaPartner;
import it.help.help.entity.Donazione;
import it.help.help.entity.Prodotto;
import it.help.help.entity.RichiestaAdHoc;
import it.help.help.utils.DBMS;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class GestoreAziendaPartner {

    public TextField fieldQuantità;
    public DatePicker pickerDataScadenza;
    public MenuButton selectAlimenti;
    public TextField fieldMenuSelected;
    public Button buttonEffettuaDonazioneAdHoc;
    public TextField fieldIdRichiesta;
    public VBox lista;

    public void schermataEffettuaDonazioneSpontanea(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata effettua donazione spontanea", "/it/help/help/azienda_partner/SchermataEffettuaDonazione.fxml", stage, c -> {
            return new SchermataEffettuaDonazione(this);
        });

        LocalDate currentDate = LocalDate.now();

        Parent root = stage.getScene().getRoot();
        if (currentDate.getDayOfMonth() == 1) {
            Prodotto[] listaProdotti = DBMS.queryGetProdotti();
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

                    if (prodotto.getSenzaGlutine()) {
                        checkBoxSenzaGlutine.setSelected(true);
                    } else {
                        checkBoxSenzaGlutine.setSelected(false);
                    }

                    if (prodotto.getSenzaLattosio()) {
                        checkBoxSenzaLattosio.setSelected(true);
                    } else {
                        checkBoxSenzaLattosio.setSelected(false);
                    }

                    if (prodotto.getSenzaZucchero()) {
                        checkBoxSenzaZuccheri.setSelected(true);
                    } else {
                        checkBoxSenzaZuccheri.setSelected(false);
                    }
                });

                selectAlimenti.getItems().add(menuItem);
            }
        } else {
            Group dona = (Group) root.lookup("#dona");
            dona.setVisible(false);
            Label label = (Label) root.lookup("#message");
            label.setVisible(true);
        }
    }


    public void effettuaDonazioneSpontanea(Stage stage, String codice_prodotto, String quantità, LocalDate data_scadenza) throws Exception {
        boolean showErrorAlert = false;
        String error = "";

        if(!codice_prodotto.isEmpty() && !quantità.isEmpty() && data_scadenza != null) {
            if(MainUtils.contieneSoloNumeri(codice_prodotto) && MainUtils.contieneSoloNumeri(quantità)) {
                // Conversione da LocalDate a java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(data_scadenza);
                DBMS.querySalvaDonazione(MainUtils.responsabileLoggato.getIdLavoro(), Integer.parseInt(codice_prodotto), Integer.parseInt(quantità), sqlDate);
            } else {
                showErrorAlert = true;
                error = "Inserisci i dati nel giusto formato";
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
            MainUtils.tornaAllaHome(stage);
        }
    }

    public void schermataVisualizzaDonazioniEffettuate(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata visualizza donazioni effettuate","/it/help/help/azienda_partner/SchermataVisualizzaDonazioniEffettuate.fxml", stage, c -> {
            return new SchermataVisualizzaDonazioniEffettuate();
        });

        Parent root = stage.getScene().getRoot();

        Donazione[] listaDonazioni = DBMS.queryGetAllDonazioni(MainUtils.responsabileLoggato.getIdLavoro());
        lista = (VBox) stage.getScene().getRoot().lookup("#lista");
        for (Donazione donazione : listaDonazioni) {
            Prodotto prodotto = DBMS.queryGetProdotto(donazione.getCodiceProdotto());
            AziendaPartner aziendaPartner = DBMS.queryGetAziendaPartner(donazione.getIdAzienda());

            Label label = new Label("Prodotto: " + prodotto.getTipo() + "\n"
                    + "Quantità: " + donazione.getQuantità() + "\n"
                    + "Data di scadenza: " + donazione.getScadenzaProdotto() + "\n");
            lista.getChildren().add(label);
        }
    }

    public void schermataListaDonazioniAdHoc(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata lista donazioni ad hoc","/it/help/help/azienda_partner/SchermataListaDonazioniAdHoc.fxml", stage, c -> {
            return new SchermataListaDonazioniAdHoc();
        });

        RichiestaAdHoc[] listaRichieste = DBMS.queryGetRichiesteAdHoc();
        Parent root = stage.getScene().getRoot();

        VBox listaDonazioniAdHoc = (VBox) root.lookup("#listaDonazioniAdHoc");
        listaDonazioniAdHoc.getChildren().clear(); // Rimuovi eventuali elementi precedenti

        boolean richiestePresenti = false; // Variabile per tenere traccia dello stato delle richieste

        for (RichiestaAdHoc richiestaAdHoc : listaRichieste) {
            richiestePresenti = true; // Ci sono richieste presenti
            Prodotto prodotto = DBMS.queryGetProdotto(richiestaAdHoc.getCodiceProdotto());

            HBox itemBox = new HBox();
            itemBox.setSpacing(10);

            Label nomeProdottoLabel = new Label(prodotto.getTipo());
            Label quantitaLabel = new Label("Quantità: " + richiestaAdHoc.getQuantità());
            Button donaButton = new Button("Dona");

            donaButton.setOnAction(e -> {
                try {
                    // effettuaDonazioneAdHoc(donaButton, richiestaAdHoc);
                    DBMS.querySalvaDonazione(MainUtils.responsabileLoggato.getIdLavoro(), richiestaAdHoc.getCodiceProdotto(), richiestaAdHoc.getQuantità(), null);
                    // elimino la richiesta ad-hoc perché l'ho soddisfatta
                    DBMS.queryEliminaRichiestaAdHoc(richiestaAdHoc.getId());
                    schermataListaDonazioniAdHoc(stage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            itemBox.getChildren().addAll(nomeProdottoLabel, quantitaLabel, donaButton);
            listaDonazioniAdHoc.getChildren().add(itemBox);
        }

        if (!richiestePresenti) {
            // Nessuna richiesta presente, aggiungi la label al contenitore
            Label nessunaRichiestaLabel = new Label("Nessuna richiesta ad-hoc");
            listaDonazioniAdHoc.getChildren().add(nessunaRichiestaLabel);
        }
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
            // MainUtils.tornaAllaHome(buttonEffettuaDonazioneAdHoc);
        }
    }
}
