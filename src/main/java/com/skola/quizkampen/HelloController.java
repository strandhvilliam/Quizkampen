package com.skola.quizkampen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        QuestionGenerator qg = new QuestionGenerator();
        List<Question> questionArrayList = qg.readQuestions();

        System.out.println(questionArrayList.get(0).getCategory() + "\n" + questionArrayList.get(0).getQuestion() + "\n" + questionArrayList.get(0).getCorrectAnswer());
        System.out.println(questionArrayList.get(0).getWrongAnswers()[0]);
        System.out.println(questionArrayList.get(0).getWrongAnswers()[1]);
        System.out.println(questionArrayList.get(0).getWrongAnswers()[2]);
    }
}
