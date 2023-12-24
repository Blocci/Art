package last.project;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class SqliteConnection{
    public static Connection Connector(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection Conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\kaiai\\OneDrive\\Desktop\\deuley\\file.db");
            return Conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
            // TODO: handle exception
        }
    }

    public static ObservableList<users> getDatausers() {
        Connection conn = Connector();
        ObservableList<users> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select NAME, ANAME, LNAME, TIME, MNAME, ADDR from Info;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                
                list.add(new users(rs.getString("NAME"), rs.getString("ANAME"), rs.getString("LNAME"), rs.getString("TIME"), rs.getString("MNAME"), rs.getString("ADDR")));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return list;
    }
}
