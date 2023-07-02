package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreAutenticazione;
import it.help.help.autenticazione.controll.GestoreProfilo;
import it.help.help.azienda_partner.controll.GestoreAziendaPartner;
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

public class SchermataHomeResponsabileAziendaPartner {

    public Button buttonLogout;
    public Button buttonVisualizzaDonazioniEffettuate;
    public Button buttonEffettuaDonazioneSpontanea;
    public Button buttonListaDonazioniAdHoc;
    public Button buttonVisualizzaProfiloAziendaPartner;

    public void clickLogout(ActionEvent actionEvent) throws Exception {
        GestoreAutenticazione gestoreAutenticazione = new GestoreAutenticazione();
        gestoreAutenticazione.logout((Stage) buttonLogout.getScene().getWindow());
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

    public void clickEffettuaDonazioneSpontanea(ActionEvent actionEvent) throws Exception {
        GestoreAziendaPartner gestoreAziendaPartner = new GestoreAziendaPartner();
        gestoreAziendaPartner.schermataEffettuaDonazioneSpontanea((Stage) buttonEffettuaDonazioneSpontanea.getScene().getWindow());
    }

    public void clickListaDonazioniAdHoc(ActionEvent actionEvent) throws Exception {
        GestoreAziendaPartner gestoreAziendaPartner = new GestoreAziendaPartner();
        gestoreAziendaPartner.schermataListaDonazioniAdHoc((Stage) buttonListaDonazioniAdHoc.getScene().getWindow());
    }

    public void clickVisualizzaDonazioniEffettuate(ActionEvent actionEvent) throws Exception {
        GestoreAziendaPartner gestoreAziendaPartner = new GestoreAziendaPartner();
        gestoreAziendaPartner.schermataVisualizzaDonazioniEffettuate((Stage) buttonVisualizzaDonazioniEffettuate.getScene().getWindow());
    }
    public void clickVisualizzaProfiloAziendaPartner(ActionEvent actionEvent) throws Exception {
        GestoreProfilo gestoreProfilo = new GestoreProfilo();
        gestoreProfilo.visualizzaProfiloAziendaPartner((Stage) buttonVisualizzaProfiloAziendaPartner.getScene().getWindow());
    }
}
