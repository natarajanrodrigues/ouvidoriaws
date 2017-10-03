/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pdm.ouvidoriaws.resources;

import ifpb.pdm.ouvidoriaws.daos.exceptions.AuditorException;
import ifpb.pdm.ouvidoriaws.entities.Usuario;
import ifpb.pdm.ouvidoriaws.services.UsuarioService;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author natarajan
 */

@Path("usuario")
@Stateless
public class UsuarioResources {
    
    @EJB
    private UsuarioService usuarioService;
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuario(@PathParam("id") Long id) {
        Usuario usuario = usuarioService.getById(id);
        if (usuario  == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return Response.ok().entity(usuario).build();
    }
    
    @GET
    @Path("/byemail/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarioByEmail(@PathParam("email") String email) {
        Usuario usuario = usuarioService.getByEmail(email);
        if (usuario  == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return Response.ok().entity(usuario).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addClient(Usuario usuario, @Context UriInfo uriInfo) throws URISyntaxException {

        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        return Response
                .created(new URI("/ouvidoriaws/api/usuario/" + usuario.getId()))
//                .created(URI.create("/ouvidoriaws/api/usuario/" + usuario.getId()))
                .entity(usuario)
                .build();

    }
    
    @PUT
    @Path("/auditor/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response tornarAuditor(@PathParam("id") String id) throws URISyntaxException {

        try {
            
            Usuario usuario = usuarioService.tornarAuditor(Long.parseLong(id));

            return Response
                .accepted(new URI("/ouvidoriaws/api/usuario/" + usuario.getId()))
                .entity(usuario)
                .build();
        } catch (AuditorException e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorMessage).build();
        }
    }
    
    @DELETE
    @Path("/auditor/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerAuditor(@PathParam("id") String id) throws URISyntaxException {

        try {
            Usuario usuario = null;
//            usuario = usuarioService.tornarAuditor(Long.parseLong(id));
            usuario = usuarioService.deletarAuditor(Long.parseLong(id));

            return Response
                .accepted(new URI("/ouvidoriaws/api/usuario/" + usuario.getId()))
                .entity(usuario)
                .build();
        } catch (AuditorException e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorMessage).build();
        }
        

    }
    
    
}
