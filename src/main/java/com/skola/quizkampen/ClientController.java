package com.skola.quizkampen;

import Server.Question;
import Server.ServerSidePlayer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ClientController implements Initializable {


    private String correctAnswerForRound;

    private boolean isWaiting;

    private Client client;

    protected List<Boolean> playerScore = new ArrayList<>();
    private List<Question> questionsInRound;

    protected String opponentName;

    protected int totalNumOfRounds;
    protected int questionsPerRound;

    private Stage waitingWindow;
    private String username;




    /*TODO: metod som hanterar när användare skriver in användarnamn.
        ber klienten skicka request till servern att valt användarnamn
        sätter namnet i GUIn
     */


    public void displayNextQuestion() throws IOException {
        if (isWaiting) {
            waitingWindow.getScene().getWindow().hide();
            isWaiting = false;
        }
        if (questionsInRound.size() > 0) {
            Question questionToDisplay = questionsInRound.get(0);
            questionsInRound.remove(0);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("question-form.fxml"));
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            QuestionWindowController questionWindowController = loader.getController();
            questionWindowController.initData(this, questionToDisplay, username);

            stage.show();
        } else {
            sendResult();
            requestIsDoneWithRound();
        }



        /*else {
           for (Boolean result : roundResult) {
               System.out.println(result);
           }
        }*/

    }

    public void sendResult() throws IOException {
        client.sendObject(playerScore);
    }


    public void displayCategoryChooser() {
        System.out.println("displayCategoryChooser");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("category-chooser.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Choose category");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        CategoryController categoryController = fxmlLoader.getController();
        categoryController.setupClient(this.client, this);
        stage.show();
    }

    public void requestIsDoneWithRound() {
        client.requestPlayerDoneWithRound();
    }


    public void startRound(List<Question> questions) throws IOException {
        this.questionsInRound = questions;

        if (isWaiting) {
            waitingWindow.getScene().getWindow().hide();
            isWaiting = false;
        }

        displayNextQuestion();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize method");

    }

    public void setupClient(Client client) {
        this.client = client;
        System.out.println("Client set");
    }


    public void displayStatistics(List<Boolean> opponentResult) {
        System.out.println("Motståndare:");
        for (Boolean b : opponentResult) {
            System.out.println(b);
        }

        System.out.println();

        System.out.println("Mitt resultat:");
        for (Boolean b : playerScore) {
            System.out.println(b);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("statistics.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Choose category");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StatisticsController controller = fxmlLoader.getController();
        controller.initStatistics(this, playerScore, opponentResult, totalNumOfRounds, questionsPerRound);

        stage.show();


    }

    public void startGame(String username) {
        this.username = username;

        System.out.println("inside startgame");

        client.requestStartGame(username);

        displayWaitingWindow();

    }



    public void displayWaitingWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("waiting-window.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Waiting for opponent");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();

        waitingWindow = stage;
        isWaiting = true;
    }

    public void checkIfGameIsOver() {
        if (playerScore.size() == totalNumOfRounds) {
            client.requestGameOver();
        } else {
            client.requestNewRound();
        }
    }

    public void displayGameResult(String[] resArray) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("results-windows.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Results");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResultsController resultsController = fxmlLoader.getController();

        resultsController.initData(resArray);

        stage.show();
    }

    private void resultsController(String[] resArray) {

    }

    public void initOpponent(String s) {

        if (isWaiting) {
            waitingWindow.getScene().getWindow().hide();
            isWaiting = false;
        }

        opponentName = s;

    }
}