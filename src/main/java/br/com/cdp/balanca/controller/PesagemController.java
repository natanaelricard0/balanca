package br.com.cdp.balanca.controller;

import br.com.cdp.balanca.db.DbException;
import br.com.cdp.balanca.model.entities.AutorizacaoEntradaSaida;
import br.com.cdp.balanca.model.services.AutorizacaoEntradaSaidaServices;
import br.com.cdp.balanca.utils.Constraints;
import br.com.cdp.balanca.utils.LeituraPortaCOM;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class PesagemController implements Initializable {

    private AutorizacaoEntradaSaidaServices service;

    private Boolean primeiraPesagem;

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

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void setService(AutorizacaoEntradaSaidaServices service) {
        this.service = service;
    }

    public void setPrimeiraPesagem(boolean primeiraPesagem){this.primeiraPesagem = primeiraPesagem;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Constraints.setTextFieldInteger(txtAutorizacaoEntrada);
    }

    private Boolean autorizacaoIsValid(){
        AutorizacaoEntradaSaida autorizacaoEntradaSaida = service.findById(Integer.parseInt(txtAutorizacaoEntrada.getText()));

        if(autorizacaoEntradaSaida.equals(null)){
            throw new DbException("Autorização não existe");
        }else if(autorizacaoEntradaSaida.autorizacaIsValid()){
            return true;
        } else {
            return false;
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
        }
    }
}
