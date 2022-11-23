package com.skola.quizkampen;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionGenerator {
    private String row;
    private String question = "";
    private String rightAnswer = "";
    private String wrongAnswer = "";
    ArrayList<Question> questionArrayList = new ArrayList<Question>();
    private String[] wrongAnswers = new String[3];


    private Category category;
    public ArrayList<Question> readQuestions (){
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\javamapp\\Quizkampen\\src\\main\\java\\com\\skola\\quizkampen\\questions"))) {
            while((row = br.readLine()) != null){
                if (!row.isBlank()) {
                    if (row.startsWith("spel")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        //System.out.println("Spelfråga: " + question + "\n");
                        category = Category.SPEL;
                    }
                    if (row.startsWith("geografi")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        //System.out.println("Geografifråga: " + question + "\n");
                        category = Category.GEOGRAFI;
                    }
                    if (row.startsWith("historia")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        //System.out.println("Historiefråga: " + question + "\n");
                        category = Category.HISTORIA;
                    }
                    if (row.startsWith("film")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        //System.out.println("Filmfråga: " + question + "\n");
                        category = Category.FILM;
                    }
                    if (row.startsWith("djur")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        //System.out.println("Djurfråga: " + question + "\n");
                        category = Category.DJUR;
                    }
                    if (row.startsWith("teknik")) {
                        int space = row.indexOf(' ');
                        question = row.substring(space + 1);
                        //System.out.println("Teknikfråga: " + question + "\n");
                        category = Category.TEKNIK;
                    }
                    if (row.startsWith("wrong")) {
                        int space = row.indexOf(' ');
                        wrongAnswer = row.substring(space + 1);
                        wrongAnswers[i] = wrongAnswer;
                        i++;

                        //System.out.println("Fel svar: " + wrongAnswer + "\n");
                    }
                    if (row.startsWith("correct")) {
                        int space = row.indexOf(' ');
                        rightAnswer = row.substring(space + 1);
                        //System.out.println("Rätt svar: " + rightAnswer + "\n");
                        questionArrayList.add(new Question(question, rightAnswer, category, wrongAnswers));
                        System.out.println(wrongAnswers[0]);
                        System.out.println(wrongAnswers[1]);
                        System.out.println(wrongAnswers[2]);
                        Arrays.fill(wrongAnswers, null);
                        i = 0;
                    }
                    //System.out.println(questionArray[i].getQuestion() + " " + " " + questionArray[i].getCorrectAnswer() + " " + questionArray[i].getCategory());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionArrayList;
    }
}

