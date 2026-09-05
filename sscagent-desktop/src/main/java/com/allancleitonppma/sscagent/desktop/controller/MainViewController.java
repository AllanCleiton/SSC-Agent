package com.allancleitonppma.sscagent.desktop.controller;

import com.allancleitonppma.sscagent.desktop.enums.ResizeDirection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
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

    //============================================================================
    //  todo:   Variáveis responsáveis por controlar o redimencionamento da janela
    // ==========================================================================*/
    private ResizeDirection resizeDirection = ResizeDirection.NONE;
    private ResizeDirection activeResizeDirection = ResizeDirection.NONE;
    private static final double RESIZE_MARGIN = 6;
    private double resizeStartX;
    private double resizeStartY;

    private double initialStageX;
    private double initialStageY;

    private double initialStageWidth;
    private double initialStageHeight;

    //todo: PROVISORIO PARA CARREGAR O CONTEUDO DA ABA MAPA DE SEPARAÇÃO
    @FXML
    public void onSelectCollectMap() throws IOException {
        tabCollectMap.setContent(loadView("/gui/MapCollectView.fxml"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    //------------Alão de arrastar a janela ao clicar e segurar na barra superior
        try {
            windowTitleBar.setOnMousePressed(event -> {
                if (resizeDirection != ResizeDirection.NONE) {
                    return;
                }
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });


            windowTitleBar.setOnMouseDragged(event -> {
                if (resizeDirection != ResizeDirection.NONE) {
                    return;
                }
                Stage stage = (Stage) windowTitleBar.getScene().getWindow();

                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });

    //------------Ação de minimizar a janela -----------------------------------------

            minimizeButton.setOnAction(event -> {

                Stage stage = (Stage) minimizeButton.getScene().getWindow();

                stage.setIconified(true);
            });

    //-------------Ação de maximizar a janela -------------------------------------------
            maximizeButton.setOnAction(event -> toggleMaximize());

    //-----------------Ação de fechar a janela -------------------------------------------
            closeButton.setOnAction(event -> {

                Stage stage = (Stage) closeButton.getScene().getWindow();

                stage.close();
            });

    //--------------Ações de redimencionar a janela ------------------------------------
            windowTitleBar.sceneProperty().addListener((obs, oldScene, newScene) -> {

                if (newScene != null) {

                    newScene.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {

                        Stage stage = (Stage) newScene.getWindow();

                        resizeDirection = getResizeDirection(
                                event.getSceneX(),
                                event.getSceneY(),
                                newScene.getWidth(),
                                newScene.getHeight()
                        );

                        updateResizeCursor(newScene);
                    });

                    newScene.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {

                        if (resizeDirection == ResizeDirection.NONE) {
                            return;
                        }

                        activeResizeDirection = resizeDirection;

                        Stage stage = (Stage) newScene.getWindow();

                        resizeStartX = event.getScreenX();
                        resizeStartY = event.getScreenY();

                        initialStageX = stage.getX();
                        initialStageY = stage.getY();

                        initialStageWidth = stage.getWidth();
                        initialStageHeight = stage.getHeight();
                    });

                    newScene.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {

                        Stage stage = (Stage) newScene.getWindow();

                        double deltaX = event.getScreenX() - resizeStartX;
                        double deltaY = event.getScreenY() - resizeStartY;

                        if (activeResizeDirection == ResizeDirection.EAST) {

                            double newWidth = initialStageWidth + deltaX;

                            if (newWidth >= stage.getMinWidth()) {
                                stage.setWidth(newWidth);
                            }
                        }

                        if (activeResizeDirection == ResizeDirection.SOUTH) {

                            double newHeight = initialStageHeight + deltaY;

                            if (newHeight >= stage.getMinHeight()) {
                                stage.setHeight(newHeight);
                            }
                        }

                        if (activeResizeDirection == ResizeDirection.WEST) {

                            double newWidth = initialStageWidth - deltaX;

                            if (newWidth >= stage.getMinWidth()) {
                                stage.setX(initialStageX + deltaX);
                                stage.setWidth(newWidth);
                            }
                        }

                        if (activeResizeDirection == ResizeDirection.NORTH) {

                            double newHeight = initialStageHeight - deltaY;

                            if (newHeight >= stage.getMinHeight()) {
                                stage.setY(initialStageY + deltaY);
                                stage.setHeight(newHeight);
                            }
                        }
                    });

                    newScene.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
                        activeResizeDirection = ResizeDirection.NONE;
                    });
                }
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

    //============================================================================
    // todo: Metodo responsável por controlar a maximização da janela
    // ==========================================================================*/
    private void toggleMaximize() {

        Stage stage = (Stage) maximizeButton.getScene().getWindow();

        stage.setMaximized(!stage.isMaximized());
    }


    //============================================================================
    // todo: Metodo responsável por método que identifica a região
    // ==========================================================================*/
    private ResizeDirection getResizeDirection(
            double mouseX,
            double mouseY,
            double width,
            double height
    ) {

        boolean left = mouseX <= RESIZE_MARGIN;
        boolean right = mouseX >= width - RESIZE_MARGIN;

        boolean top = mouseY <= RESIZE_MARGIN;
        boolean bottom = mouseY >= height - RESIZE_MARGIN;

        if (top && left) {
            return ResizeDirection.NORTH_WEST;
        }

        if (top && right) {
            return ResizeDirection.NORTH_EAST;
        }

        if (bottom && left) {
            return ResizeDirection.SOUTH_WEST;
        }

        if (bottom && right) {
            return ResizeDirection.SOUTH_EAST;
        }

        if (top) {
            return ResizeDirection.NORTH;
        }

        if (bottom) {
            return ResizeDirection.SOUTH;
        }

        if (left) {
            return ResizeDirection.WEST;
        }

        if (right) {
            return ResizeDirection.EAST;
        }

        return ResizeDirection.NONE;
    }


    //============================================================================
    // todo: Metodo responsável por Mapear direção → cursor
    // ==========================================================================*/
    private void updateResizeCursor(Scene scene) {

        switch (resizeDirection) {

            case NORTH:
            case SOUTH:
                scene.setCursor(Cursor.V_RESIZE);
                break;

            case EAST:
            case WEST:
                scene.setCursor(Cursor.H_RESIZE);
                break;

            case NORTH_WEST:
            case SOUTH_EAST:
                scene.setCursor(Cursor.NW_RESIZE);
                break;

            case NORTH_EAST:
            case SOUTH_WEST:
                scene.setCursor(Cursor.NE_RESIZE);
                break;

            default:
                scene.setCursor(Cursor.DEFAULT);
                break;
        }
    }

}
