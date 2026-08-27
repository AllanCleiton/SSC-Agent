package com.allancleitonppma.sscagent.desktop.controller;

import com.allancleitonppma.sscagent.desktop.alerts.DefaultAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {

    @FXML
    private TabPane tabPane;
    @FXML
    private ComboBox<String> comboBoxDataMode;


    @FXML
    public void onTabOrderDeCargaAction() {
        System.out.println("Entrou!");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    public void initializeNodes() {
        comboBoxDataMode.getItems().add("Arquivo");
        comboBoxDataMode.getItems().add("Banco De dados");
        comboBoxDataMode.getSelectionModel().selectFirst();
        setDataModel(comboBoxDataMode.getValue());

        comboBoxDataMode.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {

                    System.out.println("Valor anterior: " + oldValue);
                    System.out.println("Novo valor: " + newValue);
                    comboBoxDataModelAlter(newValue);

                });

        loadView("/gui/TabViewOrderCharger.fxml");

    }

    @FXML
    public void comboBoxDataModelAlter(String value) {
        setDataModel(value);
    }

    private void setDataModel(String mode) {
        //implementar a logica para escolher como os dados iram ser carregados.
    }

    private void loadView(String absoluteName) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(absoluteName)
            );

            Tab tab = loader.load();

            tabPane.getTabs().set(0,tab);

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
