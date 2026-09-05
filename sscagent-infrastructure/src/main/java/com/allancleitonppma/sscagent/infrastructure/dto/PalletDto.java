package com.allancleitonppma.sscagent.infrastructure.dto;

public class PalletDto {

    public String etiquetaPalet;
    public String produto;
    public String apontamento;
    public Double totalLiquido;
    public Integer totalCaixas;
    public String situacao;
    public String local;
    public Integer pacotes;
    public Integer duasAVencer;


    public PalletDto(String etiquetaPalet, String produto, String apontamento, Double totalLiquido, Integer totalCaixas, String situacao, String local, Integer pacotes, Integer duasAVencer) {
        this.etiquetaPalet = etiquetaPalet;
        this.produto = produto;
        this.apontamento = apontamento;
        this.totalLiquido = totalLiquido;
        this.totalCaixas = totalCaixas;
        this.situacao = situacao;
        this.local = local;
        this.pacotes = pacotes;
        this.duasAVencer = duasAVencer;
    }
}
