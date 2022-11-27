package com.skola.quizkampen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CategoryController {

    private Client client;

    private ClientController clientController;

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
        clientController.displayWaitingWindow();

    }

    public void setupClient(Client client, ClientController clientController) {
        this.client = client;
        this.clientController = clientController;
    }
}
