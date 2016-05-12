/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MySQLConnection;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.year;

import MySQLConnection.dbConnector;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author thor
 */
public class TableUsersDAO {
    static java.sql.Connection con; 
    
    
    public TableUsersDAO() throws Exception{
        con = dbConnector.getDbConnection();     
    }
    
    
    public static int LoginCheck(String loginName, String password){
        System.out.println("loginCheck invoked");
        int response = 0; //variable used for returning the responsecode
        try{
            con = dbConnector.getDbConnection();
            
            
            String preparedUserQuery = "select * from users where (login_name = ? AND password = ?)";
            java.sql.PreparedStatement pStm = con.prepareStatement(preparedUserQuery);
            
            pStm.setString(1, loginName);
            pStm.setString(2, password);
           ResultSet rs = pStm.executeQuery();
           int resultCounter =0;
           while(rs.next()){//simply counting if resultset contains data. if so login is succesfull
               resultCounter++;
           }
           if(resultCounter == 1){//possible valid login because of case insensitivity, should be handled in database, alas it is not
               rs.first();
               System.out.println("mulig bruger fundet");
               //alsdkfh
               System.out.println(rs.getString("login_name") + " " + rs.getString("password") );
               if (rs.getString(1).equals(loginName) && rs.getString(2).equals(password)){
                System.out.println("Bruger fundet");
            response = dbConnector.OK_RESPONSE;
            }
               
           } else response = dbConnector.USER_NOT_FOUND; //user not found
           
            System.out.println("\nLogin done");
           System.out.println("closing connection");
            con.close();
            System.out.println("Connection closed\n");
           return response;
        }catch(SQLException e){
            return dbConnector.CONNECTION_ERROR;//Connection failed 
        }catch(ClassNotFoundException e){
            return dbConnector.FATAL_ERROR;//catastrophic failure indicating file corruption on in rest server
        }
        finally{
            try {
                con.close();
                System.out.println("Db closed");
            } catch (SQLException ex) {
                Logger.getLogger(TableUsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    /**
     * Test method
     * @return
     * @throws SQLException
     * @deprecated
     */
    @Deprecated
    public boolean GetAllUserData() throws SQLException{
        java.sql.Statement stm = con.createStatement();
        
        try{
           ResultSet rs = stm.executeQuery("SELECT * FROM users");
           while(rs.next()){
               System.out.println(rs.getString(1));
           }
        }catch(Exception e){
            return false;
        }
        return true;
    }
    /**
     * Test Method
     * @param i
     * @return
     * @throws SQLException
     * @throws Exception
     * @deprecated
     */
    @Deprecated
    public boolean sendTestSensorData(int i) throws SQLException, Exception{
        
        
        java.sql.Statement stm = con.createStatement(); 
        Calendar cal = Calendar.getInstance();
        //cal.setTimeInMillis(0);
        //java.util.Date date = cal.getTime();
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        
        String stamp = sdf.format(date);
        System.out.println("from dao " + stamp);
        try {
            //stm.execute("insert into temperature(sensor_id, datatime, temp_c) values ('one','" + stamp + "' ,100)");
            stm.execute("insert into new_table(testvalue, testv2) values ('one" + i + "'," + i+100 + ")");
        } catch (SQLException ex) {
            Logger.getLogger(TableUsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
        return true;
    }
    public void closeCon() throws SQLException{
        if (con != null) con.close();
    }
    
}
