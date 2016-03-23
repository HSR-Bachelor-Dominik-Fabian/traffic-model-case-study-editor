package simmapservice.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by fke on 21.03.2016.
 */
@Path("/xyToQuadtile/{x}/{y}")
public class XYToQuadtileResource {
    @GET
    public Response getQuadTileForXY(@PathParam("x") double x, @PathParam("y") double y) {

        return Response.ok().build();
    }
}
