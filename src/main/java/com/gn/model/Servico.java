package com.gn.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="servico")
public class Servico implements Serializable {

    // adicionar comboBox funcionario, cliente e recibo

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long ID;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "dataServico", nullable = false)
    private LocalDate dataServico;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "funcionario_id", referencedColumnName = "ID")
    private Funcionario funcionario;

    @Column(name = "preco", nullable = false)
    private Double preco;

    @Column(name = "custo", nullable = false)
    private Double custo;

    public Servico() { }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataServico() {
        return dataServico;
    }

    public void setDataServico(LocalDate dataServico) {
        this.dataServico = dataServico;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getCusto() {
        return custo;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void setCusto(Double custo) {
        this.custo = custo;
    }
}
