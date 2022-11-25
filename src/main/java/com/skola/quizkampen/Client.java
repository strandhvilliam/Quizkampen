package com.skola.quizkampen;


import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client extends Task<Void> {

    private static final String OPPONENT_NAME = "OPPONENT_NAME";

    private static final String PROPERTIES_PROTOCOL = "PROPERTIES_PROTOCOL";

    private static final String NOT_YOUR_TURN = "NOT_YOUR_TURN";
    private static final String CHOOSE_CATEGORY = "CHOOSE_CATEGORY";

    private static final String START_ROUND = "START_ROUND";



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
    protected Void call() throws Exception {
        try {
            socket = new Socket(serverAddress, PORT);
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            Object fromServer;
            while ((fromServer = inputStream.readObject()) != null) {
                processResponse(fromServer);
            }
        } catch (IOException | ClassNotFoundException e) {

        }
        return null;
    }


    public void sendObject(Object obj) {
        try {
            outputStream.writeObject(obj);
            outputStream.flush();
            outputStream.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Skickar object i form av sträng för att sätta användarnamn
     *
     * @param username som hämtas från GUI controllern
     * @throws IOException
     */
    public void initializeUser(String username /* TODO: bild för profilbild */) throws IOException {
        sendObject(username);
    }

    public void requestCategoryQuestions(Category category) {
        sendObject(category);
    }

    public void requestOtherUsername() {
        //TODO: skickar förfrågan till servern för motståndarens användarnamn
    }

    public void requestStatistics() throws IOException {
        //TODO: fundera ut vilket format vi ska skicka till servern
        List<Boolean> testOpponentList = List.of(true, true, true, false);
        processResponse(testOpponentList);
    }


    /**
     * Metoden hanterar all inkommande data från servern och väljer vad som ska göras med den
     *
     * @param resFromServer objekt som kommer från servern
     */
    public void processResponse(Object resFromServer) throws IOException {
        if (resFromServer instanceof List) {
            if (((List<?>) resFromServer).get(0) instanceof Question) {
                List<Question> questionForRound = (List<Question>) resFromServer;
                Platform.runLater(() -> {
                    try {
                        controller.startRound(questionForRound);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } else if (((List<?>) resFromServer).get(0) instanceof Boolean) {
                List<Boolean> opponentResult = (List<Boolean>) resFromServer;
                Platform.runLater(() -> controller.displayStatistics(opponentResult));
            }
        } else if (resFromServer instanceof String[]) {
            String[] resArray = (String[]) resFromServer;
            if (resArray[0].equals(OPPONENT_NAME)) {
                Platform.runLater(() -> controller.opponentName = resArray[1]);
                sendObject(PROPERTIES_PROTOCOL);
            }

        } else if (resFromServer instanceof int[]) {
            int[] properties = (int[]) resFromServer;
            Platform.runLater(() -> controller.totalNumOfRounds = properties[0]);
            Platform.runLater(() -> controller.questionsPerRound = properties[1]);
            sendObject(START_ROUND);
        } else if (resFromServer instanceof String) {
            String res = (String) resFromServer;
            if (res.equals(CHOOSE_CATEGORY)) {
                controller.displayCategoryChooser();
            } else if (res.equals(NOT_YOUR_TURN)) {
                controller.displayWaitingWindow();
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


    public static void main(String[] args) throws IOException {
    }
}
