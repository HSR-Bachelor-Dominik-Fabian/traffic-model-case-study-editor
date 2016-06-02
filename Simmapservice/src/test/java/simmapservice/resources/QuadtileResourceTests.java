package simmapservice.resources;

import businesslogic.datafetch.DataFetchLogic;
import common.DataAccessLayerException;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.powermock.api.support.membermodification.MemberModifier;
import testenvironment.TestDataUtil;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.Calendar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class QuadtileResourceTests {

    private static final DataFetchLogic dataFetchLogic = mock(DataFetchLogic.class);

    private static final Date lastModifiedDate = new Date(Calendar.getInstance().getTimeInMillis());
    private static final JSONObject dataForTile = new JSONObject();
    private QuadtileResource quadtileResource = new QuadtileResource(TestDataUtil.getTestProperties());


    @Rule
    public ResourceTestRule resource = ResourceTestRule.builder().setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addResource(quadtileResource).build();

    @Before
    public void setup() throws Exception {
        when(dataFetchLogic.getLastModified(eq(10), eq(10), eq(10), eq(1))).thenReturn(lastModifiedDate);
        dataForTile.put("type", "FeatureCollection");
        dataForTile.put("features", new JSONArray());
        when(dataFetchLogic.getDataForTile(eq(10), eq(10), eq(10), eq(1))).thenReturn(dataForTile);
        when(dataFetchLogic.getDataForTileWithNodes(eq(10), eq(10), eq(10), eq(1))).thenReturn(dataForTile);
        MemberModifier.field(QuadtileResource.class, "dataFetchLogic").set(quadtileResource, dataFetchLogic);
    }

    @Test
    public void testConstructorWithoutDataFetchLogic() {
        QuadtileResource quadtileResource = new QuadtileResource(TestDataUtil.getTestProperties());
        assertNotNull(quadtileResource);
    }

    @Test
    public void testGetQuadTileValidTarget() {
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/quadtile/1/10/10/10").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testGetQuadTileInvalidTarget() {
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/api/quadtile/1/10/10/10").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void testGetQuadTileInvalidMediaType() {
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/quadtile/1/10/10/10").request()
                .accept(MediaType.APPLICATION_XML).get(Response.class);
        assertEquals(406, response.getStatus()); // HTTP Status 406 - Not acceptable
    }

    @Test
    public void testGetQuadTileNoContentWithLastModifiedNull() {
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/quadtile/10/10/10/10").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(204, response.getStatus());
    }

    @Test
    public void testGetQuadTileThrowException() throws DataAccessLayerException {
        when(dataFetchLogic.getDataForTile(eq(10), eq(10), eq(10), eq(1))).thenThrow(DataAccessLayerException.class);
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/quadtile/1/10/10/10").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(500, response.getStatus());
    }

    @Test
    public void testGetQuadTileWithNodesValidTarget() {
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/quadtile/edit/1/10/10/10").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testGetQuadTileWithNodesInvalidTarget() {
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/api/quadtile/edit/1/10/10/10").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void testGetQuadTileWithNodesInvalidMediaType() {
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/quadtile/edit/1/10/10/10").request()
                .accept(MediaType.APPLICATION_XML).get(Response.class);
        assertEquals(406, response.getStatus()); // HTTP Status 406 - Not acceptable
    }

    @Test
    public void testGetQuadTileWithNodesNoContentWithLastModifiedNull() {
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/quadtile/edit/10/10/10/10").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(204, response.getStatus());
    }

    @Test
    public void testGetQuadTileWithNodesThrowException() throws DataAccessLayerException {
        when(dataFetchLogic.getDataForTileWithNodes(eq(10), eq(10), eq(10), eq(1))).thenThrow(DataAccessLayerException.class);
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/quadtile/edit/1/10/10/10").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(500, response.getStatus());
    }

    @After
    public void tearDown(){
        reset(dataFetchLogic);
    }
}