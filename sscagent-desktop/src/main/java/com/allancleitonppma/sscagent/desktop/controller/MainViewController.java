package com.allancleitonppma.sscagent.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {
    @FXML
    public Tab tabOrderCharge;
    @FXML
    private ComboBox<String> comboBoxDataMode;
    @FXML
    private Tab tabCollectMap;
    @FXML
    private TabPane tabPaneMain;

    //PROVISORIO PARA CARREGAR O CONTEUDO DA ABA MAPA DE SEPARAÇÃO
    @FXML
    public void onSelectCollectMap() throws IOException {
        tabCollectMap.setContent(loadView("/gui/MapCollectView.fxml"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeNodes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeNodes() throws IOException {
        comboBoxDataMode.getItems().add("Excel");
        comboBoxDataMode.getItems().add("Json");
        comboBoxDataMode.getItems().add("Pdf");
        comboBoxDataMode.getItems().add("Banco De dados");
        comboBoxDataMode.getSelectionModel().selectFirst();
        //CARREGANDO O CONTEUDO DA ABA ORDEM DE CARGA
        tabOrderCharge.setContent(loadView(setDataModel(comboBoxDataMode.getValue())));


        comboBoxDataMode.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {

                    System.out.println("Valor anterior: " + oldValue);
                    System.out.println("Novo valor: " + newValue);
                    try {
                        //CARREGANDO O CONTEUDO DA ABA ORDEM DE CARGA
                        tabOrderCharge.setContent(loadView(setDataModel(newValue)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });
    }


    private Node loadView(String absoluteName) throws IOException {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(absoluteName)
            );

            return loader.load();
    }

    private String setDataModel(String mode) throws IOException {
        String path = "/gui/OrderChargeFileView.fxml";
        switch (mode) {

            case "Json" -> path = (
                    "/gui/OrderChargeFileView.fxml"
            );

            case "Excel" -> path = (
                    "/gui/OrderChargeExcel.fxml"
            );

            case "Pdf" -> path = (
                    "/gui/OrderChargePDF.fxml"
            );

            case "Banco De dados" -> path = (
                    "/gui/OrderChargeDatabaseView.fxml"
            );

        }
        return path;

    }

    @FXML
    private void loadAbout() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/gui/About.fxml")
        );

        Tab aboutTab = loader.load();

        tabPaneMain.getTabs().add(aboutTab);
    }


}
