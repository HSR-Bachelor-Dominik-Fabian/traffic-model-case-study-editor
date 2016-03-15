package simmapservice.resources;

import javax.ws.rs.*;

@Path("/tile/{z}/{x}/{y}")
public class StreetserviceResource {
    @GET
    public String getTile(@PathParam("z") int z, @PathParam("x") int x, @PathParam("y") int y){
        return "Anfrage fuer Tile: [" + x + "," + y + "] mit Zoomlevel: "+ z;
    }
}
