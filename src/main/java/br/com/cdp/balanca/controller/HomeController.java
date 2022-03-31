package br.com.cdp.balanca.controller;

import br.com.cdp.balanca.application.Main;
import br.com.cdp.balanca.model.services.AutorizacaoEntradaSaidaServices;
import br.com.cdp.balanca.model.services.FuncionarioServices;
import br.com.cdp.balanca.model.services.VeiculoServices;
import br.com.cdp.balanca.utils.DialogForm;
import br.com.cdp.balanca.utils.ResourceStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class HomeController implements Initializable {
    @FXML
    private Label lblUser;

    @FXML
    private Button btnFuncionario;

    @FXML
    private Button btnExportacao;

    @FXML
    private Button btnImportacaoDescargaNavio;

    @FXML
    private Button btnImportacaoSaidaRodoviaria;

    @FXML
    private Button btnConfiguracao;

    @FXML
    private Button btnTrocarUsario;

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

    @FXML
    private void onBtnSairAction(){
        System.exit(1);
    }

    @FXML
    private void onBtnFuncionarioAction(ActionEvent event) {
        loadView(ResourceStage.currentStage(event), "/br/com/cdp/balanca/view/funcionario.fxml", "Gerenciamento de Funcionario", (FuncionarioController controller) -> {
            controller.setService(new FuncionarioServices());
            controller.updateTableView();
        });
    }

    @FXML
    private void onBtPesagemExportacaoAction(ActionEvent event) {
        loadView(ResourceStage.currentStage(event), "/br/com/cdp/balanca/view/exportacao.fxml", "Pesagem Exportação", (PesagemController controller) -> {
            controller.setService(new AutorizacaoEntradaSaidaServices());
            controller.setPrimeiraPesagem(false);
        });
    }

    @FXML
    private void onBtCadastroTara(ActionEvent event) {
        loadView(ResourceStage.currentStage(event), "/br/com/cdp/balanca/view/tara.fxml", "Cadastro de Tara", (TaraController controller) -> controller.setServices(new VeiculoServices()));
    }

    private synchronized <T> void loadView(Stage parentStage, String absolutName, String title, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName));
            Pane pane = loader.load();

            T controller = loader.getController();
            initializingAction.accept(controller);

            DialogForm.createDialogForm(pane, title, parentStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBtActionTrocaUsuarioAction() throws IOException {
        Main.setDataUser(null);
        Main.changeScene("Login");
    }
}
