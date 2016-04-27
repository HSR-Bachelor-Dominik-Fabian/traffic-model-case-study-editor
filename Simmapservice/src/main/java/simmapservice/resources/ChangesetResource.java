package simmapservice.resources;

import businesslogic.changeset.ChangesetFullModel;
import businesslogic.changeset.ChangesetLogic;
import businesslogic.changeset.ChangesetModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
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
    public Response postNewChangeset(@PathParam("userid") int userid, ChangesetFullModel fullModel){
        //return Response.created().build();
        return null; //TODO: return created
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
}
