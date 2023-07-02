package it.help.help.polo.boundary;

import it.help.help.polo.controll.GestoreNucleo;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataInserimentoNucleo {
    public Button buttonHome;
    public Button buttonRegistraNucleo;
    public TextField fieldCognome;
    public TextField fieldReddito;
    public GestoreNucleo gestoreNucleo;
    public SchermataInserimentoNucleo(GestoreNucleo gestoreNucleo) {
        this.gestoreNucleo = gestoreNucleo;
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }

    public void clickRegistraNucleo(ActionEvent actionEvent) throws Exception {
        String cognome = fieldCognome.getText() != null ? fieldCognome.getText() : "";
        String reddito = fieldReddito.getText() != null ? fieldReddito.getText() : "";
        gestoreNucleo.registraNucleo((Stage) buttonRegistraNucleo.getScene().getWindow(), cognome, reddito);
    }
}
