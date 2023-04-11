package com.rasmoo.api.rasfood.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordens")
public class Ordem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "valor_total")
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Column(name = "data_de_criacao")
    private LocalDateTime dataDeCriacao = LocalDateTime.now();

    @OneToMany(mappedBy = "ordem", cascade = CascadeType.ALL)
    private List<OrdensCardapio> ordensCardapioList = new ArrayList<>();

//    @ManyToMany
//    @JoinTable(name = "oderns_cardapio", joinColumns = @JoinColumn(name = "ordens_id"), inverseJoinColumns = @JoinColumn(name = "cardapio_id"))
//    private List<Cardapio> cardapioList;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    public Ordem() {
    }

    public void addOrdensCardapio(OrdensCardapio ordensCardapio){
        ordensCardapio.setOrdem(this);
        this.ordensCardapioList.add(ordensCardapio);
        this.valorTotal = valorTotal.add(ordensCardapio.getValor().multiply(BigDecimal.valueOf(ordensCardapio.getQuantidade())));
    }

    public Ordem(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    public List<OrdensCardapio> getOrdensCardapioList() {
        return ordensCardapioList;
    }

    public void setOrdensCardapioList(List<OrdensCardapio> ordensCardapioList) {
        this.ordensCardapioList = ordensCardapioList;
    }

    @Override
    public String toString() {
        return "Ordem{" +
                "id=" + id +
                ", valorTotal=" + valorTotal +
                ", dataDeCriacao=" + dataDeCriacao +
                ", ordensCardapioList=" + ordensCardapioList +
                ", cliente=" + cliente +
                '}';
    }
}
