package Client;

import Models.Question;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class QuestionWindowController implements Initializable {


    @FXML
    private Label questionLabel = new Label();

    @FXML
    private Label categoryLabel = new Label();

    @FXML
    private ProgressIndicator pIndicator;

    @FXML
    private Button optionOneButton;
    @FXML
    private Button optionTwoButton;
    @FXML
    private Button optionThreeButton;
    @FXML
    private Button optionFourButton;

    @FXML
    private Button nextQuestionButton;

    @FXML
    private Label countDownLabel;

    private ClientGame game;

    private Question currentQuestion;

    List<Button> buttons = new ArrayList<>();

    Timer timer;

    @FXML
    public void optionSelectedAction(ActionEvent event) {
        Button button = (Button) event.getSource();
        processPlayerAnswer(button);
        timer.stopTimer();
    }


    private void processPlayerAnswer(Button clickedButton) {
        setButtonColours(clickedButton);
    }

    public void setButtonColours(Button clickedButton) {
        setBorderButton();
        setBackgroundButton(clickedButton);
        nextQuestionButton.setDisable(false);

    }

    private void setBorderButton() {
        for (Button b : buttons) {
            if (b.getText().equals(currentQuestion.getCorrectAnswer())) {
                b.setStyle("-fx-border-color: #48cb27");
                b.setMouseTransparent(true);
            } else {
                b.setStyle("-fx-border-color: #FE4545");
                b.setMouseTransparent(true);
            }
        }
    }

    private void setBackgroundButton(Button clickedButton) {
        if (clickedButton.getText().equals(currentQuestion.getCorrectAnswer())) {
            game.addPlayerScore(true);
            clickedButton.setStyle("-fx-background-color: #48cb27; -fx-text-fill: #ffffff");
            countDownLabel.textProperty().unbind();
            countDownLabel.setText("");
            countDownLabel.setGraphic(new ImageView(getClass().getResource("icons/icons8_ok_127px.png").toExternalForm()));
            countDownLabel.setStyle("-fx-background-color: #fff");
        } else {
            game.addPlayerScore(false);
            clickedButton.setStyle("-fx-background-color: #FE4545; -fx-text-fill: #ffffff");
            pIndicator.setVisible(false);
            countDownLabel.textProperty().unbind();
            countDownLabel.setText("");
            countDownLabel.setGraphic(new ImageView(getClass().getResource("icons/icons8_cancel_127px.png").toExternalForm()));
            countDownLabel.setStyle("-fx-background-color: #fff");
        }
    }

    public void nextQuestionAction() {
        game.displayQuestionWindow();
    }


    private void setOptionButtons(List<String> wrongAlternatives, String correctAnswer) {
        List<String> allAnswers = new ArrayList<>(wrongAlternatives);
        allAnswers.add(correctAnswer);
        Collections.shuffle(allAnswers);

        buttons.add(optionOneButton);
        buttons.add(optionTwoButton);
        buttons.add(optionThreeButton);
        buttons.add(optionFourButton);

        for (int i = 0; i < allAnswers.size(); i++) {
            buttons.get(i).setText(allAnswers.get(i));
        }
    }


    public void initData(ClientGame game, Question question) {
        this.game = game;
        this.currentQuestion = question;
        setOptionButtons(List.of(question.getWrongAnswers()), question.getCorrectAnswer());
        questionLabel.setText(question.getQuestion());
        categoryLabel.setText(question.getCategory().name);
        nextQuestionButton.setDisable(true);

        countDownLabel.textProperty().bind(pIndicator.progressProperty().multiply(10).asString("%.0f"));
        timer = new Timer();
        Thread thread = new Thread(timer);
        thread.start();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    class Timer extends Task<Void> {

        private Timeline timeline;
        @Override
        protected Void call() throws Exception {
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(10),
                            e -> {
                                System.out.println("times, up");
                                buttons.forEach(b -> b.setMouseTransparent(true));
                                setBorderButton();
                                nextQuestionButton.setDisable(false);
                                game.addPlayerScore(false);
                                countDownLabel.textProperty().unbind();
                                countDownLabel.setText("");
                                countDownLabel.setGraphic(new ImageView(getClass().getResource("icons/icons8_cancel_127px.png").toExternalForm()));
                                countDownLabel.setStyle("-fx-background-color: #fff");
                            },
                            new KeyValue(pIndicator.progressProperty(), 0)),
                    new KeyFrame(Duration.ZERO,
                            e -> System.out.println("start"),
                            new KeyValue(pIndicator.progressProperty(), 1)));

            timeline.play();
            return null;
        }

        public void stopTimer() {
            timeline.stop();
        }
    }
}
