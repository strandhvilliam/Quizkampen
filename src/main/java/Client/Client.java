package Client;


import Models.Data;
import Models.Question;
import Models.Task;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Client extends javafx.concurrent.Task<Void> {


    private final String serverAddress;


    private final static int PORT = 5555;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private ClientGame game;


    public Client(String serverAdress) throws IOException {
        this.serverAddress = serverAdress;

    }

    @Override
    protected Void call() {
        try (Socket socket = new Socket(serverAddress, PORT)) {
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

            case PROPERTIES_PROTOCOL -> initProperties(data);
            case CHOOSE_CATEGORY -> showCategoryWindow();
            case NOT_YOUR_TURN -> waitForOpponent();
            case OPPONENT_SCORE -> showStatistics(data);
            case OPPONENT_NAME -> initOpponentName(data);
            case GAME_RESULT -> displayGameResult(data);
            case SET_QUESTIONS -> setQuestions(data);
            case SEND_MESSAGE -> receiveMessage(data);
        }
    }

    private void showStatistics(Data data) {
        Platform.runLater(() -> game.displayStatisticsWindow(data.listOfBooleans));
        System.out.println("Client received " + data.listOfBooleans);
    }

    private void waitForOpponent() {
        Platform.runLater(() -> game.displayWaitingWindow());
    }

    private void showCategoryWindow() {
        Platform.runLater(() -> game.displayCategoryWindow());
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
        Data req = new Data(Task.PROPERTIES_PROTOCOL);
        sendObject(req);
    }


    private void setQuestions(Data data) {
        List<Question> questions = data.listOfQuestions;
        Platform.runLater(() -> game.startRound(questions));
    }

    protected void displayGameResult(Data data) {
        String[] gameResult = data.result;
        Platform.runLater(() -> game.displayResultWindow(gameResult));
    }

    protected void initProperties(Data data) {
        Platform.runLater(() -> game.setProperties(data.properties[0], data.properties[1]));
        sendObject(new Data(Task.START_ROUND));
    }

    public void setGame(ClientGame clientGame) {
        this.game = clientGame;
    }
    public void receiveMessage(Data data){
        String message = data.message;
        Platform.runLater(() -> game.receiveMessage(message));
    }
}

