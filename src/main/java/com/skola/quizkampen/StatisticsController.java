package com.skola.quizkampen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {


    @FXML
    private GridPane playerPointGrid;

    @FXML
    private GridPane opponentPointGrid;

    @FXML
    private GridPane roundGrid;

    private ClientController clientController;

    private int numOfRounds;


    public void initStatistics(ClientController clientController, List<Boolean> playerResult, List<Boolean> opponentResult, int numOfRounds) {
        this.clientController = clientController;

        //playerPointGrid.setGridLinesVisible(true);
        //opponentPointGrid.setGridLinesVisible(true);

        int numOfColumns = playerResult.size() / numOfRounds;
        System.out.println("rounds : " + numOfRounds);
        System.out.println("num of cols: " +numOfColumns);
        System.out.println("resultsize: " + playerResult.size());


        for (int row = 0; row < numOfRounds; row++) {
            Label roundLabel = new Label("" + (row + 1));
            roundLabel.getStyleClass().add("medium-gray-label");
            //roundLabel.setAlignment(Pos.CENTER);
            roundGrid.add(roundLabel, 0, row);
        }

        fillStatistics(playerResult, playerPointGrid, numOfColumns, numOfRounds);
        fillStatistics(opponentResult, opponentPointGrid, numOfColumns, numOfRounds);
    }

    private void fillStatistics(List<Boolean> result, GridPane grid, int numOfColumns, int numOfRounds) {


        for (int row = 0; row < numOfRounds; row++) {
             for (int col = 0; col < numOfColumns; col++) {
                 Label pointLabel = new Label("");
                 pointLabel.getStyleClass().add("point-circle");
                 if (result.get(0)) {
                     pointLabel.getStyleClass().add("win-point-label");
                 } else {
                     pointLabel.getStyleClass().add("loss-point-label");
                 }
                 grid.add(pointLabel, col, row);
                 result.remove(0);
             }
        }

        grid.setAlignment(Pos.CENTER);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
