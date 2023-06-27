package it.help.help.help.controll;

import it.help.help.autenticazione.boundary.SchermataRegistrazionePolo;
import it.help.help.help.boundary.SchermataVisualizzaPrevisioneDistribuzione;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class GestoreHelp {
    public Button buttonVisualizzaPrevisioneDistribuzione;

    public void clickVisualizzaPrevisioneDiDistribuzione(ActionEvent actionEvent) throws Exception {
        SchermataVisualizzaPrevisioneDistribuzione l = new SchermataVisualizzaPrevisioneDistribuzione();
        Stage window = (Stage) buttonVisualizzaPrevisioneDistribuzione.getScene().getWindow();
        l.start(window);
    }
}
