package com.skola.quizkampen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ClientController implements Initializable {


    private String correctAnswerForRound;

    private Client client;

    protected List<Boolean> roundResult = new ArrayList<>();
    private List<Question> questionsInRound;


    /*TODO: metod som hanterar när användare skriver in användarnamn.
        ber klienten skicka request till servern att valt användarnamn
        sätter namnet i GUIn
     */


    public void displayNextQuestion() throws IOException {
        if (questionsInRound.size() > 0) {
            Question questionToDisplay = questionsInRound.get(0);
            questionsInRound.remove(0);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("question-form.fxml"));
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            QuestionWindowController questionWindowController = loader.getController();
            questionWindowController.initData(this, questionToDisplay);

            stage.show();
        }
        else{
            sendResult();
            requestIsDoneWithRound();
        }



        /*else {
           for (Boolean result : roundResult) {
               System.out.println(result);
           }
        }*/

    }

    public void sendResult() throws IOException {
        client.sendObject(roundResult);
    }


    public void displayCategoryChooser() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("categoryChooser.fxml"));
        fxmlLoader.setController(this);
        Stage stage = new Stage();
        stage.setTitle("Choose category");
        try {
            stage.setScene(new Scene(fxmlLoader.load(), 320, 240));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }

    public void requestIsDoneWithRound() throws IOException {
        client.sendObject("ROUND_FINISHED");
    }


    /**
     * Metod som hanterar när användaren klickar på en kategori.
     * Ber klienten skicka request till servern att valt kategori
     *
     * @param event klick på kategori
     */
    @FXML
    public void chooseCategoryAction(ActionEvent event) {
        Button button = (Button) event.getSource();
        String categoryString = button.getText();

        for (Category category : Category.values()) {
            if (category.name.equalsIgnoreCase(categoryString)) {
                client.requestCategoryQuestions(category);
                System.out.println("Category chosen: " + category.name);
                break;
            }
        }

    }


    public void startRound(List<Question> questions) throws IOException {
        this.questionsInRound = questions;
        displayNextQuestion();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            client = new Client("127.0.0.1", this);
            Thread clientThread = new Thread(client);
            clientThread.setDaemon(true);
            clientThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        try {
//            client.requestStatistics();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    //test list
    List<Boolean> myResult = List.of(true, false, true, true);

    public void displayStatistics(List<Boolean> opponentResult) {
        System.out.println("Motståndare:");
        for (Boolean b : opponentResult) {
            System.out.println(b);
        }

        System.out.println();

        System.out.println("Mitt resultat:");
        for (Boolean b : roundResult) {
            System.out.println(b);
        }
    }
}