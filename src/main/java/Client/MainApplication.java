package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("initial-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome!");
        stage.setScene(scene);
        stage.show();

        InitialController initialController = fxmlLoader.getController();

        Client client = new Client("127.0.0.1");
        Thread clientThread = new Thread(client);
        clientThread.setDaemon(true);
        clientThread.start();

        ClientGame clientGame = new ClientGame(client, stage);


        initialController.setupGame(clientGame);

    }

    public static void main(String[] args) {
        launch();
    }
}