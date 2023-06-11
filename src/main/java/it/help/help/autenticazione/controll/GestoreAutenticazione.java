package it.help.help.autenticazione.controll;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GestoreAutenticazione {

    public Button buttonSignIn;
    public Button buttonLogin;
    public Label labelBenvenuto;

    public void clickSignIn(ActionEvent actionEvent){

    }

    public void clickLogin(ActionEvent actionEvent){

    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
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
