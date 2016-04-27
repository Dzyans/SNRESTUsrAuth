/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usrAuth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
    
    @Path("regMainNode/{login_id}/{password}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response regMainNode(@PathParam("login_id")String login_id, @PathParam("password")String password){
        return Response.ok().build();
    }
    
}
