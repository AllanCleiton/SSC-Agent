package com.allancleitonppma.sscagent.infrastructure.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BoxStockDTO {

    private String endereco;
    private String etiquetaMae;
    private Long etiquetaProduto;
    private Long apont;
    private Long codigoSankhya;
    private String produto;
    private Integer diasAVencer;
    private String dataValidade;
    private BigDecimal pesoLiquidoCaixa;
    private Integer pacotes;
    private String status;
    private String camaraFria;

    public BoxStockDTO() {
    }

    public BoxStockDTO(
            String endereco,
            String etiquetaMae,
            Long etiquetaProduto,
            Long apont,
            Long codigoSankhya,
            String produto,
            Integer diasAVencer,
            String dataValidade,
            BigDecimal pesoLiquidoCaixa,
            Integer pacotes,
            String status,
            String camaraFria) {

        this.endereco = endereco;
        this.etiquetaMae = etiquetaMae;
        this.etiquetaProduto = etiquetaProduto;
        this.apont = apont;
        this.codigoSankhya = codigoSankhya;
        this.produto = produto;
        this.diasAVencer = diasAVencer;
        this.dataValidade = dataValidade;
        this.pesoLiquidoCaixa = pesoLiquidoCaixa;
        this.pacotes = pacotes;
        this.status = status;
        this.camaraFria = camaraFria;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEtiquetaMae() {
        return etiquetaMae;
    }

    public void setEtiquetaMae(String etiquetaMae) {
        this.etiquetaMae = etiquetaMae;
    }

    public Long getEtiquetaProduto() {
        return etiquetaProduto;
    }

    public void setEtiquetaProduto(Long etiquetaProduto) {
        this.etiquetaProduto = etiquetaProduto;
    }

    public Long getApont() {
        return apont;
    }

    public void setApont(Long apont) {
        this.apont = apont;
    }

    public Long getCodigoSankhya() {
        return codigoSankhya;
    }

    public void setCodigoSankhya(Long codigoSankhya) {
        this.codigoSankhya = codigoSankhya;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public Integer getDiasAVencer() {
        return diasAVencer;
    }

    public void setDiasAVencer(Integer diasAVencer) {
        this.diasAVencer = diasAVencer;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }

    public BigDecimal getPesoLiquidoCaixa() {
        return pesoLiquidoCaixa;
    }

    public void setPesoLiquidoCaixa(BigDecimal pesoLiquidoCaixa) {
        this.pesoLiquidoCaixa = pesoLiquidoCaixa;
    }

    public Integer getPacotes() {
        return pacotes;
    }

    public void setPacotes(Integer pacotes) {
        this.pacotes = pacotes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCamaraFria() {
        return camaraFria;
    }

    public void setCamaraFria(String camaraFria) {
        this.camaraFria = camaraFria;
    }
}