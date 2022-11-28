/*package Client;

import Models.Data;
import Models.Question;
import Models.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ClientController implements Initializable {


    private String correctAnswerForRound;

    private boolean isWaiting;

    private Client client;

    protected List<Boolean> playerScore = new ArrayList<>();
    private List<Question> questionsInRound;

    protected String opponentName;

    protected int totalNumOfRounds;
    protected int questionsPerRound;

    private Stage waitingWindow;
    protected String userName;

    public void displayNextQuestion() {
        if (questionsInRound.size() > 0) {
            Question questionToDisplay = questionsInRound.get(0);
            questionsInRound.remove(0);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("question-form.fxml"));
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            QuestionWindowController questionWindowController = loader.getController();
            questionWindowController.initData(this, questionToDisplay);

            stage.show();
        } else {
            sendResult();
        }
    }

    public void sendResult() {
        Data data = new Data();
        data.task = Task.ROUND_FINISHED;
        data.listOfBooleans = playerScore;
        client.sendObject(data);
    }


    public void displayCategoryChooser() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("categoryChooser.fxml"));
        fxmlLoader.setController(this);
        Stage stage = new Stage();
        stage.setTitle("Choose category");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("questionScene.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) currentNode.getScene().getWindow();
        Scene scene = new Scene(root);
        Platform.runLater(() -> {
            stage.setScene(scene);
            stage.show();
        }); // TODO: KANSKE ALTERNATIVT SÄTT ATT BYTA SCENER?
    }
*/
    /*@FXML
    public void chooseCategoryAction(ActionEvent event) {
        Button button = (Button) event.getSource();
        String categoryString = button.getText();

        Data categories = new Data();
        categories.task = Task.CHOOSE_CATEGORY;

        for (Category category : Category.values()) {
            if (category.name.equalsIgnoreCase(categoryString)) {
                categories.category = Category.valueOf(categoryString);
                client.sendObject(categories);
                System.out.println("Category chosen: " + category.name);
                break;
            }
        }
    } */
/*
    public void startRound(List<Question> questions) {
        this.questionsInRound = questions;

        if (isWaiting) {
            waitingWindow.getScene().getWindow().hide();
            isWaiting = false;
        }

        displayNextQuestion();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize method");

    }

    public void setupClient(Client client) {
        this.client = client;
    }

    public void displayStatistics(List<Boolean> opponentResult) {
        System.out.println("Motståndare:");
        for (Boolean b : opponentResult) {
            System.out.println(b);
        }

        System.out.println();

        System.out.println("Mitt resultat:");
        for (Boolean b : playerScore) {
            System.out.println(b);
        }

        //TODO: antal rundor variabel ska bytas ut till properties värde senare


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("statistics-window.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Choose category");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StatisticsController controller = fxmlLoader.getController();
        controller.initStatistics(this, playerScore, opponentResult, totalNumOfRounds, questionsPerRound);

        stage.show();


    }

    public void startGame(String username) {
        this.userName = username;
        Data data = new Data();
        data.message = username;
        data.task = Task.START_GAME;
        client.sendObject(data);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("waiting-window.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Waiting for opponent");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.getCause();
            e.printStackTrace();
        }
        stage.show();

    }

    public void displayWaitingWindow() {
        // TODO: Denna metod skapar krasch: EOF exception
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("waiting-window.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Choose category");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();

        isWaiting = true;
        waitingWindow = stage;
    }

    public void checkIfGameIsOver() {
        Data data = new Data();
        if (playerScore.size() == totalNumOfRounds) {
            data.task = Task.GAME_FINISHED;
            client.sendObject(data);
        } else {
            data.task = Task.ROUND_FINISHED;
            client.sendObject(data);
        }
    }

    public void displayGameResult(Data data) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("results-windows.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Results");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResultWindowController resultsWindowController = fxmlLoader.getController();

        resultsWindowController.initData(data);

        stage.show();
    }

    public void initProperties(int totalNumOfRounds, int questionsPerRound) {
        this.totalNumOfRounds = totalNumOfRounds;
        this.questionsPerRound = questionsPerRound;
        Data data = new Data();
        data.task = Task.START_ROUND;
        client.sendObject(data);
    }
} */