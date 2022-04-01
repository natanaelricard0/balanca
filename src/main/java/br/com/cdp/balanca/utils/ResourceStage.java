package br.com.cdp.balanca.utils;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ResourceStage {
    public static Stage currentStage(Event event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
}
