package it.help.help.azienda_partner.boundary;

import it.help.help.utils.MainUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataListaDonazioniAdHoc extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataListaDonazioniAdHoc.class.getResource("/it/help/help/azienda_partner/SchermataListaDonazioniAdHoc.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Schermata lista donazioni ad-hoc");
        stage.setScene(scene);
        stage.show();
        MainUtils.boundaryStack.add((Stage) scene.getWindow());
    }

    public static void main(String[] args) {
        launch();
    }
}
