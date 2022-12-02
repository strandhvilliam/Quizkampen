package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.charset.CharsetDecoder;


public class InitialController {
    @FXML
    private TextField usernameTextField;

    @FXML
    public void initializeGameAction(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        game.startGame(username);
    }

    private ClientGame game;

    public void setupGame(ClientGame clientGame) {
        this.game = clientGame;

    }
}
