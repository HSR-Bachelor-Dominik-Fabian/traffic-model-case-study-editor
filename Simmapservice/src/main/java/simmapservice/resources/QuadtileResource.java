package simmapservice.resources;

import businesslogic.datafetch.DataFetchLogic;
import com.google.common.base.Stopwatch;
import dataaccess.DataAccessException;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.sql.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
@Produces("application/json")
@Path("/quadtile")
public class QuadtileResource {

    private final Properties properties;

    public QuadtileResource(Properties props) {
        this.properties = props;
    }

    @GET
    @Path("/{networkid}/{z}/{x}/{y}")
    public Response getQuadTile(@PathParam("z") int z, @PathParam("x") int x, @PathParam("y") int y, @PathParam("networkid") int networkID, @Context Request request) {
        try {
            DataFetchLogic dataFetchLogic = new DataFetchLogic(properties);
            CacheControl cc = new CacheControl();
            cc.setMaxAge(3600);
            cc.setPrivate(true);
            Response.ResponseBuilder builder = null;
            Stopwatch stopwatch1 = Stopwatch.createStarted();
            Date date = dataFetchLogic.getLastModified(x, y, z, networkID);
            stopwatch1.stop();
            System.out.println("Get Cache: " + stopwatch1.elapsed(TimeUnit.MILLISECONDS) + " ms");
            if (date == null) {
                return Response.noContent().cacheControl(cc).build();
            }
            EntityTag etag = new EntityTag(date.hashCode() + "id" + x + "." + y + "." + z + "." + networkID);

            builder = request.evaluatePreconditions(new EntityTag(etag.getValue() + "--gzip"));

            if (builder != null) {
                System.out.println("Has Cache!");
                return builder.cacheControl(cc).tag(etag).build();
            }

            Stopwatch stopwatch = Stopwatch.createStarted();
            JSONObject json = dataFetchLogic.getDataForTile(x, y, z, networkID);
            stopwatch.stop();
            System.out.println("Get Data: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");

            builder = Response.ok(json.toString());
            builder.cacheControl(cc).tag(etag);
            return builder.build();
        } catch (DataAccessException exc) {
            return Response.serverError().entity(exc).type(MediaType.APPLICATION_JSON).build();
        }
    }

    @GET
    @Path("/edit/{networkid}/{z}/{x}/{y}")
    public Response getQuadTileWithNodes(@PathParam("z") int z, @PathParam("x") int x, @PathParam("y") int y, @PathParam("networkid") int networkID, @Context Request request) {
        try {
            DataFetchLogic dataFetchLogic = new DataFetchLogic(properties);
            CacheControl cc = new CacheControl();
            cc.setMaxAge(88400);
            cc.setPrivate(true);
            Response.ResponseBuilder builder = null;
            Stopwatch stopwatch1 = Stopwatch.createStarted();
            Date date = dataFetchLogic.getLastModified(x, y, z, networkID);
            stopwatch1.stop();
            System.out.println("Get Cache: " + stopwatch1.elapsed(TimeUnit.MILLISECONDS) + " ms");
            if (date == null) {
                return Response.noContent().cacheControl(cc).build();
            }
            EntityTag etag = new EntityTag(date.hashCode() + "id" + x + "." + y + "." + z + "." + networkID + ".edit");

            builder = request.evaluatePreconditions(new EntityTag(etag.getValue() + "--gzip"));

            if (builder != null) {
                System.out.println("Has Cache!");
                return builder.cacheControl(cc).tag(etag).build();
            }

            Stopwatch stopwatch = Stopwatch.createStarted();
            JSONObject json = dataFetchLogic.getDataForTileWithNodes(x, y, z, networkID);
            stopwatch.stop();
            System.out.println("Get Data: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
            if (json == null) {
                return Response.noContent().cacheControl(cc).build();
            }
            builder = Response.ok(json.toString());
            builder.cacheControl(cc).tag(etag);
            return builder.build();
        } catch (DataAccessException exc) {
            return Response.serverError().entity(exc).type(MediaType.APPLICATION_JSON).build();
        }
    }
}
