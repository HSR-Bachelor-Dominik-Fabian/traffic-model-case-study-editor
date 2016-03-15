package simmapservice.resources;

import businesslogic.mercatorconvert.BoundingBox;
import businesslogic.mercatorconvert.Converter;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/networks/{z}/{x}/{y}")
public class StreetserviceResource {
    @GET
    public Response getNetwork(@PathParam("z") int z, @PathParam("x") int x, @PathParam("y") int y){
        System.out.println("x:" + x + ", y:"+ y + ", z:" + z);
        BoundingBox coord = Converter.tile2boundingBox(x,y,z);
        System.out.println("north:" + coord.north + ", west:"+ coord.west + ", south:" + coord.south + ", east:" + coord.east);
        return Response.ok().build();
    }
}
