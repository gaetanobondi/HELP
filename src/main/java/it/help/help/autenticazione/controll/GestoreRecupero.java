package it.help.help.autenticazione.controll;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.Random;

public class GestoreRecupero {

    public Button buttonIndietro;
    public TextField fieldEmail;
    public Button buttonRecupera;

    public void clickIndietro(ActionEvent actionEvent) {
        System.out.println("ciao Recupero Password");
    }


    public boolean isValidEmail(String email) {
        // Definisci la regex per il formato dell'email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // Verifica se la stringa email corrisponde alla regex
        return email.matches(emailRegex);
    }


    public void clickRecupera(ActionEvent actionEvent) throws Exception {
            String email = fieldEmail.getText();
            Boolean showErrorAlert = false;
            String error = "";
            Random random = new Random();
            if(!email.isEmpty()) {
                if (isValidEmail(email)) { // se l'email risulta valida
                    if(true){ //se l'email risulta nel DBMS

                        int n1 = random.nextInt(10);
                        int n2 = random.nextInt(10);
                        int n3 = random.nextInt(10);
                        int n4 = random.nextInt(10);

                        String codice = n1 + "" + n2 + "" + n3 + "" + n4;
                        System.out.println(codice);

                        if (true) {   //se il codice inserito coincide

                        } else {  // Pop-Up errore codice
                            showErrorAlert = true;
                            error = "Codice errato";
                        }

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
