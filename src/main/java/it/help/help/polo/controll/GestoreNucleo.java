package it.help.help.polo.controll;

import it.help.help.polo.boundary.*;
import it.help.help.utils.DBMS;
import it.help.help.utils.MainUtils;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import it.help.help.entity.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

public class GestoreNucleo {
    public TextField fieldCognome;
    public TextField fieldNome;
    public TextField fieldCellulare;
    public TextField fieldIndirizzo;

    public void schermataSchemaDistribuzioneNucleo(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata visualizza schema di distribuzione", "/it/help/help/polo/SchermataSchemaDistribuzioneNucleo.fxml", stage, c -> {
            return new SchermataSchemaDistribuzioneNucleo();
        });

        SchemaDistribuzione[] schemiDistribuzione = DBMS.queryGetSchemiDistribuzione(2, MainUtils.nucleo.getId());

        Parent root = stage.getScene().getRoot();
        int id_nucleo = 0;

        double layoutY = 140;
        double spacing = 40.0; // Spazio verticale tra i componenti
        double layoutX = 20.0; // Spazio laterale

        // Ottieni il nome del mese corrente in italiano
        LocalDate currentDate = LocalDate.now();
        String nomeMeseCorrente = currentDate.format(DateTimeFormatter.ofPattern("MMMM", new Locale("it")));

        // Aggiungi il titolo
        Label titoloLabel = new Label("Schema di distribuzione di " + nomeMeseCorrente);
        titoloLabel.setLayoutX(layoutX);
        titoloLabel.setLayoutY(80);
        titoloLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        ((Pane) root).getChildren().add(titoloLabel);
        boolean schemaRitirato = false;

        for (SchemaDistribuzione schemaDistribuzione : schemiDistribuzione) {
            id_nucleo = schemaDistribuzione.getIdType();
            schemaRitirato = schemaDistribuzione.getStatoRitiro();
            Prodotto prodotto = DBMS.queryGetProdotto(schemaDistribuzione.getCodiceProdotto());

            Label label = new Label(schemaDistribuzione.getQuantità() + " di " + prodotto.getTipo());
            label.setLayoutX(layoutX);
            label.setLayoutY(layoutY);
            layoutY += spacing;

            ((Pane) root).getChildren().add(label);
        }

        if(schemaRitirato) {
            Label label = new Label("Carico correttamente ritirato");
            label.setLayoutX(layoutX);
            label.setLayoutY(layoutY);
            ((Pane) root).getChildren().add(label);
        } else {
            // Aggiungi il bottone "Ritirato"
            Button ritiratoButton = new Button("Ritirato");
            ritiratoButton.setLayoutX(layoutX);
            ritiratoButton.setLayoutY(layoutY);
            int finalId_nucleo = id_nucleo;
            ritiratoButton.setOnAction(event -> {
                try {
                    ritiraSchemaNucleo(stage, finalId_nucleo);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            ((Pane) root).getChildren().add(ritiratoButton);
        }
    }
    public void ritiraSchemaNucleo(Stage stage, int id_nucleo) throws Exception {
        DBMS.queryRitiraSchemaDistribuzione(2, id_nucleo);
        schermataSchemaDistribuzioneNucleo(stage);
    }

    public void registraNucleo(Stage stage, String cognome, String reddito) throws Exception {
        boolean showErrorAlert = false;
        String error = "";

        if(!cognome.isEmpty() && !reddito.isEmpty()) {
            if(MainUtils.contieneSoloLettere(cognome) && MainUtils.contieneSoloNumeri(reddito)) {
                DBMS.queryRegistraNucleo(MainUtils.responsabileLoggato.getIdLavoro(), cognome, Integer.parseInt(reddito));
                MainUtils.tornaAllaHome(stage);
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
        }
    }

    public static void eliminaNucleo(int id_nucleo) throws Exception {
        DBMS.queryEliminaNucleo(id_nucleo);
    }
    public void schermataModificaNucleo(Stage stage, Nucleo nucleo) {
        SchermataModificaNucleo p = new SchermataModificaNucleo(this);
        MainUtils.cambiaInterfaccia("Schermata modifica nucleo", "/it/help/help/polo/SchermataModificaNucleo.fxml", stage, c -> {
            return p;
        });
        p.inizialize(nucleo.getId(), nucleo.getCognome(), nucleo.getReddito());
    }
    public void modificaNucleo(Stage stage, int idNucleo, String cognome, String reddito) throws Exception {
        boolean showErrorAlert = false;
        String error = "";

        if(!cognome.isEmpty() && !reddito.isEmpty()) {
            // aggiorno la tabella azienda
            HashMap<String, Object> datiAggiornati = new HashMap<>();
            datiAggiornati.put("cognome", cognome);
            datiAggiornati.put("reddito", reddito);
            DBMS.queryModificaDati(idNucleo, "nucleo", datiAggiornati);
            MainUtils.tornaAllaHome(stage);
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi.";
        }

        if(showErrorAlert) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText(error);
            alert.showAndWait();
        }
    }
    public void schermataVisualizzaListaNucleiFamiliari(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata visualizza lista nuclei familiari", "/it/help/help/polo/SchermataVisualizzaListaNucleiFamiliari.fxml", stage, c -> {
            return new SchermataVisualizzaListaNucleiFamiliari();
        });

        Parent root = stage.getScene().getRoot();
        VBox lista = (VBox) stage.getScene().getRoot().lookup("#lista");

        Nucleo[] listaNuclei = DBMS.queryGetNuclei(MainUtils.responsabileLoggato.getIdLavoro());

        double layoutY = 100;
        double spacing = 40.0; // Spazio verticale tra i componenti

        for (Nucleo nucleo : listaNuclei) {
            Button buttonCognome = new Button();
            buttonCognome.setText(nucleo.getCognome());
            buttonCognome.setPrefHeight(19.0);
            buttonCognome.setPrefWidth(155.0);
            buttonCognome.setOnAction(event -> {
                try {
                    GestoreMembro gestoreMembro = new GestoreMembro();
                    gestoreMembro.schermataComponentiNucleo((Stage) buttonCognome.getScene().getWindow(), nucleo);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            Font font = new Font(15.0);
            buttonCognome.setFont(font);

            Button buttonModificaNucleo = new Button();
            buttonModificaNucleo.setText("MODIFICA");
            // buttonModificaNucleo.setMnemonicParsing(false);
            // buttonModificaNucleo.setStyle("-fx-background-color: FFFFFF;");
            buttonModificaNucleo.setOnAction(event -> {
                try {
                    schermataModificaNucleo((Stage) buttonModificaNucleo.getScene().getWindow(), nucleo);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            Button buttonEliminaNucleo = new Button();
            buttonEliminaNucleo.setText("ELIMINA");
            buttonEliminaNucleo.setOnAction(event -> {
                try {
                    GestoreNucleo.eliminaNucleo(nucleo.getId());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Pop-Up Conferma");
                    alert.setHeaderText("Nucleo " + nucleo.getCognome() + " eliminato");
                    alert.showAndWait();
                    schermataVisualizzaListaNucleiFamiliari(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            HBox buttonContainer = new HBox();
            buttonContainer.setAlignment(Pos.CENTER_LEFT);
            buttonContainer.setSpacing(10.0);
            buttonContainer.getChildren().addAll(buttonCognome, buttonModificaNucleo, buttonEliminaNucleo);

            lista.getChildren().add(buttonContainer);

            layoutY += buttonCognome.getHeight() + spacing;
        }

        if(listaNuclei.length == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText("Nessun nucleo presente");
            alert.showAndWait();
            MainUtils.tornaAllaHome(stage);
        }
    }

    public void schermataInserimentoNucleo(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata inserimento nucleo", "/it/help/help/polo/SchermataInserimentoNucleo.fxml", stage, c -> {
            return new SchermataInserimentoNucleo(this);
        });
    }
}
