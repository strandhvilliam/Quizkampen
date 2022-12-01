package Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {


    @FXML
    private StackPane playerPointGrid;

    @FXML
    private StackPane opponentPointGrid;

    @FXML
    private GridPane roundGrid;

    private ClientGame game;
    @FXML
    private Label playerNameLabel;

    @FXML
    private Label opponentNameLabel;

    @FXML
    private Button nextRoundButton;
    private int numOfRounds;
    private int numOfColumns;


    public void nextRoundAction() {
        game.checkIfGameFinished();
    }


    public void initData(ClientGame game, List<Boolean> playerResult, List<Boolean> opponentResult, int numOfRounds, int numOfColumns) {
        this.game = game;
        this.numOfRounds = numOfRounds;
        this.numOfColumns = numOfColumns;
        playerNameLabel.setText(game.playerName);
        opponentNameLabel.setText(game.opponentName);

        if (playerResult.size() == numOfRounds * numOfColumns) {
            nextRoundButton.setText("Se resultat!");
        }


        for (int row = 0; row < numOfRounds; row++) {
            Label roundLabel = new Label("" + (row + 1));
            roundLabel.getStyleClass().add("medium-gray-label");
            roundGrid.add(roundLabel, 0, row);
            GridPane.setVgrow(roundLabel, Priority.ALWAYS);
        }

        fillStatistics(playerResult, playerPointGrid);
        fillStatistics(opponentResult, opponentPointGrid);
    }

    private void fillStatistics(List<Boolean> result, StackPane stackPane) {

        for (Boolean aResult : result) {
            System.out.println(aResult);
        }

        List<Boolean> copyOfResult = new ArrayList<>(result);
        GridPane gridPane = createGridPane(copyOfResult);
        stackPane.getChildren().add(gridPane);
    }

    private GridPane createGridPane(List<Boolean> copyOfResult) {
        GridPane gridPane = new GridPane();

        for (int row = 0; row < numOfRounds; row++) {
            for (int col = 0; col < numOfColumns; col++) {

                VBox vBox = new VBox();
                Label pointLabel = new Label("");
                pointLabel.getStyleClass().add("point-circle");
                if (!copyOfResult.isEmpty()) {
                    if (copyOfResult.get(0)) {
                        pointLabel.getStyleClass().add("win-point-label");
                    } else {
                        pointLabel.getStyleClass().add("loss-point-label");
                    }
                    copyOfResult.remove(0);
                }

                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().add(pointLabel);
                gridPane.add(vBox, col, row);
                GridPane.setVgrow(vBox, Priority.ALWAYS);
                GridPane.setHgrow(vBox, Priority.ALWAYS);

            }
        }
        return gridPane;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
