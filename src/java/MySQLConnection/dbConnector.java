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
    
    public static java.sql.Connection getDbConnection() throws SQLException, ClassNotFoundException{
        
            Class.forName("com.mysql.jdbc.Driver");
            try {
                java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://ubuntu4.javabog.dk:3306/SensorData", "sensor", "net");
            return con;
        } catch (Exception e) {
            throw new SQLException();
        }
           
          //  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LocalSensor", "root", "Roar");
            
      
        
    }
    
}
