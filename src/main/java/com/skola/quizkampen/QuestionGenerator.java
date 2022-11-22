package com.skola.quizkampen;

import java.io.*;

public class QuestionGenerator {
    private String row;
    private String question = "";
    private String rightAnswer = "";
    private String wrongAnswer = "";
    public void readQuestions (){

        try (BufferedReader br = new BufferedReader(new FileReader("D:\\javamapp\\Quizkampen\\src\\main\\java\\com\\skola\\quizkampen\\questions"))) {
            while((row = br.readLine()) != null){
                if (row.startsWith("spel")) {
                    int space = row.indexOf(' ');
                    question = row.substring(space + 1);
                    System.out.println("Fråga: " + question + "\n");
                }
                if (row.startsWith("geografi")) {
                    int space = row.indexOf(' ');
                    question = row.substring(space + 1);
                    System.out.println("Fråga: " + question + "\n");
                }
                if (row.startsWith("historia")) {
                    int space = row.indexOf(' ');
                    question = row.substring(space + 1);
                    System.out.println("Fråga: " + question + "\n");
                }
                if (row.startsWith("film")) {
                    int space = row.indexOf(' ');
                    question = row.substring(space + 1);
                    System.out.println("Fråga: " + question + "\n");
                }
                if (row.startsWith("djur")) {
                    int space = row.indexOf(' ');
                    question = row.substring(space + 1);
                    System.out.println("Fråga: " + question + "\n");
                }
                if (row.startsWith("teknik")) {
                    int space = row.indexOf(' ');
                    question = row.substring(space + 1);
                    System.out.println("Fråga: " + question + "\n");
                }
                if (row.startsWith("correct")) {
                    int space = row.indexOf(' ');
                    rightAnswer = row.substring(space + 1);
                    System.out.println("Rätt svar: " + rightAnswer + "\n");
                }
                if (row.startsWith("wrong")) {
                    int space = row.indexOf(' ');
                    wrongAnswer = row.substring(space + 1);
                    System.out.println("Fel svar: " + wrongAnswer + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

