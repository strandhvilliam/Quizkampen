package Models;


import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    public Task task;

    public Data() {
    }

    public Data(Task task) {
        this.task = task;
    }

    public List<Boolean> score;
    public Category category;
    public String[] arrayOfStrings;
    public String[] result;
    public int[] properties;
    public List<Boolean> listOfBooleans;
    public String message;
    public List<Question> listOfQuestions;


}
