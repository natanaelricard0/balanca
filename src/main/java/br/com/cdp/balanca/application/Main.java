package br.com.cdp.balanca.application;

import br.com.cdp.balanca.model.entities.Funcionario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
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
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private static void setHomeScene() {
        try {
            SplitPane fxmlHome = FXMLLoader.load(Main.class.getResource("/br/com/cdp/balanca/view/home.fxml"));
            homeScene = new Scene(fxmlHome);

            stage.setScene(homeScene);
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeScene(String tela) {
        switch (tela) {
            case "Login":
                stage.setScene(loginScene);
                stage.setMaximized(false);
                stage.setResizable(false);
                stage.setWidth(600);
                stage.setHeight(425);
                stage.show();
                break;
            case "Home":
                setHomeScene();
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
