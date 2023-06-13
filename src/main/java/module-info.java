module it.help.help {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens it.help.help to javafx.fxml;
    exports it.help.help.autenticazione.boundary;
    exports it.help.help.autenticazione.controll;
    opens it.help.help.autenticazione.controll to javafx.fxml;
    opens it.help.help.autenticazione.boundary to javafx.fxml;
    exports it.help.help.diocesi.boundary;
    exports it.help.help.diocesi.controll;
    opens it.help.help.diocesi.boundary to javafx.fxml;
}