package com.gn.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.gn.dao.CrudGenericoDAO;
import com.gn.model.Cliente;
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
import jfxtras.scene.control.LocalTimeTextField;

public class cadastro implements Initializable, ICadastro {


    @FXML
    private Button btnNovo;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField tfPesquisa;
    @FXML
    private JFXTextField tfID;
    @FXML
    private JFXTextField tfNome;
    @FXML
    private JFXTextField tfCPF;
    @FXML
    private JFXTextField tfEndereco;
    @FXML
    public JFXTextField TFNomePet;
    @FXML
    private TableView<Cliente> tableView;

    private CrudGenericoDAO<Cliente> dao = new CrudGenericoDAO();
    private ObservableList<Cliente> observableList = FXCollections.observableArrayList();
    private List<Cliente> listaCliente;
    private Cliente objetoSelecionado = new Cliente();

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
        Cliente cliente = new Cliente();

        if (objetoSelecionado != null) {
            cliente.setID(objetoSelecionado.getID());
        }

        cliente.setNome(tfNome.getText());
        cliente.setCPF(tfCPF.getText());
        cliente.setEndereco(tfEndereco.getText());

        dao.salvar(cliente);

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
        TableColumn<Cliente, Long> colunaID = new TableColumn<>("ID");
        colunaID.setMinWidth(40);
        colunaID.setMaxWidth(40);
        TableColumn<Cliente, String> colunaNome = new TableColumn<>("Nome");
        TableColumn<Cliente, String> colunaCPF = new TableColumn<>("CPF");
        TableColumn<Cliente, String> colunaEndereco = new TableColumn<>("Endereço");

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getColumns().addAll(colunaID, colunaNome, colunaCPF, colunaEndereco);

        colunaID.setCellValueFactory(new PropertyValueFactory("ID"));
        colunaNome.setCellValueFactory(new PropertyValueFactory("Nome"));
        colunaCPF.setCellValueFactory(new PropertyValueFactory("CPF"));
        colunaEndereco.setCellValueFactory(new PropertyValueFactory("Endereço"));
    }

    @Override
    public void atualizarTabela() {
        observableList.clear();
        listaCliente = dao.consultar("nome", tfPesquisa.getText(), "Cliente");

        for (Cliente c : listaCliente) {
            observableList.add(c);
        }

        tableView.getItems().setAll(observableList);
        tableView.getSelectionModel().selectFirst();

    }

    @Override
    public void setCamposFormulario() {
        objetoSelecionado = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
        tfID.setText(String.valueOf(objetoSelecionado.getID()));
        tfNome.setText(objetoSelecionado.getNome());
        tfCPF.setText(objetoSelecionado.getCPF());
        tfEndereco.setText(objetoSelecionado.getEndereco());
    }

    @Override
    public void limparCamposFormulario() {
        objetoSelecionado = null;
        tfID.clear();
        tfNome.clear();
        tfCPF.clear();
        tfEndereco.clear();
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
