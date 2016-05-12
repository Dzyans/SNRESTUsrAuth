/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package usrAuth;

import MySQLConnection.TableChauf_Node_regDAO;
import MySQLConnection.TableMainNodeDAO;
import MySQLConnection.dbConnector;
import brugerautorisation.transport.soap.Bruger;
import java.lang.annotation.Annotation;
import javax.enterprise.inject.ResolutionException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
    
    //(TestMethod
    @Path("xxx")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXmlXxx() {
        System.out.println("xxx called");
        //TODO return proper representation object
        return "hurra Der er hul igennem";
    }
    
    
    @Path("/unRegCauffeurAtNode/{chauffeur_id}/{chauffeur_pw}/{node_id}/{timestamp}")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response unregisterChauffeurAtNode(@PathParam("chauffeur_id")String id, @PathParam("node_id")String node_id, @PathParam("chauffeur_pw")String pw, @PathParam("timestamp") String timestamp){
       try{
         Bruger chffr = hentBruger(id, pw);  
            System.out.println(chffr.getFornavn());
           String timestamp_end = TableChauf_Node_regDAO.unregisterChaufAtNode(id, node_id, timestamp);
            
              JsonObject json = Json.createObjectBuilder().add("chauffeur_unregistered at: ", timestamp_end).build();
            return Response.ok().entity(json.toString(), new Annotation[]{}).
                    allow("POST", "GET", "PUT", "HEAD", "OPTIONS", "UPDATE", "POST").header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
        } catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(new String(e.getMessage())).allow("POST", "GET", "PUT", "HEAD", "OPTIONS", "UPDATE", "POST").header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();       
        }
    }
    /**
     * returns the timestamp, that should be used when unregistering
     * @param id
     * @param node_id
     * @param pw
     * @return 
     */
    @Path("/regChauffeurAtNode/{chauffeur_id}/{chauffeur_pw}/{node_id}")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)    
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrateChauffeurWithNode(@PathParam("chauffeur_id")String id, @PathParam("node_id")String node_id, @PathParam("chauffeur_pw")String pw){
        try{
            Bruger chffr = hentBruger(id, pw);  
            System.out.println(chffr.getFornavn());
            String timestamp = TableChauf_Node_regDAO.registerChaufAtNode(id, node_id);
//            TableChauf_Node_regDAO.unregisterChaufAtNode(id, node_id, timestamp);
              JsonObject json = Json.createObjectBuilder().add("time_stamp", timestamp).build();
            return Response.ok().entity(json.toString(), new Annotation[]{}).
                    allow("POST", "GET", "PUT", "HEAD", "OPTIONS", "UPDATE", "POST").header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();
        } catch(Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).allow("POST", "GET", "PUT", "HEAD", "OPTIONS", "UPDATE", "POST").header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With").build();       
        }
    }
    
    @Path("regMainNode/{node_id}/{password}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response regMainNode(@PathParam("node_id")String node_id, @PathParam("password")String password){
        System.out.println("Invoking regMainNode");
        
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

    private static Bruger hentBruger(java.lang.String arg0, java.lang.String arg1) {
        brugerautorisation.transport.soap.BrugeradminImplService service = new brugerautorisation.transport.soap.BrugeradminImplService();
        brugerautorisation.transport.soap.Brugeradmin port = service.getBrugeradminImplPort();
        return port.hentBruger(arg0, arg1);
    }
    
    
}