module com.skola.quizkampen {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.skola.quizkampen to javafx.fxml;
    exports com.skola.quizkampen;
    exports TransferData;
    exports Server;
    opens Server to javafx.fxml;
}