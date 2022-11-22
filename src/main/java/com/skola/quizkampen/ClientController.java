package com.skola.quizkampen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {


    private Client client;

    @FXML
    private Label welcomeText;


    /*TODO: metod som hanterar när användare skriver in användarnamn.
        ber klienten skicka request till servern att valt användarnamn
        sätter namnet i GUIn
     */


    /*TODO: metod som visar ruta med en fråga och svarsalternativ


     */

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
            client = new Client("localhost");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}