package Client;

import Models.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

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


    private ClientGame game;

    private Question currentQuestion;


    @FXML
    public void optionSelectedAction(ActionEvent event){
        Button button = (Button) event.getSource();
        if (button.getText().equals(currentQuestion.getCorrectAnswer())) {
            button.setBackground(Background.fill(Color.GREEN));
        }
        else {
            button.setBackground(Background.fill(Color.RED));
        }
        //TODO: lägg in kod så att GUI reagerar med grönt om rätt, rött om fel

        processPlayerAnswer(button.getText());
        game.displayQuestionWindow();
    }

    private void processPlayerAnswer(String userAnswer) {
        game.addPlayerScore(userAnswer.equals(currentQuestion.getCorrectAnswer()));
    }


    private void setOptionButtons(List<String> wrongAlternatives, String correctAnswer) {
        List<String> allAnswers = new ArrayList<>(wrongAlternatives);
        allAnswers.add(correctAnswer);
        Collections.shuffle(allAnswers);
        optionOneButton.setBackground(Background.fill(Color.WHITE));
        optionTwoButton.setBackground(Background.fill(Color.WHITE));
        optionThreeButton.setBackground(Background.fill(Color.WHITE));
        optionFourButton.setBackground(Background.fill(Color.WHITE));
        optionOneButton.setText(allAnswers.get(0));
        optionTwoButton.setText(allAnswers.get(1));
        optionThreeButton.setText(allAnswers.get(2));
        optionFourButton.setText(allAnswers.get(3));
    }


    public void initData(ClientGame game, Question question) {
        this.game = game;
        this.currentQuestion = question;
        setOptionButtons(List.of(question.getWrongAnswers()), question.getCorrectAnswer());
        questionLabel.setText(question.getQuestion());
        categoryLabel.setText(question.getCategory().name);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}