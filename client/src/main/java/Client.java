import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {
    static Stage mainStage;
    Parent root;
    static FXMLLoader loader;


    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        loader = new FXMLLoader(ClassLoader.getSystemResource("chatWindow.fxml"));
        //loader = new FXMLLoader(ClassLoader.getSystemResource("chatWindow.fxml"));
        //loader.load(getClass().getResource("chatWindow.fxml"));
        root = (Parent) loader.load();
        primaryStage.setTitle("WhatAf*cksApp");
        primaryStage.setScene(new Scene(root, 420,460));
        primaryStage.setMinWidth(420);
        primaryStage.setMinHeight(460);
        primaryStage.show();

        Connection connection = new Connection();
        //connection.connect();
    }

    public static FXMLLoader getLoader() {
        return loader;
    }
}
