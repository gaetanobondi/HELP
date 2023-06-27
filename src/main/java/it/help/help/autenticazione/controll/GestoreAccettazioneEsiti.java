package it.help.help.autenticazione.controll;

import it.help.help.entity.*;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;

import it.help.help.utils.DBMS;

public class GestoreAccettazioneEsiti {

    public Button buttonRichiesteDiocesi;
    public Button buttonRichiesteAziendePartner;

    public void clickRichiesteDiocesi(ActionEvent actionEvent) throws Exception {
        Diocesi[] listaRichieste = DBMS.getRichiesteDiocesi();

        // Pane root = new Pane();

        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/SchermataVisualizzaRichieste.fxml"));
        Stage window = (Stage) buttonRichiesteDiocesi.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Visualizza Richieste Diocesi");

        double layoutY = 100;
        double spacing = 40.0; // Spazio verticale tra i componenti

        for (Diocesi diocesi : listaRichieste) {
            Responsabile responsabile = DBMS.getResponsabile(diocesi.getId());
            System.out.println("Responsabile diocesi iterata: " + responsabile.getId());
            System.out.println("Responsabile LOGGATO: " + MainUtils.responsabileLoggato.getId());
            Button buttonAccettaRichiesta = new Button();
            buttonAccettaRichiesta.setId("buttonAccettaRichiesta");
            buttonAccettaRichiesta.setLayoutX(300.0);
            buttonAccettaRichiesta.setLayoutY(layoutY);
            buttonAccettaRichiesta.setMnemonicParsing(false);
            buttonAccettaRichiesta.setOnAction(event -> {
                try {
                    GestoreAccettazioneEsiti.clickAccettaDiocesi(diocesi);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }); // Passa la diocesi al metodo clickAccetta()
            buttonAccettaRichiesta.setStyle("-fx-background-color: #ffffff;");
            buttonAccettaRichiesta.setText("ACCETTA");

            Label labelDiocesiRichiesta = new Label();
            labelDiocesiRichiesta.setId("labelDiocesiRichiesta");
            labelDiocesiRichiesta.setLayoutX(180.0);
            labelDiocesiRichiesta.setLayoutY(layoutY + 5.0); // Sposta l'etichetta leggermente più in basso rispetto al pulsante
            labelDiocesiRichiesta.setText(responsabile.getEmail()); // Imposta il testo dell'etichetta con il nome della diocesi

            ScrollPane scrollPane = new ScrollPane();
            Pane paneRoot = (Pane) root;
            // Imposta il margine per la ScrollPane
            Insets margin = new Insets(20.0); // Imposta il margine a 20 pixel su tutti i lati
            scrollPane.setPadding(margin);

            scrollPane.setFitToWidth(true);
            paneRoot.getChildren().addAll(buttonAccettaRichiesta, labelDiocesiRichiesta);
            scrollPane.setContent(paneRoot);
            layoutY += buttonAccettaRichiesta.getHeight() + spacing;
        }
    }

    public void clickRichiesteAziendePartner(ActionEvent actionEvent) throws Exception {
        AziendaPartner[] listaRichieste = DBMS.getRichiesteAziendePartner();

        // Pane root = new Pane();

        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/SchermataVisualizzaRichieste.fxml"));
        Stage window = (Stage) buttonRichiesteAziendePartner.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Visualizza Richieste Aziende Partner");

        double layoutY = 100;
        double spacing = 40.0; // Spazio verticale tra i componenti

        for (AziendaPartner azienda : listaRichieste) {
            System.out.println(azienda.getId());
            Responsabile responsabile = DBMS.getResponsabile(azienda.getIdResponsabile());
            Button buttonAccettaRichiesta = new Button();
            buttonAccettaRichiesta.setId("buttonAccettaRichiesta");
            buttonAccettaRichiesta.setLayoutX(300.0);
            buttonAccettaRichiesta.setLayoutY(layoutY);
            buttonAccettaRichiesta.setMnemonicParsing(false);
            buttonAccettaRichiesta.setOnAction(event -> {
                try {
                    GestoreAccettazioneEsiti.clickAccettaAziendaPartner(azienda);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }); // Passa la diocesi al metodo clickAccetta()
            buttonAccettaRichiesta.setStyle("-fx-background-color: #ffffff;");
            buttonAccettaRichiesta.setText("ACCETTA");

            Label labelDiocesiRichiesta = new Label();
            labelDiocesiRichiesta.setId("labelDiocesiRichiesta");
            labelDiocesiRichiesta.setLayoutX(180.0);
            labelDiocesiRichiesta.setLayoutY(layoutY + 5.0); // Sposta l'etichetta leggermente più in basso rispetto al pulsante
            labelDiocesiRichiesta.setText(responsabile.getEmail()); // Imposta il testo dell'etichetta con il nome della diocesi

            ScrollPane scrollPane = new ScrollPane();
            Pane paneRoot = (Pane) root;
            // Imposta il margine per la ScrollPane
            Insets margin = new Insets(20.0); // Imposta il margine a 20 pixel su tutti i lati
            scrollPane.setPadding(margin);

            scrollPane.setFitToWidth(true);
            paneRoot.getChildren().addAll(buttonAccettaRichiesta, labelDiocesiRichiesta);
            scrollPane.setContent(paneRoot);
            layoutY += buttonAccettaRichiesta.getHeight() + spacing;
        }
    }

    public static void clickAccettaDiocesi(Diocesi diocesi) throws Exception {
        DBMS.accettaRichiesta(diocesi.getId(), "diocesi");
        System.out.println("Diocesi " + diocesi.getId() + " accettata.");
    }

    public static void clickAccettaAziendaPartner(AziendaPartner aziendaPartner) throws Exception {
        DBMS.accettaRichiesta(aziendaPartner.getId(), "azienda_partner");
        System.out.println("Azienda " + aziendaPartner.getId() + " accettata.");
    }
}
