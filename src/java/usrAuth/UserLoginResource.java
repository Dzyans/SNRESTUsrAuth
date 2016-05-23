/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package usrAuth;

import MySQLConnection.TableUsersDAO;

import java.lang.annotation.Annotation;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import tokenGen.TokenBuilder;



/**
 * REST Web Service
 *
 * @author thor
 */
@Path("user")
public class UserLoginResource {
    
    @Context
    private UriInfo context;
    
    /**
     * Creates a new instance of GenericResource
     */
    public UserLoginResource() {
    }
    
    /**
     * Retrieves representation of an instance of usrAuth.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    /**
     * testmethod
     * @return 
     */
    @Deprecated
    @Path("xxx")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXmlXxx() {
        
        
        
        System.out.println("xxx called");
//TODO return proper representation object
return "hurra";
    }
    
    @Path("login/{usr}/{pw}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginTry(@PathParam("usr") String user, @PathParam("pw")String psw){
        System.out.println("Login Attempted with user: " + user);
        int returnStatus = TableUsersDAO.LoginCheck(user, psw);
        
        if( returnStatus != 1){//something went wrong
            
            if(returnStatus == 2){// user login invalid
                return Response.status(Response.Status.BAD_REQUEST)
                        .allow("GET", "HEAD", "OPTIONS")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
                
            } else return Response.serverError()//serverside error
                    .allow("GET", "HEAD", "OPTIONS")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
            
        }else { //when user login is valid
            
            String token = TokenBuilder.buildToken(user, "valid", "2", "sx1");
            JsonObject json = Json.createObjectBuilder().add("User", user).add("token", token).build();
            return Response.status(Response.Status.OK).entity(json.toString(),new Annotation[]{})
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
        }       
    }
    
    
    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    
    
}
