package last.project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class ListController implements Initializable {

    public JdbcDao jdbcDao = new JdbcDao();

    @FXML
    private Label listLbl;

    @FXML
    private TableView<users> table_users;

    @FXML
    private TableColumn<users, String> col_Addr;

    @FXML
    private TableColumn<users, String> col_Art;

    @FXML
    private TableColumn<users, String> col_ArtistFname;

    @FXML
    private TableColumn<users, String> col_ArtistLname;

    @FXML
    private TableColumn<users, String> col_Date;

    @FXML
    private TableColumn<users, String> col_Museum;

    @FXML
    private TextField txt_address;

    @FXML
    private TextField txt_art;

    @FXML
    private TextField txt_date;

    @FXML
    private TextField txt_first;

    @FXML
    private TextField txt_last;

    @FXML
    private TextField txt_museum;

    @FXML
    private TextField filterField;

    ObservableList<users> listM;
    ObservableList<users> dataList;

    int index = -1;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void Add_users(){
        conn = SqliteConnection.Connector();
        String sql = "INSERT INTO Info(NAME, ANAME, LNAME, TIME, MNAME, ADDR) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_art.getText());
            pst.setString(2, txt_first.getText());
            pst.setString(3, txt_last.getText());
            pst.setString(4, txt_date.getText());
            pst.setString(5, txt_museum.getText());
            pst.setString(6, txt_address.getText());
            pst.execute();

            System.out.println("Sucess");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }
    @FXML
    void getSelected(MouseEvent event){
        index = table_users.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txt_art.setText(col_Art.getCellData(index).toString());
        txt_first.setText(col_ArtistFname.getCellData(index).toString());
        txt_last.setText(col_ArtistLname.getCellData(index).toString());
        txt_date.setText(col_Date.getCellData(index).toString());
        txt_museum.setText(col_Museum.getCellData(index).toString());
        txt_address.setText(col_Addr.getCellData(index).toString());
    }

    public void Edit(){
        try {
            conn = SqliteConnection.Connector();
            String value1 = txt_art.getText();
            String value2 = txt_first.getText();
            String value3 = txt_last.getText();
            String value4 = txt_date.getText();
            String value5 = txt_museum.getText();
            String value6 = txt_address.getText();

            String sql = "update Info set NAME = '"+ value1 +"', ANAME = '"+ value2 +"', LNAME = '"+ value3 +"', TIME = '"+ value4 +"', MNAME = '"+ value5 +"', ADDR = '"+ value6 +"' where NAME = '"+ value1 +"'";
            pst = conn.prepareStatement(sql);
            pst.execute();

            System.out.println("updated");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

    public void Delete(){
        conn = SqliteConnection.Connector();
        String sql = "delete from Info where NAME = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_art.getText());
            pst.execute();
            System.out.println("Deleted");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

    public void UpdateTable(){
        col_Art.setCellValueFactory(new PropertyValueFactory<users, String>("art"));
        col_ArtistFname.setCellValueFactory(new PropertyValueFactory<users, String>("first"));
        col_ArtistLname.setCellValueFactory(new PropertyValueFactory<users, String>("last"));
        col_Date.setCellValueFactory(new PropertyValueFactory<users, String>("date"));
        col_Museum.setCellValueFactory(new PropertyValueFactory<users, String>("museum"));
        col_Addr.setCellValueFactory(new PropertyValueFactory<users, String>("address"));

        listM = SqliteConnection.getDatausers();
        table_users.setItems(listM);
    }

    @FXML
    void search_user(){
        col_Art.setCellValueFactory(new PropertyValueFactory<users, String>("art"));
        col_ArtistFname.setCellValueFactory(new PropertyValueFactory<users, String>("first"));
        col_ArtistLname.setCellValueFactory(new PropertyValueFactory<users, String>("last"));
        col_Date.setCellValueFactory(new PropertyValueFactory<users, String>("date"));
        col_Museum.setCellValueFactory(new PropertyValueFactory<users, String>("museum"));
        col_Addr.setCellValueFactory(new PropertyValueFactory<users, String>("address"));

        dataList = SqliteConnection.getDatausers();
        FilteredList<users> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Info ->  {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (Info.getArt().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else if(Info.getFirst().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else if(Info.getLast().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else if(Info.getDate().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else if(Info.getMuseum().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else if(Info.getAddress().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<users> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_users.comparatorProperty());
        table_users.setItems(sortedData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        UpdateTable();
        search_user();
    }

    public void GetList(String list) {
        // TODO Auto-generated method stub
        listLbl.setText(list);
    }
    
    public void SignOut(ActionEvent event) throws MalformedURLException, IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        InputStream url = new File("C:\\Users\\kaiai\\OneDrive\\Desktop\\deuley\\src\\main\\java\\last\\project\\1.fxml").toURI().toURL().openStream();
        Pane root = loader.load(url);
                
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}