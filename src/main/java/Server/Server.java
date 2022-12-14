package Server;


import java.io.IOException;
import java.net.ServerSocket;

public class Server {


    public static void main(String[] args) {

        Database database = new Database();


        System.out.println("Server is now online, let the quiz begin!");
        try (ServerSocket listener = new ServerSocket(5555)) {
            while (true) {
                ServerGame game = new ServerGame();
                ServerSidePlayer playerOne = new ServerSidePlayer(listener.accept(), "Player_1", game, database);
                ServerSidePlayer playerTwo = new ServerSidePlayer(listener.accept(), "Player_2", game, database);
                game.setPlayers(playerOne, playerTwo);


                playerTwo.start();
                playerOne.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
