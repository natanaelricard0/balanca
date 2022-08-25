package br.com.cdp.balanca.controller;

import br.com.cdp.balanca.model.entities.Pesagem;
import br.com.cdp.balanca.model.services.PesagemServices;
import br.com.cdp.balanca.utils.Alerts;
import br.com.cdp.balanca.utils.ShowReports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class RelatorioController implements Initializable {

    PesagemServices pesagemServices = new PesagemServices();

    @FXML
    private DatePicker dataInicial;

    @FXML
    private DatePicker dataFinal;

    @FXML
    private TextField txtVeiculo;

    @FXML
    private TextField txtUsuario;

    @FXML
    private ComboBox<String> cmbTipo;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnGerarPdf;

    @FXML
    private TableView<Pesagem> tblPesagem;

    @FXML
    private TableColumn<Pesagem, String> colPlaca;

    @FXML
    private TableColumn<Pesagem, Integer> colAutorizacao;

    @FXML
    private TableColumn<Pesagem, Float> colTara;

    @FXML
    private TableColumn<Pesagem, Float> colPesoCheio;

    @FXML
    private TableColumn<Pesagem, Timestamp> colDataHora;

    @FXML
    private TableColumn<Pesagem, String> colOperdador;

    @FXML
    private TableColumn<Pesagem, Float> colPesoLiquido;

    @FXML
    private TableColumn<Pesagem, String> colusuarioPrimeiraPesagem;

    @FXML
    private TableColumn<Pesagem, String> colusuarioSegundaPesagem;

    @FXML
    private TableColumn<Pesagem, String> coldataPrimeiraPesagem;

    @FXML
    private TableColumn<Pesagem, String> coldataSegundaPesagem;

    private ObservableList<Pesagem> pesagens;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }

    private void initialize() {
        populateComboBox();
        dataInicial.setValue(LocalDate.now());
        dataFinal.setValue(LocalDate.now());
        initialNodes();
    }

    private void populateComboBox() {
        String[] tipos = {"Entrada", "Saída"};
        for (String tipo : tipos) {
            cmbTipo.getItems().add(tipo);
        }
        cmbTipo.getSelectionModel().select(0);
    }

    @FXML
    private void buscar() {
        updateTableView();
    }

    private void updateTableView() {
        char tipo = cmbTipo.getSelectionModel().getSelectedItem().charAt(0);
        String placa = txtVeiculo.getText();
        Timestamp dataInicial = Timestamp.valueOf(this.dataInicial.getValue().atTime(0, 0));
        Timestamp dataFinal = Timestamp.valueOf(this.dataFinal.getValue().atTime(23, 59));
        String operador = txtUsuario.getText();

        List<Pesagem> result = pesagemServices.findByRelatorio(operador ,placa, Timestamp.valueOf(dataInicial.toString()), Timestamp.valueOf(dataFinal.toString()), tipo);

        pesagens = FXCollections.observableArrayList(result);
        tblPesagem.setItems(pesagens);
    }

    @FXML
    private void printRelatorio(){
        if(pesagens.size() > 0) {
            ShowReports.printRelatorioPesagem(pesagens);
        } else {
            Alerts.showAlert("Aviso", "", "Nenhum registro encontrado para o filtro informado", Alert.AlertType.WARNING);
        }
    }

    private void initialNodes(){
        colAutorizacao.setCellValueFactory(new PropertyValueFactory<>("idAutorizacao"));
        colDataHora.setCellValueFactory(new PropertyValueFactory<>("dataSegundapesagem"));
        colOperdador.setCellValueFactory(new PropertyValueFactory<>("usuarioSegundaPesagem"));
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colPesoCheio.setCellValueFactory(new PropertyValueFactory<>("pesoBruto"));
        colPesoLiquido.setCellValueFactory(new PropertyValueFactory<>("pesoLiquido"));
        colTara.setCellValueFactory(new PropertyValueFactory<>("tara"));
        colusuarioPrimeiraPesagem.setCellValueFactory((new PropertyValueFactory<>("usuarioPrimeiraPesagem")));
       colusuarioSegundaPesagem.setCellValueFactory(new PropertyValueFactory<>("usuarioSegundaPesagem"));

    }
}
