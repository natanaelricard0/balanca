package br.com.cdp.balanca.application;

import br.com.cdp.balanca.model.entities.Funcionario;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Scene loginScene;
    private static Scene homeScene;
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent fxmlLogin = FXMLLoader.load(Main.class.getResource("/br/com/cdp/balanca/view/login.fxml"));
        loginScene = new Scene(fxmlLogin, 600, 400);

        primaryStage.setTitle("Balança Rodoviária CDP");
        primaryStage.setResizable(false);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private static void setHomeScene() {
        try {
            SplitPane fxmlHome = FXMLLoader.load(Main.class.getResource("/br/com/cdp/balanca/view/home.fxml"));
            homeScene = new Scene(fxmlHome);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeScene(String tela) {
        switch (tela) {
            case "Login":
                stage.close();
                stage.setScene(loginScene);
                stage.show();
                break;
            case "Home":
                setHomeScene();
                stage.setScene(homeScene);
                stage.show();
                break;
        }
    }

    public static void setDataUser(Funcionario obj) {
        stage.setUserData(obj);
    }

    public static Funcionario getDataUser() {
        return (Funcionario) stage.getUserData();
    }

    public static Scene getScene() {
        return homeScene;
    }
}
