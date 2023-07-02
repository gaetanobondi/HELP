package it.help.help.polo.boundary;

import it.help.help.polo.controll.GestoreSegnalazione;
import it.help.help.utils.MainUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataSegnalazioneErrore {

    public Button buttonHome;
    public TextField fieldQuantità;
    public MenuButton selectAlimenti;
    public Button buttonSegnala;
    public RadioButton radioDiocesi;
    public RadioButton radioNucleo;
    public MenuButton selectNuclei;
    public TextField fieldMenuAlimentiSelected;
    public TextField fieldMenuNucleiSelected;
    public GestoreSegnalazione gestoreSegnalazione;
    public SchermataSegnalazioneErrore(GestoreSegnalazione gestoreSegnalazione) {
        this.gestoreSegnalazione = gestoreSegnalazione;
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }

    public void clickConfermaSegnalazione(ActionEvent actionEvent) {
    }

    public void clickRadioDiocesi(ActionEvent actionEvent) {
        gestoreSegnalazione.clickRadioDiocesi((Stage) radioDiocesi.getScene().getWindow());
    }

    public void clickRadioNucleo(ActionEvent actionEvent) throws Exception {
        gestoreSegnalazione.clickRadioNucleo((Stage) radioNucleo.getScene().getWindow());
    }
}
