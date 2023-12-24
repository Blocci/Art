package last.project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class RegisterController implements Initializable{

    public JdbcDao jdbcDao = new JdbcDao();

    
      @FXML
      private Label isConnected;

      @FXML
      private TextField txtEmail;

      @FXML
      private TextField txtUser;

      @FXML
      private PasswordField txtPass;

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'initialize'");
        if(jdbcDao.isDbConnected()){
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
    }

    public void Login(ActionEvent event) throws IOException{
        try {
            if (jdbcDao.isLogin(txtEmail.getText(), txtUser.getText(), txtPass.getText())){
                isConnected.setText("Users credentails are correct");
                ((Node)event.getSource()).getScene().getWindow().hide();
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                InputStream url = new File("C:\\Users\\kaiai\\OneDrive\\Desktop\\deuley\\src\\main\\java\\last\\project\\3.fxml").toURI().toURL().openStream();
                Pane root = loader.load(url);
                ListController listController = (ListController)loader.getController();
                listController.GetList(txtUser.getText());
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                stage.setScene(scene);
                stage.show();


            } else {
                isConnected.setText("Users credentails are incorrect");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            isConnected.setText("Users credentails are incorrect");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}