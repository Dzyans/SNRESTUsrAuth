/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package usrAuth;

import MySQLConnection.TableMainNodeDAO;
import MySQLConnection.dbConnector;
import java.lang.annotation.Annotation;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import tokenGen.TokenBuilder;

/**
 *
 * @author thor
 */
@Path("MainNode")
public class MainNodeRegistrationResource {
    
    @Context
    private UriInfo context;
    
    
    
    //empty constructor because reasons
    public MainNodeRegistrationResource(){
        
    }
    
    @Path("regMainNode/{node_id}/{password}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response regMainNode(@PathParam("node_id")String node_id, @PathParam("password")String password){
        
        int status = TableMainNodeDAO.verifyNode(node_id, password);
        System.out.println("node login status: " + status);
        
        if(status != 1){
            
            if(status == dbConnector.CONNECTION_ERROR || status == dbConnector.FATAL_ERROR){
                return Response.serverError()
//                    .allow("POST", "GET", "HEAD", "OPTIONS", "UPDATE", "POST")
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                        .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
            }else {//status can only be 2 
                return Response.status(Response.Status.BAD_REQUEST)
//                    .allow("POST", "GET", "HEAD", "OPTIONS", "UPDATE", "POST")
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                        .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
            }
           
        } else {// MainNode validated, returning token
             String token = TokenBuilder.buildToken("pleb", password, password, password);
            
            
            JsonObject json = Json.createObjectBuilder().add("token", token).add("node_id", node_id).build();
            
            return Response.status(Response.Status.OK).entity(json.toString(),new Annotation[]{})
//                    .allow("POST", "GET", "HEAD", "OPTIONS", "UPDATE", "POST")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
        }
        
    
    }
}