package com.gn.model;

import javax.persistence.*;

@Entity
@Table(name="funcionario")
public class Funcionario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long ID;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "cpf", nullable = false)
    private Long cpf;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "salario", nullable = false)
    private Double salario;

    @Column(name = "CRMV")
    private Double crmv;

    public Funcionario() {
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Double getCRMV() {
        return crmv;
    }

    public void setCRMV(Double crmv) {
        this.crmv = crmv;
    }

    @Override
    public String toString() {
        return nome;
    }
}
