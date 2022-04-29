package br.com.cdp.balanca.controller;

import br.com.cdp.balanca.application.Main;
import br.com.cdp.balanca.model.entities.AutorizacaoEntradaSaida;
import br.com.cdp.balanca.model.entities.Pesagem;
import br.com.cdp.balanca.model.entities.Veiculo;
import br.com.cdp.balanca.model.services.AutorizacaoEntradaSaidaServices;
import br.com.cdp.balanca.model.services.PesagemServices;
import br.com.cdp.balanca.model.services.VeiculoServices;
import br.com.cdp.balanca.utils.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class PesagemImportacaoController implements Initializable {

    //INSTANCIAÇÃO DOS SERVIÇOS RELACIONADOS À PESAGEM DE IMPORTAÇÃO
    PesagemServices pesagemServices;

    //INSTANCIAÇÃO DOS SERVIÇOS RELACIONADOS À AUTORIZAÇÃO DE ENTRADA E SAÍDA
    AutorizacaoEntradaSaidaServices autorizacaoEntradaSaidaServices;

    //INSTANCIAÇÃO DO SERVIÇOS RELACIONADOS À VEICULOS
    VeiculoServices veiculoServices;

    //OBJETO PESAGEM DA CLASSE
    Pesagem pesagem = new Pesagem();

    //FORMATAÇÃO DE DATA PARA REGRA DE DATAS DO SCAP
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //INSTANCIAÇÃO DOS COMPONENTES DA PÁGINA FXML
    @FXML
    private TextField txtAutorizacaoSaida;

    @FXML
    private TextField txtNotaFiscal;

    @FXML
    private TextField txtVeiculo;

    @FXML
    private TextField txtTara;

    @FXML
    private TextField txtPesoTotal;

    @FXML
    private TextField txtPesoLiquido;

    @FXML
    private Label lblCodigo;

    @FXML
    private Label lblPlaca;

    @FXML
    private Label lblTara;

    @FXML
    private Button btnPesar;

    @FXML
    private Button btnSalvar;

    @FXML
    private GridPane gridPaneVeiculo;

    //INJEÇÃO DE DEPENDÊNCIA DO SERVIÇO
    public void setPesagemServices(PesagemServices pesagemServices){
        this.pesagemServices = pesagemServices;
    }

    //INJEÇÃO DE DEPENDÊNCIA DO SERVIÇO AUTORIZAÇÃO DE ENTRADA E SAÍDA
    public void setAutorizacaoEntradaSaidaServices(AutorizacaoEntradaSaidaServices autorizacaoEntradaSaidaServices){
        this.autorizacaoEntradaSaidaServices = autorizacaoEntradaSaidaServices;
    }

    //INJEÇÃO DE DEPENDÊNCIA DO SERVIÇO VEICULO
    public void setVeiculoServices(VeiculoServices veiculoServices){
        this.veiculoServices = veiculoServices;
    }

    //FUNÇÃO INICIAL DA PÁGINA
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialNodes();
    }

    //NODES INICIAIS DA PÁGINA
    private void initialNodes(){
        eventos();
        Constraints.setTextFieldInteger(txtAutorizacaoSaida);
    }

    //EVENTOS PARA OS COMPONENTES
    private void eventos(){
        txtAutorizacaoSaida.setOnAction(event -> {
            verificacaoAutorizacao();
        });

        txtVeiculo.setOnAction(event -> {
            verificaVeiculo();
        });

        btnPesar.setOnAction(event -> {
            pesar();
        });

        btnSalvar.setOnAction(event -> {
            salvar(event);
        });
    }

    //VERIFICA SE A AUTORIZAÇÃO É VÁLIDA
    private void verificacaoAutorizacao(){
        String valor = txtAutorizacaoSaida.getText();

        if(valor != ""){
            AutorizacaoEntradaSaida autorizacaoEntradaSaida = autorizacaoEntradaSaidaServices.findById(Integer.parseInt(valor));
            if(autorizacaoEntradaSaida != null && autorizacaoEntradaSaidaServices.autorizacaoIsValid(autorizacaoEntradaSaida.getIdAutorizacaoEntradaSaida()) && autorizacaoEntradaSaida.getTipoEntradaSaida().equals("S")){
                txtAutorizacaoSaida.setStyle("-fx-border-color: green");
                pesagem.setIdAutorizacao(autorizacaoEntradaSaida.getIdAutorizacaoEntradaSaida());
            }else{
                txtAutorizacaoSaida.setStyle("-fx-border-color: red");
            }
        }
    }

    //VERIFICA SE O VEÍCULO EXISTE E TEM TARA CADASTRADA
    private void verificaVeiculo(){
        if(isDigit()) {
            Veiculo veiculo = veiculoServices.findById(Integer.parseInt(txtVeiculo.getText()));
            preencheCampos(veiculo);
        } else {
            Veiculo veiculo = veiculoServices.findByPlaca(txtVeiculo.getText());
            preencheCampos(veiculo);
        }
    }

    //PREEENCHE OS CAMPOS DO TARA E PESO
    private void preencheCampos(Veiculo veiculo){
        if (veiculo != null) {
            txtVeiculo.setStyle("-fx-border-color: green");
            gridPaneVeiculo.setVisible(true);
            gridPaneVeiculo.setStyle("-fx-background-color: green");
            txtTara.setText(veiculo.getPesoTara() + " KG");
            lblCodigo.setText(veiculo.getIdVeiculo() + "");
            lblPlaca.setText(veiculo.getPlacaVeiculo());
            lblTara.setText(veiculo.getPesoTara() + " KG");
            pesagem.setPlaca(veiculo.getPlacaVeiculo());
            pesagem.setTara(veiculo.getPesoTara());
        } else {
            txtVeiculo.setStyle("-fx-border-color: red");
            gridPaneVeiculo.setVisible(false);
            Alerts.showAlert("Veículo não encontrado", null, "Veículo não encontrado", Alert.AlertType.ERROR);
        }
    }

    //VERIFICA SE O CAMPO VEICULO É CODIGO OU PLACA
    private boolean isDigit(){
        return txtVeiculo.getText().matches("[0-9]+");
    }

    //REALIZA A LEITURA DE PESO
    private void pesar(){
        Double valor = LeituraPortaCOM.leituraPeso();
        txtPesoTotal.setText(valor.toString());
        pesagem.setPesoBruto(Float.parseFloat(txtPesoTotal.getText()));
        if(txtTara.getText() != ""){pesoLiquido();}
    }

    //CALCULA O PESO LIQUIDO
    private void pesoLiquido(){
        Float pesoCheio = Float.parseFloat(txtPesoTotal.getText());
        Float tara = Float.parseFloat(txtTara.getText());
        Float resultado = pesoCheio - tara;
        txtPesoLiquido.setText(resultado.toString());
        if(resultado <= 0.0){
            Alerts.showAlert("Atenção", "","Tara maior que peso total", Alert.AlertType.ERROR);
            txtPesoLiquido.setStyle("-fx-border-color: red");
        }else {
            txtPesoLiquido.setStyle("-fx-border-color: green");
        }
    }

    //ENVIA OS PARAMETROS PARA REALIZAR A INSERÇÃO NO BANCO DE DADOS
    private void salvar(Event event){
        verificaVeiculo();
        verificacaoAutorizacao();
        Float pesoLiquido = Float.parseFloat(txtPesoLiquido.getText());
        if(pesagem.getIdAutorizacao() != null && pesagem.getPesoBruto() != null && pesagem.getTara() != null && pesagem.getPlaca() != null && pesoLiquido > 0.0){
            pesagem.setNotaFiscal(txtNotaFiscal.getText());
            pesagem.setUsuarioPrimeiraPesagem(Main.getDataUser().getLoginScap());
            pesagem.setUsuarioSegundaPesagem(Main.getDataUser().getLoginScap());
            pesagem.setDataPrimeiraPesagem(Timestamp.valueOf(sdf.format(new Date())));
            pesagem.setDataSegundapesagem(Timestamp.valueOf(sdf.format(new Date())));

            pesagemServices.insertPesagemImportacao(pesagem);

            ShowReports.printComprovanteSegundaPesagem(pesagem);
            ResourceStage.currentStage(event).close();
            Alerts.showAlert("Sucesso","","Pesagem Inserida com Sucesso", Alert.AlertType.INFORMATION);
        }else {
            Alerts.showAlert("Atenção","","Campos Obrigatórios Não estão Preenchidos Corretamente", Alert.AlertType.ERROR);
        }
    }
}
