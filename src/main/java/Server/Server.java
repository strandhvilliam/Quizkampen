package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    // todo: change player one & player two from hard values to input from user.
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(5555);
        System.out.println("Server is now online, let the quiz begin!");
        try{
            while(true){
                ServerGame game = new ServerGame();
                ServerSidePlayer playerOne = new ServerSidePlayer(listener.accept(), "Placeholder one", game);
                ServerSidePlayer playerTwo = new ServerSidePlayer(listener.accept(), "Placeholder two", game);

                playerOne.setOpponant(playerTwo);
                playerTwo.setOpponant(playerOne);
                /*game.currentPlayer = playerOne;*/  // tanken är att kontrollera vems tur det är från ticTacToe spelet,
                // men kanske inte behövs, tankar kring detta?
                playerOne.start();
                playerTwo.start();

            }
        } finally {
            listener.close();
        }
    }
}
