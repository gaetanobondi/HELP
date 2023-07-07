package it.help.help.polo.controll;

import it.help.help.entity.*;
import it.help.help.polo.boundary.SchermataScaricamentoReport;
import it.help.help.polo.boundary.SchermataSegnalazioneErrore;
import it.help.help.utils.DBMS;
import it.help.help.utils.MainUtils;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Locale;


public class GestoreSegnalazione {

    public void confermaSegnalazione(Stage stage, Boolean nucleoSelected, Boolean diocesiSelected, String codice_prodotto, String id_nucleo, String quantità) throws Exception {
        boolean showErrorAlert = false;
        String error = "";

        if(!codice_prodotto.isEmpty() && !quantità.isEmpty()) {
            if(MainUtils.contieneSoloNumeri(codice_prodotto) && MainUtils.contieneSoloNumeri(quantità)) {
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
                    Polo polo = DBMS.queryGetPolo(MainUtils.responsabileLoggato.getIdLavoro());
                    id_soggetto = polo.getId_diocesi();
                    DBMS.querySalvaSegnalazione(MainUtils.responsabileLoggato.getIdLavoro(), tipo_soggetto, id_soggetto, Integer.parseInt(codice_prodotto), Integer.parseInt(quantità));
                } else {
                    showErrorAlert = true;
                    error = "Compila tutti i campi.";
                }
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

    public void schermataSegnalazioneErrore(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata segnalazione errore", "/it/help/help/polo/SchermataSegnalazioneErrore.fxml", stage, c -> {
            return new SchermataSegnalazioneErrore(this);
        });

        Prodotto[] listaProdotti = DBMS.queryGetProdotti();
        Parent root = stage.getScene().getRoot();
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

    public void schermataScaricamentoReport(Stage stage) throws IOException {
        MainUtils.cambiaInterfaccia("Schermata scaricamento report", "/it/help/help/polo/SchermataScaricamentoReport.fxml", stage, c -> {
            return new SchermataScaricamentoReport(this);
        });
    }

    public void clickRadioDiocesi(Stage stage) {
        Parent root = stage.getScene().getRoot();
        MenuButton selectNuclei = (MenuButton) root.lookup("#selectNuclei");
        selectNuclei.setVisible(false);
    }

    public void clickRadioNucleo(Stage stage) throws Exception {
        Nucleo[] listaNuclei = DBMS.queryGetNuclei(MainUtils.responsabileLoggato.getIdLavoro());
        Parent root = stage.getScene().getRoot();
        TextField fieldMenuNucleiSelected = (TextField) root.lookup("#fieldMenuNucleiSelected");
        MenuButton selectNuclei = (MenuButton) root.lookup("#selectNuclei");
        selectNuclei.setVisible(true);

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

    public void selectMensile(Stage stage, VBox lista) throws Exception {
        Parent root = stage.getScene().getRoot();

        // Ottieni il mese corrente
        Month currentMonth = LocalDate.now().getMonth();

        // Creazione del layout contenitore VBox
        VBox vbox = lista;
        vbox.setSpacing(10);
        vbox.getChildren().clear();
        boolean almenoUno = false;

        // Creazione della lista dei mesi in ordine inverso a partire dal mese corrente
        for (int i = currentMonth.getValue(); i >= 1; i--) {
            Month month = Month.of(i);
            String monthName = month.getDisplayName(TextStyle.FULL, Locale.ITALIAN);
            Segnalazione[] listaSegnalazioni = DBMS.queryGetSegnalazioni(0, month.getValue());

            if(listaSegnalazioni.length > 0) {
                almenoUno = true;
                Button downloadButton = new Button("Download");
                downloadButton.setOnAction(event -> {
                    // Logica per il download del mese corrispondente
                    String selectedMonth = monthName; // Esempio: salvare il mese selezionato in una variabile
                    System.out.println("Download del mese: " + selectedMonth + " " + month.getValue());
                    MainUtils.generatePDF(listaSegnalazioni, monthName);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Pop-Up Conferma");
                    alert.setHeaderText("Report scaricato nella cartella download del tuo pc");
                    alert.showAndWait();
                });

                HBox hbox = new HBox(new Label(monthName), downloadButton);
                vbox.getChildren().add(hbox);
            }
        }

        if(!almenoUno) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText("Nessun report per questo intervallo presente");
            alert.showAndWait();
        }

        // Aggiunta del layout contenitore al root della scena
        ((Pane) root).getChildren().add(vbox);

    }

    private String getTrimesterName(int startMonth) {
        String trimesterName;

        switch (startMonth) {
            case 1:
            case 2:
            case 3:
                trimesterName = "Gennaio/Febbraio/Marzo";
                break;
            case 4:
            case 5:
            case 6:
                trimesterName = "Aprile/Maggio/Giugno";
                break;
            case 7:
            case 8:
            case 9:
                trimesterName = "Luglio/Agosto/Settembre";
                break;
            case 10:
            case 11:
            case 12:
                trimesterName = "Ottobre/Novembre/Dicembre";
                break;
            default:
                trimesterName = "Trimestre sconosciuto";
                break;
        }

        return trimesterName;
    }


    public void selectTrimestrale(Stage stage, VBox lista) throws Exception {
        Parent root = stage.getScene().getRoot();

        // Ottieni il mese corrente
        Month currentMonth = LocalDate.now().getMonth();

        // Creazione del layout contenitore VBox
        VBox vbox = lista;
        vbox.setSpacing(10);
        vbox.getChildren().clear();
        boolean almenoUno = false;

        // Creazione della lista dei mesi in ordine inverso a partire dal mese corrente
        for (int i = 1; i <= 12; i += 3) {
            int startMonth = i;
            int endMonth = i + 2;
            String trimesterName = getTrimesterName(startMonth);

            Segnalazione[] listaSegnalazioni = DBMS.queryGetSegnalazioni(1, startMonth);

            if (listaSegnalazioni.length > 0) {
                almenoUno = true;
                Button downloadButton = new Button("Download");
                downloadButton.setOnAction(event -> {
                    // Logica per il download del trimestre corrispondente
                    String selectedTrimester = trimesterName; // Esempio: salvare il trimestre selezionato in una variabile
                    System.out.println("Download del trimestre: " + selectedTrimester);
                    MainUtils.generatePDF(listaSegnalazioni, trimesterName);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Pop-Up Conferma");
                    alert.setHeaderText("Report scaricato nella cartella download del tuo pc");
                    alert.showAndWait();
                });

                HBox hbox = new HBox(new Label(trimesterName), downloadButton);
                vbox.getChildren().add(hbox);
            }
        }


        if(!almenoUno) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText("Nessun report per questo intervallo presente");
            alert.showAndWait();
        }

        // Aggiunta del layout contenitore al root della scena
        ((Pane) root).getChildren().add(vbox);
    }

