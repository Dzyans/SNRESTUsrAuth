/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package usrAuth;

import MySQLConnection.TableUsersDAO;
import com.firebase.security.token.TokenGenerator;
import java.awt.BorderLayout;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;

import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * REST Web Service
 *
 * @author thor
 */
@Path("user")
public class GenericResource {
    
    @Context
    private UriInfo context;
    
    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
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
    
    
    @Path("xxx")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXmlXxx() {
        
        
        
        System.out.println("xxx called");
//TODO return proper representation object
return "hurra";
    }
    
    @Path("get/{usr}/{pw}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginTry(@PathParam("usr") String user, @PathParam("pw")String psw){
        System.out.println("Login Attempted with user: " + user);
        int returnStatus = TableUsersDAO.LoginCheck(user, psw);
        
        if( returnStatus != 1){//something went wrong
            
            if(returnStatus == 2){// user login invalid
                return Response.status(Response.Status.FORBIDDEN)
                        .allow("POST", "GET", "HEAD", "OPTIONS", "UPDATE", "POST")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
                
            } else return Response.status(Response.Status.SERVICE_UNAVAILABLE)//serverside error
                    .allow("POST", "GET", "HEAD", "OPTIONS", "UPDATE", "POST")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
            
        }else { //when user login is valid
            
            //since user has been verified, a valid firebase token is generated, and send as response
            TokenGenerator tg = new TokenGenerator("4Y6DIqj0QU7pnn4ws5xfvqUBJvhZC9tYNNHinp8L");
            HashMap authPayload = new HashMap<String, Object>();
            authPayload.put("uid", "1");
            authPayload.put("some", "arbitrary");
            authPayload.put("data", "here");
            
            String tk = tg.createToken(authPayload);
            JsonObject json = Json.createObjectBuilder().add("User", user).add("token", tk).build();
            return Response.status(Response.Status.OK).entity(json.toString(),new Annotation[]{}).allow("POST", "GET", "HEAD", "OPTIONS", "UPDATE", "POST")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();}
        /*if(!user.equals("Sensor")){
        return Response.status(Response.Status.FORBIDDEN).allow("POST", "GET", "HEAD", "OPTIONS", "UPDATE", "POST")
        .header("Access-Control-Allow-Origin", "*")
        .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
        .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
        }else{
        
        TokenGenerator tg = new TokenGenerator("4Y6DIqj0QU7pnn4ws5xfvqUBJvhZC9tYNNHinp8L");
        HashMap authPayload = new HashMap<String, Object>();
        authPayload.put("uid", "1");
        authPayload.put("some", "arbitrary");
        authPayload.put("data", "here");
        
        String tk = tg.createToken(authPayload);
        JsonObject json = Json.createObjectBuilder().add("User", user).add("token", tk).build();
        return Response.ok().entity(json.toString(),new Annotation[]{}).allow("POST", "GET", "HEAD", "OPTIONS", "UPDATE", "POST")
        .header("Access-Control-Allow-Origin", "*")
        .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
        .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();}}*/
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
