package com.skola.quizkampen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {


    @FXML
    private StackPane playerPointGrid;

    @FXML
    private StackPane opponentPointGrid;

    @FXML
    private GridPane roundGrid;

    private ClientController clientController;



    public void nextRoundAction() {
        roundGrid.getScene().getWindow().hide();
        clientController.checkIfGameIsOver();
    }


    /**
     * Initierar datan som ska presenteras för spelaren efter varje runda är klar.
     * @param clientController kontrollern för klienten
     * @param playerResult resultatet för spelaren
     * @param opponentResult resultatet för motståndaren
     * @param numOfRounds antal ronder i spelet totalt (från properties)
     * @param numOfColumns antal frågor per runda blir antal kolumner (från properties)
     */
    public void initStatistics(ClientController clientController, List<Boolean> playerResult, List<Boolean> opponentResult, int numOfRounds, int numOfColumns) {
        this.clientController = clientController;

        for (int row = 0; row < numOfRounds; row++) {
            Label roundLabel = new Label("" + (row + 1));
            roundLabel.getStyleClass().add("medium-gray-label");
            roundGrid.add(roundLabel, 0, row);
            GridPane.setVgrow(roundLabel, Priority.ALWAYS);
        }

        fillStatistics(playerResult, playerPointGrid, numOfColumns, numOfRounds);
        fillStatistics(opponentResult, opponentPointGrid, numOfColumns, numOfRounds);
    }

    /**
     * Fyller i statistiken för spelaren eller motståndaren.
     * @param result resultatet för spelaren eller motståndaren
     * @param stackPane FXML destination för statistiken
     * @param numOfColumns antal frågor per runda blir antal kolumner (från properties)
     * @param numOfRounds antal ronder i spelet totalt (från properties)
     */
    private void fillStatistics(List<Boolean> result, StackPane stackPane, int numOfColumns, int numOfRounds) {

        GridPane gridPane = new GridPane();

        for (int row = 0; row < numOfRounds; row++) {
            for (int col = 0; col < numOfColumns; col++) {

                VBox vBox = new VBox();
                Label pointLabel = new Label("");
                pointLabel.getStyleClass().add("point-circle");
                if (!result.isEmpty()) {
                    if (result.get(0)) {
                        pointLabel.getStyleClass().add("win-point-label");
                    } else {
                        pointLabel.getStyleClass().add("loss-point-label");
                    }
                    result.remove(0);
                }

                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().add(pointLabel);
                gridPane.add(vBox, col, row);
                GridPane.setVgrow(vBox, Priority.ALWAYS);
                GridPane.setHgrow(vBox, Priority.ALWAYS);

            }
        }

        stackPane.getChildren().add(gridPane);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
