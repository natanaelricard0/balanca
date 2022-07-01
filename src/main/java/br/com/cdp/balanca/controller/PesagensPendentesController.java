package br.com.cdp.balanca.controller;

import br.com.cdp.balanca.listeners.DataChangeListeners;
import br.com.cdp.balanca.model.entities.Pesagem;
import br.com.cdp.balanca.model.services.AutorizacaoEntradaSaidaServices;
import br.com.cdp.balanca.model.services.PesagemServices;
import br.com.cdp.balanca.model.services.VeiculoServices;
import br.com.cdp.balanca.utils.DialogForm;
import br.com.cdp.balanca.utils.ResourceStage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class PesagensPendentesController implements Initializable , DataChangeListeners {

    PesagemServices service;

    VeiculoServices veiculoServices;

    AutorizacaoEntradaSaidaServices autorizacaoEntradaSaidaServices;

    @FXML
    TableView<Pesagem> tabelaPesagem;

    @FXML
    TableColumn<Pesagem, Integer> columnAutorizacao;

    @FXML
    TableColumn<Pesagem, String> columnPlaca;

    @FXML
    TableColumn<Pesagem, Timestamp> columnDataPrimeiraPesagem;

    @FXML
    TableColumn<Pesagem, Float> columnPesoBruto;

    @FXML
    TableColumn<Pesagem, String> columnBalanceiro;

    @FXML
    TableColumn<Pesagem, Pesagem> columnEdit;

    @FXML
    Button btnPesquisar;

    @FXML
    Button btnCadastrar;

    @FXML
    TextField txtPesquisar;

    private ObservableList<Pesagem> obslist;

    public void setService(PesagemServices service){ this.service = service;}

    public void setVeiculoServices(VeiculoServices veiculoServices){ this.veiculoServices = veiculoServices;}

    public void setAutorizacaoEntradaSaidaServices(AutorizacaoEntradaSaidaServices autorizacaoEntradaSaidaServices){this.autorizacaoEntradaSaidaServices = autorizacaoEntradaSaidaServices;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialNodes();
    }

    public void updateTableView(){
        List<Pesagem> list;

        list = service.listarPesagensPendentes();
        obslist = FXCollections.observableArrayList(list);
        tabelaPesagem.setItems(obslist);
        initEditButtons();
    }

    public void pesquisarActionTableView(){
        List<Pesagem> list;

        list = service.listarPesagensPesquisa(txtPesquisar.getText());
        obslist = FXCollections.observableArrayList(list);
        tabelaPesagem.setItems(obslist);
        initEditButtons();
    }

    private void initEditButtons() {
        columnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        columnEdit.setCellFactory(param -> new TableCell<Pesagem, Pesagem>() {
            private final Button button = new Button("Pesar");

            @Override
            protected void updateItem(Pesagem obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(obj, ResourceStage.currentStage(event),"/br/com/cdp/balanca/view/exportacao.fxml","Segunda Pesagem"));
            }
        });
    }

    private void initialNodes(){
        columnAutorizacao.setCellValueFactory(new PropertyValueFactory<>("idAutorizacao"));
        columnPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        columnDataPrimeiraPesagem.setCellValueFactory(new PropertyValueFactory<>("dataPrimeiraPesagem"));
        columnPesoBruto.setCellValueFactory(new PropertyValueFactory<>("pesoBruto"));
        columnBalanceiro.setCellValueFactory(new PropertyValueFactory<>("usuarioPrimeiraPesagem"));
    }

    private void createDialogForm(Pesagem obj, Stage parentStage, String url, String title){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            VBox vbox = loader.load();

            PesagemController controller = loader.getController();
            controller.setService(new AutorizacaoEntradaSaidaServices());
            controller.setVeiculoServices(new VeiculoServices());
            controller.subscribeDataChangeListener(this);
            controller.setPesagemServices(new PesagemServices());
            if (obj == null) {
                controller.setPesagem(new Pesagem());
                controller.setPrimeiraPesagem(true);
            } else {
                controller.setPesagem(obj);
                controller.setPrimeiraPesagem(false);
                controller.buscarVeiculoPlaca(obj.getPlaca());
                controller.updateFormData();
            }


            DialogForm.createDialogForm(vbox, title, parentStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

    @FXML
    private void onActionCadastrar(ActionEvent event) {
        createDialogForm(null, ResourceStage.currentStage(event), "/br/com/cdp/balanca/view/exportacao.fxml", "Cadastro de pesagem de exportação");
    }

    @FXML
    private void onActionPesquisar(){
        pesquisarActionTableView();
    }
}
