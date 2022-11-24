package Server;

import java.util.ArrayList;
import java.util.List;

public class ServerGame {
    // BEHÖVER VI ENS DEN HÄR KLASSEN?
    ServerSidePlayer theCurrentPlayer;
    List<Boolean> listOfPlayers1;
    List<Boolean> listOfPlayers2;
    int scorePlayerOne;
    int scorePlayerTwo;
    int numberOfRounds;


    public synchronized boolean playerTurn(ServerSidePlayer player) {
        // Metod för att kontrollera turbaserade rundor. TODO;
        if (player == theCurrentPlayer) {
            theCurrentPlayer = theCurrentPlayer.opponent;
            return true;
        }
        return false;
    }

    public void getScore(List<Boolean> playerScores, String nameOfPlayer) {
        int numberOfQuestionsAnsweredOne = 0;
        int numberOfQuestionsAnsweredTwo = 0;

        if (nameOfPlayer.equals("Placeholder one")) {
            listOfPlayers1 = new ArrayList<>();
            listOfPlayers1.addAll(playerScores);
        } else if (nameOfPlayer.equals("Placeholder two")) {
            listOfPlayers2 = new ArrayList<>();
            listOfPlayers2.addAll(playerScores);
        }
//        getWinner(listOfPlayers1, listOfPlayers2);
        int score1 = 0;
        int score2 = 0;

        if (listOfPlayers1 != null) {
            for (Boolean aBoolean : listOfPlayers1) {
                if (aBoolean) {
                    score1++;
                }
            }
            numberOfQuestionsAnsweredOne = listOfPlayers1.size();
        }
        if (listOfPlayers2 != null) {
            for (Boolean aBoolean : listOfPlayers2) {
                if (aBoolean) {
                    score2++;
                }
            }
            numberOfQuestionsAnsweredTwo = listOfPlayers2.size();
        }

        if (numberOfQuestionsAnsweredOne == 4 && numberOfQuestionsAnsweredTwo == 4) {
//            System.out.println("score one --> " + score1 + " score two --> " + score2 + " rounds --> " + numberOfQuestionsAnsweredOne + " number 2 --> " + numberOfQuestionsAnsweredTwo);
            if(score1 > score2){
                System.out.println("Placeholder one won!" + " score Player 1 --> " + score1 + " Player 2 --> " + score2);
            } else if(score1 == score2){
                System.out.println("It's a draw!");
            } else {
                System.out.println("Placeholder 2 won!" + " score Player 2 --> " + score2 + " Player 1 --> " + score1);
            }
        }
    }

    public void getWinner(List<Boolean> playerOne, List<Boolean> playerTwo) {
        System.out.println(playerOne + " --> " + playerTwo);
//        List<Boolean> asd1 = new ArrayList<>();
//        List<Boolean> asd2 = new ArrayList<>();

//        asd1.addAll(playerOne);
//        asd2.addAll(playerTwo);
//        int score1 = 0;
//        int score2 = 0;
        for (Boolean aBoolean : playerOne) {
            if (aBoolean) {
                scorePlayerOne++;
            }
        }

        for (Boolean aBoolean : playerTwo) {
            if (aBoolean) {
                scorePlayerTwo++;
            }
        }
        numberOfRounds++;
        System.out.println("player one --> " + scorePlayerOne + " player two --> " + scorePlayerTwo + " --> " + numberOfRounds);
    }


    /*
        Eventuellt ta inspiration från:
        https://github.com/sigrunolafsdottir/natverksprogrammering/blob/master/src/TicTacToeSimple/ServerSideGame.java
        Vad vill vi behålla och inte behålla.
    */
}
