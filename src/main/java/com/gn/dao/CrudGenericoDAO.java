package com.gn.dao;

import com.gn.model.Funcionario;
import com.gn.model.Produto;
import com.gn.model.Servico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CrudGenericoDAO<Classe> {

    public boolean salvar(Classe classe) {
        try {
            Session session = ConexaoBanco.getSessionFactory().openSession();
            session.beginTransaction();
            session.merge(classe);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception erro) {
            System.out.println("Ocorreou o erro: " + erro);
            return false;
        }
    }

    public List<Classe> consultar(String colunaBusca, String stringBuscada, String nomeClasse) {

        List<Classe> lista = new ArrayList<>();
        Session session = ConexaoBanco.getSessionFactory().openSession();
        session.beginTransaction();
        if (stringBuscada.length() == 0) {
            lista = session.createQuery(" from " + nomeClasse).getResultList();
        } else {
            // t é o apelido da tabela
            // comando para realizar uma busca com retorno de tudo que comeca com o que foi digitado
            lista = session.createQuery(" from " + nomeClasse + " m where m." + colunaBusca + " like " + "'" + stringBuscada + "%'").getResultList();
        }
        session.getTransaction().commit();
        session.close();

        return lista;
    }

    public void excluir(Classe classe) {
        try {
            Session session = ConexaoBanco.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(classe);
            session.getTransaction().commit();
            session.close();
            System.out.println("Registro excluido com sucesso");
        } catch (Exception erro) {
            System.out.println("Ocorreou o erro: " + erro);
        }
    }

    public List<Classe> listBetweenDates(String nomeClasse, LocalDate dataInicio, LocalDate dataFim) {
        List<Classe> lista = new ArrayList<>();
        Session session = ConexaoBanco.getSessionFactory().openSession();
        session.beginTransaction();
        lista = session.createQuery(" from " + nomeClasse + " WHERE data" + nomeClasse + " BETWEEN "  + "'" + dataInicio + "'" + " AND " + "'" + dataFim + "'").getResultList();
        session.getTransaction().commit();
        session.close();

        return lista;
    }

}
