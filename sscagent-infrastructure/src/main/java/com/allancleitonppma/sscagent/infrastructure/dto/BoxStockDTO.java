package com.allancleitonppma.sscagent.infrastructure.dto;

import java.math.BigDecimal;

public class BoxStockDTO {
    /**
     *REFERENCIA A COLUNA {[0]-Endereço}
     */
    private String address;
    /**
     *REFERENCIA A COLUNA {[1]-Etiq Mãe}
     */
    private String motherId;
    /**
     *REFERENCIA A COLUNA {[2]-Etiq prod}
     */
    private Long productId;
    /**
     *REFERENCIA A COLUNA {[3]-Apont}
     */
    private Long integrationCode;
    /**
     *REFERENCIA A COLUNA {[4]-Cód Sankhya}
     */
    private Long SankhyaId;
    /**
     *REFERENCIA A COLUNA {[5]-Produto}
     */
    private String productDescription;
    /**
     *REFERENCIA A COLUNA {[6]-Dias a vencer}
     */
    private Integer daysToExpire;
    /**
     *REFERENCIA A COLUNA {[7]-Data Val}
     */
    private String validity;
    /**
     *REFERENCIA A COLUNA {[13]-Peso Liq CX}
     */
    private BigDecimal netWeight;
    /**
     *REFERENCIA A COLUNA {[14]-Pacotes}
     */
    private Integer packages;
    /**
     *REFERENCIA A COLUNA {[15]-Data Fab}
     */
    private String manufacturingDate;
    /**
     *REFERENCIA A COLUNA {[16]-Data Fab}
     */
    private String status;

    public BoxStockDTO() {
    }

    public BoxStockDTO(
            String address,
            String motherId,
            Long productId,
            Long integrationId,
            Long SankhyaId,
            String productDescription,
            Integer daysToExpire,
            String validity,
            BigDecimal netWeight,
            Integer packages,
            String manufacturingDate,
            String status) {

        this.address = address;
        this.motherId = motherId;
        this.productId = productId;
        this.integrationCode = integrationId;
        this.SankhyaId = SankhyaId;
        this.productDescription = productDescription;
        this.daysToExpire = daysToExpire;
        this.validity = validity;
        this.netWeight = netWeight;
        this.packages = packages;
        this.manufacturingDate = manufacturingDate;
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getIntegrationCode() {
        return integrationCode;
    }

    public void setIntegrationCode(Long integrationCode) {
        this.integrationCode = integrationCode;
    }

    public Long getSankhyaId() {
        return SankhyaId;
    }

    public void setSankhyaId(Long sankhyaId) {
        this.SankhyaId = sankhyaId;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Integer getDaysToExpire() {
        return daysToExpire;
    }

    public void setDaysToExpire(Integer daysToExpire) {
        this.daysToExpire = daysToExpire;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public Integer getPackages() {
        return packages;
    }

    public void setPackages(Integer packages) {
        this.packages = packages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(String manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    @Override
    public String toString() {
        return "BoxStockDTO{" +
                "address='" + address + '\'' +
                ", motherId='" + motherId + '\'' +
                ", productId=" + productId +
                ", integrationId=" + integrationCode +
                ", SankhyaId=" + SankhyaId +
                ", productDescription='" + productDescription + '\'' +
                ", daysToExpire=" + daysToExpire +
                ", validity='" + validity + '\'' +
                ", netWeight=" + netWeight +
                ", packages=" + packages +
                ", manufacturingDate='" + manufacturingDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}