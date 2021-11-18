package com.gn.dao;

import com.gn.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConexaoBanco {

    private static SessionFactory conexao = null;
    private static Configuration configuracao;

    private static SessionFactory buildSessionFactory() {
        // --> Objeto que armazena as configuracoes de conexao
        configuracao = new Configuration().configure();

        // --> Configurando usuario e senha para acesso ao banco de dados
        configuracao.setProperty("hibernate.connection.username", "root");
        configuracao.setProperty("hibernate.connection.password", "qwe123QWE!@#");

        // --> Indicando o mapeamento das classes
        configuracao.addPackage("com.gn.model").addAnnotatedClass(Servico.class);
        configuracao.addPackage("com.gn.model").addAnnotatedClass(Funcionario.class);
        configuracao.addPackage("com.gn.model").addAnnotatedClass(Produto.class);
        configuracao.addPackage("com.gn.model").addAnnotatedClass(Cliente.class);
        configuracao.addPackage("com.gn.model").addAnnotatedClass(Pedido.class);

        // --> sessionFactory recebe a construcao da sessao de conexao com o banco de dados
        conexao = configuracao.buildSessionFactory();

        return conexao;
    }

    public static SessionFactory getSessionFactory() {
        if (conexao == null) {
            conexao = buildSessionFactory();
        }
        return conexao;
    }

}
