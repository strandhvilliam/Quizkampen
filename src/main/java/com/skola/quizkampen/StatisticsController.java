package com.skola.quizkampen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private List<Boolean> playerResult;
    private List<Boolean> opponentResult;

    private int numOfRounds;


    public void initStatistics(ClientController clientController, List<Boolean> playerResult, List<Boolean> opponentResult, int numOfRounds) {
        this.clientController = clientController;
        this.playerResult = playerResult;
        this.opponentResult = opponentResult;
        this.numOfRounds = numOfRounds;

        //playerPointGrid.setGridLinesVisible(true);
        //opponentPointGrid.setGridLinesVisible(true);


        fillStatistics();
    }

    private void fillStatistics() {

        int numOfColumns = playerResult.size() / numOfRounds;

        for (int row = 0; row < numOfRounds; row++) {
             for (int col = 0; col < numOfColumns; col++) {
                 Label pointLabel = new Label("");
                 pointLabel.getStyleClass().add("point-circle");
                 if (playerResult.get(0)) {
                     pointLabel.getStyleClass().add("win-point-label");
                 } else {
                     pointLabel.getStyleClass().add("loss-point-label");
                 }
                 System.out.println(row + " " + col);
                 playerPointGrid.add(pointLabel, col, row);
                 playerResult.remove(0);
             }
        }

        for (int row = 0; row < numOfRounds; row++) {
             for (int col = 0; col < numOfColumns; col++) {
                 Label pointLabel = new Label("");
                 pointLabel.getStyleClass().add("point-circle");
                 if (opponentResult.get(0)) {
                     pointLabel.getStyleClass().add("win-point-label");
                 } else {
                     pointLabel.getStyleClass().add("loss-point-label");
                 }
                 System.out.println(row + " " + col);
                 opponentPointGrid.add(pointLabel, col, row);
                 opponentResult.remove(0);
             }
        }









        /*
        //för varje runda lägg till en rad i gridpane player, opponent och round
        for (int i = 0; i < numOfRounds; i++) {
            roundGrid.addRow(1);
            playerPointGrid.addRow(numOfColumns);
            opponentPointGrid.addRow(numOfColumns);
        }

        playerPointGrid.row

        playerPointGrid.addColumn(numOfColumns);
        opponentPointGrid.addColumn();

        for (int i = 0; i < numOfRounds; i++) {
            roundGrid.add(new Label(""+(i + 1)), 0, i);
        }

        for (int i = 0; i < playerPointGrid.getRowCount(); i++) {
            for (int j = 0; j < playerPointGrid.getColumnCount(); j++) {
                if (playerResult.get(0)) {
                    Label win = new Label("");
                    win.getStyleClass().add("point-circle");
                    win.getStyleClass().add("win-point-label");
                    playerPointGrid.add(win, j, i);
                } else {
                    Label lose = new Label("");
                    lose.getStyleClass().add("point-circle");
                    lose.getStyleClass().add("lose-point-label");
                    playerPointGrid.add(lose, j, i);
                }
                                playerResult.remove(0);

                playerPointGrid.add(new Label("1"), j, i);
            }
        }

        for (int i = 0; i < opponentPointGrid.getRowCount(); i++) {
            for (int j = 0; j < opponentPointGrid.getColumnCount(); j++) {
                if (playerResult.get(0)) {
                    Label win = new Label("");
                    win.getStyleClass().add("point-circle");
                    win.getStyleClass().add("win-point-label");
                    opponentPointGrid.add(win, j, i);
                } else {
                    Label lose = new Label("");
                    lose.getStyleClass().add("point-circle");
                    lose.getStyleClass().add("lose-point-label");
                    opponentPointGrid.add(lose, j, i);
                }
                opponentResult.remove(0);
                opponentPointGrid.add(new Label("1"), j, i);
            }
        }

        //antalet frågor / rundor = antal kolumner i gridpane
        //lägg till resterande kolumner som saknas

        */
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
