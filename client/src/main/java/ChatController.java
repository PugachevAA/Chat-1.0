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
            chatTextArea.appendText(dateFormatter.format(dt) + " Client: " + messageTextField.getText()+"\n");
            Connection.setOut(messageTextField.getText());
            messageTextField.clear();
            messageTextField.requestFocus();
        }
    }

    public void setMessage(String s) {
        dt = new Date();
        chatTextArea.appendText(dateFormatter.format(dt) + " Server: " + s +"\n");
    }

}
