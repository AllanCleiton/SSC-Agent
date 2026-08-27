package com.allancleitonppma.sscagent.desktop.controller;

import com.allancleitonppma.sscagent.application.usecase.ImportSalesLoadUseCase;
import com.allancleitonppma.sscagent.desktop.alerts.DefaultAlert;
import com.allancleitonppma.sscagent.desktop.dto.OrderDTO;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.OrderPreview;
import com.allancleitonppma.sscagent.infrastructure.adapters.json.JsonSalesLoadReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

import static com.allancleitonppma.sscagent.application.usecase.ImportListOrderPreview.getListOrderDto;

public class TabViewOrderCharger implements Initializable {
    @FXML
    public Label lbFileStatus;
    @FXML
    private TextField txtImportPath;
    @FXML
    private Button btnLoaderFile;
    @FXML
    private Label lbStatus;

    @FXML
    private TableView<OrderDTO> tbOrderChargeList;

    @FXML
    private TableColumn<OrderDTO,String> tableColumnProduct;
    @FXML
    private TableColumn<OrderDTO,String> tableColumNeed;
    @FXML
    private TableColumn<OrderDTO,String> tableColumnCondition;
    @FXML
    private TableColumn<OrderDTO,String> tableColumnOrder;
    @FXML
    private TableColumn<OrderDTO,String> tableColumnInstruction;


    @FXML
    public void onLoaderAction(){
        try {
            OnOpenFileAction();
            ImportSalesLoadUseCase salesLoadUseCase = new ImportSalesLoadUseCase(new JsonSalesLoadReader());

            List<OrderPreview> orderPreview = getListOrderDto(Path.of(txtImportPath.getText()), salesLoadUseCase);

            ObservableList<OrderDTO> orderDTOS = FXCollections.observableArrayList(orderPreview.stream().map(orderPreview1 -> new OrderDTO(

                    orderPreview1.getProduct(),
                    String.valueOf(orderPreview1.getNeed()),
                    orderPreview1.getCondition(),
                    orderPreview1.getOrder(),
                    orderPreview1.getInstruction()
            )).toList());

            tbOrderChargeList.setItems(orderDTOS);

            lbStatus.setText("Sucesso!");

        }catch (NumberFormatException e) {
            lbStatus.setText("erro!");
            DefaultAlert.showAlert("Erro!", "Parce Error", e.getMessage(), Alert.AlertType.ERROR);

        }
    }


    public void OnOpenFileAction(){
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Selecionar arquivo");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Arquivos de carga",
                        "*.json",
                        "*.xls",
                        "*.xlsx"
                )
        );

        File arquivo = fileChooser.showOpenDialog(txtImportPath.getScene().getWindow());

        if (arquivo != null) {
            txtImportPath.setText(arquivo.getAbsolutePath());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }



    public void initializeNodes(){
        tableColumnProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        tableColumNeed.setCellValueFactory(new PropertyValueFactory<>("need"));
        tableColumnCondition.setCellValueFactory(new PropertyValueFactory<>("condition"));
        tableColumnOrder.setCellValueFactory(new PropertyValueFactory<>("order"));
        tableColumnInstruction.setCellValueFactory(new PropertyValueFactory<>("instruction"));

    }
}
