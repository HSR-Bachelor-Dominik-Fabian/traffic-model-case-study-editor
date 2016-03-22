package simmapservice.resources;

import businesslogic.quadtile.QuadTile;

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

        System.out.println("Path for tile: /18/" + QuadTile.getXTileNumber(x, 18)
                + "/" + QuadTile.getYTileNumber(y, 18));

        System.out.println("MaxPossibleTile: " + QuadTile.getMaxQuadTileKey("210231031300020312", "210231031300020313"));

        return Response.ok().build();
    }
}
