package com.skola.quizkampen;

import Server.Question;
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
    private Button optionOneButton;
    @FXML
    private Button optionTwoButton;
    @FXML
    private Button optionThreeButton;
    @FXML
    private Button optionFourButton;


    private ClientController clientController;

    private Question currentQuestionRound;




    @FXML
    public void optionSelectedAction(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();

        //TODO: lägg in kod så att GUI reagerar med grönt om rätt, rött om fel

        checkIfCorrectAnswer(button.getText());
        clientController.displayNextQuestion();
        questionLabel.getScene().getWindow().hide();
    }

    private void checkIfCorrectAnswer(String userAnswer) {
        if (userAnswer.equals(currentQuestionRound.getCorrectAnswer())) {
            clientController.roundResult.add(true);
        } else {
            clientController.roundResult.add(false);
        }
    }


    private void setOptionButtons(List<String> wrongAlternatives, String correctAnswer) {
        List<String> allAnswers = new ArrayList<>(wrongAlternatives);
        allAnswers.add(correctAnswer);
        Collections.shuffle(allAnswers);
        optionOneButton.setText(allAnswers.get(0));
        optionTwoButton.setText(allAnswers.get(1));
        optionThreeButton.setText(allAnswers.get(2));
        optionFourButton.setText(allAnswers.get(3));
    }



    public void initData(ClientController clientController, Question question) {
        this.clientController = clientController;
        this.currentQuestionRound = question;
        setOptionButtons(List.of(question.getWrongAnswers()), question.getCorrectAnswer());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
