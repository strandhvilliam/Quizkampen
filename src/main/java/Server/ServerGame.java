package Server;

import Models.Data;
import Models.Task;

import java.util.ArrayList;
import java.util.List;

public class ServerGame {
    List<Boolean> scoreOfPlayerOne;
    List<Boolean> scoreOfPlayerTwo;
    boolean playerOneReady = false;
    boolean playerTwoReady = false;
    ServerSidePlayer playerOne, playerTwo;

    int numberOfRounds;
    private final String idInstanceOne = "Player_1";
    private final String idInstanceTwo = "Player_2";
    String theCurrentPlayer = idInstanceOne;


    public synchronized Data playerTurn(String idInstance) {
        Data data = new Data();
        if (idInstance.equals(idInstanceOne)) {
            playerOneReady = true;

        } else if (idInstance.equals(idInstanceTwo)) {
            playerTwoReady = true;
        }
        if (theCurrentPlayer.equals(idInstance)) {
            data.task = Task.CHOOSE_CATEGORY;
        } else {
            data.task = Task.NOT_YOUR_TURN;
        }

        if (playerOneReady && playerTwoReady) {
            if (theCurrentPlayer.equals(idInstanceOne)) {
                theCurrentPlayer = idInstanceTwo;
            } else {
                theCurrentPlayer = idInstanceOne;
            }

            playerOneReady = false;
            playerTwoReady = false;
        }
        return data;
    }

    public void getScore(List<Boolean> playerScores, String idInstance) {

        if (idInstance.equals(idInstanceOne)) {
            scoreOfPlayerOne = new ArrayList<>();
            scoreOfPlayerOne.addAll(playerScores);
        } else if (idInstance.equals(idInstanceTwo)) {
            scoreOfPlayerTwo = new ArrayList<>();
            scoreOfPlayerTwo.addAll(playerScores);
        }

    }

    protected void roundIsFinished(String player) {


        if (player.equals(idInstanceOne)) {
            playerOneReady = true;
        } else if (player.equals(idInstanceTwo)) {
            playerTwoReady = true;
        }

        if (playerOneReady && playerTwoReady) {
            playerOneReady = false;
            playerTwoReady = false;

            Data playerOneResult = new Data(Task.OPPONENT_SCORE);
            Data playerTwoResult = new Data(Task.OPPONENT_SCORE);
            playerOneResult.listOfBooleans = scoreOfPlayerTwo;
            playerTwoResult.listOfBooleans = scoreOfPlayerOne;

            playerOne.sendData(playerOneResult);
            playerTwo.sendData(playerTwoResult);
        }
    }

    public String[] getWinner() {
        List<Boolean> playerOne = this.scoreOfPlayerOne;
        List<Boolean> playerTwo = this.scoreOfPlayerTwo;

        int playerScoreOne = 0;
        int playerScoreTwo = 0;

        for (Boolean aBoolean : playerOne) {
            if (aBoolean) {
                playerScoreOne++;
            }
        }

        for (Boolean aBoolean : playerTwo) {
            if (aBoolean) {
                playerScoreTwo++;
            }
        }

        String[] results = new String[3];
        if (playerScoreOne > playerScoreTwo) {
            results[0] = idInstanceOne;
            results[1] = String.valueOf(playerScoreOne);
            results[2] = String.valueOf(playerScoreTwo);
            return results;
        } else if (playerScoreTwo > playerScoreOne) {
            results[0] = idInstanceTwo;
            results[1] = String.valueOf(playerScoreTwo);
            results[2] = String.valueOf(playerScoreOne);
            return results;
        } else {
            results[0] = "DRAW";
            results[1] = String.valueOf(playerScoreOne);
            results[2] = String.valueOf(playerScoreTwo);
            return results;
        }
    }

    public void setPlayers(ServerSidePlayer playerOne, ServerSidePlayer playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }


    public ServerSidePlayer getOpponent(ServerSidePlayer callerPlayer) {
        if (callerPlayer == playerOne) {
            return playerTwo;
        } else {
            return playerOne;
        }
    }

    public void sendOpponentNames(String idInstance) {
        if (idInstance.equals(idInstanceOne)) {
            playerOneReady = true;
        } else if (idInstance.equals(idInstanceTwo)) {
            playerTwoReady = true;
        }

        if (playerOneReady && playerTwoReady) {
            playerOneReady = false;
            playerTwoReady = false;

            Data data = new Data(Task.OPPONENT_NAME);
            data.message = playerTwo.nameOfPlayer;
            System.out.println("Player 1 name: " + data.message);
            playerOne.sendData(data);

            data = new Data(Task.OPPONENT_NAME);
            data.message = playerOne.nameOfPlayer;
            System.out.println("Player 2 name: " + data.message);
            playerTwo.sendData(data);
        }


    }
}
