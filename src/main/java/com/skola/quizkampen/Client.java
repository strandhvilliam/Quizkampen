package com.skola.quizkampen;


import Server.Question;
import TransferData.Data;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Client extends Task<Void> {

    private static final String OPPONENT_NAME = "OPPONENT_NAME";

    private static final String PROPERTIES_PROTOCOL = "PROPERTIES_PROTOCOL";

    private static final String NOT_YOUR_TURN = "NOT_YOUR_TURN";
    private static final String CHOOSE_CATEGORY = "CHOOSE_CATEGORY";

    private static final String START_ROUND = "START_ROUND";

    public static final String GAME_FINISHED = "GAME_FINISHED";

    public static final String ROUND_FINISHED = "ROUND_FINISHED";


    private final String serverAddress;


    private Socket socket;
    private final static int PORT = 5555;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private final ClientController controller;


    public Client(String serverAdress, ClientController controller) throws IOException {
        this.serverAddress = serverAdress;
        this.controller = controller;

    }

    @Override
    protected Void call() {
        try {
            socket = new Socket(serverAddress, PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            Data fromServer;
            while ((fromServer = (Data) inputStream.readObject()) != null) {
                System.out.println("Client received " + fromServer.task);
                dataProtocol(fromServer);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.getStackTrace();
        } finally {
            try {
                System.out.println("Socket closed " + controller.userName);
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    protected void dataProtocol(Data data) {
        switch (data.task) {

            // TODO: Fortsätt här. Skapa metoder för varje uppdrag enligt switch.

//            case START_GAME -> (data);
//            case PROPERTIES_PROTOCOL -> ();
             case CHOOSE_CATEGORY -> controller.displayCategoryChooser();  // TODO: EOF EXCEPTION
             case NOT_YOUR_TURN -> controller.displayWaitingWindow();  // TODO: EOF EXCEPTION

            case OPPONENT_NAME -> opponentsName(data);
//            case ROUND_FINISHED -> (data);
            case STATISTICS -> requestStatistics();
//            case GAME_FINISHED -> ();
            case GAME_RESULT -> displayGameResult(data);
//            case SET_QUESTIONS ->
//            case START_ROUND -> ();

            // TODO: DO THIS SHIT
        }
    }


    public void sendObject(Data obj) {
        try {
            System.out.println("Client tries to send " + obj.task);
            outputStream.writeObject(obj);
            System.out.println("Client has sent " + obj.task);
            outputStream.flush();
            outputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Skickar object i form av sträng för att sätta användarnamn
     *
     * @param username som hämtas från GUI controllern
     * @throws IOException
     */
    public void initializeUser(Data username /* TODO: bild för profilbild */) throws IOException {
        sendObject(username);
    }

//    public void requestCategoryQuestions(Data category) {
//        sendObject(category);
//    }

    public void requestOtherUsername() {
        //TODO: skickar förfrågan till servern för motståndarens användarnamn
    }

    public void requestStatistics() {
        Data data = new Data();
        data.task = TransferData.Task.STATISTICS;
//        data.listOfBooleans =
        // Spara till senare.
        //TODO: fundera ut vilket format vi ska skicka till servern
        processResponse(data);
    }

    protected void opponentsName(Data data1) {
        Platform.runLater(() -> controller.opponentName = data1.message);
        Data data = new Data();
        data.task = TransferData.Task.PROPERTIES_PROTOCOL;
        sendObject(data);
    }

    protected void displayGameResult(Data data) {
        Platform.runLater(() -> controller.displayGameResult(data));
    }

    protected void properties(Data data) {
        // TODO: Fortsätt här in the next rework in jersey shore.
        Platform.runLater(() -> controller.totalNumOfRounds = data.properties[0]);
        Platform.runLater(() -> controller.questionsPerRound = data.properties[1]);
        requestNewRound();
    }


    /**
     * Metoden hanterar all inkommande data från servern och väljer vad som ska göras med den
     *
     * @param resFromServer objekt som kommer från servern
     */
    public void processResponse(Data resFromServer) {
        if (resFromServer instanceof List) {
            if (((List<?>) resFromServer).get(0) instanceof Question) {
                List<Question> questionsForRound = (List<Question>) resFromServer;
                //TODO: kolla hur många frågor per rond och avgör storlek på lista efter det
                Platform.runLater(() -> {
                    controller.startRound(questionsForRound);
                });
            } else if (((List<?>) resFromServer).get(0) instanceof Boolean) {
                List<Boolean> opponentResult = (List<Boolean>) resFromServer;
                Platform.runLater(() -> controller.displayStatistics(opponentResult));
            }
        }

        /*


        OM servern skickar en lista med frågor från ur en kategori
            starta runda med frågor
            startRound(List<Questions>)
            controller.startRound
        OM servern skickar resultat från föregående omgång
            be controllern visa ruta med statistik och knapp att börja nästa omgång
        OM servern skickar motståndarens namn
            be controllern initiera GUIn med motståndarens namn

         */

    }

    public void requestNewRound() {
//        sendObject(START_ROUND);
    }

    public void requestGameOver() {
//        sendObject(GAME_FINISHED);
    }


    public static void main(String[] args) throws IOException {
    }


    public void requestPlayerDoneWithRound() {
//        sendObject(ROUND_FINISHED);
    }
}
