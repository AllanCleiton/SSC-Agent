package com.allancleitonppma.sscagent.desktop.controller;

import com.allancleitonppma.sscagent.application.usecase.ConsolidateSalesLoad;
import com.allancleitonppma.sscagent.application.usecase.ImportSalesLoadUseCase;
import com.allancleitonppma.sscagent.desktop.alerts.DefaultAlert;
import com.allancleitonppma.sscagent.desktop.dto.OrderDTO;
import com.allancleitonppma.sscagent.desktop.util.DataChangeListener;
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
import java.util.*;

public class OrderChargeExcelController implements Initializable, DataChangeListener {
    @FXML
    public Button btnLoaderOrder;
    @FXML
    private TextField txtImportPath;
    @FXML
    private Button btnLoaderFile;
    @FXML
    private Label lbStatus;
    @FXML
    private TextField txtNewCondition;
    @FXML
    private TextField txtNewCode;
    @FXML
    private TextField txtNewQuantity;
    @FXML
    private TextField txtNewOrder;

    @FXML
    private Label lbStatusApply;

    private int actualLineSelected;

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

    private List<DataChangeListener> listers = new ArrayList<>();

    private List<OrderPreview> orders = new ArrayList<>();

    @FXML
    private TableView<OrderDTO> tableViewOrderCharge;

    @FXML
    public void onNewOrderAction(){
        if((!txtNewCode.getText().isEmpty()) && (!txtNewQuantity.getText().isEmpty()) && (!txtNewOrder.getText().isEmpty())) {
            subscribeDataChangeListener(this);

            for(OrderPreview orderPreview : orders){
                if(Objects.equals(orderPreview.getProduct(), txtNewCode.getText()))
                    subtractValue(txtNewQuantity.getText(),actualLineSelected);
            }

            orders.add(new OrderPreview(UUID.randomUUID(),txtNewCode.getText(), Double.parseDouble(txtNewQuantity.getText()), txtNewCondition.getText(), txtNewOrder.getText(), ""));
            lbStatusApply.setText("Sucesso!");


            txtNewCode.setText("");
            txtNewCondition.setText("");
            txtNewQuantity.setText("");
            txtNewOrder.setText("");



            notifyDataChangerListeners();
        }else{
            lbStatusApply.setText("Falhou!");
        }
    }

    @FXML
    public void onLoaderAction(){
        try {
            OnOpenFileAction();
            ImportSalesLoadUseCase salesLoadUseCase = new ImportSalesLoadUseCase(new JsonSalesLoadReader());

            if(!orders.isEmpty()){
                orders.clear();
            }
            orders.addAll(ConsolidateSalesLoad.consolidate(Path.of(txtImportPath.getText()), salesLoadUseCase));

            ObservableList<OrderDTO> orderDTOS = FXCollections.observableArrayList(orders.stream().map(orderPreview1 -> new OrderDTO(
                    orderPreview1.getId(),
                    orderPreview1.getProduct(),
                    String.valueOf(orderPreview1.getNeed()),
                    orderPreview1.getCondition(),
                    orderPreview1.getOrder(),
                    orderPreview1.getInstruction()
            )).toList());

            tableViewOrderCharge.setItems(orderDTOS);

            lbStatus.setText("Sucesso!");

        }catch (NumberFormatException e) {
            lbStatus.setText("erro!");
            DefaultAlert.showAlert("Erro!", "Parce Error", e.getMessage(), Alert.AlertType.ERROR);

        }
    }

    private void notifyDataChangerListeners() {
        for(DataChangeListener listener : listers){
            listener.onDataChange();
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


        tableViewOrderCharge.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                deleteItemTableView();
            }

            listenerSelectTableView();
        });
    }

    public void subscribeDataChangeListener(DataChangeListener lister){
        listers.add(lister);
    }

    private void upDateTableVew(){
        ObservableList<OrderDTO> orderDTOS = FXCollections.observableArrayList(orders.stream().map(orderPreview1 -> new OrderDTO(
                orderPreview1.getId(),
                orderPreview1.getProduct(),
                String.valueOf(orderPreview1.getNeed()),
                orderPreview1.getCondition(),
                orderPreview1.getOrder(),
                orderPreview1.getInstruction()
        )).toList());

        tableViewOrderCharge.setItems(orderDTOS);
    }

    @Override
    public void onDataChange() {
        upDateTableVew();
    }

    private void listenerSelectTableView(){
        OrderDTO orderSelect = tableViewOrderCharge.getSelectionModel().getSelectedItem();

        if (orderSelect != null) {
                    //System.out.println("Indice"+tableViewOrderCharge.getSelectionModel().getSelectedIndex());
            txtNewCode.setText(orderSelect.getProduct());
            txtNewOrder.setText(orderSelect.getOrder());
            actualLineSelected = tableViewOrderCharge.getSelectionModel().getSelectedIndex();
        }
    }

    private void subtractValue(String value, Integer index){
        orders.forEach( orderPreview -> {

            if (orderPreview.getId() == tableViewOrderCharge.getItems().get(index).getId()){
                orderPreview.updateNeed(value);
            }
        });
        ObservableList<OrderDTO> orderDTOS =
                FXCollections.observableArrayList(
                        orders.stream()
                                .map(orderPreview1 -> new OrderDTO(
                                        orderPreview1.getId(),
                                        orderPreview1.getProduct(),
                                        String.valueOf(orderPreview1.getNeed()),
                                        orderPreview1.getCondition(),
                                        orderPreview1.getOrder(),
                                        orderPreview1.getInstruction()
                                ))
                                .toList()
                );

        tableViewOrderCharge.setItems(orderDTOS);


    }

    private void deleteItemTableView(){
        OrderDTO orderSelect = tableViewOrderCharge.getSelectionModel().getSelectedItem();

        if (orderSelect != null) {

            orders.removeIf(x ->
                    Objects.equals(x.getId(), orderSelect.getId())
            );

            ObservableList<OrderDTO> orderDTOS =
                    FXCollections.observableArrayList(
                            orders.stream()
                                    .map(orderPreview1 -> new OrderDTO(
                                            orderPreview1.getId(),
                                            orderPreview1.getProduct(),
                                            String.valueOf(orderPreview1.getNeed()),
                                            orderPreview1.getCondition(),
                                            orderPreview1.getOrder(),
                                            orderPreview1.getInstruction()
                                    ))
                                    .toList()
                    );

            tableViewOrderCharge.setItems(orderDTOS);
            subscribeDataChangeListener(this);
        }


    }
}
