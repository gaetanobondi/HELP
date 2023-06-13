package it.help.help.autenticazione.controll;

import it.help.help.entity.*;
import it.help.help.utils.MainUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import it.help.help.utils.DBMS;

import javafx.scene.control.Alert.AlertType;
public class GestoreAccettazioneEsiti {

    public static void clickAccettaDiocesi(Diocesi diocesi) throws Exception {
        DBMS.accettaRichiesta(Responsabile.getId(), "diocesi");
        System.out.println("Diocesi " + diocesi.getId() + " accettata.");
    }

    public static void clickAccettaAziendaPartner(AziendaPartner aziendaPartner) throws Exception {
        DBMS.accettaRichiesta(Responsabile.getId(), "azienda_partner");
        System.out.println("Azienda " + aziendaPartner.getId() + " accettata.");
    }
}
