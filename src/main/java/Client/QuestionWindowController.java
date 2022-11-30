package Client;

import Models.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
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
    private Button optionOneButton;
    @FXML
    private Button optionTwoButton;
    @FXML
    private Button optionThreeButton;
    @FXML
    private Button optionFourButton;

    @FXML
    private Button nextQuestionButton;
    //testkommentar
    //testkommentar 2


    private ClientGame game;

    private Question currentQuestion;

    List<Button> buttons = new ArrayList<>();

    @FXML
    public void optionSelectedAction(ActionEvent event) {
        Button button = (Button) event.getSource();

        //TODO: lägg in kod så att GUI reagerar med grönt om rätt, rött om fel



        processPlayerAnswer(button);
        //game.displayQuestionWindow();
    }

    //nextRoundButtonClicked {
    //      game.displayQuestionWindow()

    private void processPlayerAnswer(Button clickedButton) {
        String correctAnswer = currentQuestion.getCorrectAnswer();
        for (Button b : buttons) {
            if (b.getText().equals(correctAnswer)) {
                b.setStyle("-fx-border-color: #48cb27");
                b.setMouseTransparent(true);
            } else {
                b.setStyle("-fx-border-color: #FE4545");
                b.setMouseTransparent(true);
            }
        }
        if (clickedButton.getText().equals(correctAnswer)) {
            game.addPlayerScore(true);
            clickedButton.setStyle("-fx-background-color: #48cb27; -fx-text-fill: #ffffff");
        } else {
            game.addPlayerScore(false);
            clickedButton.setStyle("-fx-background-color: #FE4545; -fx-text-fill: #ffffff");
        }

        nextQuestionButton.setDisable(false);

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

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
