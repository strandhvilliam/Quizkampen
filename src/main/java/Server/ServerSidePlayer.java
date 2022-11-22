package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSidePlayer extends Thread implements Serializable {
    String nameOfPlayer;
    List<Boolean> scorePlayer;  // räkna antal poäng för spelare.
    ServerSidePlayer opponant;
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    ServerGame game;
    int rounderCounter = 1;

    protected boolean isWaiting = false;


    public ServerSidePlayer(Socket socket, String nameOfPlayer, ServerGame game) {
        this.socket = socket;
        this.nameOfPlayer = nameOfPlayer;
        this.game = game;
        this.scorePlayer = new ArrayList<>();
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

    public void setScore(List<Boolean> scores) {
        scorePlayer.addAll(scores);
    }

    public List<Boolean> getScore() {
        return this.scorePlayer;
    }


    public void sendOpponentScoreStat() throws IOException {
//        if(!opponant.getScore().isEmpty()){
        output.writeObject(opponant.getScore());
        output.flush();
        output.reset();

       /* } else {
//            output.writeObject(scoresOpponent);
        }*/
    }

    public void run() {
        System.out.println("All players have connected (2).");
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            System.out.println("Welcome " + nameOfPlayer);

            Object object;
            while (true) {
                object = input.readObject();
                if (object instanceof List<?>) {
                    setScore((List<Boolean>) object);
                } else if (object instanceof String) {
                    String s = (String) object;
                    if (s.equals("ROUND_FINISHED")) {
                        if (!opponant.isWaiting) {
                            this.isWaiting = true;
                        } else {
                            this.isWaiting = false;
                            opponant.isWaiting = false;
                            sendOpponentScoreStat();
                            opponant.sendOpponentScoreStat();
                        }
                    }
                }


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
}


//Todo: Lägga till metod ifall en person har vunnit trots att det fortfarande finns ronder kvar att spela