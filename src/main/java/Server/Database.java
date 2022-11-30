package Server;

import Models.Category;
import Models.Question;

import java.io.*;
import java.util.*;

public class Database implements Serializable {

    static List<Question> allQuestions = new ArrayList<>();

    static Category category;
    private int roundsPerGame;
    private int questionsPerRound;

    public Database() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/Server/Settings.properties"));
            String questionsPerGameString = properties.getProperty("roundsPerGame", "2");
            roundsPerGame = Integer.parseInt(questionsPerGameString);
            String questionsPerRoundString = properties.getProperty("questionsPerRound", "2");
            questionsPerRound = Integer.parseInt(questionsPerRoundString);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IOException");
        }
        getAllQuestions();

    }


    public List<Question> getByCategory(Category category) {
        List<Question> categoryQList = new ArrayList<>();
        for (Question allQuestion : allQuestions) {
            if (allQuestion.getCategory().equals(category)) {
                categoryQList.add(allQuestion);
            }
        }
        return categoryQList;
    }

    public void getAllQuestions() {
        int i = 0;
        String row;
        String question = "";
        String rightAnswer;
        String wrongAnswer;
        String[] wrongAnswers = new String[3];

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Server/questions"))) {
            while ((row = br.readLine()) != null) {
                if (!row.isBlank()) {
                    if (row.startsWith("spel")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        category = Category.SPEL;
                    }
                    if (row.startsWith("geografi")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        category = Category.GEOGRAFI;
                    }
                    if (row.startsWith("historia")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        category = Category.HISTORIA;
                    }
                    if (row.startsWith("film")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        category = Category.FILM;
                    }
                    if (row.startsWith("djur")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        category = Category.DJUR;
                    }
                    if (row.startsWith("teknik")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        category = Category.TEKNIK;
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
                        allQuestions.add(new Question(question, rightAnswer, category, wrongAnswers));
                        Arrays.fill(wrongAnswers, null);
                        i = 0;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getRoundsPerGame() {
        return roundsPerGame;
    }

    public int getQuestionsPerRound() {
        return questionsPerRound;
    }

}