package it.help.help.azienda_partner.boundary;

import it.help.help.utils.MainUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataEffettuaDonazioneAdHoc extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchermataEffettuaDonazioneAdHoc.class.getResource("/it/help/help/azienda_partner/SchermataEffettuaDonazioneAdHoc.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Schermata effettua donazione ad-hoc");
        stage.setScene(scene);
        stage.show();
        MainUtils.boundaryStack.add((Stage) scene.getWindow());
    }

    public static void main(String[] args) {
        launch();
    }
}
