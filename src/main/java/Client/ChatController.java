package Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ChatController {

    @FXML
    private TextArea chatField = new TextArea();

    @FXML
    private TextField chatMessageBox;

    private ClientGame game;

    private String message;

    private String messageLog;

    @FXML
    public void sendMessage() {
        message = chatMessageBox.getText();
        if (!message.isBlank()) {
            messageLog = chatField.getText() + "\n" + game.playerName + ": " + message;
            chatField.setText(messageLog);
            chatMessageBox.clear();
            game.sendMessage(message);
            chatField.setScrollTop(Double.MAX_VALUE);
        }
    }

    @FXML
    public void receiveMessage(String opponentMessage) {
        chatField.setText(chatField.getText() + "\n" + game.opponentName + ": " + opponentMessage);
        chatField.setScrollTop(Double.MAX_VALUE);
    }

    public void setGame(ClientGame game) {
        this.game = game;
    }

}
