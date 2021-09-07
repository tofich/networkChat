package aa.trusov.client;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Controller {
    protected ClientConnection client;
    public Controller() {
        client = new ClientConnection();
        client.init(this);
    }

    @FXML
    public TextArea jta;
    @FXML
    public TextField jtf;
    @FXML
    public Button sendBtn;

    @FXML
    public TextField jtfAuthLogin;

    @FXML
    public PasswordField jtfAuthPass;

    @FXML
    public Button authBtn;

    @FXML
    public GridPane gridPaneAuth;

    @FXML
    public Label labelAuth1;

    @FXML
    public Label labelAuth2;

    public void sendMsg(){
        if (!jtf.getText().equals("")) {
            try {
                String textMsg = jtf.getText();
                client.out.writeUTF(textMsg);
                //client.out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            jtf.clear();
        }
    }

    public void showMessage(String msg){
        jta.appendText("(" + getCurrDate() + ")" + " " + msg + "\n");
    }

    private String getCurrDate(){
        Date currDateTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return dateFormat.format(currDateTime);
    }
    //добавить на форму поля и передать их сюда, и повесить на кнопку
    public void auth(){
        if(!jtfAuthLogin.getText().equals("") || !jtfAuthPass.getText().equals("")) {
            client.auth(jtfAuthLogin.getText(), jtfAuthPass.getText());
            jtfAuthLogin.setText("");
            jtfAuthPass.setText("");
        } else
        {
            jtfAuthLogin.setStyle("-fx-border-color:red; -fx-border-radius: 3px;");
            jtfAuthPass.setStyle("-fx-border-color:red; -fx-border-radius: 3px;");
        }
    }

    public void switchWindows(){
        sendBtn.setDisable(!client.isAuthorized());
        jtfAuthLogin.setVisible(!client.isAuthorized());
        jtfAuthPass.setVisible(!client.isAuthorized());
        labelAuth1.setVisible(!client.isAuthorized());
        labelAuth2.setVisible(client.isAuthorized());
        authBtn.setVisible(!client.isAuthorized());

    }

    public void disconnect(){
        client.disconnect();
    }
}
