package it.help.help.autenticazione.controll;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import it.help.help.autenticazione.boundary.HelloApplication;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GestoreAutenticazione {

    public Button buttonSignIn;
    public Button buttonLogin;
    public Label labelBenvenuto;
    public PasswordField fieldPassword;
    public TextField fieldEmail;
    public Button buttonRecuperaPassword;
    public Button buttonAccedi;
    public Button buttonIndietro;

    public void clickSignIn(ActionEvent actionEvent){
        // Apri la schermata iniziale al click del pulsante "Sign In"
        HelloApplication seconda = new HelloApplication();
        Stage stage = new Stage();
        try {
            seconda.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private AnchorPane contentPane;
    public void clickLogin(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataLogin.fxml"));
        Stage window = (Stage) buttonLogin.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickIndietro(ActionEvent actionEvent) {

    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void fillInPassword(ActionEvent actionEvent) {
    }

    public void fillInEmail(ActionEvent actionEvent) {
    }

    public void clickRecuperaPassword(ActionEvent actionEvent) {
    }

    public void clickAccedi(ActionEvent actionEvent) {
        String email = fieldEmail.getText();
        String password = fieldPassword.getText();

        if(esistenzaEmail(email)) {
            System.out.println("Email già esistente");
        } else {
            // controllo la password
            if(validatePassword(password)) {
                System.out.println("PROCEDO");
            } else {
                System.out.println("Password troppo debole");
                // Creazione di un oggetto Alert di tipo Avviso
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Errore");
                alert.setHeaderText("Password troppo debole");
                // alert.setContentText("Messaggio di avviso da visualizzare.");

                // Mostra il popup e attende la chiusura
                alert.showAndWait();
            }
        }
    }

    public static boolean validatePassword(String password) {
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

    private boolean esistenzaEmail(String email) {
        if(email.equals("mia@email.com")) {
            return true;
        }
        return false;
    }





    //connessione con il DBMS
   /* @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        String url = "jdbc:mysql://localhost:3306/help";
        String username = "root";
        String password = "";
        try {
            System.out.println("Provo a connettermi al db");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from responsabile where id=2");
            System.out.println(resultSet);
            if(resultSet.next()) {
                System.out.println("Ottengo i dati dal db:");
                while(resultSet.next()) {
                    System.out.println(resultSet.getInt(1)+ " " + resultSet.getString(2) + " " + resultSet.getString(3));
                }
            } else {
                System.out.println("Nessun dato presente.");
            }

            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }*/


}
