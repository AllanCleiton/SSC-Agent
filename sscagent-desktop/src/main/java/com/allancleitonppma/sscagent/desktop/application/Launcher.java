package com.allancleitonppma.sscagent.desktop.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Launcher extends Application{

    private static Stage stage;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/gui/MainView.fxml")
            );

            Parent parent = loader.load();

            Scene scene = new Scene(parent);
            stage.setScene(scene);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void main(String[] args) {
        Application.launch(Launcher.class, args);

    }


}

