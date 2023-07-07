package it.help.help.help.controll;

import it.help.help.entity.*;
import it.help.help.help.boundary.SchermataVisualizzaRichieste;
import it.help.help.utils.MainUtils;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import it.help.help.utils.DBMS;

public class GestoreAccettazioneAccount {

    public void schermataRichiesteDiocesi(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata richieste diocesi","/it/help/help/help/SchermataVisualizzaRichieste.fxml", stage, c -> {
            return new SchermataVisualizzaRichieste(this);
        });
        Diocesi[] listaRichieste = DBMS.getRichiesteDiocesi();

        if(listaRichieste.length == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText("Nessuna richiesta presente");
            alert.showAndWait();
            MainUtils.tornaAllaHome(stage);
        }

        double layoutY = 100;
        double spacing = 40.0; // Spazio verticale tra i componenti
        Pane rootPane = (Pane) stage.getScene().getRoot();
        for (Diocesi diocesi : listaRichieste) {
            Responsabile responsabile = DBMS.getResponsabile(1, diocesi.getId());
            Button buttonAccettaRichiesta = new Button();
            buttonAccettaRichiesta.setId("buttonAccettaRichiesta");
            buttonAccettaRichiesta.setLayoutX(350.0);
            buttonAccettaRichiesta.setLayoutY(layoutY);
            buttonAccettaRichiesta.setMnemonicParsing(false);
            buttonAccettaRichiesta.setOnAction(event -> {
                try {
                    DBMS.queryAccettaRichiesta(diocesi.getId(), "diocesi");
                    System.out.println("Diocesi " + diocesi.getId() + " accettata.");
                    schermataRichiesteDiocesi(stage);
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

    public void schermataRichiesteAziende(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata richieste aziende partner","/it/help/help/help/SchermataVisualizzaRichieste.fxml", stage, c -> {
            return new SchermataVisualizzaRichieste(this);
        });
        AziendaPartner[] listaRichieste = DBMS.queryGetRichiesteAziendePartner();

        if(listaRichieste.length == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText("Nessuna richiesta presente");
            alert.showAndWait();
            MainUtils.tornaAllaHome(stage);
        }

        double layoutY = 100;
        double spacing = 40.0; // Spazio verticale tra i componenti

        Pane rootPane = (Pane) stage.getScene().getRoot(); // Ottieni il nodo radice esistente

        for (AziendaPartner azienda : listaRichieste) {
            System.out.println(azienda.getId());
            Responsabile responsabile = DBMS.getResponsabile(3, MainUtils.responsabileLoggato.getIdLavoro());
            Button buttonAccettaRichiesta = new Button();
            buttonAccettaRichiesta.setId("buttonAccettaRichiesta");
            buttonAccettaRichiesta.setLayoutX(350.0);
            buttonAccettaRichiesta.setLayoutY(layoutY);
            buttonAccettaRichiesta.setMnemonicParsing(false);
            buttonAccettaRichiesta.setOnAction(event -> {
                try {
                    DBMS.queryAccettaRichiesta(azienda.getId(), "azienda");
                    System.out.println("Azienda " + azienda.getId() + " accettata.");
                    schermataRichiesteAziende(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }); // Passa la diocesi al metodo clickAccetta()
            buttonAccettaRichiesta.setStyle("-fx-background-color: #ffffff;");
            buttonAccettaRichiesta.setText("ACCETTA");

            Label labelDiocesiRichiesta = new Label();
            labelDiocesiRichiesta.setId("labelDiocesiRichiesta");
            labelDiocesiRichiesta.setLayoutX(200.0);
            labelDiocesiRichiesta.setLayoutY(layoutY + 5.0); // Sposta l'etichetta leggermente più in basso rispetto al pulsante
            labelDiocesiRichiesta.setText(responsabile.getEmail()); // Imposta il testo dell'etichetta con il nome della diocesi

            rootPane.getChildren().addAll(buttonAccettaRichiesta, labelDiocesiRichiesta); // Aggiungi i componenti al nodo radice esistente

            layoutY += buttonAccettaRichiesta.getHeight() + spacing;
        }
    }
}
