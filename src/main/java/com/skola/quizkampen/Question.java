package com.skola.quizkampen;

import java.util.ArrayList;
import java.util.List;

public class Question {


    private String question;
    private String correctAnswer;
    private List<String> wrongAlternatives;

    private Category category;


    public Question(String question, String correctAnswer, Category c) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.category = c;
        this.wrongAlternatives = List.of("Fel Alt 1", "Fel Alt 2", "Fel Alt 3");
    }

    public String getQuestion() {
        return question;
    }

    public Category getCategory() {
        return category;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getWrongAlternatives() {
        return wrongAlternatives;
    }
}
