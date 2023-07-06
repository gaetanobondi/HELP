package it.help.help.autenticazione.boundary;

import it.help.help.autenticazione.controll.GestoreAutenticazione;
import it.help.help.polo.controll.GestorePolo;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class SchermataSospensionePolo {

    public Button buttonConfermaSospensione;
    public Button buttonHome;
    public GestoreAutenticazione gestoreAutenticazione;
    public SchermataSospensionePolo(GestoreAutenticazione gestoreAutenticazione) {
        this.gestoreAutenticazione = gestoreAutenticazione;
    }

    public void clickConfermaSospensione(ActionEvent actionEvent) throws Exception {
        gestoreAutenticazione.confermaSospensionePolo((Stage) buttonConfermaSospensione.getScene().getWindow());
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }
}
