package Server;

import TransferData.Data;
import TransferData.Task;

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

    private static final int WAITING = 0;
    private static final int STARTROUND = 1;
    private static final int NEXTROUND = 2;
    private static final int NEXTGAME = 3;
// för att bestämma vilken state spelaren är i

    private static final int NUMBEROFQUESTIONS = 4;

    private int state = WAITING;
    private int currentQuestion = 0;


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
        int numberOfQuestionsAnsweredOne = 0;
        int numberOfQuestionsAnsweredTwo = 0;

        if (idInstance.equals(idInstanceOne)) {
            scoreOfPlayerOne = new ArrayList<>();
            scoreOfPlayerOne.addAll(playerScores);
        } else if (idInstance.equals(idInstanceTwo)) {
            scoreOfPlayerTwo = new ArrayList<>();
            scoreOfPlayerTwo.addAll(playerScores);
        }

        int score1 = 0;
        int score2 = 0;


        if (scoreOfPlayerOne != null) {
            for (Boolean aBoolean : scoreOfPlayerOne) {
                if (aBoolean) {
                    score1++;
                }
            }
        }
        if (scoreOfPlayerTwo != null) {
            for (Boolean aBoolean : scoreOfPlayerTwo) {
                if (aBoolean) {
                    score2++;
                }
            }
        }

    }

    protected void roundFinished(List<Boolean> scorePlayer, String player) {
        Data data = new Data();
        data.task = Task.OPPONENT_SCORE;

        Data data1 = new Data();
        data1.task = Task.OPPONENT_SCORE;

        if (player.equals(idInstanceOne)) {
            playerOneReady = true;
            data1.score = scorePlayer;
        } else if (player.equals(idInstanceTwo)) {
            playerTwoReady = true;
            data.score = scorePlayer;
        }

        if (playerOneReady && playerTwoReady) {
            playerOneReady = false;
            playerTwoReady = false;
            playerOne.sendData(data);
            playerTwo.sendData(data1);
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


}
