package TransferData;

import Server.Category;
import Server.Question;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    public Task task;

    public List<Boolean> score;
    public Category category;
    public String[] arrayOfStrings;
    public String[] result;
    public int[] properties;
    public List<Boolean> listOfBooleans;
    public String message;
    public List<Question> listOfQuestions;

}
