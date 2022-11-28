package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class InitialController {
    @FXML
    private TextField usernameTextField;

    @FXML
    public void initializeGameAction(ActionEvent event) {
        String username = usernameTextField.getText();
        game.startGame(username);

    }

    private ClientGame game;

    public void setupGame(ClientGame clientGame) {
        this.game = clientGame;

    }
}
