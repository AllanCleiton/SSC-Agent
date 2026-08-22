package com.allancleitonppma.sscagent.desktop.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Launcher extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            var root = new BorderPane();
            var scene = new Scene(root,400,400);
            //scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("src/main/resources/com/allancleitonppma/sscagent/styles/MainView.css")).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static void main(String[] args) {

        Application.launch(Launcher.class, args);
    }
}

