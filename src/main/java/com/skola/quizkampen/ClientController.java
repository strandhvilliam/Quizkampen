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
import java.util.ResourceBundle;
import java.util.Stack;

public class ClientController implements Initializable {


    private Client client;



    /*TODO: metod som hanterar när användare skriver in användarnamn.
        ber klienten skicka request till servern att valt användarnamn
        sätter namnet i GUIn
     */


    /*TODO: metod som visar ruta med en fråga och svarsalternativ
     */
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

    @FXML
    public void chooseCategoryAction(ActionEvent event) {
        Button button = (Button) event.getSource();
        //Category category = Category.DJUR;
        System.out.println(Category.DJUR.name());
        //System.out.println("Category chosen: " + category.name);
    }

    /*TODO: Action event metod när användare väljer ett alternativ.
            1. Itererar igenom listan med alternativ och ser om rätt svar är valt
            2. Addera en vinst (true) i klintens lista eller false
            3. OM alla frågor är ställda : be klienten skicka användarens resultat till servern

     */

    public void startRound() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            client = new Client();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}