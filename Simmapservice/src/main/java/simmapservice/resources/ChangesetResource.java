package simmapservice.resources;

import businesslogic.changeset.ChangesetFullModel;
import businesslogic.changeset.ChangesetLogic;
import businesslogic.changeset.ChangesetModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Properties;

/**
 * Created by dohee on 13.04.2016.
 */
@Produces("application/json")
@Path("/changesets")
public class ChangesetResource {
    private final Properties properties;

    public ChangesetResource(Properties props){
        this.properties = props;
    }
    @GET @Path("/user/{userid}")
    public Response getAllChangesets(@PathParam("userid")int userid){
        try{
            ChangesetLogic businessLogic = new ChangesetLogic(this.properties);
            List<ChangesetModel> changesetModelList = businessLogic.getAllChangesets(userid);
            return Response.ok(changesetModelList, MediaType.APPLICATION_JSON).build();
        }
        catch (Exception exc){
            return Response.serverError().entity(exc).type(MediaType.APPLICATION_JSON).build();
        }

    }
    @POST @Path("/user/{userid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postNewChangeset(@PathParam("userid") int userid, ChangesetFullModel fullModel, @Context UriInfo uriInfo){
        ChangesetLogic businessLogic = new ChangesetLogic(this.properties);
        if(fullModel.getId() != null){
            return Response.status(409).entity("Has already an id cannot be inserted").build();
        }
        Long id = businessLogic.insertChangeset(fullModel);
        return Response.created(URI.create("/api/changesets/"+id)).header("Access-Control-Expose-Headers","Location").build();
    }
    @PUT @Path("/{id}")
    public Response putUpdateChangeset(@PathParam("id") long id, ChangesetFullModel fullModel){
        ChangesetLogic businessLogic = new ChangesetLogic(this.properties);
        if(!businessLogic.hasChangeset(id)){
            return Response.status(409).entity("Changeset not found").build();
        }
        businessLogic.updateChangeset(fullModel);
        return Response.noContent().build();
    }

    @GET @Path("/{id}")
    public Response getOneChangeset(@PathParam("id")long id){
        try{
            ChangesetLogic businessLogic = new ChangesetLogic(this.properties);
            ChangesetFullModel changesetFullModel = businessLogic.getFullChangeset(id);
            return Response.ok(changesetFullModel, MediaType.APPLICATION_JSON).build();
        }
        catch (Exception exc){
            return Response.serverError().entity(exc).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @DELETE @Path("/{id}")
    public Response deleteChangeset(@PathParam("id") long id){
        ChangesetLogic businessLogic = new ChangesetLogic(this.properties);
        ChangesetFullModel changesetFullModel = businessLogic.getFullChangeset(id);
        if(changesetFullModel.getId() == null){
            return Response.status(409).entity("Changeset not found").build();
        }
        businessLogic.deleteChangeset(changesetFullModel);
        return Response.noContent().build();
    }
}