package br.com.cdp.balanca.controller;

import br.com.cdp.balanca.application.Main;
import br.com.cdp.balanca.listeners.DataChangeListeners;
import br.com.cdp.balanca.model.entities.AutorizacaoEntradaSaida;
import br.com.cdp.balanca.model.entities.ItemAutorizacao;
import br.com.cdp.balanca.model.entities.Pesagem;
import br.com.cdp.balanca.model.entities.Veiculo;
import br.com.cdp.balanca.model.services.AutorizacaoEntradaSaidaServices;
import br.com.cdp.balanca.model.services.PesagemServices;
import br.com.cdp.balanca.model.services.VeiculoServices;
import br.com.cdp.balanca.utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PesagemController implements Initializable {

    private AutorizacaoEntradaSaidaServices service;

    private VeiculoServices veiculoServices;

    private PesagemServices pesagemServices;

    private Boolean primeiraPesagem;

    private Pesagem pesagem;

    private List<DataChangeListeners> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtVeiculo;

    @FXML
    private TextField txtAutorizacaoEntrada;

    @FXML
    private TextField txtNotaFiscal;

    @FXML
    private TextField txtPesoCheio;

    @FXML
    private TextField txtDataHoraPesagemCheio;

    @FXML
    private TextField txtPesoVazio;

    @FXML
    private TextField txtDataHoraPesagemVazio;

    @FXML
    private TextField txtPesoLiquido;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnPesar;

    @FXML
    private Label lblErrorAutorizacao;

    @FXML
    private GridPane gridPaneVeiculo;

    @FXML
    private Label lblCodigo;

    @FXML
    private Label lblPlaca;

    @FXML
    private Label lblTara;

    @FXML
    private TableView<ItemAutorizacao> tableViewItemAutorizacao;

    @FXML
    private TableColumn<ItemAutorizacao, Integer> tableColumnAutorizacao;

    @FXML
    private TableColumn<ItemAutorizacao, String> tableColumnDescricao;

    @FXML
    private TableColumn<ItemAutorizacao, Float> tableColumnPeso;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Veiculo veiculo;

    AutorizacaoEntradaSaida autorizacaoEntradaSaida;

    ObservableList<ItemAutorizacao> observableListItemAutorizacao;

    public void setService(AutorizacaoEntradaSaidaServices service) {
        this.service = service;
    }

    public void setVeiculoServices(VeiculoServices veiculoServices) {
        this.veiculoServices = veiculoServices;
    }

    public void setPrimeiraPesagem(boolean primeiraPesagem){this.primeiraPesagem = primeiraPesagem;}

    public void setPesagem(Pesagem pesagem){this.pesagem = pesagem;}

    public void setPesagemServices(PesagemServices pesagemServices) {
        this.pesagemServices = pesagemServices;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Constraints.setTextFieldInteger(txtAutorizacaoEntrada);
        initialNodes();
        tableValueFactory();
    }

    @FXML
    private void onBtActionSalvar(ActionEvent event) {
        if (validateFields()){
            if(primeiraPesagem == true){
                pesagem.setIdAutorizacao(autorizacaoEntradaSaida.getIdAutorizacaoEntradaSaida());
                pesagem.setDataPrimeiraPesagem(Timestamp.valueOf(txtDataHoraPesagemCheio.getText()));
                pesagem.setPesoBruto(Float.parseFloat(txtPesoCheio.getText()));
                pesagem.setPlaca(veiculo.getPlacaVeiculo());
                pesagem.setUsuarioPrimeiraPesagem(Main.getDataUser().getLoginScap());
                pesagem.setNotaFiscal(txtNotaFiscal.getText());

                pesagemServices.insertPrimeiraPesagem(pesagem);

                ShowReports.printComprovantePrimeiraPesagem(pesagem);
                Alerts.showAlert("Sucesso","", "Pesagem inserida com sucesso", Alert.AlertType.INFORMATION);
                notifyDataChangeListener();
                ResourceStage.currentStage(event).close();
            } else {
                pesagem.setUsuarioSegundaPesagem(Main.getDataUser().getLoginScap());
                pesagem.setTara(Float.parseFloat(txtPesoVazio.getText()));
                pesagem.setDataSegundapesagem(Timestamp.valueOf(txtDataHoraPesagemVazio.getText()));

                pesagemServices.insertSegundaPesagem(pesagem, Float.parseFloat(txtPesoLiquido.getText()));

                ShowReports.printComprovanteSegundaPesagem(pesagem);
                Alerts.showAlert("Sucesso", "","Pesagem Salva Com Sucesso", Alert.AlertType.INFORMATION);
                notifyDataChangeListener();
                ResourceStage.currentStage(event).close();
            }

        }else {
            Alerts.showAlert("Error","","Campos obrigatórios não são válidos ou estão em branco", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onBtActionPesar() {
        Double valorRecuperado = LeituraPortaCOM.leituraPeso();
        if(primeiraPesagem.equals(true)){
            txtPesoCheio.setText(valorRecuperado.toString());
            txtDataHoraPesagemCheio.setText(sdf.format(new Date()));
        }else {
            txtPesoVazio.setText(valorRecuperado.toString());
            txtDataHoraPesagemVazio.setText(sdf.format(new Date()));
            setPesoLiquido();
        }
    }

    private void validarAutorizacao(){
        if(!txtAutorizacaoEntrada.getText().equals("")){
            autorizacaoEntradaSaida = service.findById(Integer.parseInt(txtAutorizacaoEntrada.getText()));
            if(autorizacaoEntradaSaida != null){
                if(service.autorizacaoIsValid(autorizacaoEntradaSaida.getIdAutorizacaoEntradaSaida()) && autorizacaoEntradaSaida.getTipoEntradaSaida().equals("E")){
                    txtAutorizacaoEntrada.setStyle("-fx-border-color: green");
                    updateTableView();
                }else {
                    txtAutorizacaoEntrada.setStyle("-fx-border-color: red");
                    Alerts.showAlert("Error","","Autorização Inválida", Alert.AlertType.ERROR);
                }
            }else {
                lblErrorAutorizacao.setStyle("-fx-background-color: red");
                txtAutorizacaoEntrada.setStyle("-fx-border-color: red");
                Alerts.showAlert("Error","","Autorização não encontrada", Alert.AlertType.ERROR);
            }
        }
    }

    public void initialNodes(){
        txtAutorizacaoEntrada.focusedProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (!newValue) {
                        if(!txtAutorizacaoEntrada.getText().equals("")){
                            validarAutorizacao();
                        }else {
                            txtAutorizacaoEntrada.setStyle("-fx-border-color: red");
                        }
                    }
                });

        txtVeiculo.focusedProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(!newValue) {
                        if (txtVeiculo.getText().equals("")) {
                            txtVeiculo.setStyle("-fx-border-color: red");
                        } else {
                            validarVeiculo();
                        }
                    }
                });
    }

    private void tableValueFactory(){
        tableColumnAutorizacao.setCellValueFactory(new PropertyValueFactory<>("idAutorizacaoEntradaSaida"));
        tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tableColumnPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
    }

    private void updateTableView(){
        List<ItemAutorizacao> list = new ArrayList<>();
        list.add(service.findById(Integer.parseInt(txtAutorizacaoEntrada.getText())).getItemAutorizacao());
        observableListItemAutorizacao = FXCollections.observableArrayList(list);
        tableViewItemAutorizacao.setItems(FXCollections.observableArrayList(list));
    }

    private void validarVeiculo(){
        if(isDigit()){
            veiculo = veiculoServices.findById(Integer.parseInt(txtVeiculo.getText()));
            if(veiculo != null){
                gridPaneVeiculo.setVisible(true);
                gridPaneVeiculo.setStyle("-fx-background-color: green");
                lblCodigo.setText(veiculo.getIdVeiculo().toString());
                lblPlaca.setText(veiculo.getPlacaVeiculo());
                lblTara.setText(veiculo.getPesoTara().toString());
                txtVeiculo.setStyle("-fx-border-color: green");
            }else {
                Alerts.showAlert("Error","","Veiculo não encontrado", Alert.AlertType.ERROR);
                txtVeiculo.setStyle("-fx-border-color: red");
                gridPaneVeiculo.setVisible(false);
            }
        }else {
            veiculo = veiculoServices.findByPlaca(txtVeiculo.getText());
            if(veiculo != null) {
                gridPaneVeiculo.setVisible(true);
                gridPaneVeiculo.setStyle("-fx-background-color: green");
                lblCodigo.setText(veiculo.getIdVeiculo().toString());
                lblPlaca.setText(veiculo.getPlacaVeiculo());
                lblTara.setText(veiculo.getPesoTara().toString());
                txtVeiculo.setStyle("-fx-border-color: green");
            } else  {
                Alerts.showAlert("Error","","Veiculo não encontrado", Alert.AlertType.ERROR);
                txtVeiculo.setStyle("-fx-border-color: red");
                gridPaneVeiculo.setVisible(false);
            }
        }
    }

    public void buscarVeiculoPlaca(String placa){ veiculo = veiculoServices.findByPlaca(placa);}

    public void updateFormData(){
        if(pesagem == null){
            throw new IllegalStateException("Pesagem Is Null");
        }
        txtVeiculo.setText(veiculo.getIdVeiculo().toString());
        validarVeiculo();
        txtAutorizacaoEntrada.setText(pesagem.getIdAutorizacao().toString());
        validarAutorizacao();
        txtNotaFiscal.setText(pesagem.getNotaFiscal());
        txtPesoCheio.setText(pesagem.getPesoBruto().toString());
        txtDataHoraPesagemCheio.setText(pesagem.getDataPrimeiraPesagem().toString());
    }

    private void setPesoLiquido(){
        double pesocheio = Double.parseDouble(txtPesoCheio.getText());
        double pesoVazio = Double.parseDouble(txtPesoVazio.getText());
        double total = pesocheio - pesoVazio;
        txtPesoLiquido.setText(String.valueOf(total));
    }

    public void subscribeDataChangeListener(DataChangeListeners listener) {
        dataChangeListeners.add(listener);
    }

    private void notifyDataChangeListener() {
        for (DataChangeListeners listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    private boolean validateFields(){
        validarVeiculo();
        validarAutorizacao();
        if(veiculo != null || autorizacaoEntradaSaida != null){
            if(service.autorizacaoIsValid(autorizacaoEntradaSaida.getIdAutorizacaoEntradaSaida()) || autorizacaoEntradaSaida.getTipoEntradaSaida().equals("E")){
                if(!primeiraPesagem){
                    Float pesoLiquido = Float.parseFloat(txtPesoLiquido.getText());
                    if(pesoLiquido > 0){
                        return true;
                    } else {
                        txtPesoLiquido.setStyle("-fx-border-color: red");
                        return false;
                    }
                }
                return true;
            }else return false;
        }else return false;
    }

    private boolean isDigit(){
        return txtVeiculo.getText().matches("[0-9]+");
    }
}
