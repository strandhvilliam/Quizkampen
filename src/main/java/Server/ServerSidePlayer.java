package Server;


import TransferData.Data;
import TransferData.Task;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

public class ServerSidePlayer extends Thread implements Serializable {
    final String idInstance;

    String nameOfPlayer;
    List<Boolean> scorePlayer;  // räkna antal poäng för spelare.
    ServerSidePlayer opponent;
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    ServerGame game;
    Database db = new Database();

    private final int amountOfRounds;
    private final int amountOfQuesitons;

    int rounderCounter = 1;

    protected boolean isWaiting = false;


    public ServerSidePlayer(Socket socket, String idInstance, ServerGame game) throws IOException {
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

    public void sendOpponentName(String name){
        Data data = new Data();
        data.task = Task.OPPONENT_NAME;
        data.message = name;
        sendData(data);
    }

    public String getNameOfPlayer() {
        return this.nameOfPlayer;
    }

    public String getOpponentName() {
        return opponent.nameOfPlayer;
    }


    public void setOpponent(ServerSidePlayer opponent) {
        this.opponent = opponent;
    }

    public void setScore(List<Boolean> scores) {
        //scorePlayer.addAll(scores);
        scorePlayer = new ArrayList<>(scores);
    }


    public void sendData(Data data) {


        try {
            output.writeObject(data);
            flushAndReset();
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* } else {
//            output.writeObject(scoresOpponent);
        }*/
    }

    public void run() {
        System.out.println("All players have connected (2).");
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            Data object;

            while (true) {
                object = (Data) input.readObject();
                dataProtocol(object);

            }
        } catch (IOException e) {
            System.out.println("Could not find server: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        /*TODO: lägg till logiken för spelet. Om spelare svarar rätt spara antal poäng,
           om spelare svarar fel spara antal fel eventuellt skicka till server*/

        /* WHILE output inte är tom
         *       OM output är en instans av lista med booleans
         *           skicka statistik från rond till båda klienterna
         *       OM output är en category (från en av klienterna)
         *           skicka frågor ur rätt kategori till båda klienterna
         *
         */
    }

    protected void dataProtocol(Data data) throws IOException {
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

    public void flushAndReset() {
        try {
            output.flush();
            output.reset();
        } catch (IOException e) {
            System.out.println("Could not flush / reset object stream" + " " + e.getMessage());
        }

    }

    protected void setCategory(Data data) throws IOException {
        List<Question> listOfQuestions = db.getByCategory(data.category);
        output.writeObject(listOfQuestions);
    }

    protected void roundFinished() throws IOException {
        if (!opponent.isWaiting) {
            this.isWaiting = true;
        } else {
            this.isWaiting = false;
            opponent.isWaiting = false;
            Data data = new Data();
            data.task = Task.OPPONENT_SCORE;
            data.score = scorePlayer;

            sendData(data);
            opponent.sendData(data);

//                            System.out.println(getScore() + " --> " + opponent.getScore());
//                            game.getScore(getScore(), getName());
//                            game.getScore(opponent.getScore(), getName());

        }
    }


    protected void startGame(Data startGame) throws IOException {
        setNameOfPlayer(startGame.message);
        opponent.sendOpponentName(getNameOfPlayer());
    }

    protected void propertiesProtocol() throws IOException {
        int[] propertiesValues = new int[2];
        propertiesValues[0] = amountOfRounds;
        propertiesValues[1] = amountOfQuesitons;
        output.writeObject(propertiesValues);
    }

    protected void startRound() throws IOException {
        Data data = new Data();
        if (game.playerTurn(this)) {
            data.task = Task.CHOOSE_CATEGORY;
        } else {
            data.task = Task.NOT_YOUR_TURN;
        }
        sendData(data);
    }


    protected void gameFinished() throws IOException {
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

        output.writeObject(sendArray);
        //TODO: implementera resultat av vinnare / förlorare
    }


}
//Todo: Lägga till metod ifall en person har vunnit trots att det fortfarande finns ronder kvar att spela