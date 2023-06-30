package it.help.help.polo.controll;

import it.help.help.entity.*;
import it.help.help.polo.boundary.SchermataScaricamentoReport;
import it.help.help.polo.boundary.SchermataSegnalazioneErrore;
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
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;


public class GestoreSegnalazione {
    public TextField fieldQuantità;
    public Button buttonSegnala;
    public Button buttonSegnalazioneErrori;
    public Button buttonReport;
    public MenuButton selectNuclei;
    public RadioButton radioDiocesi;
    public RadioButton radioNucleo;
    public TextField fieldMenuAlimentiSelected;
    public TextField fieldMenuNucleiSelected;
    public MenuButton selectIntervallo;

    public void clickConfermaSegnalazione(ActionEvent actionEvent) throws Exception {
        Boolean nucleoSelected = radioNucleo.isSelected();
        Boolean diocesiSelected = radioDiocesi.isSelected();
        String codice_prodotto = fieldMenuAlimentiSelected.getText();
        String id_nucleo = fieldMenuNucleiSelected.getText();
        String quantità = fieldQuantità.getText();
        boolean showErrorAlert = false;
        String error = "";

        if(!codice_prodotto.isEmpty() && !quantità.isEmpty()) {
            int tipo_soggetto;
            int id_soggetto;
            if(nucleoSelected && !id_nucleo.isEmpty()) {
                // segnalazione riguardo il nucleo
                tipo_soggetto = 1;
                id_soggetto = Integer.parseInt(id_nucleo);
                DBMS.querySalvaSegnalazione(MainUtils.responsabileLoggato.getIdLavoro(), tipo_soggetto, id_soggetto, Integer.parseInt(codice_prodotto), Integer.parseInt(quantità));
            } else if(diocesiSelected) {
                // segnalazione riguardo la diocesi
                tipo_soggetto = 0;
                Polo polo = DBMS.getPolo(MainUtils.responsabileLoggato.getIdLavoro());
                id_soggetto = polo.getId_diocesi();
                DBMS.querySalvaSegnalazione(MainUtils.responsabileLoggato.getIdLavoro(), tipo_soggetto, id_soggetto, Integer.parseInt(codice_prodotto), Integer.parseInt(quantità));
            } else {
                showErrorAlert = true;
                error = "Compila tutti i campi.";
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
            MainUtils.tornaAllaHome(buttonSegnala);
        }
    }

    public void clickSegnalazioneErrori(ActionEvent actionEvent) throws Exception {
        SchermataSegnalazioneErrore l = new SchermataSegnalazioneErrore();
        Stage window = (Stage) buttonSegnalazioneErrori.getScene().getWindow();
        l.start(window);

        Prodotto[] listaProdotti = DBMS.queryGetProdotti();
        Parent root = window.getScene().getRoot();
        TextField fieldMenuAlimentiSelected = (TextField) root.lookup("#fieldMenuAlimentiSelected");
        MenuButton selectAlimenti = (MenuButton) root.lookup("#selectAlimenti");

        for (Prodotto prodotto : listaProdotti) {
            MenuItem menuItem = new MenuItem(prodotto.getTipo());
            menuItem.setUserData(prodotto.getCodice());
            menuItem.setOnAction(event -> {
                String selectedProductName = ((MenuItem) event.getSource()).getText();
                selectAlimenti.setText(selectedProductName);
                fieldMenuAlimentiSelected.setText("" + prodotto.getCodice());
            });

            selectAlimenti.getItems().add(menuItem);
        }
    }

    public void clickReport(ActionEvent actionEvent) throws IOException {
        SchermataScaricamentoReport l = new SchermataScaricamentoReport();
        Stage window = (Stage) buttonReport.getScene().getWindow();
        l.start(window);
    }

    public void clickRadioDiocesi(ActionEvent actionEvent) {
        selectNuclei.setVisible(false);
    }

    public void clickRadioNucleo(ActionEvent actionEvent) throws Exception {
        selectNuclei.setVisible(true);
        Stage window = (Stage) radioDiocesi.getScene().getWindow();
        Nucleo[] listaNuclei = DBMS.getNuclei(MainUtils.responsabileLoggato.getIdLavoro());
        Parent root = window.getScene().getRoot();
        TextField fieldMenuNucleiSelected = (TextField) root.lookup("#fieldMenuNucleiSelected");
        MenuButton selectNuclei = (MenuButton) root.lookup("#selectNuclei");

        for (Nucleo nucleo : listaNuclei) {
            MenuItem menuItem = new MenuItem(nucleo.getCognome());
            menuItem.setUserData(nucleo.getId());
            menuItem.setOnAction(event -> {
                String selectedProductName = ((MenuItem) event.getSource()).getText();
                selectNuclei.setText(selectedProductName);
                fieldMenuNucleiSelected.setText("" + nucleo.getId());
            });

            selectNuclei.getItems().add(menuItem);
        }
    }

    public void selectMensile(ActionEvent actionEvent) {
        Stage window = (Stage) selectIntervallo.getScene().getWindow();
        Parent root = window.getScene().getRoot();

        // Ottieni il mese corrente
        Month currentMonth = LocalDate.now().getMonth();

        // Creazione del layout contenitore VBox
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        // Creazione della lista dei mesi in ordine inverso a partire dal mese corrente
        for (int i = currentMonth.getValue(); i >= 1; i--) {
            Month month = Month.of(i);
            String monthName = month.getDisplayName(TextStyle.FULL, Locale.ITALIAN);

            Button downloadButton = new Button("Download");
            downloadButton.setOnAction(event -> {
                // Logica per il download del mese corrispondente
                String selectedMonth = monthName; // Esempio: salvare il mese selezionato in una variabile
                System.out.println("Download del mese: " + selectedMonth + " " + month.getValue());
                MainUtils.generatePDF(1, month.getValue());
            });

            HBox hbox = new HBox(new Label(monthName), downloadButton);
            vbox.getChildren().add(hbox);
        }

        // Aggiunta del layout contenitore al root della scena
        ((Pane) root).getChildren().add(vbox);

    }

    public void selectTrimestrale(ActionEvent actionEvent) {
    }

    public void selectAnnuale(ActionEvent actionEvent) {
    }
}
