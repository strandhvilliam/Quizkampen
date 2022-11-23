package com.skola.quizkampen;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable {
    private static List<Question> allQuestions;

    public Database(String question, String correctAnswer) {
    }

    public void loadQuestionFromFile(){

    }
    public static Object readObject(String fileName){
        ObjectInputStream objectInputStream=null;
        Object object = null;

        try{
            FileInputStream streamIn = new FileInputStream(fileName);
            objectInputStream=new ObjectInputStream(streamIn);
            object=objectInputStream.readObject();
            objectInputStream.close();
        }
        catch(Exception e){
            e.printStackTrace();
    }
        return object;
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
