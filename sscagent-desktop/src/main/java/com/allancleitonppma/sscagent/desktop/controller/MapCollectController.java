package com.allancleitonppma.sscagent.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MapCollectController implements Initializable {
    @FXML
    private VBox paneMapCollect;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            paneMapCollect.getChildren().add(loadView("/gui/MapCollectNodeView.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Node loadView(String absoluteName) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(absoluteName)
        );

        return loader.load();
    }
}
