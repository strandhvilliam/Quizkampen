package com.skola.quizkampen;


import TransferData.Data;
import TransferData.Task;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends javafx.concurrent.Task<Void> {


    private final String serverAddress;


    private Socket socket;
    private final static int PORT = 5555;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private ClientGame game;


    public Client(String serverAdress) throws IOException {
        this.serverAddress = serverAdress;

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
                outputStream.flush();
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
            case CHOOSE_CATEGORY -> Platform.runLater(() -> game.displayCategoryWindow());
            case NOT_YOUR_TURN -> Platform.runLater(() -> game.displayWaitingWindow());

            case OPPONENT_NAME -> initOpponentName(data);
//            case ROUND_FINISHED -> (data);
            //case STATISTICS -> requestStatistics();
//            case GAME_FINISHED -> ();
            case GAME_RESULT -> displayGameResult(data);
//            case SET_QUESTIONS ->

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

    protected void initOpponentName(Data res) {
        Platform.runLater(() -> game.setOpponentName(res.message));
        Data req = new Data();
        req.task = Task.PROPERTIES_PROTOCOL;
        sendObject(req);
    }

    protected void displayGameResult(Data data) {
        Platform.runLater(() -> game.displayResultWindow(data));
    }

    protected void properties(Data data) {
        Platform.runLater(() ->  game.setProperties(data.properties[0], data.properties[1]));
    }

    public void setGame(ClientGame clientGame) {
        this.game = clientGame;
    }
}
