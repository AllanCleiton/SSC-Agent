package com.allancleitonppma.sscagent.desktop.controller;

import com.allancleitonppma.sscagent.application.port.SalesLoadReader;
import com.allancleitonppma.sscagent.application.usecase.ImportSalesLoadUseCase;
import com.allancleitonppma.sscagent.desktop.alerts.DefaultAlert;
import com.allancleitonppma.sscagent.desktop.dto.OrderDTO;
import com.allancleitonppma.sscagent.domain.model.entities.Order;
import com.allancleitonppma.sscagent.domain.model.entities.OrderLine;
import com.allancleitonppma.sscagent.domain.model.entities.SalesLoad;
import com.allancleitonppma.sscagent.infrastructure.json.JsonSalesLoadReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    private TextField txtOrderCharger;
    @FXML
    private Button btnLoader;
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
    private ObservableList<OrderDTO> orderDTOS;

    @FXML
    public void onTabOrderDeCargaAction(){
        System.out.println("Entrou!");
    }

    @FXML
    public void onLoaderAction(){

        orderDTOS = FXCollections.observableArrayList(getListOrderDto());
        tbOrderChargeList.setItems(orderDTOS);

        try {

            lbStatus.setText("Sucesso!");

        }catch (NumberFormatException e) {
            lbStatus.setText("erro!");
            DefaultAlert.showAlert("Erro!", "Parce Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes(){
        tableColumnProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        tableColumNeed.setCellValueFactory(new PropertyValueFactory<>("need"));
        tableColumnCondition.setCellValueFactory(new PropertyValueFactory<>("condition"));
        tableColumnOrder.setCellValueFactory(new PropertyValueFactory<>("order"));
        tableColumnInstruction.setCellValueFactory(new PropertyValueFactory<>("instruction"));
    }

    private List<OrderDTO> getListOrderDto() {

        SalesLoadReader reader = new JsonSalesLoadReader();
        ImportSalesLoadUseCase useCase = new ImportSalesLoadUseCase(reader);

        Path path = Path.of(
                "C:\\Users\\allan\\Documents\\MyWorkspace\\SSCAgent\\SSCAGENT\\sscagent-desktop\\src\\main\\resources\\Data\\CargaDeVenda.exemplo.json"
        );

        SalesLoad salesLoad = useCase.execute(path);

        List<OrderDTO> list = new ArrayList<>();

        for (Order order : salesLoad.Orders) {

            for (OrderLine orderLine : order.lines) {

                OrderDTO orderDTO = new OrderDTO();

                orderDTO.setOrder(String.valueOf(order.orderId));
                orderDTO.setInstruction(String.valueOf(order.loadingInstruction));
                orderDTO.setProduct(String.valueOf(orderLine.productCode));
                orderDTO.setNeed(String.valueOf(orderLine.quantity));
                orderDTO.setCondition(String.valueOf(orderLine.condition));

                list.add(orderDTO);
            }
        }

        return list;
    }
}
