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
        System.out.println(questionArrayList.get(1).getCategory() + "\n" + questionArrayList.get(1).getQuestion() + "\n" + questionArrayList.get(1).getCorrectAnswer());
        System.out.println(questionArrayList.get(1).getWrongAnswers()[0]);
        System.out.println(questionArrayList.get(1).getWrongAnswers()[1]);
        System.out.println(questionArrayList.get(1).getWrongAnswers()[2]);
        System.out.println(questionArrayList.get(2).getCategory() + "\n" + questionArrayList.get(2).getQuestion() + "\n" + questionArrayList.get(2).getCorrectAnswer());
        System.out.println(questionArrayList.get(2).getWrongAnswers()[0]);
        System.out.println(questionArrayList.get(2).getWrongAnswers()[1]);
        System.out.println(questionArrayList.get(2).getWrongAnswers()[2]);
        System.out.println(questionArrayList.get(3).getCategory() + "\n" + questionArrayList.get(3).getQuestion() + "\n" + questionArrayList.get(3).getCorrectAnswer());
        System.out.println(questionArrayList.get(3).getWrongAnswers()[0]);
        System.out.println(questionArrayList.get(3).getWrongAnswers()[1]);
        System.out.println(questionArrayList.get(3).getWrongAnswers()[2]);
        System.out.println(questionArrayList.get(4).getCategory() + "\n" + questionArrayList.get(4).getQuestion() + "\n" + questionArrayList.get(4).getCorrectAnswer());
        System.out.println(questionArrayList.get(4).getWrongAnswers()[0]);
        System.out.println(questionArrayList.get(4).getWrongAnswers()[1]);
        System.out.println(questionArrayList.get(4).getWrongAnswers()[2]);
        System.out.println(questionArrayList.get(5).getCategory() + "\n" + questionArrayList.get(5).getQuestion() + "\n" + questionArrayList.get(5).getCorrectAnswer());
        System.out.println(questionArrayList.get(5).getWrongAnswers()[0]);
        System.out.println(questionArrayList.get(5).getWrongAnswers()[1]);
        System.out.println(questionArrayList.get(5).getWrongAnswers()[2]);
        System.out.println(questionArrayList.get(6).getCategory() + "\n" + questionArrayList.get(6).getQuestion() + "\n" + questionArrayList.get(6).getCorrectAnswer());
        System.out.println(questionArrayList.get(6).getWrongAnswers()[0]);
        System.out.println(questionArrayList.get(6).getWrongAnswers()[1]);
        System.out.println(questionArrayList.get(6).getWrongAnswers()[2]);
        System.out.println(questionArrayList.get(7).getCategory() + "\n" + questionArrayList.get(7).getQuestion() + "\n" + questionArrayList.get(7).getCorrectAnswer());
        System.out.println(questionArrayList.get(7).getWrongAnswers()[0]);
        System.out.println(questionArrayList.get(7).getWrongAnswers()[1]);
        System.out.println(questionArrayList.get(7).getWrongAnswers()[2]);

    }
}
