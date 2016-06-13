package simmapservice.resources;

import businesslogic.datafetch.DataFetchLogic;
import dataaccess.DataAccessException;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import testenvironment.TestDataUtil;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

@RunWith(PowerMockRunner.class)
@PrepareForTest({QuadtileResource.class, DataFetchLogic.class})
public class QuadtileResourceTests {

    private DataFetchLogic dataFetchLogic;

    private static final Date lastModifiedDate = new Date(Calendar.getInstance().getTimeInMillis());
    private static final JSONObject dataForTile = new JSONObject();

    @Rule
    public final ResourceTestRule resource = ResourceTestRule.builder().setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addResource(new QuadtileResource(TestDataUtil.getTestProperties())).build();

    @Before
    public void setup() throws Exception {
        dataFetchLogic = PowerMock.createMock(DataFetchLogic.class);
        PowerMock.expectNew(DataFetchLogic.class, new Class[]{Properties.class},
                TestDataUtil.getTestProperties()).andReturn(dataFetchLogic);
        dataForTile.put("type", "FeatureCollection");
        dataForTile.put("features", new JSONArray());
    }

    @Test
    public void testConstructor() {
        QuadtileResource quadtileResource = new QuadtileResource(TestDataUtil.getTestProperties());
        assertNotNull(quadtileResource);
    }

    @Test
    public void testGetQuadTileValidTarget() throws DataAccessException {
        expect(dataFetchLogic.getLastModified(eq(10), eq(10), eq(10), eq(1))).andReturn(lastModifiedDate);
        expect(dataFetchLogic.getDataForTile(eq(10), eq(10), eq(10), eq(1))).andReturn(dataForTile);
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/quadtile/1/10/10/10").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(200, response.getStatus());
        verifyAll();
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
    public void testGetQuadTileNoContentWithLastModifiedNull() throws DataAccessException {
        expect(dataFetchLogic.getLastModified(eq(10), eq(10), eq(10), eq(1))).andReturn(null);
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/quadtile/1/10/10/10").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(204, response.getStatus());
        verifyAll();
    }

    @Test
    public void testGetQuadTileThrowException() throws DataAccessException {
        expect(dataFetchLogic.getLastModified(eq(10), eq(10), eq(10), eq(1))).andReturn(lastModifiedDate);
        expect(dataFetchLogic.getDataForTile(eq(10), eq(10), eq(10), eq(1))).andThrow(new DataAccessException(new SQLException()));
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/quadtile/1/10/10/10").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(500, response.getStatus());
        verifyAll();
    }

    @Test
    public void testGetQuadTileWithNodesValidTarget() throws DataAccessException {
        expect(dataFetchLogic.getLastModified(eq(10), eq(10), eq(10), eq(1))).andReturn(lastModifiedDate);
        expect(dataFetchLogic.getDataForTileWithNodes(eq(10), eq(10), eq(10), eq(1))).andReturn(dataForTile);
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/quadtile/edit/1/10/10/10").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(200, response.getStatus());
        verifyAll();
    }

    @Test
    public void testGetQuadTileWithNodesInvalidTarget() throws DataAccessException {
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
    public void testGetQuadTileWithNodesWithLastModifiedNull() throws DataAccessException {
        expect(dataFetchLogic.getLastModified(eq(10), eq(10), eq(10), eq(1))).andReturn(null);
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/quadtile/edit/1/10/10/10").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(204, response.getStatus());
        verifyAll();
    }

    @Test
    public void testGetQuadTileWithNodesWithNodesNull() throws DataAccessException {
        expect(dataFetchLogic.getLastModified(eq(10), eq(10), eq(10), eq(1))).andReturn(lastModifiedDate);
        expect(dataFetchLogic.getDataForTileWithNodes(eq(10), eq(10), eq(10), eq(1))).andReturn(null);
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/quadtile/edit/1/10/10/10").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(204, response.getStatus());
        verifyAll();
    }

    @Test
    public void testGetQuadTileWithNodesThrowException() throws DataAccessException {
        expect(dataFetchLogic.getLastModified(eq(10), eq(10), eq(10), eq(1))).andReturn(lastModifiedDate);
        expect(dataFetchLogic.getDataForTileWithNodes(eq(10), eq(10), eq(10), eq(1))).andThrow(new DataAccessException(new SQLException()));
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/quadtile/edit/1/10/10/10").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(500, response.getStatus());
        verifyAll();
    }
}