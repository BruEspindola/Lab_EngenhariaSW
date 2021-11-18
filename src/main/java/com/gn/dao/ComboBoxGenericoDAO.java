package com.gn.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class ComboBoxGenericoDAO<T> {

    private ObservableList<T> observableList = FXCollections.observableArrayList();

    public ObservableList<T> comboBox(String nomeClasse) {
        List<T> lista = new ArrayList<>();
        Session session = ConexaoBanco.getSessionFactory().openSession();
        session.beginTransaction();
        lista = session.createQuery(" from " + nomeClasse).getResultList();
        session.getTransaction().commit();
        session.close();

        for (T tipo : lista) {
            observableList.add(tipo);
        }
        return observableList;
    }

}
