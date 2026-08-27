package com.allancleitonppma.sscagent.desktop.controller;
import com.allancleitonppma.sscagent.desktop.dto.OrderDTO;
import com.allancleitonppma.sscagent.desktop.alerts.DefaultAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;




public class MainViewController implements Initializable {
    @FXML
    public Label lbFileStatus;

    @FXML
    private VBox vBoxDataImport;



    @FXML
    public void onTabOrderDeCargaAction(){
        System.out.println("Entrou!");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadView("/gui/ModeArquiveView.fxml");
    }


    private void loadView(String absoluteName) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(absoluteName)
            );

            HBox box = loader.load();

            vBoxDataImport.getChildren().set(0, box);

        } catch (IOException e) {
            DefaultAlert.showAlert(
                    "IO Exception",
                    "Erro ao carregar a tela",
                    e.getMessage(),
                    Alert.AlertType.ERROR
            );
        }
    }

}
