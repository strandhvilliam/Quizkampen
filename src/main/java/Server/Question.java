package Server;


import java.io.Serializable;

public class Question implements Serializable {
    protected String question;
    protected String correctAnswer;
    protected Category category;
    protected String[] wrongAnswers = new String[3];

    public Question(String question, String correctAnswer, Category category, String[] wrongAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.category = category;
        for (int i = 0; i < 3; i++) {
            this.wrongAnswers[i] = wrongAnswers[i];
        }
    }

    public Question(String dbQuestion, String[] dbAnswersArr, String dbCategory) {
    }
    public String getQuestion() {
        return question;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String[] getWrongAnswers() {
        return wrongAnswers;
    }
}