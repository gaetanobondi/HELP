package it.help.help.autenticazione.controll;

import it.help.help.autenticazione.boundary.SchermataCambioPassword;
import it.help.help.autenticazione.boundary.SchermataLogin;
import it.help.help.autenticazione.boundary.SchermataRecuperoPassword;
import it.help.help.utils.DBMS;
import it.help.help.utils.EmailSender;
import it.help.help.utils.MainUtils;
import javafx.scene.control.*;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class GestorePassword {
    public GestoreAutenticazione gestoreAutenticazione;

    public GestorePassword(GestoreAutenticazione gestoreAutenticazione) {
        this.gestoreAutenticazione = gestoreAutenticazione;
    }
    public void schermataLogin(Stage stage) {
        MainUtils.cambiaInterfaccia("Schermata login", "/it/help/help/SchermataLogin.fxml", stage, c -> {
            return new SchermataLogin(new GestoreAutenticazione());
        });
    }
    public void salvaPassword(Stage stage, String email, String password, String repeat_password) throws Exception {
        boolean showErrorAlert = false;
        String error = "";

        if(!password.isEmpty() && !repeat_password.isEmpty()) {
            if(MainUtils.isValidPassword(password)) {
                String encryptPassword = MainUtils.encryptPassword(password);
                DBMS.queryModificaPassword(email, encryptPassword);
                gestoreAutenticazione.schermataLogin(stage);
            } else {
                showErrorAlert = true;
                error = "La nuova password deve essere lunga almeno 8 caratteri e contenere almeno una lettera maiuscola e un carattere speciale";
            }
        } else if(!password.isEmpty() && repeat_password.isEmpty()) {
            showErrorAlert = true;
            error = "Inserisci la nuova password";
        } else if(password.isEmpty() && !repeat_password.isEmpty()) {
            showErrorAlert = true;
            error = "Ripeti la password password";
        } else {
            showErrorAlert = true;
            error = "Compila tutti i campi";
        }

        if(showErrorAlert) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText(error);
            alert.showAndWait();
        }
    }
    public void schermataRecuperoPassword(Stage stage) {
        MainUtils.cambiaInterfaccia("Schermata recupero password", "/it/help/help/SchermataRecuperoPassword.fxml", stage, c -> {
            return new SchermataRecuperoPassword(gestoreAutenticazione);
        });
    }
    public void recuperaPassword(Stage stage, String email) throws Exception {
            Boolean showErrorAlert = false;
            String error = "";
            Random random = new Random();
            if(!email.isEmpty()) {
                if (MainUtils.isValidEmail(email)) { // se l'email risulta valida
                    if(DBMS.queryControllaEsistenzaEmail(email)){ //se l'email risulta nel DBMS

                        int n1 = random.nextInt(10);
                        int n2 = random.nextInt(10);
                        int n3 = random.nextInt(10);
                        int n4 = random.nextInt(10);

                        String code = n1 + "" + n2 + "" + n3 + "" + n4;
                        System.out.println(code);

                        // invio l'email
                        EmailSender.sendEmail(email, code);

                        // Creazione del popup
                        Stage popupStage = new Stage();
                        popupStage.setTitle("Inserisci il codice");

                        // Creazione dei controlli per il popup
                        Label label = new Label("Inserisci il codice:");
                        TextField textField = new TextField();
                        Button submitButton = new Button("Invia");

                        // Configurazione del layout del popup
                        VBox vbox = new VBox(10);
                        vbox.setPadding(new Insets(10));
                        vbox.setAlignment(Pos.CENTER);
                        vbox.getChildren().addAll(label, textField, submitButton);

                        // Configurazione dell'azione del pulsante di invio
                        AtomicBoolean result = new AtomicBoolean(false);
                        submitButton.setOnAction(e -> {
                            String codeInserito = textField.getText();

                            // per rispettare il sequence, all'interno del metodo inserisci è presente il while
                            // per mostrare nuovamente il popup finché il codice inserito non è corretto
                            result.set(inserisci(code, codeInserito, popupStage, email, stage));
                        });


                        // Creazione della scena e impostazione della scena nel popup Stage
                        Scene scene = new Scene(vbox);
                        popupStage.setScene(scene);

                        // Visualizza il popup
                        popupStage.show();

                    } else { // Pop-Up errore email
                        showErrorAlert = true;
                        error = "Email non esistente";
                    }
                }
                else{
                    showErrorAlert = true;
                    error = "Email non è nel giusto formato";
                }
            }
            else {
                showErrorAlert = true;
                error = "Inserisci l'email";
            }

            if(showErrorAlert) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Pop-Up Errore");
                alert.setHeaderText(error);
                alert.showAndWait();
            }
    }

    public boolean inserisci(String code, String codeInserito, Stage popupStage, String email, Stage stage) {
        // con la variabile mostrato, mostro il pop-up di errore una sola volta, altrimenti
        // senza la variabile si continuava a mostrare il pop-up svariate volte
        boolean mostrato = false;
        // finché queste condizioni non si verificano continuo a mostrare il pop-up
        while (mostrato == false && (codeInserito.isEmpty() || !code.equals(codeInserito))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pop-Up Errore");
            alert.setHeaderText("Codice errato");
            alert.showAndWait();
            mostrato = true;
        }
        if(codeInserito.isEmpty() || Integer.parseInt(code) != Integer.parseInt(codeInserito)) {
            return false;
        } else {
            // se invece il codice è corretto mostro la schermata cambio password
            popupStage.close();
            MainUtils.cambiaInterfaccia("Schermata cambio password", "/it/help/help/SchermataCambioPassword.fxml", stage, c -> {
                return new SchermataCambioPassword(this, email);
            });
            return true;
        }
    }
}
