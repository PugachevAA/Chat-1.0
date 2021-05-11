import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatController {


    private Date dt;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("[dd.MM.yyy HH:mm:ss]");
    @FXML
    TextArea chatTextArea;
    @FXML
    TextField messageTextField;

    public void sendAction(ActionEvent actionEvent) {
        if (!messageTextField.getText().trim().isEmpty()) {
            dt = new Date();
            if (messageTextField.getText().equals("/connect")) {
                new Connection();
            } else {
                if (messageTextField.getText().startsWith("/auth")) {
                    String userLogin[] = messageTextField.getText().split(" ", 3);
                    Main.user.setLogin(userLogin[1]);
                }
                Connection.setOut(messageTextField.getText());
            }
            messageTextField.clear();
            messageTextField.requestFocus();
        }
    }

    public void setMessage(String s) {
        dt = new Date();
        chatTextArea.appendText(dateFormatter.format(dt) + " " + s +"\n");
        if (Main.user.isAuth()) {
            Main.chatLog.writeLogMsg(dateFormatter.format(dt) + " " + s);
        }
    }
    public void setLog(String s) {
        chatTextArea.appendText(s + "\n");
    }

}
