package br.com.cdp.balanca.controller;

import br.com.cdp.balanca.model.entities.Veiculo;
import br.com.cdp.balanca.model.services.VeiculoServices;
import br.com.cdp.balanca.utils.Alerts;
import br.com.cdp.balanca.utils.Constraints;
import br.com.cdp.balanca.utils.LeituraPortaCOM;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class TaraController implements Initializable{

    VeiculoServices services;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnPesar;

    @FXML
    private TextField txtIdVeiculo;

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtDataHoraPesagem;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void setServices(VeiculoServices services) {
        this.services = services;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialNodes();
        txtIdVeiculo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    System.out.println("ganhou");
                }else {
                    System.out.println("perdeu");
                }
            }
        });
    }

    private void initialNodes(){
        validationFields();
        Constraints.setTextFieldInteger(txtIdVeiculo);
    }

    private Veiculo veiculoIsValid(){
        return services.findById(Integer.parseInt(txtIdVeiculo.getText()));
    }

    @FXML
    private void btOnActionPesar(){
        Double valorRecuperado = LeituraPortaCOM.leituraPeso();
        txtPeso.setText(valorRecuperado.toString());
        txtDataHoraPesagem.setText(sdf.format(new Date()));
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
            Veiculo veiculo =veiculoIsValid();
            if (veiculo != null) {
                veiculo.setPesoTara(Float.parseFloat(txtPeso.getText()));
                veiculo.setDataPesagem(Timestamp.valueOf(txtDataHoraPesagem.getText()));
                services.insert(veiculo);
                txtIdVeiculo.clear();
                txtPeso.clear();
                txtDataHoraPesagem.clear();
                Alerts.showAlert("Sucesso", "Pesagem de Tara Cadastrada", "Pesagem de Tara do veiculo " + veiculo.getPlacaVeiculo() + " foi concluido", Alert.AlertType.INFORMATION);
            } else {
                Alerts.showAlert("Error", "Código Inválido", "Código de Veiculo não foi localizado", Alert.AlertType.ERROR);
            }
        }else{
            Alerts.showAlert("Atenção", "", "Campos em Branco", Alert.AlertType.WARNING);
        }
    }
}
