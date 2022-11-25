package com.skola.quizkampen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class InitialController {
    @FXML
    private TextField usernameTextField;

    @FXML
    void initializeGameAction(ActionEvent event) {
        String username = usernameTextField.getText();

        try {
            ClientController clientController = new ClientController();

            Client client = new Client("127.0.0.1", clientController);
            Thread clientThread = new Thread(client);

            clientThread.setDaemon(true);
            clientThread.start();
            Thread.sleep(1000);
            clientController.setupClient(client);
            clientController.startGame(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
