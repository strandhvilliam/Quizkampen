package Server;


import TransferData.Data;
import TransferData.Task;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ServerSidePlayer extends Thread implements Serializable {
    final String idInstance;  // för att servern ska kunna skilja på instanserna

    String nameOfPlayer;  // för att spelaren ska kunna välja namn
    List<Boolean> scorePlayer;  // räkna antal poäng för spelare.
    String nameOfOpponent;
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    ServerGame game;
    Database db = new Database();

    private final int amountOfRounds;
    private final int amountOfQuesitons;
    int rounderCounter = 1;

    protected boolean isWaiting = false;


    public ServerSidePlayer(Socket socket, String idInstance, ServerGame game) {
        this.socket = socket;
        this.idInstance = idInstance;
        this.game = game;
        this.scorePlayer = new ArrayList<>();


        Properties p = new Properties();
        this.amountOfRounds = db.getRoundsPerGame();
        this.amountOfQuesitons = db.getQuestionsPerRound();

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

        /* WHILE output inte är tom
         *       OM output är en instans av lista med booleans
         *           skicka statistik från rond till båda klienterna
         *       OM output är en category (från en av klienterna)
         *           skicka frågor ur rätt kategori till båda klienterna
         *
         */
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
        }
    }

    public void showMessage(String s) {
        System.out.println(s);
    }

    protected void setScore(Data data) {
        setScore(data.score);
        game.getScore(scorePlayer, idInstance);
    }

    public void checkAnswer(String score) {
        this.scorePlayer.add(Boolean.valueOf(score));  // användas för att spara svar från spelare.
    }


    protected void setCategory(Data data) {
        Data data1 = new Data();
        List<Question> listOfQuestions = db.getByCategory(data.category);
        data1.task = Task.SET_QUESTIONS;
        data1.listOfQuestions = listOfQuestions;
        sendData(data1);
    }


    protected void startGame(Data data) {
        setNameOfPlayer(data.message);
        startRound();
    }

    protected void propertiesProtocol() {
        Data data = new Data();
        data.task = Task.PROPERTIES_PROTOCOL;
        data.properties = new int[2];
        data.properties[0] = amountOfRounds;
        data.properties[1] = amountOfQuesitons;
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
        Data data = new Data();
        data.task = Task.GAME_FINISHED;
        String[] theWinner = game.getWinner();
        String[] sendArray = new String[3];
        if (theWinner[0].equals(idInstance)) {
            sendArray[0] = "WON";
        } else if (!theWinner[0].equals("DRAW")) {
            sendArray[0] = "DRAW";
        } else {
            sendArray[0] = "LOSE";
        }
        sendArray[1] = theWinner[1];
        sendArray[2] = theWinner[2];
        data.result = sendArray;
        sendData(data);
        //TODO: implementera resultat av vinnare / förlorare
    }


}
//Todo: Lägga till metod ifall en person har vunnit trots att det fortfarande finns ronder kvar att spela