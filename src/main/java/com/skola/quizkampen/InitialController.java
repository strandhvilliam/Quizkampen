package com.skola.quizkampen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class InitialController {
    @FXML
    private TextField usernameTextField;

    private ClientController clientController;

    @FXML
    void initializeGameAction(ActionEvent event) {
        String username = usernameTextField.getText();
        clientController.startGame(username);
        usernameTextField.getScene().getWindow().hide();
    }


    public void setupClientController(ClientController clientController) {
        this.clientController = clientController;
    }
}
