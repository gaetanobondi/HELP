package it.help.help.autenticazione.controll;

import it.help.help.autenticazione.boundary.*;
import it.help.help.azienda_partner.boundary.*;
import it.help.help.entity.*;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;

import it.help.help.utils.DBMS;

import javafx.scene.control.Alert.AlertType;

public class GestoreAutenticazione {

    public Button buttonSignIn;
    public Button buttonLogin;
    public Label labelBenvenuto;
    public PasswordField fieldPassword;
    public TextField fieldEmail;
    public Button buttonRecuperaPassword; //schermata login
    public Button buttonAccedi;

    public Button buttonIndietro;
    public RadioButton radioButtonDiocesi;
    public RadioButton radioButtonAziendaPartner;
    public PasswordField fieldRipetiPassword;
    public Button buttonRegistrati;
    public PasswordField fieldNuovaPassword;

    public Button buttonModificaDati;
    public Label labelEmail;
    public Label labelPassword;
    public Label labelNome;
    public Label labelCognome;
    public Label labelViveriProdotto;
    public Button buttonHome;


    @FXML
    private AnchorPane contentPane;

    public void clickIndietro(ActionEvent actionEvent) {
        System.out.println("PULSANTE PREMUTO");
        Stage currentWindow = (Stage) buttonIndietro.getScene().getWindow();
        Stage previousWindow = MainUtils.boundaryStack.get(MainUtils.boundaryStack.size() - 1);
        currentWindow.setScene(previousWindow.getScene());
    }

    public void clickHome(ActionEvent actionEvent) throws Exception {
        switch (MainUtils.responsabileLoggato.getType()) {
            case 0:
                // HELP
                // nomeSchermata = "/it/help/help/SchermataHomeResponsabileHelp.fxml";
                SchermataHomeResponsabileHelp l = new SchermataHomeResponsabileHelp();
                Stage window = (Stage) buttonHome.getScene().getWindow();
                l.start(window);
                break;
            case 1:
                // DIOCESI
                // nomeSchermata = "/it/help/help/SchermataHomeResponsabileDiocesi.fxml";
                SchermataHomeResponsabileDiocesi l1 = new SchermataHomeResponsabileDiocesi();
                Stage window1 = (Stage) buttonHome.getScene().getWindow();
                l1.start(window1);
                break;
            case 2:
                // POLO
                // nomeSchermata = "/it/help/help/SchermataHomeResponsabilePolo.fxml";
                SchermataHomeResponsabilePolo l2 = new SchermataHomeResponsabilePolo();
                Stage window2 = (Stage) buttonHome.getScene().getWindow();
                l2.start(window2);
                break;
            case 3:
                // AZIENDA PARTNER
                // nomeSchermata = "/it/help/help/SchermataHomeResponsabileAziendaPartner.fxml";
                SchermataHomeResponsabileAziendaPartner l3 = new SchermataHomeResponsabileAziendaPartner();
                Stage window3 = (Stage) buttonHome.getScene().getWindow();
                l3.start(window3);
                break;
        }
    }


    //per la SCHERMATA INIZIALE
    public void clickSignIn(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataSignin.fxml"));
        Stage window = (Stage) buttonSignIn.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Sign-In");
    }

    public void clickLogin(ActionEvent actionEvent) throws Exception {
        SchermataLogin l = new SchermataLogin();
        Stage window = (Stage) buttonLogin.getScene().getWindow();
        MainUtils.boundaryStack.add(window);
        l.start(window);
        // Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataLogin.fxml"));
        // Stage window = (Stage) buttonLogin.getScene().getWindow();
        // window.setScene(new Scene(root));
        // window.setTitle("Schermata Login");
    }





