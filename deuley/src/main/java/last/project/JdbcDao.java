package last.project;

import java.sql.*;
public class JdbcDao{
    Connection connect;
    public JdbcDao (){
        connect = SqliteConnection.Connector();
        if(connect == null) System.exit(1);
    }

    public boolean isDbConnected(){
        try {
            return !connect.isClosed();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }
    public boolean isLogin(String email, String user, String pass) throws SQLException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Email where Email_name = ? AND Email_user = ? AND Email_pass = ?";
        try {
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, user);
            preparedStatement.setString(3, pass);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else{
                return false;
            }

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }
}