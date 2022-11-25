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

    private static final int WAITING = 0;
    private static final int STARTROUND = 1;
    private static final int NEXTROUND = 2;
    private static final int NEXTGAME = 3;
// för att bestämma vilken state spelaren är i

    private static final int NUMBEROFQUESTIONS = 4;

    private int state = WAITING;

    private int currentQuestion = 0;


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
        getWinner(listOfPlayers1, nameOfPlayer);




        if (numberOfQuestionsAnsweredOne == 4 && numberOfQuestionsAnsweredTwo == 4) {
            if (score1 > score2) {

                System.out.println("Placeholder one won!" + " score Player 1 --> " + score1 + " Player 2 --> " + score2);
            } else if (score1 == score2) {
                System.out.println("It's a draw!");
            } else {
                System.out.println("Placeholder 2 won!" + " score Player 2 --> " + score2 + " Player 1 --> " + score1);
            }
        }

    }

    public void getWinner(List<Boolean> player, String name) {
        List<Boolean> playerOne = new ArrayList<>();
        List<Boolean> playerTwo = new ArrayList<>();
        int playerScoreOne = 0;
        int playerScoreTwo = 0;
        if(player != null && name.equals("Placeholder one")){
            playerOne.addAll(player);
        }

        for (Boolean aBoolean : playerOne) {
            if(aBoolean){
                playerScoreOne++;
            }
        }

        for (Boolean aBoolean : playerTwo) {
            if(aBoolean){
                playerScoreTwo++;
            }
        }
        System.out.println("Placeholder one total score "
                + playerScoreOne
                + " Placeholder two total score "
                + playerScoreTwo
                +"\nIndividual rounds: "
                + playerOne
                + " "
                + playerTwo);

        numberOfRounds++;
    }


    /*
        Eventuellt ta inspiration från:
        https://github.com/sigrunolafsdottir/natverksprogrammering/blob/master/src/TicTacToeSimple/ServerSideGame.java
        Vad vill vi behålla och inte behålla.
    */
}
