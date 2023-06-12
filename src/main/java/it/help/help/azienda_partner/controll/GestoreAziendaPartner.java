package it.help.help.azienda_partner.controll;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import it.help.help.entity.Responsabile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import it.help.help.utils.DBMS;
import it.help.help.entity.*;

import javafx.scene.control.Alert.AlertType;

public class GestoreAziendaPartner {
    public Button buttonVisualizzaProfiloAziendaPartner;
    public Button buttonVisualizzaDonazioniEffettuate;
    public Button buttonEffettuaDonazioneSpontanea;
    public Button buttonEffettuaDonazioneAdHoc;
    public Button buttonLogout;

    //per la SCHERMATA HOME RESPONSABILE AZIENDA PARTNER
    public void clickVisualizzaProfiloAziendaPartner(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataProfiloPersonaleAziendaPartner.fxml"));
        Stage window = (Stage) buttonVisualizzaProfiloAziendaPartner.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickVisualizzaDonazioniEffettuate(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataVisualizzaDonazioniEffettuate.fxml"));
        Stage window = (Stage) buttonVisualizzaDonazioniEffettuate.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickEffettuaDonazioneAdHoc(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataEffettuaDonazioneAdHoc.fxml"));
        Stage window = (Stage) buttonEffettuaDonazioneAdHoc.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickEffettuaDonazioneSpontanea(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataEffettuaDonazione.fxml"));
        Stage window = (Stage) buttonEffettuaDonazioneSpontanea.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void clickLogout(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/it/help/help/schermataHomeResponsabilePolo.fxml"));
        Stage window = (Stage) buttonLogout.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}

