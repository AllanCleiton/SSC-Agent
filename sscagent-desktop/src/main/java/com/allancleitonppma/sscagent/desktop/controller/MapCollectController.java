package com.allancleitonppma.sscagent.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MapCollectController implements Initializable {
    @FXML
    private VBox paneMapCollect;
    @FXML
    private TextField txtQtdeRequired;
    @FXML
    private VBox paneAddressMap;


    private List<MapCollectNodeController> mapCollectNodeControllers = new ArrayList<>();


    private void addMapCollect(String mapId, String product, String quantity, String lbPending, String lbCondition, String lbMapPositions) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/gui/MapCollectNodeView.fxml")
        );

        ToggleButton toggleButton = loader.load();

        MapCollectNodeController controller = loader.getController();

        controller.getLbMapID().setText(mapId);
        controller.getLbMapProduct().setText(product);
        controller.getLbQuantity().setText(quantity);
        controller.getLbMapPositions().setText(lbMapPositions);


        mapCollectNodeControllers.add(controller);

        paneMapCollect.getChildren().add(toggleButton);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeNodes();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (MapCollectNodeController controller : mapCollectNodeControllers) {

            controller.getThisToggle().selectedProperty().addListener(
                    (observable, oldValue, selected) -> {

                        if (selected) {
                            for(AllocationPositionController allocationPositionController : controller.getPositonsAddress()){
                                paneAddressMap.getChildren().add(allocationPositionController.getAllocationProduct());
                            }
                            System.out.println("Selecionado");
                        } else {
                            System.out.println("Desmarcado");
                            paneAddressMap.getChildren().clear();
                        }

                    }
            );
        }

    }

    /*===========================================================
        INICIALIZA OS COMPONETES DA TELA
    * ==========================================================*/
    private void initializeNodes() throws IOException {
        addMapCollect(
                "Mapa: 01234",
                "Produto: 11046",
                "150 Caixas",
                "",
                "",
                "Posições: Cam 3 Rua 14= 1A 2A 3A -> 14cx"
        );

        //verifica se tem algum toggleButton presionado
        configToggleButtons();

    }


    private void configToggleButtons() {
        for (Node node : paneMapCollect.getChildren()) {
            if (node instanceof ToggleButton button) {
                button.selectedProperty().addListener((obs, oldValue, selected) -> {
                    if (selected) {
                        bloquearOutros(button);

                    } else {
                        liberarTodos();

                    }
                });
            }
        }


    }


    /*===========================================================
        Bloqueia toggleButton para não ser possivel precionalo
    * ==========================================================*/
    private void bloquearOutros(ToggleButton selecionado) {

        for (Node node : paneMapCollect.getChildren()) {

            if (node instanceof ToggleButton button) {

                if (button != selecionado) {
                    button.setDisable(true);
                }
            }
        }
    }

    /*===========================================================
        Desbloqueia toggleButton para ser possivel precionalo
    * ==========================================================*/
    private void liberarTodos() {

        for (Node node : paneMapCollect.getChildren()) {

            if (node instanceof ToggleButton button) {
                button.setDisable(false);
            }
        }
    }

}
