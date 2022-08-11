package br.com.cdp.balanca.controller;

import br.com.cdp.balanca.application.Main;
import br.com.cdp.balanca.model.services.*;
import br.com.cdp.balanca.utils.Alerts;
import br.com.cdp.balanca.utils.DialogForm;
import br.com.cdp.balanca.utils.ResourceStage;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler; //new
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent; //new
import javafx.scene.layout.Pane;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.EventListener; //new
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class HomeController implements Initializable {

    @FXML
    private Pane tela;

    @FXML
    private Label lblUser;

    @FXML
    private Button btnFuncionario;

    @FXML
    private Button btnExportacao;

    @FXML
    private Button btnImportacaoSaidaRodoviaria;

    @FXML
    private Button btnConfiguracao;

    @FXML
    private Button btnTara;

    @FXML
    private Button btnRelatorios;

    @FXML
    private Button btnSair;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        lblUser.setText(Main.getDataUser().getNome());

    }

    //ACTION DE ATALHO DE TECLAS
    @FXML
    private void handleOnKeyReleased(KeyEvent event){
        if(event.getCode() == KeyCode.F2){
            cadastroTara(event);
        }
        if(event.getCode() == KeyCode.F3){
            pesagemExportacao(event);
        }
        if(event.getCode() == KeyCode.F4){
            pesagemImportacao(event);
        }
        if (event.getCode() == KeyCode.F5){
            relatorio(event);
        }
        if(event.getCode() == KeyCode.F6){
            gerenciamentoFuncionario(event);
        }
    }

    private void gerenciamentoFuncionario(Event event){
        if(Main.getDataUser().getAdministrador().equals(true)) {
            loadView(ResourceStage.currentStage(event), "/br/com/cdp/balanca/view/funcionario.fxml", "Gerenciamento de Funcionario", (FuncionarioController controller) -> {
                controller.setService(new FuncionarioServices());
                controller.updateTableView();
            });
        } else {
            Alerts.showAlert("Acesso Negado", "Você não tem permissão para acessar essa funcionalidade", "",Alert.AlertType.ERROR);
        }
    }

    private void pesagemExportacao(Event event){
        loadView(ResourceStage.currentStage(event), "/br/com/cdp/balanca/view/gerenciamentoPesagem.fxml", "Pesagem Exportação", (PesagensPendentesController controller) -> {
            controller.setService(new PesagemServices());
            controller.setAutorizacaoEntradaSaidaServices(new AutorizacaoEntradaSaidaServices());
            controller.setVeiculoServices(new VeiculoServices());
            controller.updateTableView();
        });
    }

    private void cadastroTara(Event event){
        loadView(ResourceStage.currentStage(event), "/br/com/cdp/balanca/view/tara.fxml", "Cadastro de Tara", (TaraController controller) ->
           controller.setServices(new VeiculoServices()));
    }

    private void pesagemImportacao(Event event){
        loadView(ResourceStage.currentStage(event),"/br/com/cdp/balanca/view/importacaoSaidaRodoviaria.fxml","Pesagem de importação Saída Rodoviária", (PesagemImportacaoController controller) -> {
            controller.setPesagemServices(new PesagemServices());
            controller.setVeiculoServices(new VeiculoServices());
            controller.setAutorizacaoEntradaSaidaServices(new AutorizacaoEntradaSaidaServices());
        });
    }

    private void relatorio(Event event) {
        loadView(ResourceStage.currentStage(event), "/br/com/cdp/balanca/view/relatorio.fxml", "Relatórios de Pesagem", (RelatorioController controller) -> {
        });
    }

    @FXML
    private void onBtCadastroTara(ActionEvent event) {
        cadastroTara(event);
    /*    btnTara.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 1) {
                    try {
                        cadastroTara(event);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                       // Alerts.showAlert("Erro ao abrir tela Cadastro Tara.", null, e.getMessage(), Alert.AlertType.ERROR);
                    }
                }
            }
        });*/
    }

    @FXML
    private void onBtPesagemExportacaoAction(ActionEvent event) {
        pesagemExportacao(event);
    }

    @FXML
    private void onBtPesagemImportacaoSaidaRodoviaria(ActionEvent event){
        pesagemImportacao(event);
    }

    @FXML
    private void onBtRelatorioAction(ActionEvent event) {
        relatorio(event);
    }

    @FXML
    private void onBtnFuncionarioAction(ActionEvent event) { gerenciamentoFuncionario(event);}

    private synchronized <T> void loadView(Stage parentStage, String absolutName, String title, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName));
            Pane pane = loader.load();

            tela.getChildren().addAll(pane); //new

            T controller = loader.getController();
            initializingAction.accept(controller);
            DialogForm.createDialogForm(pane, title, parentStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface EventHandler<T extends ActionEvent> extends EventListener {}

    @FXML
    private void onBtActionTrocaUsuarioAction() throws IOException {
        Main.setDataUser(null);
        Main.changeScene("Login");
    }
}
