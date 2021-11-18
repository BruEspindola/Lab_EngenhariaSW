package com.gn.controller;

import com.gn.DTO.ProdutoDTO;
import com.gn.dao.ComboBoxGenericoDAO;
import com.gn.dao.CrudGenericoDAO;
import com.gn.model.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PedidoController implements Initializable {

    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnFechar;
    @FXML
    private JFXComboBox<Cliente> cboxCliente;
    @FXML
    private CheckBox ckCliente;
    @FXML
    private JFXComboBox<Produto> cboxProduto;
    @FXML
    private JFXTextField tfQuantidade;
    @FXML
    private TableView<ProdutoDTO> tableView;
    @FXML
    private TextArea txtValorTotal;

    private CrudGenericoDAO<Pedido> dao = new CrudGenericoDAO();
    private ComboBoxGenericoDAO<Cliente> cboxClienteDAO = new ComboBoxGenericoDAO<>();
    private ComboBoxGenericoDAO<Produto> cboxProdutoDAO = new ComboBoxGenericoDAO<>();
    private ObservableList<ProdutoDTO> observableList = FXCollections.observableArrayList();
    private List<ProdutoDTO> listaProdutosDTO = new ArrayList<ProdutoDTO>();
    private List<Produto> listaProdutos = new ArrayList<Produto>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cboxCliente.setItems(cboxClienteDAO.comboBox("Cliente"));
        cboxProduto.setItems(cboxProdutoDAO.comboBox("Produto"));
        criarColunasTabela();
        atualizarTabela();
    }

    public void change(ActionEvent event) {
        if (ckCliente.isSelected()) {
            cboxCliente.setDisable(false);
        } else {
            cboxCliente.setDisable(true);
        }
    }

    @FXML
    public void fecharPedido(ActionEvent actionEvent) {
        Pedido pedido = new Pedido();

        pedido.setDataPedido(java.time.LocalDate.now());
        pedido.setHora(java.time.LocalTime.now());
        pedido.setValor(Double.parseDouble(txtValorTotal.getText()));
        if (cboxCliente.getValue() != null) {
            pedido.setCliente(cboxCliente.getValue());
        }
        pedido.setProdutos(listaProdutos);

        dao.salvar(pedido);

        listaProdutosDTO.clear();
        listaProdutos.clear();
        atualizarTabela();
        limparCamposFormulario();
        ckCliente.setSelected(false);
        cboxCliente.setValue(null);
        cboxCliente.setDisable(true);
    }

    public void criarColunasTabela() {
        TableColumn<ProdutoDTO, Long> colunaID = new TableColumn<>("ID");
        colunaID.setMinWidth(40);
        colunaID.setMaxWidth(40);
        TableColumn<ProdutoDTO, String> colunaNome = new TableColumn<>("Nome");
        TableColumn<ProdutoDTO, Long> colunaQuant = new TableColumn<>("Quantidade");
        TableColumn<ProdutoDTO, Double> colunaPreco = new TableColumn<>("Valor Unitário");
        TableColumn<ProdutoDTO, Double> colunaPrecoTotal = new TableColumn<>("Valor total");

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getColumns().addAll(colunaID, colunaNome, colunaQuant, colunaPreco, colunaPrecoTotal);

        colunaID.setCellValueFactory(new PropertyValueFactory("ID"));
        colunaNome.setCellValueFactory(new PropertyValueFactory("Nome"));
        colunaQuant.setCellValueFactory(new PropertyValueFactory("quantidade"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory("valorUni"));
        colunaPrecoTotal.setCellValueFactory(new PropertyValueFactory("valorTotal"));
    }

    @FXML
    public void addProduto(ActionEvent actionEvent) {
        Produto produto = cboxProduto.getValue();

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setID(produto.getID());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setQuantidade(Long.parseLong(tfQuantidade.getText()));
        produtoDTO.setValorUni(produto.getPreco());
        produtoDTO.setValorTotal(produtoDTO.getQuantidade() * produtoDTO.getValorUni());

        listaProdutos.add(produto);
        listaProdutosDTO.add(produtoDTO);

        atualizarTabela();
        limparCamposFormulario();
    }

    public void atualizarTabela() {
        observableList.clear();
        Double valorTotal = 0D;

        if (listaProdutosDTO != null) {
            for (ProdutoDTO p : listaProdutosDTO) {
                valorTotal += p.getValorTotal();
                observableList.add(p);
            }
        }

        txtValorTotal.setText(String.valueOf(valorTotal));
        tableView.getItems().setAll(observableList);
        tableView.getSelectionModel().selectFirst();
    }

    public void limparCamposFormulario() {
        cboxProduto.setValue(null);
        tfQuantidade.clear();
    }

}
