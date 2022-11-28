package com.skola.quizkampen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CategoryController {

    private Client client;

    private ClientGame game;

    @FXML
    public void chooseCategoryAction(ActionEvent event) {
        Button button = (Button) event.getSource();
        String categoryString = button.getText();
        for (Category category : Category.values()) {
            if (category.name.equalsIgnoreCase(categoryString)) {
                System.out.println("Category chosen: " + category.name);
                game.sendCategory(category);
                break;
            }
        }
       // clientController.displayWaitingWindow();

    }

    public void setGame(ClientGame game) {
        this.game = game;
    }
}
