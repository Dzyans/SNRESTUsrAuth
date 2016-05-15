/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thor
 */
public class TableMainNodeDAO {
    static java.sql.Connection con; 
    
    public static int verifyNode(String node_id, String node_password){
        System.out.println("verifyNode invoked");
        int response = 0;
        try {
            
            con = dbConnector.getDbConnection();
            String preparedStmQuery = "select node_id, node_password from main_nodes where (node_id = ? AND node_password = ?)";
            
            PreparedStatement ps = con.prepareStatement(preparedStmQuery);
            ps.setString(1, node_id);
            ps.setString(2, node_password);
            ResultSet rs = ps.executeQuery();
            
            
            //--- iteration the resultset and checking case sensitive passowrd----------
            int resultCounter =0;
            while(rs.next()){//simply counting if resultset contains data. if so login is possibly succesfull
               resultCounter++;
            }
            if(resultCounter == 1){//possible valid login because of case insensitivity, should be handled in database, alas it is not
               rs.first(); // resetting the resultset pointer
               System.out.println("possible valid node login, making case sensitive password check");
               //alsdkfh
               System.out.println(rs.getString("node_id") + " " + rs.getString("node_password") );
               if (rs.getString(1).equals(node_id) && rs.getString(2).equals(node_password)){
                System.out.println("Bruger fundet");
                
                
            response = dbConnector.OK_RESPONSE;
            
               }
            }else response = dbConnector.INVALID_LOGIN;
            //-----------------------------------------------------------------------------
            
//            System.out.println("\nnode registration done");
//            System.out.println("closing connection");
//            con.close();
//            System.out.println("Connection closed\n");
            
            return response;
        } catch (SQLException ex) {
             Logger.getLogger(TableMainNodeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return dbConnector.CONNECTION_ERROR;
           
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TableMainNodeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return dbConnector.FATAL_ERROR;
            
        }finally{
            try {
                con.close();
                System.out.println("db connection closed");
            } catch (SQLException ex) {
                System.out.println("ERROR db connection not closed");
                Logger.getLogger(TableMainNodeDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
        
        
        
       
    }
    
    
}
