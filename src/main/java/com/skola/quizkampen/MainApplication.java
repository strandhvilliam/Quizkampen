package com.skola.quizkampen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("home-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        fxmlLoader.setController(new ClientController());
        stage.show();


        ClientController c = fxmlLoader.getController();
        List<Boolean> x = new ArrayList<>(c.myResult);
        Collections.reverse(x);
        c.roundResult = x;
        c.displayStatistics(c.myResult);

    }

    public static void main(String[] args) {
        launch();
    }
}