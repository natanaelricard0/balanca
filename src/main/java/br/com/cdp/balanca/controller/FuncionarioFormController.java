package br.com.cdp.balanca.controller;

import br.com.cdp.balanca.listeners.DataChangeListeners;
import br.com.cdp.balanca.model.dao.FuncionarioDAO;
import br.com.cdp.balanca.model.entities.Funcionario;
import br.com.cdp.balanca.model.services.FuncionarioServices;
import br.com.cdp.balanca.utils.Alerts;
import br.com.cdp.balanca.utils.ResourceStage;
import br.com.cdp.balanca.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class FuncionarioFormController implements Initializable {
    private FuncionarioDAO funcionarioDAO;
    private Funcionario funcionario;
    private FuncionarioServices funcionarioServices;
    private List<DataChangeListeners> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtLoginRede;

    @FXML
    private TextField txtLoginScap;

    @FXML
    private CheckBox checkAdministrador;

    @FXML
    private CheckBox checkAtivo;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnLimpar;

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void setFuncionarioServices(FuncionarioServices funcionarioServices) {
        this.funcionarioServices = funcionarioServices;
    }

    @FXML
    private void btnOnActionSalvar(ActionEvent event) {
        if (funcionario == null) {
            throw new IllegalStateException("Funcionário as null");
        }
        if (funcionarioServices == null) {
            throw new IllegalStateException("Service as null");
        }
        try {
          /* funcionario = getFormData();
            funcionarioServices.insertOrUpdate(funcionario);*/
            autenticar_usuario();
            notifyDataChangeListener();
            ResourceStage.currentStage(event).close();
        } catch(RuntimeException exceptionMsg) {
            Alerts.showAlert("Erro ao Salvar Funcionário", "Erro ao tentar inserir usuário.", exceptionMsg.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void autenticar_usuario() {
       try {
            List<Funcionario> listFuncionario;
            listFuncionario = (List<Funcionario>) funcionarioServices.getFuncionario(txtLoginRede.getText());

            if (getFormData() != null && funcionario.getLoginScap() != null || funcionario.equals(listFuncionario)) {
                //if (funcionario.getLoginRede() != null && funcionario.equals(listFuncionario)) {
                funcionarioServices.loginAdConfirmation(funcionario);

            } else {
                funcionario = getFormData();
                funcionarioServices.insertOrUpdate(funcionario);
            }

       } catch (RuntimeException exceptionMsg) {
                Alerts.showAlert("Erro ao Cadastrar Funcionário", "Funcionário já existente no sistema", exceptionMsg.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnOnActionLimpar() {
        try {
            getFormClear();
            notifyDataChangeListener();
        } catch (RuntimeException exceptionMsg) {
            Alerts.showAlert("Erro ao limpar campo", null, exceptionMsg.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnOnActionCancelar(ActionEvent event) {
        ResourceStage.currentStage(event).close();
    }

    public void subscribeDataChangeListener(DataChangeListeners listener) {
        dataChangeListeners.add(listener);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void notifyDataChangeListener() {
        for (DataChangeListeners listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    private Funcionario getFormData() {
        Funcionario obj = new Funcionario();
        obj.setId(Utils.tryParseToLong(txtId.getText()));
        obj.setNome(txtNome.getText());
        obj.setLoginRede(txtLoginRede.getText());
        obj.setLoginScap(txtLoginScap.getText());
        obj.setAdministrador(checkAdministrador.isSelected());
        obj.setAtivo(checkAtivo.isSelected());
        return obj;
    }

    private void getFormClear() {
        txtNome.setText("");
        txtLoginRede.setText("");
        txtLoginScap.setText("");
        checkAdministrador.setSelected(false);
        checkAtivo.setSelected(false);
    }

    public void updateFormData() {
        if (funcionario == null) {
            throw new IllegalStateException("Funcionário é Null");
        }
        txtId.setText(String.valueOf(funcionario.getId() == null ? "" : funcionario.getId()));
        txtNome.setText(funcionario.getNome());
        txtLoginRede.setText(funcionario.getLoginRede());
        txtLoginScap.setText(funcionario.getLoginScap());
        checkAdministrador.setSelected(funcionario.getAdministrador() != null && funcionario.getAdministrador());
        checkAtivo.setSelected(funcionario.getAtivo() != null && funcionario.getAtivo());
    }


}
