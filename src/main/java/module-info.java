module com.skola.quizkampen {
    requires javafx.controls;
    requires javafx.fxml;


    opens Client to javafx.fxml;
    exports Client;
    exports Models;
    exports Server;
    opens Server to javafx.fxml;
    opens Models to javafx.fxml;
}