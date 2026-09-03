package com.allancleitonppma.sscagent.desktop.controller;

import com.allancleitonppma.sscagent.application.usecase.ConsolidateSalesLoad;
import com.allancleitonppma.sscagent.application.usecase.ImportSalesLoadUseCase;
import com.allancleitonppma.sscagent.desktop.alerts.DefaultAlert;
import com.allancleitonppma.sscagent.desktop.dto.OrderDTO;
import com.allancleitonppma.sscagent.desktop.util.DataChangeListener;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.OrderPreview;
import com.allancleitonppma.sscagent.infrastructure.adapters.json.JsonSalesLoadReader;
import com.allancleitonppma.sscagent.infrastructure.adapters.pdf.PdfSalesLoadReader;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;

public class OrderChargeFileController implements Initializable, DataChangeListener {

    @FXML
    public Button btnConsolidate;
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
    @FXML
    private TableView<OrderDTO> tableViewOrderCharge = new TableView<>();

    private List<DataChangeListener> listers = new ArrayList<>();

    private List<OrderPreview> orders = new ArrayList<>();


    @FXML
    public void onBtnConsolidateAction(){
        try {

            if(!orders.isEmpty()){
                tableViewOrderCharge.refresh();
            }

            var consolidateOrders = new ArrayList<>(Objects.requireNonNull(ConsolidateSalesLoad.consolidate(Path.of(txtImportPath.getText()), null, orders)));

            ObservableList<OrderDTO> orderDTOS = FXCollections.observableArrayList(consolidateOrders.stream().map(orderPreview1 -> new OrderDTO(
                    orderPreview1.getId(),
                    orderPreview1.getProduct(),
                    String.valueOf(orderPreview1.getNeed()),
                    orderPreview1.getCondition(),
                    orderPreview1.getOrder(),
                    orderPreview1.getInstruction()
            )).toList());

            orders.clear();
            orders.addAll(consolidateOrders);
            consolidateOrders.clear();
            tableViewOrderCharge.setItems(orderDTOS);

            lbStatus.setText("Sucesso!");
            notifyDataChangerListeners();
        }catch (NumberFormatException e) {
            lbStatus.setText("erro!");
            DefaultAlert.showAlert("Erro!", "Parce Error", e.getMessage(), Alert.AlertType.ERROR);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


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

            var salesLoadUseCase  = OnOpenFileAction();

            if(!orders.isEmpty()){
                orders.clear();
                tableViewOrderCharge.refresh();
            }
            orders.addAll(Objects.requireNonNull(ConsolidateSalesLoad.consolidate(Path.of(txtImportPath.getText()), salesLoadUseCase, null)));


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

            btnConsolidate.setDisable(false);

            notifyDataChangerListeners();
        }catch (NumberFormatException e) {
            lbStatus.setText("erro!");
            DefaultAlert.showAlert("Erro!", "Parce Error", e.getMessage(), Alert.AlertType.ERROR);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void notifyDataChangerListeners() {
        for(DataChangeListener listener : listers){
            listener.onDataChange();
        }
    }


    public ImportSalesLoadUseCase OnOpenFileAction(){
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Selecionar arquivo");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Arquivos de carga",
                        "*.json",
                        "*.xls",
                        "*.xlsx",
                        "*.pdf"
                )
        );

        File file = fileChooser.showOpenDialog(txtImportPath.getScene().getWindow());

        String fileName = file.toString();


        txtImportPath.setText(file.getAbsolutePath());
        int lastDot = fileName.lastIndexOf('.');

        if (lastDot == -1) {
            return null;
        }

        if(fileName.substring(lastDot).equals(".pdf")){
            return new ImportSalesLoadUseCase(new PdfSalesLoadReader());
        }

        if(fileName.substring(lastDot).equals(".json")){
            return new ImportSalesLoadUseCase(new JsonSalesLoadReader());
        }

        /*if(fileName.substring(lastDot).equals(".xlsx") || fileName.substring(lastDot).equals(".xls")){
            return new ImportSalesLoadUseCase(new ExcelSalesLoadReader());
        }*/

        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeNodes();

        btnConsolidate.setDisable(true);

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


        initializeConditionEditor();

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

    private void editCondition(OrderDTO order) {

        TextInputDialog dialog =
                new TextInputDialog(order.getCondition());

        dialog.setTitle("Editar condição");

        dialog.setHeaderText(
                "Alterar condição do produto "
                        + order.getProduct()
        );

        dialog.setContentText("Condição:");

        Optional<String> result =
                dialog.showAndWait();

        result.ifPresent(newCondition -> {

            orders.stream()
                    .filter(orderPreview ->
                            Objects.equals(
                                    orderPreview.getId(),
                                    order.getId()
                            )
                    )
                    .findFirst()
                    .ifPresent(orderPreview -> {

                        // Altera a condição
                        orderPreview.setCondition(newCondition);

                        // Remove a instrução
                        orderPreview.setInstruction("null");
                    });

            // Atualiza a TableView
            upDateTableVew();
        });
    }


    private void initializeConditionEditor() {

        tableColumnCondition.setCellFactory(column -> {

            TableCell<OrderDTO, String> cell =
                    new TableCell<>() {

                        @Override
                        protected void updateItem(
                                String item,
                                boolean empty
                        ) {
                            super.updateItem(item, empty);

                            if (empty) {
                                setText(null);
                            } else {
                                setText(item);
                            }
                        }
                    };

            cell.setOnContextMenuRequested(event -> {

                if (cell.isEmpty()) {
                    return;
                }

                OrderDTO orderSelect =
                        tableViewOrderCharge
                                .getItems()
                                .get(cell.getIndex());

                editCondition(orderSelect);
            });

            return cell;
        });
    }



}
