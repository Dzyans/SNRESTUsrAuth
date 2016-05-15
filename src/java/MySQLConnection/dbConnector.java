/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MySQLConnection;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thor
 */
public class dbConnector {
    
    /**
     * For fatal errors like file corruption
     */
    public final static int FATAL_ERROR = 666;
    
    public static final int OK_RESPONSE = 1;
    public static final int INVALID_LOGIN = 2;
    public static final int CONNECTION_ERROR = 6;
    
    /**
     * Method for creating database connection to "Amazon aws or Studentermaskinen"
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static java.sql.Connection getDbConnection() throws SQLException, ClassNotFoundException{
        
            Class.forName("com.mysql.jdbc.Driver");
            try {
                System.out.println("someonne is connecting");
                java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://54.229.188.28:3306/SensorNet", "SensorAdmin", "DistSysNet16");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
                System.out.println("attempting to connect to backup database @ ubuntu4.javabog.dk:3306");
            try{
                java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://ubuntu4.javabog.dk:3306/SensorData", "sensor", "net");
                 return con;
            }catch(Exception ex){               
                throw new SQLException();
            }
        }
           
          //  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LocalSensor", "root", "Roar");
            
      
        
    }
    
}
