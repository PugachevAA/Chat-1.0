import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import log.ChatLog;

public class Main extends Application {
    static Stage mainStage;
    Parent root;
    static FXMLLoader loader;
    static User user = new User();
    static ChatLog chatLog = new ChatLog();


    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        loader = new FXMLLoader(ClassLoader.getSystemResource("chatWindow.fxml"));
        root = (Parent) loader.load();
        primaryStage.setTitle("WhatAf*cksApp");
        primaryStage.setScene(new Scene(root, 420,460));
        primaryStage.setMinWidth(420);
        primaryStage.setMinHeight(460);
        primaryStage.show();

        new Connection();
    }

    public static FXMLLoader getLoader() {
        return loader;
    }

}
