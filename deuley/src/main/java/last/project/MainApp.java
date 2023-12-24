package last.project;

import java.io.File;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        try{
            URL url = new File("C:\\Users\\kaiai\\OneDrive\\Desktop\\deuley\\src\\main\\java\\last\\project\\1.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch(Exception e){
                e.printStackTrace();
        }

        
    }

    public static void main(String[] args) {
        launch(args);
    }
}