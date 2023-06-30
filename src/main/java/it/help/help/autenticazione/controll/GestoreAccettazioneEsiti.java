package it.help.help.autenticazione.controll;

import it.help.help.entity.*;
import it.help.help.help.boundary.SchermataVisualizzaRichieste;
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
        SchermataVisualizzaRichieste l = new SchermataVisualizzaRichieste();
        Stage window = (Stage) buttonRichiesteDiocesi.getScene().getWindow();
        l.start(window);
        listaRichiesteDiocesi(window);
    }

    public static void listaRichiesteDiocesi(Stage window) throws Exception {
        Diocesi[] listaRichieste = DBMS.getRichiesteDiocesi();

        if(listaRichieste.length == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText("Nessuna richiesta presente");
            alert.showAndWait();
            MainUtils.tornaAllaHome((Button)window.getScene().getRoot().lookup("#buttonHome"));
        }

        double layoutY = 100;
        double spacing = 40.0; // Spazio verticale tra i componenti
        Pane rootPane = (Pane) window.getScene().getRoot();
        for (Diocesi diocesi : listaRichieste) {
            Responsabile responsabile = DBMS.getResponsabile(1, diocesi.getId());
            Button buttonAccettaRichiesta = new Button();
            buttonAccettaRichiesta.setId("buttonAccettaRichiesta");
            buttonAccettaRichiesta.setLayoutX(300.0);
            buttonAccettaRichiesta.setLayoutY(layoutY);
            buttonAccettaRichiesta.setMnemonicParsing(false);
            buttonAccettaRichiesta.setOnAction(event -> {
                try {
                    GestoreAccettazioneEsiti.clickAccettaDiocesi(buttonAccettaRichiesta, diocesi);
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

            rootPane.getChildren().addAll(buttonAccettaRichiesta, labelDiocesiRichiesta);
            layoutY += buttonAccettaRichiesta.getHeight() + spacing;
        }
    }

    public void clickRichiesteAziendePartner(ActionEvent actionEvent) throws Exception {
        SchermataVisualizzaRichieste l = new SchermataVisualizzaRichieste();
        Stage window = (Stage) buttonRichiesteAziendePartner.getScene().getWindow();
        l.start(window);
        listaRichiesteAziende(window);
    }

    public static void listaRichiesteAziende(Stage window) throws Exception {
        AziendaPartner[] listaRichieste = DBMS.getRichiesteAziendePartner();

        double layoutY = 100;
        double spacing = 40.0; // Spazio verticale tra i componenti

        Pane rootPane = (Pane) window.getScene().getRoot(); // Ottieni il nodo radice esistente

        for (AziendaPartner azienda : listaRichieste) {
            System.out.println(azienda.getId());
            Responsabile responsabile = DBMS.getResponsabile(3, MainUtils.responsabileLoggato.getIdLavoro());
            Button buttonAccettaRichiesta = new Button();
            buttonAccettaRichiesta.setId("buttonAccettaRichiesta");
            buttonAccettaRichiesta.setLayoutX(300.0);
            buttonAccettaRichiesta.setLayoutY(layoutY);
            buttonAccettaRichiesta.setMnemonicParsing(false);
            buttonAccettaRichiesta.setOnAction(event -> {
                try {
                    GestoreAccettazioneEsiti.clickAccettaAziendaPartner(buttonAccettaRichiesta, azienda);
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

            rootPane.getChildren().addAll(buttonAccettaRichiesta, labelDiocesiRichiesta); // Aggiungi i componenti al nodo radice esistente

            layoutY += buttonAccettaRichiesta.getHeight() + spacing;
        }
    }



    public static void clickAccettaDiocesi(Button button, Diocesi diocesi) throws Exception {
        DBMS.accettaRichiesta(diocesi.getId(), "diocesi");
        System.out.println("Diocesi " + diocesi.getId() + " accettata.");
        SchermataVisualizzaRichieste l = new SchermataVisualizzaRichieste();
        Stage window = (Stage) button.getScene().getWindow();
        l.start(window);
        listaRichiesteDiocesi(window);
    }

    public static void clickAccettaAziendaPartner(Button button, AziendaPartner aziendaPartner) throws Exception {
        DBMS.accettaRichiesta(aziendaPartner.getId(), "azienda");
        System.out.println("Azienda " + aziendaPartner.getId() + " accettata.");
        SchermataVisualizzaRichieste l = new SchermataVisualizzaRichieste();
        Stage window = (Stage) button.getScene().getWindow();
        l.start(window);
        listaRichiesteAziende(window);
    }
}
