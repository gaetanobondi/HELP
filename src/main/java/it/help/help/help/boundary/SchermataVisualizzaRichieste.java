package it.help.help.help.boundary;

import it.help.help.autenticazione.controll.GestoreAccettazioneAccount;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataVisualizzaRichieste {

    public Button buttonHome;
    public GestoreAccettazioneAccount gestoreAccettazioneAccount;
    public SchermataVisualizzaRichieste(GestoreAccettazioneAccount gestoreAccettazioneAccount) {
        this.gestoreAccettazioneAccount = gestoreAccettazioneAccount;
    }

    public void clickHome(ActionEvent actionEvent) throws IOException {
        MainUtils.tornaAllaHome((Stage) buttonHome.getScene().getWindow());
    }
}

