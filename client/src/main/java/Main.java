import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static Stage mainStage;
    Parent root;
    static FXMLLoader loader;


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

        //Connection connection = new Connection();
        new Connection();
    }
    //public static void main(String[] args) { launch(args); }

    public static FXMLLoader getLoader() {
        return loader;
    }
}
