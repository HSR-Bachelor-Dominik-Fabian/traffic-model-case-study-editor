package simmapservice.resources;

import businesslogic.changeset.LinkModel;
import businesslogic.datafetch.DataFetchLogic;
import dataaccess.DataAccessLayerException;

import javax.ws.rs.*;
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
        } catch (DataAccessLayerException exc) {
            return Response.serverError().entity(exc).type(MediaType.APPLICATION_JSON).build();
        }
    }
}
