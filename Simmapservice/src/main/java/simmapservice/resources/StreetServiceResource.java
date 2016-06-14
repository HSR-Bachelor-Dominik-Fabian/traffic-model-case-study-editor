package simmapservice.resources;

import businesslogic.changeset.LinkModel;
import businesslogic.datafetch.DataFetchLogic;
import dataaccess.DataAccessException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Properties;

@Produces("application/json")
@Path("/link")
public class StreetServiceResource {

    private final Properties properties;

    public StreetServiceResource(Properties properties) {
        this.properties = properties;
    }

    @GET
    @Path("/{id}")
    public Response getStreetById(@PathParam("id") String id) {
        try {
            DataFetchLogic dataFetch = new DataFetchLogic(this.properties);
            LinkModel link = dataFetch.getLinkById(id);

            return Response.ok(link, MediaType.APPLICATION_JSON).build();
        } catch (DataAccessException exc) {
            return Response.serverError().entity(exc).type(MediaType.APPLICATION_JSON).build();
        }
    }
}
