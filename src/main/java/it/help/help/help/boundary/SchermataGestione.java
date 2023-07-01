package it.help.help.help.boundary;

import it.help.help.help.controll.GestoreHelp;
import it.help.help.utils.MainUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataGestione {
    public Button buttonHome;
    public Button buttonListaPoli;
    public Button buttonListaDiocesi;
    public Button buttonListaAziende;
    public GestoreHelp gestoreHelp;

    public  SchermataGestione(GestoreHelp gestoreHelp) {
        this.gestoreHelp = gestoreHelp;
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }

    public void clickListaPoli(ActionEvent actionEvent) throws Exception {
        gestoreHelp.schermataListaPoli((Stage) buttonListaPoli.getScene().getWindow());
    }

    public void clickListaDiocesi(ActionEvent actionEvent) {
    }

    public void clickListaAziende(ActionEvent actionEvent) {
    }
}

