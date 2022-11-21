package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSidePlayer extends Thread implements Serializable {
    String nameOfPlayer;
    List<String> scorePlayer;  // räkna antal poäng för spelare.
    ServerSidePlayer opponant;
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    ServerGame game;


    public ServerSidePlayer(Socket socket, String nameOfPlayer, ServerGame game) {
        this.socket = socket;
        this.nameOfPlayer = nameOfPlayer;
        this.game = game;
        this.scorePlayer = new ArrayList<>();

        try {
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("Welcome " + nameOfPlayer);
        } catch (IOException e) {
            System.out.println("Could not find server: " + e.getMessage());
        }
    }

    public void setNameOfPlayer(String nameOfPlayer) {
        if (nameOfPlayer instanceof String) {
            this.nameOfPlayer = nameOfPlayer;
        } else {
            System.out.println("Invalid name");
        }
        /*
         Kanske kan ersättas i servern i while loopen!
        * */
    }


    public void setOpponant(ServerSidePlayer opppannt) {
        this.opponant = opppannt;
    }

    public void run() {
        System.out.println("All players have connected (2).");

        /*TODO: lägg till logiken för spelet. Om spelare svarar rätt spara antal poäng,
           om spelare svarar fel spara antal fel eventuellt skicka till server*/
    }

    public void checkAnswer(String score) {
        this.scorePlayer.add(score);  // användas för att spara svar från spelare.
    }
}


//Todo: Lägga till metod ifall en person har vunnit trots att det fortfarande finns ronder kvar att spela