    //per la SCHERMATA LOGIN
    public void clickAccedi(ActionEvent actionEvent) throws Exception {
        String email = fieldEmail.getText();
        String password = fieldPassword.getText();
        boolean showErrorAlert = false;
        String error = "";

        if(!email.isEmpty() && !password.isEmpty()) {
            String encryptPassword = MainUtils.encryptPassword(password);
            MainUtils.responsabileLoggato = DBMS.queryControllaCredenzialiResponsabile(email, encryptPassword);
            // MainUtils.responsabileLoggato = responsabile;
            if(MainUtils.responsabileLoggato != null) {
                String nomeSchermata = "";
                switch (MainUtils.responsabileLoggato.getType()) {
                    case 0:
                        // HELP
                        // Help help = DBMS.getHelp(MainUtils.responsabileLoggato.getId());
                        nomeSchermata = "/it/help/help/SchermataHomeResponsabileHelp.fxml";
                        // GestoreProfilo gestoreProfilo = new GestoreProfilo(responsabile, help);
                        break;
                    case 1:
                        // DIOCESI
                        // Diocesi diocesi = DBMS.getDiocesi(MainUtils.responsabileLoggato.getId());
                        if(DBMS.getStatoAccount("diocesi", MainUtils.responsabileLoggato.getIdLavoro())) {
                            nomeSchermata = "/it/help/help/SchermataHomeResponsabileDiocesi.fxml";
                        } else {
                            // account non ancora attivo
                            showErrorAlert = true;
                            error = "Il tuo account non è ancora attivo.";
                        }
                        break;
                    case 2:
                        // POLO
                        // Polo polo = DBMS.getPolo(MainUtils.responsabileLoggato.getId());
                        if(MainUtils.responsabileLoggato.getIdLavoro() == 1) {
                            // POLO SOSPESO
                            nomeSchermata = "/it/help/help/SchermataSospensionePolo.fxml";
                        } else {
                            nomeSchermata = "/it/help/help/SchermataHomeResponsabilePolo.fxml";
                        }
                        break;
                    case 3:
                        // AZIENDA PARTNER
                        // DBMS.getAziendaPartner(MainUtils.responsabileLoggato.getId());
                        if(DBMS.getStatoAccount("azienda", MainUtils.responsabileLoggato.getIdLavoro())) {
                            nomeSchermata = "/it/help/help/SchermataHomeResponsabileAziendaPartner.fxml";
                        } else {
                            // account non ancora attivo
                            showErrorAlert = true;
                            error = "Il tuo account non è ancora attivo.";
                        }
                        break;
                }
                if(!showErrorAlert) {
                    Parent root = FXMLLoader.load(getClass().getResource(nomeSchermata));
                    Stage window = (Stage) buttonAccedi.getScene().getWindow();
                    window.setScene(new Scene(root));
                    window.setTitle("Schermata Home del Responsabile");
                }
            } else {
                showErrorAlert = true;
                error = "Le credenziali non sono corrette";
            }
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi";
        }

        if(showErrorAlert) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText(error);
            alert.showAndWait();
        }
    }

    public void clickRecuperaPassword(ActionEvent actionEvent) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataRecuperoPassword.fxml"));
        Stage window = (Stage) buttonRecuperaPassword.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Recupero Password");
    }


    //per la schermata HOME RESPONSABILE POLO

    public Button buttonVisualizzaProfiloPolo;
    public Button buttonVisualizzaNucleoFamiliare;
    public Button buttonInserimentoNucleo;
    public Button buttonVisualizzaSchemaDiDistribuzionePolo;
    public Button buttonVisualizzaSchemaDiDistribuzioneFamiglie;
    public Button buttonSegnalazioneErrori;
    public Button buttonReport;
    public Button buttonSospendiPolo;
    public Button buttonAggiungiViveriMagazzino;


    public void clickVisualizzaProfiloPolo(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataProfiloPersonalePolo.fxml"));
        Stage window = (Stage) buttonVisualizzaProfiloPolo.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Profilo Personale Polo");

        // Recupera le label dal file FXML utilizzando gli ID specificati nel file FXML
        Label labelNome = (Label) root.lookup("#labelNome");
        Label labelNomeResponsabile = (Label) root.lookup("#labelNomeResponsabile");
        Label labelCognomeResponsabile = (Label) root.lookup("#labelCognomeResponsabile");
        Label labelEmail = (Label) root.lookup("#labelEmail");
        Label labelIndirizzo = (Label) root.lookup("#labelIndirizzo");
        Label labelCellulare = (Label) root.lookup("#labelCellulare");

        // Imposta il testo delle label utilizzando i valori delle variabili
        labelNome.setText(MainUtils.poloLoggato.getNome());
        labelNomeResponsabile.setText(MainUtils.poloLoggato.getNome_responsabile());
        labelCognomeResponsabile.setText(MainUtils.poloLoggato.getCognome_responsabile());
        labelEmail.setText(MainUtils.responsabileLoggato.getEmail());
        labelIndirizzo.setText(MainUtils.poloLoggato.getIndirizzo());
        labelCellulare.setText("" + MainUtils.poloLoggato.getCellulare());

    }


    public void clickVisualizzaNucleoFamiliare(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataSchemaVisualizzaListaNuclei.fxml"));
        Stage window = (Stage) buttonVisualizzaNucleoFamiliare.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Visualizza Lista Nuclei");
    }


    public void clickInserimentoNucleo(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataRegistrazioneNucleoFamiliare.fxml"));
        Stage window = (Stage) buttonInserimentoNucleo.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Registrazione Nucleo Familiare");
    }


    public void clickVisualizzaSchemaDiDistribuzionePolo(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataSchemaDiDistribuzioneDelPolo.fxml"));
        Stage window = (Stage) buttonVisualizzaSchemaDiDistribuzionePolo.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Schema Di Distribuzione del Polo");
    }


    public void clickVisualizzaSchemaDiDistribuzioneFamiglie(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataSchemaDiDistribuzioneDelNucleo.fxml"));
        Stage window = (Stage) buttonVisualizzaSchemaDiDistribuzioneFamiglie.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Schema Di Distribuzione Del Nucleo");
    }


    public void clickSegnalazioneErrori(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataSegnalazioneErrori.fxml"));
        Stage window = (Stage) buttonSegnalazioneErrori.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Segnalazione Errori");
    }


    public void clickReport(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataScaricamentoReport.fxml"));
        Stage window = (Stage) buttonReport.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Scaricamento Report");
    }


    public void clickSospendiPolo(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataSospensionePolo.fxml"));
        Stage window = (Stage) buttonSospendiPolo.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Sospensione Polo");
    }


    public void clickAggiungiViveriMagazzino(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataCaricamentoViveri.fxml"));
        Stage window = (Stage) buttonAggiungiViveriMagazzino.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Caricamento Viveri");
    }





    //per la SCHERMATA HOME RESPONSABILE HELP

    public Button buttonVisualizzaProfiloHelp;
    public Button buttonVisualizzaPrevisioneDistribuzione;
    public Button buttonRichiesteDiocesi;
    public Button buttonRichiesteAziendePartner;
    public Button buttonListaDonazioniRicevute;
    public Button buttonGestione;
    public Button buttonDonazioneAziendaPartner;

    public Button buttonVisualizzaReport;

    public void clickVisualizzaProfiloHelp(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/help/SchermataProfiloPersonaleHelp.fxml"));
        Stage window = (Stage) buttonVisualizzaProfiloHelp.getScene().getWindow();
        // salvo la scena corrente in modo da poter tornare indietro
        MainUtils.previousScene = window.getScene();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Profilo Personale Help");

        // Recupera le label dal file FXML utilizzando gli ID specificati nel file FXML
        Label labelEmail = (Label) root.lookup("#labelEmail");
        Label labelNome = (Label) root.lookup("#labelNome");
        Label labelCognome = (Label) root.lookup("#labelCognome");
        Label labelCellulare = (Label) root.lookup("#labelCellulare");
        Label labelIndirizzo = (Label) root.lookup("#labelIndirizzo");

        // Imposta il testo delle label utilizzando i valori delle variabili

        MainUtils.helpLoggato = DBMS.queryGetHelp(MainUtils.responsabileLoggato.getIdLavoro());

        labelEmail.setText(MainUtils.responsabileLoggato.getEmail());
        // labelPassword.setText("**********");
        labelNome.setText(MainUtils.responsabileLoggato.getNome());
        labelCognome.setText(MainUtils.responsabileLoggato.getCognome());
        if(MainUtils.helpLoggato.getCellulare() != 0) {
            labelCellulare.setText("" + MainUtils.helpLoggato.getCellulare());
        }
        labelIndirizzo.setText(MainUtils.helpLoggato.getIndirizzo());
    }
    public void clickVisualizzaPrevisioneDiDistribuzione(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataVisualizzazionePrevisioneDiDistribuzione.fxml"));
        Stage window = (Stage) buttonVisualizzaProfiloHelp.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Visualizzazione Previsione Di Distribuzione");
    }
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

    public void clickListaDonazioniRicevute(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataListaDonaizoni.fxml"));
        Stage window = (Stage) buttonListaDonazioniRicevute.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Lista Donazioni");
    }

    public void clickGestione(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataGestione.fxml"));
        Stage window = (Stage) buttonGestione.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Gestione");
    }

    public void clickDonazioneAziendaPartner(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataDonazioneAzienda.fxml"));
        Stage window = (Stage) buttonDonazioneAziendaPartner.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Donazione Azienda Partner");
    }





    //per la schermata HOME RESPONSABILE AZIENDA PARTNER

    public Button buttonVisualizzaProfiloAziendaPartner;
    public Button buttonVisualizzaDonazioniEffettuate;
    public Button buttonEffettuaDonazioneAdHoc;
    public Button buttonLogout;
    public Button buttonEffettuaDonazioneSpontanea;

    public void clickVisualizzaProfiloAziendaPartner(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/SchermataProfiloPersonale.fxml"));
        Stage window = (Stage) buttonVisualizzaProfiloAziendaPartner.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Profilo Personale Azienda Partner");

        // Recupera le label dal file FXML utilizzando gli ID specificati nel file FXML
        Label labelNome = (Label) root.lookup("#labelNome");
        // Label labelNomeResponsabile = (Label) root.lookup("#labelNomeResponsabile");
        // Label labelCognomeResponsabile = (Label) root.lookup("#labelCognomeResponsabile");
        Label labelEmail = (Label) root.lookup("#labelEmail");
        // Label labelIndirizzo = (Label) root.lookup("#labelIndirizzo");
        // Label labelCellulare = (Label) root.lookup("#labelCellulare");
        // Label labelViveriProdotto = (Label) root.lookup("#labelViveriProdotto");

        // Imposta il testo delle label utilizzando i valori delle variabili
        labelNome.setText(MainUtils.responsabileLoggato.getNome());
        // labelNomeResponsabile.setText(MainUtils.aziendaPartnerLoggata.getNomeResponsabile());
        // labelCognomeResponsabile.setText(MainUtils.aziendaPartnerLoggata.getCognomeResponsabile());
        labelEmail.setText(MainUtils.responsabileLoggato.getEmail());
        // labelIndirizzo.setText(MainUtils.aziendaPartnerLoggata.getIndirizzo());
        // labelViveriProdotto.setText(MainUtils.aziendaPartnerLoggata.getViveriProdotto());
        // if(MainUtils.aziendaPartnerLoggata.getCellulare() != 0) {
            // labelCellulare.setText("" + MainUtils.aziendaPartnerLoggata.getCellulare());
        // }
    }

    public void clickVisualizzaDonazioniEffettuate(ActionEvent actionEvent) throws Exception {
        SchermataVisualizzaDonazioniEffettuate l = new SchermataVisualizzaDonazioniEffettuate();
        Stage window = (Stage) buttonLogin.getScene().getWindow();
        // MainUtils.boundaryStack.add(window);
        l.start(window);
    }

    public void clickEffettuaDonazioneAdHoc(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataEffettuaDonazioneAdHoc.fxml"));
        Stage window = (Stage) buttonEffettuaDonazioneAdHoc.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Effettua Donazione Ad-Hoc");
    }

    public void clickLogout(ActionEvent actionEvent) throws Exception {
        SchermataLogin l = new SchermataLogin();
        Stage window = (Stage) buttonLogout.getScene().getWindow();
        l.start(window);
    }

    public void clickEffettuaDonazioneSpontanea(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataEffettuaDonazione.fxml"));
        Stage window = (Stage) buttonEffettuaDonazioneSpontanea.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Effettua Donazione");
    }





    //per la SCHERMATA HOME RESPONSABILE DIOCESI

    public Button buttonVisualizzaSchemaDiDistribuzioneDiocesi;
    public Button buttonVisualizzaListaPoli;
    public Button buttonRegistrazionePolo;
    public Button buttonVisualizzaCarichiInviati;
    public Button buttonVisualizzaProfiloDiocesi;

    public void clickVisualizzaProfiloDiocesi(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/SchermataProfiloPersonale.fxml"));
        Stage window = (Stage) buttonVisualizzaProfiloDiocesi.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Profilo Personale Diocesi");

        // Recupera le label dal file FXML utilizzando gli ID specificati nel file FXML
        Label labelNome = (Label) root.lookup("#labelNome");
        // Label labelNomeResponsabile = (Label) root.lookup("#labelNomeResponsabile");
        // Label labelCognomeResponsabile = (Label) root.lookup("#labelCognomeResponsabile");
        Label labelEmail = (Label) root.lookup("#labelEmail");
        // Label labelIndirizzo = (Label) root.lookup("#labelIndirizzo");
        // Label labelCellulare = (Label) root.lookup("#labelCellulare");
        // Label labelNomePrete = (Label) root.lookup("#labelNomePrete");

        // Diocesi diocesi = DBMS.getDiocesi(MainUtils.responsabileLoggato.getId());

        // Imposta il testo delle label utilizzando i valori delle variabili
        labelNome.setText(MainUtils.responsabileLoggato.getNome());
        // labelNomeResponsabile.setText(diocesi.getNome_responsabile());
        // labelCognomeResponsabile.setText(diocesi.getCognome_responsabile());
        labelEmail.setText(MainUtils.responsabileLoggato.getEmail());
        // labelIndirizzo.setText(diocesi.getIndirizzo());
        // if(diocesi.getCellulare() != 0) {
            // labelCellulare.setText("" + diocesi.getCellulare());
        // }
        // labelNomePrete.setText(diocesi.getPrete());
    }

    public void clickVisualizzaListaPoli(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataVisualizzazioneListaPoli.fxml"));
        Stage window = (Stage) buttonVisualizzaListaPoli.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Visualizzazione Lista Poli");
    }

    public void clickRegistrazionePolo(ActionEvent actionEvent) throws Exception {
        SchermataRegistrazionePolo l = new SchermataRegistrazionePolo();
        Stage window = (Stage) buttonRegistrazionePolo.getScene().getWindow();
        l.start(window);
    }

    public void clickVisualizzaCarichiInviati(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataVisualizzazioneCarichi.fxml"));
        Stage window = (Stage) buttonVisualizzaCarichiInviati.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Visualizzazione Carichi");
    }

    public void clickVisualizzaSchemaDiDistribuzioneDiocesi(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataSchemaDiDistribuzioneDellaDiocesi.fxml"));
        Stage window = (Stage) buttonVisualizzaSchemaDiDistribuzioneDiocesi.getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Schermata Schema Di Distribuzione Della Diocesi");
    }
}
