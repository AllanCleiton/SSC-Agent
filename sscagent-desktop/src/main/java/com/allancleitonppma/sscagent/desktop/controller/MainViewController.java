package com.allancleitonppma.sscagent.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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

    //============================================================================
    //     Variáveis responsáveis por controlar a barra de controle da janela
    // ==========================================================================*/
    @FXML
    public HBox windowTitleBar;
    @FXML
    public Button closeButton;
    @FXML
    public Button maximizeButton;
    @FXML
    public Button minimizeButton;
    private double xOffset;
    private double yOffset;

    //PROVISORIO PARA CARREGAR O CONTEUDO DA ABA MAPA DE SEPARAÇÃO
    @FXML
    public void onSelectCollectMap() throws IOException {
        tabCollectMap.setContent(loadView("/gui/MapCollectView.fxml"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            windowTitleBar.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
    //---------------------------------------------------------------------------------

            windowTitleBar.setOnMouseDragged(event -> {

                Stage stage = (Stage) windowTitleBar.getScene().getWindow();

                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
    //---------------------------------------------------------------------------------

            minimizeButton.setOnAction(event -> {

                Stage stage = (Stage) minimizeButton.getScene().getWindow();

                stage.setIconified(true);
            });
    //---------------------------------------------------------------------------------
            maximizeButton.setOnAction(event -> toggleMaximize());
            
    //---------------------------------------------------------------------------------
            closeButton.setOnAction(event -> {

                Stage stage = (Stage) closeButton.getScene().getWindow();

                stage.close();
            });

            initializeNodes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeNodes() throws IOException {
        comboBoxDataMode.getItems().add("Arquivo");
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

            case "Arquivo" -> path = (
                    "/gui/OrderChargeFile.fxml"
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


    private void toggleMaximize() {

        Stage stage = (Stage) maximizeButton.getScene().getWindow();

        stage.setMaximized(!stage.isMaximized());
    }

}
