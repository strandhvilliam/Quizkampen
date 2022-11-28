package Server;

import Models.Category;
import Models.Question;

import java.io.*;
import java.util.*;

public class Database implements Serializable {

    static List<Question> allQuestions = new ArrayList<Question>();

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
    }

    /*public void initializeQuestions() throws IOException {
        allQuestions.clear();

        String filePath = "src/main/java/com/skola/quizkampen/questions.txt";
        String dbQuestion, dbAnswers, dbCategory;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

            while ((dbQuestion = bufferedReader.readLine()) != null) {
                dbAnswers = bufferedReader.readLine();
                dbCategory = bufferedReader.readLine();
                String[] dbAnswersArr = dbAnswers.split(",");
                allQuestions.add(new Question(dbQuestion, dbAnswersArr, dbCategory));
            }
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }*/

    public void shuffleListOfQuestions() {
        Collections.shuffle(allQuestions);
    }

    public List<Question> getAllQuestions(List<Question> questionTestList) {
        List<Question> questions = new ArrayList<>();
        Category tempCategory = questionTestList.get(0).getCategory();
        questions.add(questionTestList.get(0));

        for (int i = 1; i < questionTestList.size(); i++) {
            if (questionTestList.get(i).getCategory().equals(questionTestList.get(0).getCategory())) {
                questions.add(questionTestList.get(i));
                questionTestList.remove(i);
            }
        }
        return questions;
    }


    public List<Question> getByCategory(Category category) {
        List<Question> categoryQList = new ArrayList<>();
        getAllQuestions();
        for (int i = 0; i < allQuestions.size(); i++) {
            if (allQuestions.get(i).getCategory().equals(category)) {
                categoryQList.add(allQuestions.get(i));
            }
        }
        for (int i = 0; i < categoryQList.size(); i++) {
            System.out.println(categoryQList.get(i).getQuestion());
        }
        return categoryQList;
    }

    public void getAllQuestions() {
        int i = 0;
        String row;
        String question = "";
        String rightAnswer = "";
        String wrongAnswer = "";
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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