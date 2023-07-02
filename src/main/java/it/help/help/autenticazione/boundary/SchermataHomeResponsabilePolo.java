package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreAutenticazione;
import it.help.help.autenticazione.controll.GestoreProfilo;
import it.help.help.magazzino.controll.GestoreMagazzino;
import it.help.help.polo.controll.GestoreNucleo;
import it.help.help.polo.controll.GestorePolo;
import it.help.help.polo.controll.GestoreSegnalazione;
import it.help.help.utils.MainUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataHomeResponsabilePolo {

    public Button buttonVisualizzaSchemaDiDistribuzionePolo;
    public Button buttonVisualizzaProfilo;
    public Button buttonInserimentoNucleo;
    public Button buttonSegnalazioneErrore;
    public Button buttonReport;
    public Button buttonSospendiPolo;
    public Button buttonAggiungiViveriMagazzino;
    public Button buttonLogout;
    public Button buttonVisualizzaListaNucleiFamiliari;

    public void inizializeHelp(Stage stage) {
        if(MainUtils.responsabileHelpLoggato != null) {
            Parent root = stage.getScene().getRoot();

            Button backButton = new Button("Torna a Help"); // Creazione del bottone "Torna a Help"
            backButton.setOnAction(e -> {
                // Azione da eseguire quando viene premuto il bottone "Torna a Help"
                MainUtils.responsabileLoggato = MainUtils.responsabileHelpLoggato;
                MainUtils.responsabileHelpLoggato = null;
                MainUtils.cambiaInterfaccia("Schermata home responsabile help","/it/help/help/help/SchermataHomeResponsabileHelp.fxml", stage, c -> {
                    return new SchermataHomeResponsabileHelp();
                });
            });

            HBox buttonContainer = new HBox(backButton); // Contenitore per il bottone "Torna a Help"
            buttonContainer.setAlignment(Pos.CENTER); // Allineamento del bottone al centro

            VBox layout = new VBox(buttonContainer); // Layout principale
            layout.setAlignment(Pos.CENTER); // Allineamento del layout al centro
            Pane rootPane = (Pane) root;
            rootPane.getChildren().add(layout);
        }
    }
    public void clickVisualizzaProfilo(ActionEvent actionEvent) throws Exception {
        GestoreProfilo gestoreProfilo = new GestoreProfilo();
        gestoreProfilo.visualizzaProfiloPolo((Stage) buttonVisualizzaProfilo.getScene().getWindow());
    }

    public void clickVisualizzaSchemaDiDistribuzionePolo(ActionEvent actionEvent) throws Exception {
        GestorePolo gestorePolo = new GestorePolo();
        gestorePolo.schermataVisualizzaSchemaDiDistribuzionePolo((Stage) buttonVisualizzaSchemaDiDistribuzionePolo.getScene().getWindow());
    }

    public void clickSospendiPolo(ActionEvent actionEvent) throws Exception {
        GestoreAutenticazione gestoreAutenticazione = new GestoreAutenticazione();
        gestoreAutenticazione.schermataSospendiPolo((Stage) buttonSospendiPolo.getScene().getWindow());
    }

    public void clickInserimentoNucleo(ActionEvent actionEvent) throws Exception {
        GestoreNucleo gestoreNucleo = new GestoreNucleo();
        gestoreNucleo.schermataInserimentoNucleo((Stage) buttonInserimentoNucleo.getScene().getWindow());
    }

    public void clickAggiungiViveriMagazzino(ActionEvent actionEvent) throws Exception {
        GestoreMagazzino gestoreMagazzino = new GestoreMagazzino();
        // type 2 polo
        gestoreMagazzino.aggiungiViveriMagazzino((Stage) buttonAggiungiViveriMagazzino.getScene().getWindow(), 2);
    }

    public void clickSegnalazioneErrore(ActionEvent actionEvent) throws Exception {
        GestoreSegnalazione gestoreSegnalazione = new GestoreSegnalazione();
        gestoreSegnalazione.schermataSegnalazioneErrore((Stage) buttonSegnalazioneErrore.getScene().getWindow());
    }

    public void clickReport(ActionEvent actionEvent) {
    }

    public void clickLogout(ActionEvent actionEvent) throws Exception {
        GestoreAutenticazione gestoreAutenticazione = new GestoreAutenticazione();
        gestoreAutenticazione.logout((Stage) buttonLogout.getScene().getWindow());
    }

    public void clickVisualizzaListaNucleiFamiliari(ActionEvent actionEvent) throws Exception {
        GestoreNucleo gestoreNucleo = new GestoreNucleo();
        gestoreNucleo.schermataVisualizzaListaNucleiFamiliari((Stage) buttonVisualizzaListaNucleiFamiliari.getScene().getWindow());
    }
}

