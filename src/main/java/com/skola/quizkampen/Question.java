package com.skola.quizkampen;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Question{

    public static Object readObject(String fileName){
        ObjectInputStream objectInputStream=null;
        Object object = null;
        try{
            FileInputStream streamIn = new FileInputStream(fileName);
            objectInputStream=new ObjectInputStream(streamIn);
            object=objectInputStream.readObject();
            objectInputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return object;
    }

   Question g = new Question();
   protected String question;
   protected String correctAnswer;

   public Question(String question, String correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
   }

   public Question() {}

   public String getQuestion() {
        return question;
   }

   public String getCorrectAnswer() {
        return correctAnswer;
   }

   public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
   }

}
