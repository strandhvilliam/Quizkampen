package com.skola.quizkampen;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ResultsController {

    @FXML
    private Label opponentPoints;

    @FXML
    private Label playerPoints;

    @FXML
    private Label resultsLabel;


    @FXML
    private void exitGameAction() {
        Platform.exit();
    }

    public void initData(String[] results) {

        switch (results[0]) {
            case "WON" -> {
                resultsLabel.setText("Du vann!");
                playerPoints.setText(String.valueOf(Math.max(Integer.parseInt(results[1]), Integer.parseInt(results[2]))));
                opponentPoints.setText(String.valueOf(Math.min(Integer.parseInt(results[1]), Integer.parseInt(results[2]))));
            }
            case "DRAW" ->  {
                resultsLabel.setText("Lika!");
                playerPoints.setText(String.valueOf(results[1]));
                opponentPoints.setText(String.valueOf(results[1]));
            }
            case "LOSE" -> {
                resultsLabel.setText("Du förlorade!");
                opponentPoints.setText(String.valueOf(Math.max(Integer.parseInt(results[1]), Integer.parseInt(results[2]))));
                playerPoints.setText(String.valueOf(Math.min(Integer.parseInt(results[1]), Integer.parseInt(results[2]))));
            }
        }




    }
}