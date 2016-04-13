package simmapservice.resources;

import businesslogic.changeset.ChangesetFullModel;
import businesslogic.changeset.ChangesetLogic;
import businesslogic.changeset.ChangesetModel;
import com.sun.org.apache.regexp.internal.RESyntaxException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
