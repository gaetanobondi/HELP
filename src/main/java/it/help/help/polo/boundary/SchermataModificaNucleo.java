package it.help.help.polo.boundary;

import it.help.help.polo.controll.GestoreNucleo;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class SchermataModificaNucleo {
    public Button buttonHome;
    public Button buttonModificaNucleo;
    public TextField fieldCognome;
    public TextField fieldReddito;
    public GestoreNucleo gestoreNucleo;
    int idNucleo;
    public SchermataModificaNucleo(GestoreNucleo gestoreNucleo) {
        this.gestoreNucleo = gestoreNucleo;
    }
    public void inizialize(int idNucleo, String cognome, int reddito) {
        this.idNucleo = idNucleo;
        fieldCognome.setText(cognome);
        fieldReddito.setText("" + reddito);
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }

    public void clickModificaNucleo(ActionEvent actionEvent) throws Exception {
        String cognome = fieldCognome.getText() != null ? fieldCognome.getText() : "";
        String reddito = fieldReddito.getText() != null ? fieldReddito.getText() : "";
        gestoreNucleo.modificaNucleo((Stage) buttonModificaNucleo.getScene().getWindow(), idNucleo, cognome, reddito);
    }
}
