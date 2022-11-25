package com.skola.quizkampen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class InitialController {
    @FXML
    private TextField usernameTextField;

    @FXML
    void initializeGameAction(ActionEvent event) {
        String username = usernameTextField.getText();

        ClientController clientController = new ClientController();
        clientController.startGame(username);
    }
}
