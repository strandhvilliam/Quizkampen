package com.skola.quizkampen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CategoryController {

    @FXML
    public void chooseCategoryAction(ActionEvent event) {
        Button button = (Button) event.getSource();
        String categoryString = button.getText();
        for (Category category : Category.values()) {
            if (category.name.equalsIgnoreCase(categoryString)) {
                System.out.println("Category chosen: " + category.name);
                client.requestCategoryQuestions(category);
                button.getScene().getWindow().hide();
                break;
            }
        }
    }

    private Client client;

    public void setupClient(Client client) {
        this.client = client;
    }
}
