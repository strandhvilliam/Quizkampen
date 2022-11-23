package com.skola.quizkampen;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Database implements Serializable {

    static List<Question> allQuestions;
    private int roundsPerGame;
    private int questionsPerRound;

    public Database() throws FileNotFoundException, IOException {

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/java/com/skola/quizkampen/Settings.properties"));
            String questionsPerGameString = properties.getProperty("roundsPerGame").trim();
            roundsPerGame = Integer.parseInt(questionsPerGameString);
            String questionsPerRoundString = properties.getProperty("questionsPerRound").trim();
            questionsPerRound = Integer.parseInt(questionsPerRoundString);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    public void initializeQuestions() throws IOException {
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
    }

    public void shuffleListOfQuestions() {
        Collections.shuffle(allQuestions);
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        Category tempCategory=allQuestions.get(0).getCategory();
        questions.add(allQuestions.get(0));

        for (int i=1; i<allQuestions.size(); i++){
            if(allQuestions.get(i).getCategory().equals(allQuestions.get(0).getCategory())){
                questions.add(allQuestions.get(i));
                allQuestions.remove(i);
            }
        }
        return questions;
    }

    public int getRoundsPerGame() {
        return roundsPerGame;
    }

    public int getQuestionsPerRound() {
        return questionsPerRound;
    }
    
}