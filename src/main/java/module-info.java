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
    exports it.help.help.polo.boundary;
    exports it.help.help.polo.controll;
    opens it.help.help.polo.controll to javafx.fxml;
    opens it.help.help.polo.boundary to javafx.fxml;
    exports it.help.help.azienda_partner.boundary;
    exports it.help.help.azienda_partner.controll;
    opens it.help.help.azienda_partner.controll to javafx.fxml;
    opens it.help.help.azienda_partner.boundary to javafx.fxml;
    exports it.help.help.help.boundary;
    exports it.help.help.help.controll;
    opens it.help.help.help.controll to javafx.fxml;
    opens it.help.help.help.boundary to javafx.fxml;
    exports it.help.help.magazzino.controll;
    opens it.help.help.magazzino.controll to javafx.fxml;
    // exports it.help.help.diocesi.boundary;
    exports it.help.help.diocesi.controll;
    opens it.help.help.diocesi.controll to javafx.fxml;
    // opens it.help.help.diocesi.boundary to javafx.fxml;
    exports it.help.help.common;
    opens it.help.help.common to javafx.fxml;

}