    public void selectAnnuale(Stage stage, VBox lista) throws Exception {
        Parent root = stage.getScene().getRoot();

        // Ottieni il mese corrente
        int currentYear = LocalDate.now().getYear();

        // Creazione del layout contenitore VBox
        VBox vbox = lista;
        vbox.setSpacing(10);
        vbox.getChildren().clear();
        boolean almenoUno = false;

        // Creazione della lista dei mesi in ordine inverso a partire dal mese corrente
        for (int i = currentYear; i >= 2018; i--) {
            Year year = Year.of(i);
            // String monthName = year.getDisplayName(TextStyle.FULL, Locale.ITALIAN);
            Segnalazione[] listaSegnalazioni = DBMS.queryGetSegnalazioni(2, year.getValue());

            if(listaSegnalazioni.length > 0) {
                almenoUno = true;
                Button downloadButton = new Button("Download");
                downloadButton.setOnAction(event -> {
                    // Logica per il download del mese corrispondente
                    String selectedMonth = "" + year.getValue(); // Esempio: salvare il mese selezionato in una variabile
                    // System.out.println("Download del mese: " + selectedMonth + " " + month.getValue());
                    MainUtils.generatePDF(listaSegnalazioni, "" + year.getValue());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Pop-Up Conferma");
                    alert.setHeaderText("Report scaricato nella cartella download del tuo pc");
                    alert.showAndWait();
                });

                HBox hbox = new HBox(new Label("" + year.getValue()), downloadButton);
                vbox.getChildren().add(hbox);
            }
        }

        if(!almenoUno) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText("Nessun report per questo intervallo presente");
            alert.showAndWait();
        }

        // Aggiunta del layout contenitore al root della scena
        ((Pane) root).getChildren().add(vbox);
    }
}
