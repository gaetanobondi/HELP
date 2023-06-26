package it.help.help.polo.controll;

import it.help.help.autenticazione.boundary.SchermataLogin;
import it.help.help.autenticazione.controll.GestoreAutenticazione;
import it.help.help.utils.DBMS;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GestoreSospensionePolo {
    public Button ButtonConfermaSospensione;

    public void clickConfermaSospensione(ActionEvent actionEvent) throws Exception {
        DBMS.querySospendiPolo(MainUtils.responsabileLoggato.getIdLavoro());

        // effettuo il logout
        SchermataLogin l = new SchermataLogin();
        Stage window = (Stage) ButtonConfermaSospensione.getScene().getWindow();
        l.start(window);
    }
}
