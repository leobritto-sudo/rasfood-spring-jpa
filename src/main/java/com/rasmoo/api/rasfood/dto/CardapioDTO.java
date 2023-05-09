package com.rasmoo.api.rasfood.dto;

import java.math.BigDecimal;

public class CardapioDTO {
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private String nomeCategoria;

    public CardapioDTO(String nome, String descricao, BigDecimal valor, String nomeCategoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.nomeCategoria = nomeCategoria;
    }

    public CardapioDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    @Override
    public String toString() {
        return "CardapioDTO{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                '}';
    }
}
