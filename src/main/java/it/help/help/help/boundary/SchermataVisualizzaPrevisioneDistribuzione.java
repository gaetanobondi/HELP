package it.help.help.help.boundary;

import it.help.help.utils.MainUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataVisualizzaPrevisioneDistribuzione {
    public Button buttonHome;
    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }
}

