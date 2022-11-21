package com.skola.quizkampen;

import java.util.ArrayList;
import java.util.List;

public class Database extends Question {

    static final List<Question> allQuestions = new ArrayList<>();

    public Database(String question, String correctAnswer) {
        super();
    }

        public String getQuestion (String q) {
            for (Question question : allQuestions) {
                if (question.getQuestion().equalsIgnoreCase(q)) {
                    return question.getCorrectAnswer();
                }
            }
            return null;
        }


        public static void main (String[]args) {
    }
}
