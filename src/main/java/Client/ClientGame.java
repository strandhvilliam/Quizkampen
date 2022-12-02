package Client;

import Models.Category;
import Models.Data;
import Models.Question;
import Models.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientGame {

    private final Stage stage;
    private final Client client;
    private List<Boolean> playerScore;

    private int round;

    private String message;
    private int totalNumOfRounds;

    private int questionsPerRound;
    protected String playerName;
    protected String opponentName;
    private List<Question> listOfQuestions;

    ChatController cc;


    public ClientGame(Client client, Stage stage) {
        this.client = client;
        this.stage = stage;
        client.setGame(this);
    }

    public void displayQuestionWindow() {
        if (listOfQuestions.size() > 0) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("question-form.fxml"));
            stage.setTitle("Fråga");
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            QuestionWindowController questionWindowController = loader.getController();
            questionWindowController.initData(this, listOfQuestions.get(0));
            listOfQuestions.remove(0);
            System.out.println("Fråga: " + listOfQuestions.size());
            stage.show();

        } else {
            sendResult();
            displayWaitingWindow();
        }

    }

    public void displayCategoryWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("category-chooser.fxml"));
        stage.setTitle("Choose category");
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        CategoryController categoryController = loader.getController();
        categoryController.setGame(this);
    }

    public void displayWaitingWindow() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("waiting-window.fxml"));
        stage.setTitle("Väntar på motståndare");
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void displayResultWindow(String[] gameResult) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("results-window.fxml"));
        stage.setTitle("Resultat");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResultWindowController resultWindowController = fxmlLoader.getController();
        resultWindowController.initData(this, gameResult);

    }

    public void displayStatisticsWindow(List<Boolean> opponentScore) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("statistics-window.fxml"));
        stage.setTitle("Nuvarande statistik");
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StatisticsController statisticsController = loader.getController();
        statisticsController.initData(this, playerScore, opponentScore, totalNumOfRounds, questionsPerRound);
    }


    public void startGame(String username) {
        playerName = username;
        playerScore = new ArrayList<>();
        round = 0;


        Data data = new Data(Task.START_GAME);
        data.message = username;
        client.sendObject(data);

        displayWaitingWindow();
    }

    public void checkIfGameFinished() {
        Data data;
        if (round == totalNumOfRounds) {
            data = new Data(Task.GAME_FINISHED);
        } else {
            data = new Data(Task.START_ROUND);
        }
        client.sendObject(data);
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public void setProperties(int totalNumOfRounds, int questionsPerRound) {
        this.totalNumOfRounds = totalNumOfRounds;
        this.questionsPerRound = questionsPerRound;
    }

    public void sendCategory(Category category) {
        Data data = new Data(Task.CHOOSE_CATEGORY);
        data.category = category;
        client.sendObject(data);
    }

    public void sendResult() {
        Data data = new Data(Task.ROUND_FINISHED);
        data.listOfBooleans = playerScore;
        client.sendObject(data);
    }

    public void setChatController(ChatController cc) {
        this.cc = cc;
    }

    public void sendMessage(String message) {
        Data data = new Data(Task.SEND_MESSAGE);
        data.message = message;
        client.sendObject(data);
    }


    public void receiveMessage(String message) {
        this.message = message;
        System.out.println(message);
        try {
        cc.receiveMessage(message);
        } catch (NullPointerException e) {
            System.out.println(opponentName + " är inte uppkopplad i chatten och kan inte ta emot meddelandet.");
        }
    }


    public void startRound(List<Question> questionsForRound) {
        round++;
//        Collections.shuffle(questionsForRound);

        List<Question> list = new ArrayList<>();
        for (Question question : questionsForRound) {
            list.add(question);
            if (list.size() == questionsPerRound) {
                break;
            }
        }

        this.listOfQuestions = new ArrayList<>(list);
        displayQuestionWindow();
    }

    public void addPlayerScore(boolean isCorrect) {
        playerScore.add(isCorrect);
    }

}
