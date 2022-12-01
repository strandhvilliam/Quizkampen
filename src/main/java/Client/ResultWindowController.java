package Client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResultWindowController {

    @FXML
    private Label opponentPoints;

    @FXML
    private Label playerPoints;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label opponentNameLabel;

    @FXML
    private ImageView leftImageView;
    @FXML
    private ImageView rightImageView;

    @FXML
    private Label resultsLabel;
    private ClientGame game;


    @FXML
    private void exitGameAction() {
        Platform.exit();
    }

    @FXML
    private void playAgainAction() {
        game.startGame(playerNameLabel.getText());
    }

    public void initData(ClientGame game, String[] result) {
        this.game = game;
        this.playerNameLabel.setText(game.playerName);
        this.opponentNameLabel.setText(game.opponentName);

        switch (result[0]) {
            case "WON" -> {
                resultsLabel.setText("Du vann!");
                leftImageView.setImage(new Image(getClass().getResource("icons/icons8_confetti_100px.png").toExternalForm()));
                rightImageView.setImage(new Image(getClass().getResource("icons/icons8_confetti_100px.png").toExternalForm()));
                playerPoints.setText(String.valueOf(Math.max(Integer.parseInt(result[1]), Integer.parseInt(result[2]))));
                opponentPoints.setText(String.valueOf(Math.min(Integer.parseInt(result[1]), Integer.parseInt(result[2]))));
            }
            case "DRAW" -> {
                resultsLabel.setText("Lika!");
                leftImageView.setImage(new Image(getClass().getResource("icons/icons8_puzzled_100px.png").toExternalForm()));
                rightImageView.setImage(new Image(getClass().getResource("icons/icons8_puzzled_100px.png").toExternalForm()));
                playerPoints.setText(String.valueOf(result[1]));
                opponentPoints.setText(String.valueOf(result[1]));
            }
            case "LOSE" -> {
                resultsLabel.setText("Du förlorade!");
                leftImageView.setImage(new Image(getClass().getResource("icons/icons8_crying_baby_100px.png").toExternalForm()));
                rightImageView.setImage(new Image(getClass().getResource("icons/icons8_crying_baby_100px.png").toExternalForm()));
                opponentPoints.setText(String.valueOf(Math.max(Integer.parseInt(result[1]), Integer.parseInt(result[2]))));
                playerPoints.setText(String.valueOf(Math.min(Integer.parseInt(result[1]), Integer.parseInt(result[2]))));
            }
        }


    }
}