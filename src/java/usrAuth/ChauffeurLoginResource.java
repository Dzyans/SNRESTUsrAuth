/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package usrAuth;

import brugerautorisation.transport.soap.Bruger;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import tokenGen.TokenBuilder;

/**
 *
 * @author thor
 */
@Path("Chauffuer")
public class ChauffeurLoginResource {
    
    @Context
    private UriInfo context;
    
    public ChauffeurLoginResource() {
        
    }
    
    /**
     * Retrieves representation of an instance of usrAuth.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
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
    
    //ChauffoerLogin
    @Path("login/{login_name}/{password}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginChauffoer(@PathParam("login_name") String login_name, @PathParam("password") String password) {
        
        
        try{
          String  navn = hentBruger(login_name, password).getBrugernavn();
            System.out.println("user logged in with id:" + login_name);
            String token = TokenBuilder.buildToken("trucker", "pleb", "pleb", "pleb");
        
        JsonObject json = Json.createObjectBuilder().add("token", token).add("chauffeur_id", navn).build();
        return Response.status(Response.Status.OK).entity(json.toString(), new Annotation[]{})
             
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .build();
        } catch(Exception e){// LOGIN ERROR
            System.out.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                    .build();
        }
        
        
    }
    
    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    private static Bruger hentBruger(java.lang.String arg0, java.lang.String arg1) {
        brugerautorisation.transport.soap.BrugeradminImplService service = new brugerautorisation.transport.soap.BrugeradminImplService();
        brugerautorisation.transport.soap.Brugeradmin port = service.getBrugeradminImplPort();
        return port.hentBruger(arg0, arg1);
    }
    
}
