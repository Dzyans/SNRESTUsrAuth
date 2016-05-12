/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MySQLConnection;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.objects.NativeDate;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;

/**
 *
 * @author thor
 */
public class TableChauf_Node_regDAO {
    
    private static java.sql.Connection con;
    private static String timestamp;
    
    public static String registerChaufAtNode(String chauffeur_id, String node_id){//, String node_password){
        try {
            con = dbConnector.getDbConnection();
            System.out.println("attempting insert with node " + node_id );
            String stm = "insert into SensorNet.chauffeur_mainnode_reg (timestamp_start, node_id, chauffeur_id) values (?, ?, ?);";
      
            timestamp = getTime();            
           
            
            PreparedStatement pStm = con.prepareStatement(stm);
            pStm.setString(3, chauffeur_id);
            pStm.setString(2, node_id);
            pStm.setString(1, timestamp);
            System.out.println(pStm.toString());
            pStm.execute();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(TableChauf_Node_regDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(TableChauf_Node_regDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
                System.out.println("Connection on chauffeur_node_reg closed");
            } catch (SQLException ex) {
                Logger.getLogger(TableChauf_Node_regDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
        
        return timestamp;
    }
    
    public static String unregisterChaufAtNode(String chauffeur_id, String node_id, String timestamp_start)throws Exception{
        try{ 
            System.out.println("Unregistering");
            con = dbConnector.getDbConnection();
            System.out.println("attempting update with timestamp " + timestamp_start );
//            String stm = "update SensorData.chauffeur_mainnode_reg (timestamp_end = ?) where ( node_id = ? AND chauffeur_id = ? AND timestamp_start = ?);";
            String stm = "update SensorNet.chauffeur_mainnode_reg set timestamp_end = ? where ( node_id = ? AND chauffeur_id = ? AND timestamp_start = ?);";
//            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            long time = System.currentTimeMillis();
//            Timestamp ts = new Timestamp(time);
            
            timestamp = getTime();
            System.out.println(timestamp);
            
            PreparedStatement pStm = con.prepareStatement(stm);
            pStm.setString(4, timestamp_start);
            pStm.setString(3, chauffeur_id);
            pStm.setString(2, node_id);
            pStm.setString(1, timestamp);
            System.out.println(pStm.toString());
            //TODO: change this use of exception throwing
            pStm.execute();
        }catch(Exception e){
           throw new Exception("sensor or timestamp unrecognized");
        }finally{
            try {
                con.close();
                System.out.println("database connection closed on unregisterChaufAtNode");
            } catch (SQLException ex) {
                Logger.getLogger(TableChauf_Node_regDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    return timestamp;
    }   

    private static String getTime() {
       long time = System.currentTimeMillis();            
            Timestamp ts = new Timestamp(time);
            String s = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").format(ts);        
            System.out.println(timestamp);
            return s;
    }
}
