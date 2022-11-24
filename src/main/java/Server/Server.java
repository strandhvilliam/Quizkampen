package Server;

import com.skola.quizkampen.Category;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    public static void main(String[] args) {

        System.out.println("Server is now online, let the quiz begin!");
        try (ServerSocket listener = new ServerSocket(5555)) {
            while (true) {
                ServerGame game = new ServerGame();
                ServerSidePlayer playerOne = new ServerSidePlayer(listener.accept(), "Placeholder one", game);
                ServerSidePlayer playerTwo = new ServerSidePlayer(listener.accept(), "Placeholder two", game);

                playerOne.setOpponent(playerTwo);
                playerTwo.setOpponent(playerOne);
                /*game.currentPlayer = playerOne;*/  // tanken är att kontrollera vems tur det är från ticTacToe spelet,
                //men kanske inte behövs, tankar kring detta?
                playerTwo.start();
                playerOne.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
