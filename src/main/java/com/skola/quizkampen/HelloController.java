package com.skola.quizkampen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        QuestionGenerator qg = new QuestionGenerator();
        qg.hittaKund();
    }


}