package it.help.help.polo.boundary;

import it.help.help.polo.controll.GestoreSegnalazione;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class SchermataScaricamentoReport {
    public GestoreSegnalazione gestoreSegnalazione;
    public Button buttonHome;
    public MenuButton selectIntervallo;
    public VBox lista;

    public SchermataScaricamentoReport(GestoreSegnalazione gestoreSegnalazione) {
        this.gestoreSegnalazione = gestoreSegnalazione;
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }

    public void selectMensile(ActionEvent actionEvent) throws Exception {
        selectIntervallo.setText("MENSILE");
        gestoreSegnalazione.selectMensile((Stage) selectIntervallo.getScene().getWindow(), lista);
    }

    public void selectTrimestrale(ActionEvent actionEvent) throws Exception {
        selectIntervallo.setText("TRIMESTRALE");
        gestoreSegnalazione.selectTrimestrale((Stage) selectIntervallo.getScene().getWindow(), lista);
    }

    public void selectAnnuale(ActionEvent actionEvent) throws Exception {
        selectIntervallo.setText("ANNUALE");
        gestoreSegnalazione.selectAnnuale((Stage) selectIntervallo.getScene().getWindow(), lista);
    }

}
