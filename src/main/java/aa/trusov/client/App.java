package aa.trusov.client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    //Stage mainStage;
    private Controller chatMainForm;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("chatMainForm.fxml"));
        Parent root = loader.load(); //getClass().getClassLoader().getResource("chatMainForm.fxml")
        chatMainForm = loader.getController();
        primaryStage.setTitle("NetworkChat v.1.0");
        Scene scene = new Scene(root);
        scene.getStylesheets().add( App.class.getClassLoader().getResource( "main.css" ).toExternalForm() );
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop(){
        try {
            super.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(chatMainForm != null)
        {
            chatMainForm.disconnect();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}