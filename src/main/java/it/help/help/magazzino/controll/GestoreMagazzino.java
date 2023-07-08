package it.help.help.magazzino.controll;

import it.help.help.entity.Magazzino;
import it.help.help.magazzino.boundary.SchermataCaricamentoViveri;
import it.help.help.utils.DBMS;
import it.help.help.utils.MainUtils;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.HashMap;
import it.help.help.entity.*;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class GestoreMagazzino {

    public void aggiungiViveriMagazzino(Stage stage, int type) throws Exception {
        SchermataCaricamentoViveri p = new SchermataCaricamentoViveri(this, type);
        MainUtils.cambiaInterfaccia("Schermata aggiungi viveri magazzino","/it/help/help/help/SchermataCaricamentoViveri.fxml", stage, c -> {
            return p;
        });

        Prodotto[] listaProdotti = DBMS.queryGetProdotti();
        Parent root = stage.getScene().getRoot();
        TextField fieldMenuSelected = (TextField) root.lookup("#fieldMenuSelected");
        MenuButton selectAlimenti = (MenuButton) root.lookup("#selectAlimenti");
        CheckBox checkBoxSenzaGlutine = (CheckBox) root.lookup("#checkBoxSenzaGlutine");
        CheckBox checkBoxSenzaLattosio = (CheckBox) root.lookup("#checkBoxSenzaLattosio");
        CheckBox checkBoxSenzaZuccheri = (CheckBox) root.lookup("#checkBoxSenzaZuccheri");

        for (Prodotto prodotto : listaProdotti) {
            MenuItem menuItem = new MenuItem(prodotto.getTipo());
            menuItem.setUserData(prodotto.getCodice());
            menuItem.setOnAction(event -> {
                String selectedProductName = ((MenuItem) event.getSource()).getText();
                selectAlimenti.setText(selectedProductName);
                fieldMenuSelected.setText("" + prodotto.getCodice());

                if(prodotto.getSenzaGlutine()) {
                    checkBoxSenzaGlutine.setSelected(true);
                } else {
                    checkBoxSenzaGlutine.setSelected(false);
                }

                if(prodotto.getSenzaLattosio()) {
                    checkBoxSenzaLattosio.setSelected(true);
                } else {
                    checkBoxSenzaLattosio.setSelected(false);
                }

                if(prodotto.getSenzaZucchero()) {
                    checkBoxSenzaZuccheri.setSelected(true);
                } else {
                    checkBoxSenzaZuccheri.setSelected(false);
                }
            });

            selectAlimenti.getItems().add(menuItem);
        }
    }

    public void caricaViveri(Stage stage, int type, String codice_prodotto, String quantità, LocalDate data_scadenza) throws Exception {
        boolean showErrorAlert = false;
        String error = "";

        if(!codice_prodotto.isEmpty() && !quantità.isEmpty() && data_scadenza != null) {
            if(MainUtils.contieneSoloNumeri(codice_prodotto) && MainUtils.contieneSoloNumeri(quantità)) {
                // Conversione da LocalDate a java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(data_scadenza);
                // ottengo tutti i magazzini disponibili
                Magazzino[] listaMagazzini = DBMS.queryGetMagazzini(type, MainUtils.responsabileLoggato.getIdLavoro());
                boolean prodottoAggiunto = false;
                for(Magazzino magazzino : listaMagazzini) {
                    int nuova_capienza = magazzino.getCapienzaAttuale() + Integer.parseInt(quantità);
                    if(nuova_capienza <= magazzino.getCapienzaMax()) {
                        // c'è spazio per aggiungere il prodotto
                        DBMS.queryCaricaViveri(Integer.parseInt(codice_prodotto), magazzino.getId(), Integer.parseInt(quantità), sqlDate);

                        // aggiorno la tabella magazzino
                        HashMap<String, Object> datiAggiornati = new HashMap<>();
                        datiAggiornati.put("capienza_attuale", nuova_capienza);
                        DBMS.queryModificaDati(magazzino.getId(), "magazzino", datiAggiornati);

                        prodottoAggiunto = true;
                    }
                }

                if(!prodottoAggiunto) {
                    showErrorAlert = true;
                    error = "Spazio nei magazzini insufficiente.";
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
}
