package it.help.help.polo.boundary;

import it.help.help.polo.controll.GestoreMembro;
import it.help.help.polo.controll.GestoreNucleo;
import it.help.help.utils.MainUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataVisualizzaComponentiNucleo {
    public Button buttonHome;
    public Label labelCognome;
    public VBox listaComponenti;
    public Button buttonAggiungiNuovoMembro;
    public Button buttonVisualizzaSchemaDiDistribuzioneNucleo;

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }

    public void clickAggiungiNuovoMembro(ActionEvent actionEvent) throws Exception {
        GestoreMembro gestoreMembro = new GestoreMembro();
        gestoreMembro.schermataAggiungiNuovoMembro((Stage) buttonAggiungiNuovoMembro.getScene().getWindow());
    }

    public void clickVisualizzaSchemaDiDistribuzioneNucleo(ActionEvent actionEvent) throws Exception {
        GestoreNucleo gestoreNucleo = new GestoreNucleo();
        gestoreNucleo.schermataSchemaDistribuzioneNucleo((Stage) buttonVisualizzaSchemaDiDistribuzioneNucleo.getScene().getWindow());
    }
}
