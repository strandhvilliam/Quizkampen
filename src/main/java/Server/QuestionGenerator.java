package Server;

import com.skola.quizkampen.Category;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionGenerator {
    private String row;
    private String question;
    private String rightAnswer;
    private String wrongAnswer;
    List<Question> questionList = new ArrayList<Question>();
    private String[] wrongAnswers = new String[3];


    private com.skola.quizkampen.Category category;
    public List<Question> getAllQuestions(){
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\javamapp\\Quizkampen\\src\\main\\java\\com\\skola\\quizkampen\\questions"))) {
            while((row = br.readLine()) != null){
                if (!row.isBlank()) {
                    if (row.startsWith("spel")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        category = com.skola.quizkampen.Category.SPEL;
                    }
                    if (row.startsWith("geografi")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        category = com.skola.quizkampen.Category.GEOGRAFI;
                    }
                    if (row.startsWith("historia")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        category = com.skola.quizkampen.Category.HISTORIA;
                    }
                    if (row.startsWith("film")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        category = com.skola.quizkampen.Category.FILM;
                    }
                    if (row.startsWith("djur")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        category = com.skola.quizkampen.Category.DJUR;
                    }
                    if (row.startsWith("teknik")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        category = com.skola.quizkampen.Category.TEKNIK;
                    }
                    if (row.startsWith("wrong")) {
                        int space = row.indexOf(' ');
                        wrongAnswer = row.substring(space + 1);
                        wrongAnswers[i] = wrongAnswer;
                        i++;
                    }
                    if (row.startsWith("correct")) {
                        int space = row.indexOf(' ');
                        rightAnswer = row.substring(space + 1);
                        questionList.add(new Question(question, rightAnswer, category, wrongAnswers));
                        Arrays.fill(wrongAnswers, null);
                        i = 0;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionList;
    }

    public List<Question> getByCategory(List<Question> questionList, Category category){
        List<Question> categoryQList = new ArrayList<>();
        for (int i = 1; i < questionList.size(); i++) {
            if (questionList.get(i).getCategory().equals(category)){
                categoryQList.add(questionList.get(i));
            }
        }
        return categoryQList;
    }
}

