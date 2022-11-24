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

    private int totalQuestions;

    private int numOfRounds;


    public void initStatistics(ClientController clientController, List<Boolean> playerResult, List<Boolean> opponentResult, int numOfRounds) {
        this.clientController = clientController;

        //playerPointGrid.setGridLinesVisible(true);
        //opponentPointGrid.setGridLinesVisible(true);

        this.totalQuestions = playerResult.size();

        int numOfColumns = playerResult.size() / numOfRounds;
        System.out.println("rounds : " + numOfRounds);
        System.out.println("num of cols: " + numOfColumns);
        System.out.println("resultsize: " + playerResult.size());


        for (int row = 0; row < numOfRounds; row++) {
            Label roundLabel = new Label("" + (row + 1));
            roundLabel.getStyleClass().add("medium-gray-label");
            roundGrid.add(roundLabel, 0, row);
            GridPane.setVgrow(roundLabel, Priority.ALWAYS);
            //GridPane.setHgrow(roundLabel, Priority.ALWAYS);
        }

        fillStatistics(playerResult, playerPointGrid, numOfColumns, numOfRounds);
        fillStatistics(opponentResult, opponentPointGrid, numOfColumns, numOfRounds);
    }

    private void fillStatistics(List<Boolean> result, StackPane stackPane, int numOfColumns, int numOfRounds) {


        GridPane gridPane = new GridPane();
        //gridPane.setGridLinesVisible(true);

        for (int row = 0; row < numOfRounds; row++) {
            for (int col = 0; col < numOfColumns; col++) {

                VBox vBox = new VBox();
                Label pointLabel = new Label("");
                pointLabel.getStyleClass().add("point-circle");
                if (result.get(0)) {
                    pointLabel.getStyleClass().add("win-point-label");
                } else {
                    pointLabel.getStyleClass().add("loss-point-label");
                }
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().add(pointLabel);
                gridPane.add(vBox, col, row);
                GridPane.setVgrow(vBox, Priority.ALWAYS);
                GridPane.setHgrow(vBox, Priority.ALWAYS);

                result.remove(0);
            }
        }

        stackPane.getChildren().add(gridPane);


        //columnConstraints.setHgrow(Priority.ALWAYS);


        //grid.setAlignment(Pos.CENTER);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
