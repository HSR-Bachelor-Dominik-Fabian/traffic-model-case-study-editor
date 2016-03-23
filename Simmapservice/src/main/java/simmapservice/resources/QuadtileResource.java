package simmapservice.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by fke on 17.03.2016.
 */
@Path("/quadtile/{z}/{x}/{y}")
public class QuadtileResource {
    @GET
    public Response getQuadTile(@PathParam("z") int z, @PathParam("x") int x, @PathParam("y") int y) {


        return Response.ok().build();
    }
}
