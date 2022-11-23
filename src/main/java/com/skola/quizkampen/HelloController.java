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

        for (int i = 0; i < 9; i++) {
            System.out.println(questionArrayList.get(i).getCategory() + "\n" + questionArrayList.get(i).getQuestion() + "\n" + questionArrayList.get(i).getCorrectAnswer());
            System.out.println(questionArrayList.get(i).getWrongAnswers()[0]);
            System.out.println(questionArrayList.get(i).getWrongAnswers()[1]);
            System.out.println(questionArrayList.get(i).getWrongAnswers()[2]);
        }
    }
}
