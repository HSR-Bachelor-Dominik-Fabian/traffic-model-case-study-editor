package simmapservice.resources;

import businesslogic.quadtile.QuadTile;

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

        QuadTile quadTile = new QuadTile(x, y, z);

        String quadKey = quadTile.generateQuadTileKey();

        System.out.println("x:" + x + ", y:" + y + ", z:" + z);
        System.out.println("QuadKey: " + quadKey);

        return Response.ok().build();
    }
}
