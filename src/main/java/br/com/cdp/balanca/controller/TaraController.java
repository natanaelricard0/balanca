package br.com.cdp.balanca.controller;

import br.com.cdp.balanca.model.entities.Veiculo;
import br.com.cdp.balanca.model.services.VeiculoServices;
import br.com.cdp.balanca.utils.Alerts;
import br.com.cdp.balanca.utils.Constraints;
import br.com.cdp.balanca.utils.LeituraPortaCOM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.text.DecimalFormat;
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
    private Label lblIdVeiculo;

    @FXML
    private Label lblPlacaVeiculo;

    @FXML
    private Label lblTaraVeiculo;

    @FXML
    private GridPane gridPaneVeiculo;

    Veiculo veiculo;

    DecimalFormat df = new DecimalFormat("##,##0.00 KG");

    public void setServices(VeiculoServices services) {
        this.services = services;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialNodes();
        txtIdVeiculo.setOnAction(actionEvent -> {
            buscarVeiculo();
            if(veiculo != null){
                txtIdVeiculo.setStyle("-fx-border-color: #7bd1ff");
                gridPaneVeiculo.setVisible(true);
                gridPaneVeiculo.setStyle("-fx-background-color: #7bd1ff");
                lblIdVeiculo.setText(veiculo.getIdVeiculo().toString());
                lblPlacaVeiculo.setText(veiculo.getPlacaVeiculo());
                lblTaraVeiculo.setText(veiculo.getPesoTara().toString());
            } else {
                txtIdVeiculo.setStyle("-fx-border-color: red");
                gridPaneVeiculo.setVisible(false);
            }
        });
    }

    private void initialNodes(){
        validationFields();
    }

    @FXML
    private void btOnActionPesar(){
        if(veiculo != null){
            String valorRecuperado = String.valueOf(LeituraPortaCOM.leituraPeso());
            txtPeso.setText(df.format(Double.parseDouble(valorRecuperado)));
            veiculo.setPesoTara(Float.parseFloat(valorRecuperado));
        }else {
            txtPeso.setText("");
            Alerts.showAlert("Atenção", "Veículo não encontrado", "Por favor, verifique o ID do veículo", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btOnActionSalvarPesagem() {
        if(validationFields()) {
            if (veiculo != null) {
                services.updateTara(veiculo);
                txtIdVeiculo.clear();
                txtPeso.clear();
                gridPaneVeiculo.setVisible(false);
                Alerts.showAlert("Sucesso", "Pesagem de Tara Cadastrada", "Pesagem de Tara do veiculo " + veiculo.getPlacaVeiculo() + " foi concluido", Alert.AlertType.INFORMATION);
            } else {
                Alerts.showAlert("Error", "Código Inválido", "Código de Veiculo não foi localizado", Alert.AlertType.ERROR);
            }
        }else{
            Alerts.showAlert("Atenção", "", "Campos em Branco", Alert.AlertType.WARNING);
        }
    }

    private void buscarVeiculo(){
        if(isDigit()){
            veiculo = services.findById(Integer.parseInt(txtIdVeiculo.getText()));
        }else {
            veiculo = services.findByPlaca(txtIdVeiculo.getText());
        }
    }

    private boolean validationFields(){
        if(txtIdVeiculo.getText().equals("") || txtPeso.getText().equals("")){
            return false;
        } else {
            return true;
        }
    }

    private boolean isDigit(){
        return txtIdVeiculo.getText().matches("[0-9]+");
    }
}
