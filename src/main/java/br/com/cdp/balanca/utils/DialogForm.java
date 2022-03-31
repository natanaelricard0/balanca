package br.com.cdp.balanca.utils;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogForm {
    public static void createDialogForm(Pane pane, String title, Stage parentStage) {
        Scene scene = new Scene(pane);
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.initOwner(parentStage);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.showAndWait();
    }
}
