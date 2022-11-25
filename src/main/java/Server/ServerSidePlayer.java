package Server;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
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

    private static final String START_GAME = "START_GAME";

    private final static String START_ROUND = "START_ROUND";
    private static final String PROPERTIES_PROTOCOL = "PROPERTIES_PROTOCOL";
    private static final String OPPONENT_NAME = "OPPONENT_NAME";

    public static final String ROUND_FINISHED = "ROUND_FINISHED";

    protected boolean isWaiting = false;

    private static final String CHOOSE_CATEGORY = "CHOOSE_CATEGORY";
    private static final String NOT_YOUR_TURN = "NOT_YOUR_TURN";
    private static final String GAME_FINISHED = "GAME_FINISHED";


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

    public void sendOpponentName(String name) throws IOException {
        String[] respons_name = {OPPONENT_NAME, name};
        output.writeObject(respons_name);
        flushAndReset();
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

    public List<Boolean> getScore() {
        return this.scorePlayer;
    }


    public void sendOpponentScoreStat() throws IOException {
        //output.writeObject(opponent.getScore());
//        if(!opponant.getScore().isEmpty()){

        /*System.out.println("----\n" );
        for (Boolean aBoolean : opponent.getScore()) {
            System.out.println(aBoolean);
        }
        System.out.println("----\n" );*/  // TODO: Ändra tillbaka detta

        output.writeObject(opponent.getScore());
        flushAndReset();

       /* } else {
//            output.writeObject(scoresOpponent);
        }*/
    }

    public void run() {
        System.out.println("All players have connected (2).");
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            Object object;

            while (true) {
                object = input.readObject();
                if (object instanceof String[]) {
                    String[] array = (String[]) object;
                    if (array[0].equals(START_GAME)) {
                        setNameOfPlayer(array[1]);
                        opponent.sendOpponentName(getNameOfPlayer());
                    }
                }
                if (object instanceof List<?>) {
                    setScore((List<Boolean>) object);
                    game.getScore(scorePlayer, idInstance);
                } else if (object instanceof String s) {
                    if (s.equals(ROUND_FINISHED)) {
                        if (!opponent.isWaiting) {
                            this.isWaiting = true;
                        } else {
                            this.isWaiting = false;
                            opponent.isWaiting = false;
                            sendOpponentScoreStat();
                            opponent.sendOpponentScoreStat();
//                            System.out.println(getScore() + " --> " + opponent.getScore());
//                            game.getScore(getScore(), getName());
//                            game.getScore(opponent.getScore(), getName());

                        }

                    } else if (s.equals(PROPERTIES_PROTOCOL)) {
                        int[] propertiesValues = new int[2];
                        propertiesValues[0] = amountOfRounds;
                        propertiesValues[1] = amountOfQuesitons;
                        output.writeObject(propertiesValues);
                    } else if (s.equals(START_ROUND)) {
                        if (game.playerTurn(this)) {
                            output.writeObject(CHOOSE_CATEGORY);
                        } else {
                            output.writeObject(NOT_YOUR_TURN);
                        }

                    } else if(s.equals(GAME_FINISHED)){
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
                } else if (object instanceof Category) {
                    Category tempCategory = (Category) object;
                    List<Question> listOfQuestions = db.getByCategory(tempCategory);
                    output.writeObject(listOfQuestions);
                }
                flushAndReset();
                /*System.out.println(scorePlayer.size());
                System.out.println(opponant.getScore().size());

                output.writeObject(scorePlayer);
                if(this.scorePlayer.size() == 2 && opponant.getScore().size() == 2){
                    sendOpponentScoreStat();
                }*/
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

    public void showMessage(String s) {
        System.out.println(s);
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
}
//Todo: Lägga till metod ifall en person har vunnit trots att det fortfarande finns ronder kvar att spela