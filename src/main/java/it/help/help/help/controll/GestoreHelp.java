package it.help.help.help.controll;

import it.help.help.autenticazione.boundary.SchermataHomeResponsabileHelp;
import it.help.help.autenticazione.boundary.SchermataHomeResponsabilePolo;
import it.help.help.autenticazione.boundary.SchermataRegistrazionePolo;
import it.help.help.entity.*;
import it.help.help.help.boundary.SchermataGestione;
import it.help.help.help.boundary.SchermataLista;
import it.help.help.help.boundary.SchermataListaDonazioni;
import it.help.help.help.boundary.SchermataVisualizzaPrevisioneDistribuzione;
import it.help.help.utils.DBMS;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class GestoreHelp {
    public Button buttonVisualizzaPrevisioneDistribuzione;
    public Button buttonListaPoli;
    public Button buttonListaDiocesi;
    public Button buttonListaAziende;
    public Button buttonGestione;
    public VBox lista;
    public Button buttonListaDonazioniRicevute;

    public void visualizzaPrevisioneDiDistribuzione(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata visualizza previsione di distribuzione","/it/help/help/help/SchermataVisualizzaPrevisioneDistribuzione.fxml", stage, c -> {
            return new SchermataVisualizzaPrevisioneDistribuzione();
        });
    }

    public void tornaAHelp(Button button) throws IOException {
        MainUtils.responsabileLoggato = MainUtils.responsabileHelpLoggato;
        // SchermataHomeResponsabileHelp l = new SchermataHomeResponsabileHelp();
        Stage window = (Stage) button.getScene().getWindow();
        // l.start(window);
    }


    public void amministraPolo(Stage stage, int id_polo) throws Exception {
        MainUtils.responsabileHelpLoggato = MainUtils.responsabileLoggato;
        MainUtils.responsabileLoggato = DBMS.getResponsabile(2, id_polo);

        MainUtils.cambiaInterfaccia("Schermata home responsabile polo","/it/help/help/polo/SchermataHomeResponsabilePolo.fxml", stage, c -> {
            return new SchermataHomeResponsabilePolo();
        });

        Parent root = stage.getScene().getRoot();

        Button backButton = new Button("Torna a Help"); // Creazione del bottone "Torna a Help"
        backButton.setOnAction(e -> {
            // Azione da eseguire quando viene premuto il bottone "Torna a Help"
            try {
                tornaAHelp(backButton);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox buttonContainer = new HBox(backButton); // Contenitore per il bottone "Torna a Help"
        buttonContainer.setAlignment(Pos.CENTER); // Allineamento del bottone al centro

        VBox layout = new VBox(buttonContainer); // Layout principale
        layout.setAlignment(Pos.CENTER); // Allineamento del layout al centro
        Pane rootPane = (Pane) root;
        rootPane.getChildren().add(layout);
    }


    public void schermataListaPoli(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata lista poli","/it/help/help/help/SchermataLista.fxml", stage, c -> {
            return new SchermataLista(this);
        });

        Polo[] listaPoli = DBMS.queryGetAllPoli();
        Parent root = stage.getScene().getRoot();

        lista = (VBox) stage.getScene().getRoot().lookup("#lista");
        VBox buttonContainer = new VBox(); // Contenitore per i bottoni verticali
        buttonContainer.setSpacing(10); // Spaziatura tra i bottoni

        for (Polo polo : listaPoli) {
            Button poloButton = new Button(polo.getNome()); // Creazione del pulsante con il nome del polo
            poloButton.setOnAction(e -> {
                // Azione da eseguire quando viene premuto il pulsante del polo
                try {
                    amministraPolo((Stage) poloButton.getScene().getWindow(), polo.getId());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            buttonContainer.getChildren().add(poloButton); // Aggiunta del pulsante al contenitore
        }

        // Rimuovi il contenuto esistente e imposta il contenitore dei bottoni come nuovo contenuto
        lista.getChildren().add(buttonContainer);
    }


    public void clickListaDiocesi(ActionEvent actionEvent) throws IOException {
        // SchermataLista l = new SchermataLista();
        Stage window = (Stage) buttonListaDiocesi.getScene().getWindow();
        // l.start(window);
    }

    public void clickListaAziende(ActionEvent actionEvent) throws IOException {
        // SchermataLista l = new SchermataLista();
        Stage window = (Stage) buttonListaAziende.getScene().getWindow();
        // l.start(window);
    }

    public void schermataGestione(Stage stage) throws IOException {
        MainUtils.cambiaInterfaccia("Schermata gestione","/it/help/help/help/SchermataGestione.fxml", stage, c -> {
            return new SchermataGestione(this);
        });
    }

    public void schermataListaDonazioniRicevute(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata lista donazioni ricevute","/it/help/help/help/SchermataListaDonazioni.fxml", stage, c -> {
            return new SchermataListaDonazioni();
        });

        Parent root = stage.getScene().getRoot();

        Donazione[] listaDonazioni = DBMS.queryGetAllDonazioni();
        lista = (VBox) stage.getScene().getRoot().lookup("#lista");
        for (Donazione donazione : listaDonazioni) {
            Prodotto prodotto = DBMS.queryGetProdotto(donazione.getCodiceProdotto());
            AziendaPartner aziendaPartner = DBMS.queryGetAziendaPartner(donazione.getIdAzienda());

            Label label = new Label("Prodotto: " + prodotto.getTipo() + "\n"
                    + "Quantità: " + donazione.getQuantità() + "\n"
                    + "Azienda donatrice: " + aziendaPartner.getNome() + "\n"
                    + "Data di scadenza: " + donazione.getScadenzaProdotto() + "\n");
            lista.getChildren().add(label);
        }
    }
}
