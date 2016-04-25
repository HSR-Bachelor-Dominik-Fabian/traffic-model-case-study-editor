package simmapservice.resources;

import businesslogic.changeset.LinkModel;
import businesslogic.datafetch.DataFetchLogic;
import dataaccess.database.tables.records.LinkRecord;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Properties;

@Path("/link/{id}")
public class StreetServiceResource {

    private final Properties properties;

    public StreetServiceResource(Properties properties) {
        this.properties = properties;
    }

    @GET
    public Response getStreetById(@PathParam("id") String id){

        DataFetchLogic dataFetch = new DataFetchLogic(this.properties);
        LinkModel link = dataFetch.getLinkById(id);

        return Response.ok(link, MediaType.APPLICATION_JSON).build();
    }
}
