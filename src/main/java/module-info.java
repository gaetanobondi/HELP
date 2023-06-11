module it.help.help {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens it.help.help to javafx.fxml;
    exports it.help.help.autenticazione.boundary;
    exports it.help.help.autenticazione;
    opens it.help.help.autenticazione to javafx.fxml;
    exports it.help.help.autenticazione.controll;
    opens it.help.help.autenticazione.controll to javafx.fxml;
    opens it.help.help.autenticazione.boundary to javafx.fxml;
}