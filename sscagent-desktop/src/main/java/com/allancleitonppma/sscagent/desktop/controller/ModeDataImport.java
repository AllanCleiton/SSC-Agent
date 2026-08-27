package com.allancleitonppma.sscagent.desktop.controller;

import com.allancleitonppma.sscagent.application.usecase.ImportSalesLoadUseCase;
import com.allancleitonppma.sscagent.desktop.alerts.DefaultAlert;
import com.allancleitonppma.sscagent.desktop.dto.OrderDTO;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.OrderPreview;
import com.allancleitonppma.sscagent.infrastructure.adapters.json.JsonSalesLoadReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

import static com.allancleitonppma.sscagent.application.usecase.ImportListOrderPreview.getListOrderDto;

public class ModeDataImport implements Initializable {
    @FXML
    private TextField txtImportPath;
    @FXML
    private Button btnLoaderFile;
    @FXML
    private TextField txtOrderCharger;
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
    private ComboBox<String> comboBoxDataMode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

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

            txtOrderCharger.setText(orderDTOS.getFirst().getOrder());

            tbOrderChargeList.setItems(orderDTOS);



            lbStatus.setText("Sucesso!");

        }catch (NumberFormatException e) {
            lbStatus.setText("erro!");
            DefaultAlert.showAlert("Erro!", "Parce Error", e.getMessage(), Alert.AlertType.ERROR);

        }
    }


    @FXML
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

    public void initializeNodes(){
        tableColumnProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        tableColumNeed.setCellValueFactory(new PropertyValueFactory<>("need"));
        tableColumnCondition.setCellValueFactory(new PropertyValueFactory<>("condition"));
        tableColumnOrder.setCellValueFactory(new PropertyValueFactory<>("order"));
        tableColumnInstruction.setCellValueFactory(new PropertyValueFactory<>("instruction"));

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



    }

    @FXML
    public void  comboBoxDataModelAlter(String value){
        setDataModel(value);
    }

    private void setDataModel(String mode){
        //implementar a logica para escolher como os dados iram ser carregados.
    }
}
