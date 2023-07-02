package it.help.help.utils;
import it.help.help.Main;
import it.help.help.autenticazione.boundary.*;
import it.help.help.entity.*;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javafx.util.Callback;


public class MainUtils {
    public static Scene previousScene;
    public static Responsabile responsabileLoggato;
    public static Responsabile responsabileHelpLoggato;
    public static Polo poloLoggato;
    public static AziendaPartner aziendaPartnerLoggata;
    public static Diocesi diocesiLoggata;
    public static Help helpLoggato;
    public static Nucleo nucleo;
    public static String codice_fiscale;
    public static List<Stage> boundaryStack = new ArrayList<>(); // Inizializza la lista delle boundary precedenti
    public Button buttonIndietro;

    public static Object cambiaInterfaccia(String title, String interfaccia, Stage stage, Callback c) {
        stage.setTitle(title);
        FXMLLoader loader = creaLoader(interfaccia);
        loader.setControllerFactory(c);
        creaInterfaccia(loader, 600, 400, stage);
        return loader.getController();
    }

    public static FXMLLoader creaLoader(String path) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
        return loader;
    }

    private static void creaInterfaccia(FXMLLoader loader, int w, int h, Stage stage) {
        if (Main.mainStage != null) {
            try {
                stage.initOwner(Main.mainStage);
                stage.initModality(Modality.APPLICATION_MODAL);
            } catch (Exception e) {
            }
        }
        stage.setResizable(false);
        try {
            Scene s = new Scene(loader.load(), w, h);
            stage.setScene(s);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generatePDF(int type_interval, int interval) {
        try {
            // Creazione del documento PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            Segnalazione[] listaSegnalazioni = DBMS.queryGetSegnalazioni(type_interval, interval);
            // Scrittura dei dati nel PDF
            int row = 0;
            int yOffset = 0;

            PDType1Font fontBold = PDType1Font.HELVETICA_BOLD;
            PDType1Font fontRegular = PDType1Font.HELVETICA;

            // Stampa il titolo in grassetto
            contentStream.setFont(fontBold, 16);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 750);
            String monthName = "";
            if(type_interval == 1) {
                Month month = Month.of(interval);
                monthName = month.getDisplayName(TextStyle.FULL, Locale.ITALIAN);
            }
            contentStream.showText("Report di " + monthName);
            contentStream.endText();

            for (Segnalazione segnalazione : listaSegnalazioni) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700 - (row * 20) - yOffset);
                if(segnalazione.getTipoSoggetto() == 0) {
                    Diocesi diocesi = DBMS.getDiocesiById(segnalazione.getIdSoggetto());
                    contentStream.showText("Segnalazione riguardante la diocesi " + diocesi.getNome());
                } else {
                    Nucleo nucleo = DBMS.getNucleo(segnalazione.getIdSoggetto());
                    contentStream.showText("Segnalazione riguardante il nucleo " + nucleo.getCognome());
                }
                contentStream.endText();

                String campo1 = "" + segnalazione.getCodiceProdotto();
                String campo2 = "" + segnalazione.getQuantitàRicevuta();

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 680 - (row * 20) - yOffset);
                Prodotto prodotto = DBMS.queryGetProdotto(segnalazione.getCodiceProdotto());
                contentStream.showText("Ricevuto " + campo2 + " di " + prodotto.getTipo());
                contentStream.endText();

                row++;
                yOffset += 40; // Aggiungi uno spazio verticale tra i titoli
            }



            // Chiusura del documento PDF
            contentStream.close();
            String fileName = "file.pdf";

            // Ottieni il percorso della cartella di download predefinita in base al sistema operativo
            String home = System.getProperty("user.home");
            String os = System.getProperty("os.name").toLowerCase();

            String downloadPath;
            if (os.contains("win")) {
                downloadPath = Paths.get(home, "Downloads", fileName).toString();
            } else if (os.contains("mac")) {
                downloadPath = Paths.get(home, "Downloads", fileName).toString();
            } else {
                downloadPath = Paths.get(home, fileName).toString();
            }

            Path outputPath = Paths.get(downloadPath);

            // Salva il documento PDF nel percorso di download
            document.save(outputPath.toFile());
            document.close();

            System.out.println("PDF generato correttamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encryptPassword(String password) {
        try {
            // Crea un oggetto MessageDigest con l'algoritmo SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Calcola l'hash della password
            byte[] hash = md.digest(password.getBytes());

            // Converti l'array di byte in una stringa Base64 per salvarla nel database
            String encryptedPassword = Base64.getEncoder().encodeToString(hash);

            return encryptedPassword;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void tornaAllaHome(Stage stage) throws IOException {
        String nomeSchermata;
        switch (MainUtils.responsabileLoggato.getType()) {
            case 0:
                // HELP
                nomeSchermata = "/it/help/help/help/SchermataHomeResponsabileHelp.fxml";
                MainUtils.cambiaInterfaccia("Schermata responsabile help", nomeSchermata, stage, c -> {
                    return new SchermataHomeResponsabileHelp();
                });
                break;
            case 1:
                // DIOCESI
                nomeSchermata = "/it/help/help/diocesi/SchermataHomeResponsabileDiocesi.fxml";
                SchermataHomeResponsabileDiocesi homeDiocesi = new SchermataHomeResponsabileDiocesi();
                MainUtils.cambiaInterfaccia("Schermata responsabile diocesi", nomeSchermata, stage, c -> {
                    return homeDiocesi;
                });
                homeDiocesi.inizializeHelp(stage);
                break;
            case 2:
                // POLO
                nomeSchermata = "/it/help/help/polo/SchermataHomeResponsabilePolo.fxml";
                SchermataHomeResponsabilePolo homePolo = new SchermataHomeResponsabilePolo();
                MainUtils.cambiaInterfaccia("Schermata responsabile polo", nomeSchermata, stage, c -> {
                    return homePolo;
                });
                homePolo.inizializeHelp(stage);
                break;
            case 3:
                // AZIENDA PARTNER
                nomeSchermata = "/it/help/help/azienda_partner/SchermataHomeResponsabileAziendaPartner.fxml";
                SchermataHomeResponsabileAziendaPartner homeAzienda = new SchermataHomeResponsabileAziendaPartner();
                MainUtils.cambiaInterfaccia("Schermata responsabile azienda", nomeSchermata, stage, c -> {
                    return homeAzienda;
                });
                homeAzienda.inizializeHelp(stage);
                break;
        }
    }

    public static boolean isValidPassword(String password) {
        // Controlla se la password ha almeno 8 caratteri
        if (password.length() < 8) {
            return false;
        }

        // Controlla se la password contiene almeno una lettera maiuscola
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Controlla se la password contiene almeno un numero
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // Controlla se la password contiene almeno un carattere speciale
        if (!password.matches(".*[!@#$%^&*()].*")) {
            return false;
        }

        // La password soddisfa tutti i requisiti
        return true;
    }

    public static boolean isValidEmail(String email) {
        // Definisci la regex per il formato dell'email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // Verifica se la stringa email corrisponde alla regex
        return email.matches(emailRegex);
    }

    public void clickIndietro(ActionEvent actionEvent) {
    }
}
