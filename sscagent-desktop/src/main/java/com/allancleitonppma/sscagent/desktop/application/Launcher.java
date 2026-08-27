package com.allancleitonppma.sscagent.desktop.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class Launcher extends Application{

    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            BorderPane borderPane = loader.load();


            mainScene = new Scene(borderPane);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("SSG-Agent");
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void main(String[] args) {
        Application.launch(Launcher.class, args);

    }

    public static Scene getMainScene(){
        return mainScene;
    }


}

