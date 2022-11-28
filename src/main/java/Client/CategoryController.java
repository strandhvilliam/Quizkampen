package Client;

import Models.Category;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CategoryController {

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
    }

    public void setGame(ClientGame game) {
        this.game = game;
    }
}
