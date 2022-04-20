package br.com.cdp.balanca.controller;

import br.com.cdp.balanca.application.Main;
import br.com.cdp.balanca.listeners.DataChangeListeners;
import br.com.cdp.balanca.model.entities.AutorizacaoEntradaSaida;
import br.com.cdp.balanca.model.entities.Pesagem;
import br.com.cdp.balanca.model.entities.Veiculo;
import br.com.cdp.balanca.model.services.AutorizacaoEntradaSaidaServices;
import br.com.cdp.balanca.model.services.PesagemServices;
import br.com.cdp.balanca.model.services.VeiculoServices;
import br.com.cdp.balanca.utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    private Label lblErrorVeiculo;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Veiculo veiculo;

    AutorizacaoEntradaSaida autorizacaoEntradaSaida;

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
        Constraints.setTextFieldInteger(txtVeiculo);
        initialize();
    }

    public void initialize(){
        txtAutorizacaoEntrada.setOnAction(actionEvent -> {
            validarAutorizacao();
        });

        txtVeiculo.setOnAction(actionEvent -> {
            validarVeiculo();
        });
    }

    private void validarAutorizacao(){
        if(!txtAutorizacaoEntrada.getText().equals("")){
            autorizacaoEntradaSaida = service.findById(Integer.parseInt(txtAutorizacaoEntrada.getText()));
            if(autorizacaoIsValid(autorizacaoEntradaSaida)){
                if(service.autorizacaoIsValid(autorizacaoEntradaSaida.getIdAutorizacaoEntradaSaida()) && autorizacaoEntradaSaida.getTipoEntradaSaida().equals("E")){
                    lblErrorAutorizacao.setStyle("-fx-background-color: green");
                    txtAutorizacaoEntrada.setStyle("-fx-border-color: green");
                    lblErrorAutorizacao.setText("Autorização é Válida");
                }else {
                    lblErrorAutorizacao.setStyle("-fx-background-color: red");
                    txtAutorizacaoEntrada.setStyle("-fx-border-color: red");
                    lblErrorAutorizacao.setText("Autorização Não é válida para essa Operação");
                }
            }else {
                lblErrorAutorizacao.setStyle("-fx-background-color: red");
                txtAutorizacaoEntrada.setStyle("-fx-border-color: red");
                lblErrorAutorizacao.setText("Autorização Não é Válida");
            }
        }
    }

    private void validarVeiculo(){
        buscarVeiculo();
        if(veiculo != null){
            lblErrorVeiculo.setStyle("-fx-background-color: green");
            txtVeiculo.setStyle("-fx-border-color: green");
            lblErrorVeiculo.setText("PLACA DO VEICULO: "+veiculo.getPlacaVeiculo());
        } else {
            lblErrorVeiculo.setStyle("-fx-background-color: red");
            txtVeiculo.setStyle("-fx-border-color: red");
            lblErrorVeiculo.setText("VEICULO NÃO ENCONTRADO");
        }
    }

    private void buscarVeiculo(){
        veiculo = veiculoServices.findById(Integer.parseInt(txtVeiculo.getText()));
    }

    public void buscarVeiculoPlaca(String placa){ veiculo = veiculoServices.findByPlaca(placa);}

    private Boolean autorizacaoIsValid(AutorizacaoEntradaSaida autorizacaoEntradaSaida){
        if(autorizacaoEntradaSaida == null){
            return false;
        }else {
            return true;
        }
    }

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
            }else {
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
            if(!service.autorizacaoIsValid(autorizacaoEntradaSaida.getIdAutorizacaoEntradaSaida()) || !autorizacaoEntradaSaida.getTipoEntradaSaida().equals("E")){
                return false;
            }else return true;
        }else return false;
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
}
