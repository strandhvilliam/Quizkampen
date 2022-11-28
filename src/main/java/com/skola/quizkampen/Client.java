package com.skola.quizkampen;


import Server.Question;
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
    private static final String START_GAME = "START_GAME";
    private static final String START_ROUND = "START_ROUND";
    public static final String GAME_FINISHED = "GAME_FINISHED";
    public static final String ROUND_FINISHED = "ROUND_FINISHED";



    private final String serverAddress;
    private Socket socket;
    private final static int PORT = 5555;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private final ClientController controller;


    public Client(String serverAdress, ClientController controller) {
        this.serverAddress = serverAdress;
        this.controller = controller;

    }

    @Override
    protected Void call() {
        try {
            socket = new Socket(serverAddress, PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("inside call");

            Object fromServer;
            while ((fromServer = inputStream.readObject()) != null) {
                processResponse(fromServer);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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


    public void requestCategoryQuestions(Category category) {
        System.out.println("requestCategoryQuestions + " + category.name);
        sendObject(category.name);
    }

    public void requestStartGame(String username) {
        String[] req = {START_GAME, username};
        sendObject(req);
    }

    public void processResponse(Object resFromServer) throws IOException {
        if (resFromServer instanceof List) {
            if (((List<?>) resFromServer).get(0) instanceof Question) {
                List<Question> questionsForRound = (List<Question>) resFromServer;
                //TODO: kolla hur många frågor per rond och avgör storlek på lista efter det
                Platform.runLater(() -> {
                    try {
                        controller.startRound(questionsForRound);
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
                Platform.runLater(() -> controller.initOpponent(resArray[1]));
                System.out.println("inside opponent name");
                sendObject(PROPERTIES_PROTOCOL);
            } else {
                Platform.runLater(() -> controller.displayGameResult(resArray));
            }

        } else if (resFromServer instanceof int[]) {
            int[] properties = (int[]) resFromServer;
            Platform.runLater(() -> controller.totalNumOfRounds = properties[0]);
            Platform.runLater(() -> controller.questionsPerRound = properties[1]);
            System.out.println("inside properties");
            requestNewRound();
        } else if (resFromServer instanceof String) {
            String res = (String) resFromServer;
            if (res.equals(CHOOSE_CATEGORY)) {
                Platform.runLater(() -> controller.displayCategoryChooser());
            } else if (res.equals(NOT_YOUR_TURN)) {
                Platform.runLater(() -> controller.displayWaitingWindow());
            }
            System.out.println("inside string");
        }
    }

    public void requestNewRound() {
        sendObject(START_ROUND);
    }

    public void requestGameOver() {
        sendObject(GAME_FINISHED);
    }

    public void requestPlayerDoneWithRound() {
        sendObject(ROUND_FINISHED);
    }
}
