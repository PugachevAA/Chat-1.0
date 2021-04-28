import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.fxml.FXMLLoader;

public class Connection {
    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;

    public Connection() {
        try {
            connect();
            addCloseListener();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void connect() throws IOException {
        socket = new Socket(Config.SERVER_IP, Config.SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        FXMLLoader loader = Main.getLoader();
        ChatController chatController = (ChatController) loader.getController();
        chatController.setMessage("Соединение с сервером установлено");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String strFromServer = in.readUTF();
                        if (strFromServer.equalsIgnoreCase("/end")) {
                            break;
                        }
                        chatController.setMessage(strFromServer.toString());
                    }
                } catch (Exception e) {
                    chatController.setMessage("Cоединение разорвано сервером. \nДля повторного подключения введите /connect");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void addCloseListener() {
        EventHandler<WindowEvent> onCloseRequest = Main.mainStage.getOnCloseRequest();
        Main.mainStage.setOnCloseRequest(event -> {
            disconnect();
            if (onCloseRequest != null) {
                onCloseRequest.handle(event);
            }
        });
    }

    private void disconnect() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setOut(String s) {
        try {
            out.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
