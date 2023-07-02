package it.help.help.help.controll;

import it.help.help.autenticazione.boundary.*;
import it.help.help.diocesi.controll.GestoreDiocesi;
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

    public void tornaAHelp(Stage stage) throws IOException {
        MainUtils.responsabileLoggato = MainUtils.responsabileHelpLoggato;
        MainUtils.responsabileHelpLoggato = null;
        MainUtils.cambiaInterfaccia("Schermata home responsabile help","/it/help/help/help/SchermataHomeResponsabileHelp.fxml", stage, c -> {
            return new SchermataHomeResponsabileHelp();
        });
    }

    public void amministraDiocesi(Stage stage, int id_diocesi) throws Exception {
        MainUtils.responsabileHelpLoggato = MainUtils.responsabileLoggato;
        MainUtils.responsabileLoggato = DBMS.getResponsabile(1, id_diocesi);

        SchermataHomeResponsabileDiocesi p = new SchermataHomeResponsabileDiocesi();
        MainUtils.cambiaInterfaccia("Schermata home responsabile diocesi","/it/help/help/diocesi/SchermataHomeResponsabileDiocesi.fxml", stage, c -> {
            return p;
        });
        p.inizializeHelp(stage);
    }
    public void amministraAzienda(Stage stage, int id_azienda) throws Exception {
        MainUtils.responsabileHelpLoggato = MainUtils.responsabileLoggato;
        MainUtils.responsabileLoggato = DBMS.getResponsabile(3, id_azienda);

        MainUtils.cambiaInterfaccia("Schermata home responsabile azienda","/it/help/help/azienda_partner/SchermataHomeResponsabileAziendaPartner.fxml", stage, c -> {
            return new SchermataHomeResponsabileAziendaPartner();
        });

        Parent root = stage.getScene().getRoot();

        Button backButton = new Button("Torna a Help"); // Creazione del bottone "Torna a Help"
        backButton.setOnAction(e -> {
            // Azione da eseguire quando viene premuto il bottone "Torna a Help"
            try {
                tornaAHelp(stage);
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
                tornaAHelp(stage);
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


    public void schermataListaDiocesi(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata lista diocesi","/it/help/help/help/SchermataLista.fxml", stage, c -> {
            return new SchermataLista(this);
        });

        Diocesi[] listaDiocesi = DBMS.queryGetAllDiocesi();
        Parent root = stage.getScene().getRoot();

        lista = (VBox) stage.getScene().getRoot().lookup("#lista");
        VBox buttonContainer = new VBox(); // Contenitore per i bottoni verticali
        buttonContainer.setSpacing(10); // Spaziatura tra i bottoni

        for (Diocesi diocesi : listaDiocesi) {
            Button diocesiButton = new Button(diocesi.getNome()); // Creazione del pulsante con il nome del polo
            diocesiButton.setOnAction(e -> {
                // Azione da eseguire quando viene premuto il pulsante del polo
                try {
                    amministraDiocesi((Stage) diocesiButton.getScene().getWindow(), diocesi.getId());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            buttonContainer.getChildren().add(diocesiButton); // Aggiunta del pulsante al contenitore
        }

        // Rimuovi il contenuto esistente e imposta il contenitore dei bottoni come nuovo contenuto
        lista.getChildren().add(buttonContainer);
    }

    public void schermataListaAziende(Stage stage) throws Exception {
        MainUtils.cambiaInterfaccia("Schermata lista aziende","/it/help/help/help/SchermataLista.fxml", stage, c -> {
            return new SchermataLista(this);
        });

        AziendaPartner[] listaAziende = DBMS.queryGetAllAziende();
        Parent root = stage.getScene().getRoot();

        lista = (VBox) stage.getScene().getRoot().lookup("#lista");
        VBox buttonContainer = new VBox(); // Contenitore per i bottoni verticali
        buttonContainer.setSpacing(10); // Spaziatura tra i bottoni

        for (AziendaPartner aziendaPartner : listaAziende) {
            Button aziendaButton = new Button(aziendaPartner.getNome()); // Creazione del pulsante con il nome del polo
            aziendaButton.setOnAction(e -> {
                // Azione da eseguire quando viene premuto il pulsante del polo
                try {
                    amministraAzienda((Stage) aziendaButton.getScene().getWindow(), aziendaPartner.getId());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            buttonContainer.getChildren().add(aziendaButton); // Aggiunta del pulsante al contenitore
        }

        // Rimuovi il contenuto esistente e imposta il contenitore dei bottoni come nuovo contenuto
        lista.getChildren().add(buttonContainer);
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
