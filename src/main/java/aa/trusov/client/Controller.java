package aa.trusov.client;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.text.SimpleDateFormat;
import java.util.Collections;
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
                String textMsg = jtf.getText();
                client.sendMsg(textMsg);
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
        if(!jtfAuthLogin.getText().equals("") && !jtfAuthPass.getText().equals("")) {
            client.auth(jtfAuthLogin.getText(), jtfAuthPass.getText());
            jtfAuthLogin.setText("");
            jtfAuthPass.setText("");
        } else {
            verifyAndHighlightRequiredField(jtfAuthLogin);
            verifyAndHighlightRequiredField(jtfAuthPass);
            showMessage("Please, enter all required fields.");
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

    private void setErrorStyle(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        if(!styleClass.contains("errorTextField")) {
            styleClass.add("errorTextField");
        }
    }

    private void removeErrorStyle(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        styleClass.removeAll(Collections.singleton("errorTextField"));
    }

    private void verifyAndHighlightRequiredField(TextField tf){
        if (tf.getText().equals("")) {
            setErrorStyle(tf);
        } else {
            removeErrorStyle(tf);
        }
    }
}
