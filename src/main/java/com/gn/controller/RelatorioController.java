package com.gn.controller;

import com.gn.dao.CrudGenericoDAO;
import com.gn.model.Pedido;
import com.gn.model.Servico;
import com.opencsv.CSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class RelatorioController {

    @FXML
    private Button btnGerarRelatorio;
    @FXML
    private DatePicker dpDataInicio;
    @FXML
    private DatePicker dtDataFim;

    private CrudGenericoDAO<Servico> daoServico = new CrudGenericoDAO();
    private CrudGenericoDAO<Pedido> daoPedido = new CrudGenericoDAO();
    private List<Servico> listaServicos = new ArrayList<>();
    private List<Pedido> listaPedidos = new ArrayList<>();
    private static final String CSV_PATH = "./src/main/resources/Relatorio.csv";
    List<String[]> relatorio = new ArrayList<String[]>();

    @FXML
    public void gerarRelatorio(ActionEvent actionEvent) {
        try {
            System.out.println("iniciando geracao do Relatorio.csv");

            File arquivoCSV = new File(CSV_PATH);
            Double valorTotalServicos = 0D;
            Double valorTotalPedidos = 0D;
            Double valorTotalRelatorio = 0D;

            if (arquivoCSV.exists()) {
                arquivoCSV.delete();
                System.out.println("Relatorio antigo apagado.");
            }

            FileWriter fw = new FileWriter(new File(CSV_PATH));
            CSVWriter cw = new CSVWriter(fw);

            listaServicos = daoServico.listBetweenDates("Servico", dpDataInicio.getValue(), dtDataFim.getValue());
            int quantServicos = listaServicos.size();

            listaPedidos = daoPedido.listBetweenDates("Pedido", dpDataInicio.getValue(), dtDataFim.getValue());
            int quantPedidos = listaPedidos.size();

            String[] headerServico = {"data", "descricao", "hora", "funcionario", "valor", "custo"};
            relatorio.add(headerServico);

            for (Servico s : listaServicos) {
                String[] item = {String.valueOf(s.getDataServico()), s.getDescricao(), String.valueOf(s.getHora()),
                        String.valueOf(s.getFuncionario()), String.valueOf(s.getPreco()), String.valueOf(s.getCusto())};
                valorTotalServicos += s.getPreco();
                relatorio.add(item);
            }

            pulaLinha();

            String[] headerPedido = {"data", "hora", "valor"};
            relatorio.add(headerPedido);

            for (Pedido p : listaPedidos) {
                String[] item = {String.valueOf(p.getDataPedido()), String.valueOf(p.getHora()),
                        String.valueOf(p.getValor())};
                valorTotalPedidos += p.getValor();
                relatorio.add(item);
            }

            pulaLinha();

            valorTotalRelatorio = valorTotalServicos + valorTotalPedidos;

            String[] headerFechamento = {"N. de Servicos", "Valor Total Servicos", "N. de Pedidos", "Valor Total Pedidos",
                    "Valor Total Relatorio"};
            String[] valoresFechamento = {String.valueOf(quantServicos), String.valueOf(valorTotalServicos),
                    String.valueOf(quantPedidos), String.valueOf(valorTotalPedidos), String.valueOf(valorTotalRelatorio)};
            relatorio.add(headerFechamento);
            relatorio.add(valoresFechamento);

            cw.writeAll(relatorio);

            cw.close();
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pulaLinha() {
        String[] linhaVazia = {};
        relatorio.add(linhaVazia);
    }

}
