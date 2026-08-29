package com.allancleitonppma.sscagent.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class MapCollectNodeController implements Initializable {
    @FXML
    private Label lbMapID;
    @FXML
    private Label lbMapProduct;
    @FXML
    private Label lbQuantity;
    @FXML
    private Label lbPending;
    @FXML
    private Label lbCondition;
    @FXML
    private Label lbMapPositions;

    @FXML
    private ToggleButton thisToggle;

    private List<AllocationPositionController> address = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadAllocationPositions();
            loadAllocationPositions();
            loadAllocationPositions();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ESTE METODO VAI ADICIONAR A LISTA De addres (QUE REPRESENTA O ENDEREÇAMENTO
    // ONDE O PRODUTO DA SEPARAÇÃO ESTÁ ALOCADO) TODOS AS POSIÇÕES ONDE O SISTEMA
    // PEGOU O PRODUTO E TAMBEM TODAS AS POSIÇÕES QUE EXISTE ESTE PRODUTO       */

    private void loadAllocationPositions() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/gui/AllocationPositionView.fxml")
        );
        VBox node = loader.load();
        AllocationPositionController positionController = loader.getController();

        positionController.setAllocationProduct(node);

        address.add(positionController);

    }

    @FXML
    public void editMapCollectItem() throws IOException {

        if(thisToggle.isSelected()){
            System.out.println("EDITAR MAPA...");

            //* QUANDO CLICAR EM NO BOTÃO DO MAPA DE SEPARAÇÃO, DEVE
            //  CARREGAR AS TELAS DE ENDEREÇOS DO PRODUTO DO BLOCO DE
            //  SEPARAÇÃO SELECIONAO.
            // */
        }else{
            System.out.println("SAIR DE EDITAR MAPA...");
            //* AQUI O COMPORTAMENTO ESPERADO É: QUANDO ENTRAR NO MODO DE
            //  EDIÇÃO, E NÃO ALTERAR NADA, PODE SIMPLISMETE CLICAR NO BOTÃO,
            //  MAS SE EDITAR, DEVE SALVAR OU CANCELAR AS ALTERAÇÕES.
            // *//
        }

    }

    public MapCollectNodeController(){};

    public MapCollectNodeController(String lbMapID, String lbMapProduct, String quantity, String lbPending, String lbCondition, String lbMapPositions) {
        this.lbMapID.setText(lbMapID);
        this.lbMapProduct.setText(lbMapProduct);
        this.lbQuantity.setText(quantity);
        this.lbPending.setText(lbPending);
        this.lbCondition.setText(lbCondition);
        this.lbMapPositions.setText(lbMapPositions);
    }

    public Label getLbMapID() {
        return lbMapID;
    }

    public void setLbMapID(Label lbMapID) {
        this.lbMapID = lbMapID;
    }

    public Label getLbMapProduct() {
        return lbMapProduct;
    }

    public void setLbMapProduct(Label lbMapProduct) {
        this.lbMapProduct = lbMapProduct;
    }

    public Label getLbQuantity() {
        return lbQuantity;
    }

    public void setLbQuantity(Label lbQuantity) {
        this.lbQuantity = lbQuantity;
    }

    public Label getLbPending() {
        return lbPending;
    }

    public void setLbPending(Label lbPending) {
        this.lbPending = lbPending;
    }

    public Label getLbCondition() {
        return lbCondition;
    }

    public void setLbCondition(Label lbCondition) {
        this.lbCondition = lbCondition;
    }

    public Label getLbMapPositions() {
        return lbMapPositions;
    }

    public void setLbMapPositions(Label lbMapPositions) {
        this.lbMapPositions = lbMapPositions;
    }

    public ToggleButton getThisToggle() {
        return thisToggle;
    }

    public void setThisToggle(ToggleButton thisToggle) {
        this.thisToggle = thisToggle;
    }

    public List<AllocationPositionController> getPositonsAddress() {
        return address;
    }

    public void setAddress(List<AllocationPositionController> address) {
        this.address = address;
    }


}
