package simmapservice.resources;

import businesslogic.datafetch.DataFetchLogic;
import com.google.common.base.Stopwatch;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by fke on 17.03.2016.
 */
@Produces("application/json")
@Path("/quadtile/{networkid}/{z}/{x}/{y}")
public class QuadtileResource {

    private final Properties properties;

    public QuadtileResource(Properties props){
        this.properties = props;
    }

    @GET
    public Response getQuadTile(@PathParam("z") int z, @PathParam("x") int x, @PathParam("y") int y, @PathParam("networkid") int networkID) {

        DataFetchLogic dataFetchLogic = new DataFetchLogic();
        Stopwatch stopwatch = Stopwatch.createStarted();
        JSONObject json =  dataFetchLogic.getDataForTile(x, y, z, networkID, this.properties);
        stopwatch.stop();
        System.out.println("Get Data: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
        CacheControl cc = new CacheControl();
        cc.setMaxAge(88400);
        cc.setPrivate(true);
        
        Response.ResponseBuilder builder = Response.ok(json.toString());
        builder.cacheControl(cc);
        return builder.build();
    }
}
