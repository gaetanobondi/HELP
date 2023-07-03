package it.help.help.help.boundary;

import it.help.help.help.controll.GestoreHelp;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class SchermataLista {
    public Button buttonHome;
    public VBox lista;
    public GestoreHelp gestoreHelp;

    public SchermataLista(GestoreHelp gestoreHelp) {
        this.gestoreHelp = gestoreHelp;
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }
}

