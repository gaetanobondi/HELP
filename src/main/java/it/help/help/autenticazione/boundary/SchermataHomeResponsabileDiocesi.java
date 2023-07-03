package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreAutenticazione;
import it.help.help.autenticazione.controll.GestoreProfilo;
import it.help.help.diocesi.controll.GestoreDiocesi;
import it.help.help.magazzino.controll.GestoreMagazzino;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SchermataHomeResponsabileDiocesi {

    public Button buttonVisualizzaProfiloDiocesi;
    public Button buttonVisualizzaSchemaDiDistribuzioneDiocesi;
    public Button buttonVisualizzaListaPoli;
    public Button buttonRegistrazionePolo;
    public Button buttonLogout;
    public Button buttonAggiungiViveriMagazzino;

    public void clickVisualizzaProfiloDiocesi(ActionEvent actionEvent) throws Exception {
        GestoreProfilo gestoreProfilo = new GestoreProfilo();
        gestoreProfilo.visualizzaProfiloDiocesi((Stage) buttonVisualizzaProfiloDiocesi.getScene().getWindow());
    }

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

    public void clickVisualizzaSchemaDiDistribuzioneDiocesi(ActionEvent actionEvent) throws Exception {
        GestoreDiocesi gestoreDiocesi = new GestoreDiocesi();
        gestoreDiocesi.schermataVisualizzaSchemaDiDistribuzioneDiocesi((Stage) buttonVisualizzaSchemaDiDistribuzioneDiocesi.getScene().getWindow());
    }

    public void clickVisualizzaListaPoli(ActionEvent actionEvent) throws Exception {
        GestoreDiocesi gestoreDiocesi = new GestoreDiocesi();
        gestoreDiocesi.schermataVisualizzaPoliIscritti((Stage) buttonVisualizzaListaPoli.getScene().getWindow());
    }

    public void clickRegistrazionePolo(ActionEvent actionEvent) {
        GestoreDiocesi gestoreDiocesi = new GestoreDiocesi();
        gestoreDiocesi.schermataRegistraPolo((Stage) buttonRegistrazionePolo.getScene().getWindow());
    }

    public void clickLogout(ActionEvent actionEvent) throws Exception {
        GestoreAutenticazione gestoreAutenticazione = new GestoreAutenticazione();
        gestoreAutenticazione.logout((Stage) buttonLogout.getScene().getWindow());
    }

    public void clickAggiungiViveriMagazzino(ActionEvent actionEvent) throws Exception {
        GestoreMagazzino gestoreMagazzino = new GestoreMagazzino();
        // type 1 diocesi
        gestoreMagazzino.aggiungiViveriMagazzino((Stage) buttonAggiungiViveriMagazzino.getScene().getWindow(), 1);
    }
}
