package br.com.cdp.balanca.controller;

import br.com.cdp.balanca.model.entities.Veiculo;
import br.com.cdp.balanca.model.services.VeiculoServices;
import br.com.cdp.balanca.utils.Alerts;
import br.com.cdp.balanca.utils.Constraints;
import br.com.cdp.balanca.utils.LeituraPortaCOM;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TaraController implements Initializable{

    VeiculoServices services;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnPesar;

    @FXML
    private Label lblPlacaRecuperada;

    @FXML
    private TextField txtIdVeiculo;

    @FXML
    private TextField txtPeso;

    Veiculo veiculo;

    public void setServices(VeiculoServices services) {
        this.services = services;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialNodes();
        txtIdVeiculo.setOnAction(actionEvent -> {
            buscarVeiculo();
            if(veiculo != null){
                lblPlacaRecuperada.setStyle("-fx-background-color: green");
                txtIdVeiculo.setStyle("-fx-border-color: green");
                lblPlacaRecuperada.setText("PLACA DO VEICULO: "+veiculo.getPlacaVeiculo());
            } else {
                lblPlacaRecuperada.setStyle("-fx-background-color: red");
                txtIdVeiculo.setStyle("-fx-border-color: red");
                lblPlacaRecuperada.setText("VEICULO NÃO ENCONTRADO");
            }
        });
    }

    private void initialNodes(){
        validationFields();
        Constraints.setTextFieldInteger(txtIdVeiculo);
    }

    private void buscarVeiculo(){
        veiculo = services.findById(Integer.parseInt(txtIdVeiculo.getText()));
    }

    @FXML
    private void btOnActionPesar(){
        Double valorRecuperado = LeituraPortaCOM.leituraPeso();
        txtPeso.setText(valorRecuperado.toString());
    }

    private boolean validationFields(){
        if(txtIdVeiculo.getText().equals("") || txtPeso.getText().equals("")){
            return false;
        } else {
            return true;
        }
    }

    @FXML
    private void btOnActionSalvarPesagem() {
        if(validationFields()) {
            if (veiculo != null) {
                veiculo.setPesoTara(Float.parseFloat(txtPeso.getText()));
                services.updateTara(veiculo);
                txtIdVeiculo.clear();
                txtPeso.clear();
                Alerts.showAlert("Sucesso", "Pesagem de Tara Cadastrada", "Pesagem de Tara do veiculo " + veiculo.getPlacaVeiculo() + " foi concluido", Alert.AlertType.INFORMATION);
            } else {
                Alerts.showAlert("Error", "Código Inválido", "Código de Veiculo não foi localizado", Alert.AlertType.ERROR);
            }
        }else{
            Alerts.showAlert("Atenção", "", "Campos em Branco", Alert.AlertType.WARNING);
        }
    }
}
