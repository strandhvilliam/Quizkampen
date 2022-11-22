package com.skola.quizkampen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class ClientController implements Initializable {


    @FXML
    private Button optionOneButton, optionTwoButton, optionThreeButton, optionFourButton;

    @FXML

    private Client client;



    /*TODO: metod som hanterar när användare skriver in användarnamn.
        ber klienten skicka request till servern att valt användarnamn
        sätter namnet i GUIn
     */


    /*TODO: metod som visar ruta med en fråga och svarsalternativ
     */

    public void displayQuestion(Question question) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("question-form.fxml"));
        loader.setController(this);
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();

        /*
        KAN IMPLEMENTERAS NÄR DATABASEN ÄR KLAR

        questionLabel.setText(question.getQuestion());

        optionOneButton.setText(question.getOptions().get(0));
        optionTwoButton.setText(question.getOptions().get(1));
        optionThreeButton.setText(question.getOptions().get(2));
        optionFourButton.setText(question.getOptions().get(3));
        */
        optionOneButton.setText("Svar 1");
        optionTwoButton.setText("Svar 2");
        optionThreeButton.setText("Svar 3");
        optionFourButton.setText("Svar 4");

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


    /**
     * Metod som hanterar när användaren klickar på en kategori.
     * Ber klienten skicka request till servern att valt kategori
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
    public void startRound() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            client = new Client();
            Thread clientThread = new Thread(client);
            //clientThread.setDaemon(true);
            clientThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}