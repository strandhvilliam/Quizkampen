package com.skola.quizkampen;

import TransferData.Data;
import TransferData.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ClientGame {

    private final Stage stage;
    private final Client client;

    private List<Boolean> playerScore;


    private int totalNumOfRounds;

    private int questionsPerRound;
    private String playerName;
    private String opponentName;


    public ClientGame(Client client, Stage stage) {
        this.client = client;
        this.stage = stage;
        client.setGame(this);
    }

    public void displayQuestionWindow() {

    }

    public void displayCategoryWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("category-chooser.fxml"));
        stage.setTitle("Choose category");
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayWaitingWindow() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("waiting-window.fxml"));
        stage.setTitle("Waiting for opponent");
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void displayResultWindow(Data data) {

    }

    public void displayStatisticsWindow() {

    }

    public void sendResult() {

    }

    public void startGame(String username) {
        this.playerName = username;

        Data data = new Data();
        data.task = Task.START_GAME;
        data.message = username;
        client.sendObject(data);

        displayWaitingWindow();
    }

    public void checkIfGameFinished() {

    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }
    public void setProperties(int totalNumOfRounds, int questionsPerRound) {
        this.totalNumOfRounds = totalNumOfRounds;
        this.questionsPerRound = questionsPerRound;
    }

    public void sendCategory(Category category) {
    }
}
