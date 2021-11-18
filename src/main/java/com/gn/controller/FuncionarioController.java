package com.gn.controller;

import com.gn.dao.CrudGenericoDAO;
import com.gn.model.Funcionario;
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

public class FuncionarioController implements Initializable, ICadastro {

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
    private JFXTextField tfCPF;
    @FXML
    private JFXTextField tfCRMV;
    @FXML
    private JFXTextField tfSalario;
    @FXML
    private JFXTextField tfTelefone;
    @FXML
    private CheckBox ckVeterinario;
    @FXML
    private JFXTextField tfEndereco;
    @FXML
    private TableView<Funcionario> tableView;

    private CrudGenericoDAO<Funcionario> dao = new CrudGenericoDAO();
    private ObservableList<Funcionario> observableList = FXCollections.observableArrayList();
    private List<Funcionario> listaFuncionarios;
    private Funcionario objetoSelecionado = new Funcionario();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criarColunasTabela();
        atualizarTabela();
    }

    public void change(ActionEvent event) {
        if (ckVeterinario.isSelected()) {
            tfCRMV.setDisable(false);
        } else {
            tfCRMV.setDisable(true);
        }
    }

    @FXML
    public void incluirRegistro(ActionEvent actionEvent) {
        limparCamposFormulario();
    }

    @FXML
    public void salvarRegistro(ActionEvent actionEvent) {
        Funcionario funcionario = new Funcionario();

        if (objetoSelecionado != null) {
            funcionario.setID(objetoSelecionado.getID());
        }

        funcionario.setNome(tfNome.getText());
        funcionario.setEndereco(tfEndereco.getText());
        funcionario.setCpf(Long.parseLong(tfCPF.getText()));
        funcionario.setTelefone(tfTelefone.getText());
        funcionario.setSalario(Double.parseDouble(tfSalario.getText()));
        if (!tfCRMV.isDisable()) {
            funcionario.setCRMV(Double.parseDouble(tfCRMV.getText()));
        }

        dao.salvar(funcionario);

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
        TableColumn<Funcionario, Long> colunaID = new TableColumn<>("ID");
        colunaID.setMinWidth(40);
        colunaID.setMaxWidth(40);
        TableColumn<Funcionario, String> colunaNome = new TableColumn<>("Nome");
        TableColumn<Funcionario, String> colunaEndereco = new TableColumn<>("Endereço");
        TableColumn<Funcionario, Long> colunaCPF = new TableColumn<>("CPF");
        TableColumn<Funcionario, String> colunaTelefone = new TableColumn<>("Telefone");
        TableColumn<Funcionario, Double> colunaSalario = new TableColumn<>("Salário");
        TableColumn<Funcionario, Double> colunaCRMV = new TableColumn<>("CRMV");

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getColumns().addAll(colunaID, colunaNome, colunaEndereco, colunaCPF, colunaTelefone, colunaSalario, colunaCRMV);

        colunaID.setCellValueFactory(new PropertyValueFactory("ID"));
        colunaNome.setCellValueFactory(new PropertyValueFactory("Nome"));
        colunaEndereco.setCellValueFactory(new PropertyValueFactory("endereco"));
        colunaCPF.setCellValueFactory(new PropertyValueFactory("cpf"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory("Telefone"));
        colunaSalario.setCellValueFactory(new PropertyValueFactory("salario"));
        colunaCRMV.setCellValueFactory(new PropertyValueFactory("CRMV"));
    }

    @Override
    public void atualizarTabela() {
        observableList.clear();
        listaFuncionarios = dao.consultar("nome", tfPesquisa.getText(), "Funcionario");

        for (Funcionario f : listaFuncionarios) {
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
        tfEndereco.setText(objetoSelecionado.getEndereco());
        tfCPF.setText(String.valueOf(objetoSelecionado.getCpf()));
        tfTelefone.setText(objetoSelecionado.getTelefone());
        tfSalario.setText(String.valueOf(objetoSelecionado.getSalario()));
        tfCRMV.setText(String.valueOf(objetoSelecionado.getCRMV()));
    }

    @Override
    public void limparCamposFormulario() {
        objetoSelecionado = null;
        tfID.clear();
        tfNome.clear();
        tfEndereco.clear();
        tfCPF.clear();
        tfTelefone.clear();
        tfSalario.clear();
        ckVeterinario.setSelected(false);
        tfCRMV.clear();
        tfCRMV.setDisable(true);
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
