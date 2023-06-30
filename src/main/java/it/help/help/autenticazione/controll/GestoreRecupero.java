package it.help.help.autenticazione.controll;

import it.help.help.autenticazione.boundary.SchermataCambioPassword;
import it.help.help.autenticazione.boundary.SchermataLogin;
import it.help.help.utils.DBMS;
import it.help.help.utils.EmailSender;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Random;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class GestoreRecupero {

    public Button buttonIndietro;
    public TextField fieldEmail;
    public Button buttonRecupera;
    public PasswordField fieldNuovaPassword;
    public PasswordField filedRipetiPassword;
    public Button buttonSalvaNuovaPassword;

    public void clickIndietro(ActionEvent actionEvent) {
        System.out.println("ciao Recupero Password");
    }

    public void clickSalvaPassword(ActionEvent actionEvent) throws Exception {
        String email = fieldEmail.getText();
        String password = fieldNuovaPassword.getText();
        String repeat_password = filedRipetiPassword.getText();
        boolean showErrorAlert = false;
        String error = "";

        if(!password.isEmpty() && !repeat_password.isEmpty()) {
            if(MainUtils.isValidPassword(password)) {
                String encryptPassword = MainUtils.encryptPassword(password);
                DBMS.queryModificaPassword(email, encryptPassword);
                SchermataLogin l = new SchermataLogin();
                Stage window = (Stage) buttonSalvaNuovaPassword.getScene().getWindow();
                l.start(window);
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

    public void clickRecupera(ActionEvent actionEvent) throws Exception {
            String email = fieldEmail.getText();
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
                        EmailSender.sendEmail(email, Integer.parseInt(code));

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
                        submitButton.setOnAction(e -> {
                            String codeInserito = textField.getText();
                            // Esegui le operazioni necessarie con il codice inserito

                            if (!codeInserito.isEmpty() && code.equals(codeInserito)) {   //se il codice inserito coincide
                                SchermataCambioPassword l = new SchermataCambioPassword();
                                Stage window = (Stage) buttonRecupera.getScene().getWindow();
                                try {
                                    l.start(window);
                                    TextField fieldEmail = (TextField) window.getScene().lookup("#fieldEmail");
                                    fieldEmail.setText(email);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }

                                // Chiudi il popup
                                popupStage.close();
                            } else {  // Pop-Up errore codice
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Pop-Up Errore");
                                alert.setHeaderText("Codice errato");
                                alert.showAndWait();
                            }
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
}
