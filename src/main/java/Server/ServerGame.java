package Server;

import java.util.ArrayList;
import java.util.List;

public class ServerGame {
    // BEHÖVER VI ENS DEN HÄR KLASSEN?
    List<Boolean> scoreOfPlayerOne;
    List<Boolean> scoreOfPlayerTwo;
    boolean playerOneReady = false;
    boolean playerTwoReady = false;

    int numberOfRounds;
    // TODO: FIXA SYNC PROBLEM?
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


    public synchronized String playerTurn(String idInstance) {
        // Metod för att kontrollera turbaserade rundor. TODO;
        if(idInstance.equals(idInstanceOne)){
            playerOneReady = true;
        } else if (idInstance.equals(idInstanceTwo)){
            playerTwoReady = true;
        }

        String currentPlayerNow = theCurrentPlayer;

        if (playerOneReady && playerTwoReady){
            if (theCurrentPlayer.equals(idInstanceOne)) {
                theCurrentPlayer = idInstanceTwo;
            } else {
                theCurrentPlayer = idInstanceOne;
            }
            playerOneReady = false;
            playerTwoReady = false;
        }
        return currentPlayerNow;
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
            //numberOfQuestionsAnsweredOne = scoreOfPlayerOne.size();
        }
        if (scoreOfPlayerTwo != null) {
            for (Boolean aBoolean : scoreOfPlayerTwo) {
                if (aBoolean) {
                    score2++;
                }
            }
            //numberOfQuestionsAnsweredTwo = scoreOfPlayerTwo.size();
        }
        //getWinner(scoreOfPlayerOne, idInstance);




        /* if (numberOfQuestionsAnsweredOne == 4 && numberOfQuestionsAnsweredTwo == 4) {
            if (score1 > score2) {

                System.out.println("Placeholder one won!" + " score Player 1 --> " + score1 + " Player 2 --> " + score2);
            } else if (score1 == score2) {
                System.out.println("It's a draw!");
            } else {
                System.out.println("Placeholder 2 won!" + " score Player 2 --> " + score2 + " Player 1 --> " + score1);
            }
        } */

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

       /* System.out.println("Placeholder one total score "
                + playerScoreOne
                + " Placeholder two total score "
                + playerScoreTwo
                + "\nIndividual rounds: "
                + playerOne
                + " "
                + playerTwo);

        numberOfRounds++;*/
    }


    /*
        Eventuellt ta inspiration från:
        https://github.com/sigrunolafsdottir/natverksprogrammering/blob/master/src/TicTacToeSimple/ServerSideGame.java
        Vad vill vi behålla och inte behålla.
    */
}
