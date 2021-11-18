package com.gn.controller;

import com.gn.dao.CrudGenericoDAO;
import com.gn.model.Produto;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProdutoController  implements Initializable, ICadastro {

    @FXML
    private Button btnNovo;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnNExcluir;
    @FXML
    private TextField tfPesquisa;
    @FXML
    private JFXTextField tfID;
    @FXML
    private JFXTextField tfNome;
    @FXML
    private JFXTextField tfPreco;
    @FXML
    private JFXTextField tfCusto;
    @FXML
    private TableView<Produto> tableView;

    private CrudGenericoDAO<Produto> dao = new CrudGenericoDAO();
    private ObservableList<Produto> observableList = FXCollections.observableArrayList();
    private List<Produto> listaProdutos;
    private Produto objetoSelecionado = new Produto();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criarColunasTabela();
        atualizarTabela();
    }

    @FXML
    public void incluirRegistro(ActionEvent actionEvent) {
        limparCamposFormulario();
    }

    @FXML
    public void salvarRegistro(ActionEvent actionEvent) {
        Produto produto = new Produto();

        if (objetoSelecionado != null) {
            produto.setID(objetoSelecionado.getID());
        }

        produto.setNome(tfNome.getText());
        produto.setPreco(Double.parseDouble(tfPreco.getText()));
        produto.setCusto(Double.parseDouble(tfCusto.getText()));

        dao.salvar(produto);

        atualizarTabela();
        limparCamposFormulario();
    }

    @FXML
    public void excluirRegistro(ActionEvent actionEvent) {
        dao.excluir(objetoSelecionado);

        limparCamposFormulario();
        atualizarTabela();
    }

    @Override
    public void criarColunasTabela() {
        TableColumn<Produto, Long> colunaID = new TableColumn<>("ID");
        colunaID.setMinWidth(40);
        colunaID.setMaxWidth(40);
        TableColumn<Produto, String> colunaNome = new TableColumn<>("Nome");
        TableColumn<Produto, Double> colunaPreco = new TableColumn<>("Preço");
        TableColumn<Produto, Double> colunaCusto = new TableColumn<>("Custo");

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getColumns().addAll(colunaID, colunaNome, colunaPreco, colunaCusto);

        colunaID.setCellValueFactory(new PropertyValueFactory("ID"));
        colunaNome.setCellValueFactory(new PropertyValueFactory("Nome"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory("preco"));
        colunaCusto.setCellValueFactory(new PropertyValueFactory("custo"));
    }

    @Override
    public void atualizarTabela() {
        observableList.clear();
        listaProdutos = dao.consultar("nome", tfPesquisa.getText(), "Produto");

        for (Produto f : listaProdutos) {
            observableList.add(f);
        }

        tableView.getItems().setAll(observableList);
        tableView.getSelectionModel().selectFirst();
    }

    @Override
    public void setCamposFormulario() {
        objetoSelecionado = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
        tfID.setText(String.valueOf(objetoSelecionado.getID()));
        tfNome.setText(objetoSelecionado.getNome());
        tfPreco.setText(String.valueOf(objetoSelecionado.getPreco()));
        tfCusto.setText(String.valueOf(objetoSelecionado.getCusto()));
    }

    @Override
    public void limparCamposFormulario() {
        objetoSelecionado = null;
        tfID.clear();
        tfNome.clear();
        tfPreco.clear();
        tfCusto.clear();
        tfNome.requestFocus();
    }

    @FXML
    public void filtrarRegistros(KeyEvent keyEvent) {
        atualizarTabela();
    }

    @FXML
    public void clicarTabela(MouseEvent mouseEvent) {
        setCamposFormulario();
    }

    public void pesquisar(ActionEvent actionEvent) { }

}
