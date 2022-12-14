package Server;

import Models.Data;
import Models.Task;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSidePlayer extends Thread implements Serializable {
    final String idInstance;  // för att servern ska kunna skilja på instanserna

    String nameOfPlayer;  // för att spelaren ska kunna välja namn
    List<Boolean> scorePlayer;  // räkna antal poäng för spelare.
    String nameOfOpponent;
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    ServerGame game;
    Database db;

    private final int amountOfRounds;
    private final int amountOfQuestions;


    public ServerSidePlayer(Socket socket, String idInstance, ServerGame game, Database db) {
        this.socket = socket;
        this.idInstance = idInstance;
        this.game = game;
        this.db = db;
        this.scorePlayer = new ArrayList<>();

        this.amountOfRounds = db.getRoundsPerGame();
        this.amountOfQuestions = db.getQuestionsPerRound();

    }

    public void setNameOfPlayer(String nameOfPlayer) {
        this.nameOfPlayer = nameOfPlayer;
    }

    public void setScore(List<Boolean> scores) {
        //scorePlayer.addAll(scores);

        scorePlayer = new ArrayList<>(scores);
    }


    public void sendData(Data data) {
        try {
            System.out.println("Server tries to send " + data.task + ", " + idInstance);
            output.writeObject(data);
            System.out.println("Server sent " + data.task + ", " + idInstance);
            output.flush();
            output.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("All players have connected (2).");
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            while (true) {
                // Iterationen tar emot data från strömmen och skickar
                // det vidare till protokollet som avgör vad som ska
                // hända härnäst igenom en switch sats.
                Data object = (Data) input.readObject();
                System.out.println("Server received " + object.task + ", " + idInstance);
                dataProtocol(object);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Socket closed " + idInstance);
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    protected void dataProtocol(Data data) {
        // protocol för vad som ska hända i en switch.
        switch (data.task) {
            case START_GAME -> startGame(data);
            case PROPERTIES_PROTOCOL -> propertiesProtocol();
            case CHOOSE_CATEGORY -> setCategory(data);
            case ROUND_FINISHED -> setScore(data);
            case GAME_FINISHED -> gameFinished();
            case START_ROUND -> startRound();
            case SEND_MESSAGE -> sendChatMessage(data);
        }
    }

    public void sendChatMessage (Data data) {
        game.sendChatMessage(data.message, idInstance);
    }

    protected void setScore(Data data) {
        setScore(data.listOfBooleans);
        game.getScore(scorePlayer, idInstance);
        game.roundIsFinished(idInstance);
    }


    protected void setCategory(Data data) {
        Data res = new Data(Task.SET_QUESTIONS);

        res.listOfQuestions = db.getByCategory(data.category);
        sendData(res);
        game.getOpponent(this).sendData(res);

    }


    protected void startGame(Data data) {
        setNameOfPlayer(data.message);
        game.sendOpponentNames(idInstance);
    }

    protected void propertiesProtocol() {
        Data data = new Data(Task.PROPERTIES_PROTOCOL);
        data.properties = new int[2];
        data.properties[0] = amountOfRounds;
        data.properties[1] = amountOfQuestions;
        sendData(data);
    }

    protected void startRound() {
        if (idInstance.equals("Player_1")) {
            nameOfOpponent = "Player_2";
        } else {
            nameOfOpponent = "Player_1";
        }
        sendData(game.playerTurn(idInstance));
    }


    protected void gameFinished() {
        Data data = new Data(Task.GAME_RESULT);
        String[] theWinner = game.getWinner();
        String[] sendArray = new String[3];
        if (theWinner[0].equals(idInstance)) {
            sendArray[0] = "WON";
        } else if (theWinner[0].equals("DRAW")) {
            sendArray[0] = "DRAW";
        } else {
            sendArray[0] = "LOSE";
        }
        sendArray[1] = theWinner[1];
        sendArray[2] = theWinner[2];
        data.result = sendArray;
        sendData(data);
    }
}
