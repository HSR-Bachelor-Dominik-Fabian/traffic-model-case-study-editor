package simmapservice.resources;

import businesslogic.changeset.ChangesetFullModel;
import businesslogic.changeset.ChangesetLogic;
import dataaccess.DataAccessException;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import testenvironment.TestDataUtil;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Properties;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ChangesetResource.class)
public class ChangesetResourceTests {

    private ChangesetLogic changesetLogic;

    @Rule
    public final ResourceTestRule resource = ResourceTestRule.builder().setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addResource(new ChangesetResource(TestDataUtil.getTestProperties())).build();

    @Before
    public void setup() throws Exception {
        changesetLogic = PowerMock.createMock(ChangesetLogic.class);
        PowerMock.expectNew(ChangesetLogic.class, new Class[]{Properties.class},
                TestDataUtil.getTestProperties()).andReturn(changesetLogic);
    }

    @Test
    public void testGetAllChangesets() throws DataAccessException {
        expect(changesetLogic.getAllChangesets(eq(1))).andReturn(TestDataUtil.getListChangesetModels());
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/changesets/user/1").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(200, response.getStatus());
        verifyAll();
    }

    @Test
    public void testGetAllChangesetsWithoutChangesets() throws DataAccessException {
        expect(changesetLogic.getAllChangesets(eq(1))).andReturn(null);
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/changesets/user/1").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(204, response.getStatus());
        verifyAll();
    }

    @Test
    public void testGetAllChangesetsThrowException() throws DataAccessException {
        expect(changesetLogic.getAllChangesets(eq(1))).andThrow(new DataAccessException(new SQLException()));
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/changesets/user/1").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(500, response.getStatus());
        verifyAll();
    }

    @Test
    public void testPostChangesetThatAlreadyExists() throws DataAccessException {
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Entity<ChangesetFullModel> changesetFullModelEntity = Entity.entity(new ChangesetFullModel(
                TestDataUtil.getSingleSelectChangesetTestRecord()), MediaType.APPLICATION_JSON);
        Response response = jerseyTest.target("/changesets/user/1").request().post(changesetFullModelEntity);
        assertEquals(409, response.getStatus()); // HTTP Status Code 409 - Conflict
        verifyAll();
    }

    @Test
    public void testPostNewChangeset() throws DataAccessException {
        ChangesetFullModel fullModel = new ChangesetFullModel(TestDataUtil.getSingleSelectChangesetTestRecord());
        fullModel.setId(null);
        expect(changesetLogic.insertChangeset(anyObject())).andReturn((long) 1);
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Entity<ChangesetFullModel> changesetFullModelEntity = Entity.entity(fullModel, MediaType.APPLICATION_JSON);
        Response response = jerseyTest.target("/changesets/user/1").request().post(changesetFullModelEntity);
        assertEquals(201, response.getStatus()); // HTTP Status Code 201 - Created
        verifyAll();
    }

    @Test
    public void testPostNewChangesetThrowException() throws DataAccessException {
        ChangesetFullModel fullModel = new ChangesetFullModel(TestDataUtil.getSingleSelectChangesetTestRecord());
        fullModel.setId(null);
        expect(changesetLogic.insertChangeset(anyObject())).andThrow(new DataAccessException(new SQLException()));
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Entity<ChangesetFullModel> changesetFullModelEntity = Entity.entity(fullModel, MediaType.APPLICATION_JSON);
        Response response = jerseyTest.target("/changesets/user/1").request().post(changesetFullModelEntity);
        assertEquals(500, response.getStatus()); // HTTP Status Code
        verifyAll();
    }

    @Test
    public void testGetOneChangeset() throws DataAccessException {
        expect(changesetLogic.getFullChangeset(eq((long) 1))).andReturn(new ChangesetFullModel(TestDataUtil.getChangesetRecord()));
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/changesets/1").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(200, response.getStatus());
        verifyAll();
    }

    @Test
    public void testGetOneChangesetThrowsException() throws DataAccessException {
        expect(changesetLogic.getFullChangeset(eq((long) 1))).andThrow(new DataAccessException(new SQLException()));
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/changesets/1").request()
                .accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        assertEquals(500, response.getStatus());
        verifyAll();
    }

    @Test
    public void testDeleteChangeset() throws Exception {
        ChangesetFullModel fullModel = new ChangesetFullModel(TestDataUtil.getChangesetRecord());
        expect(changesetLogic.getFullChangeset(eq((long) 1))).andReturn(fullModel);
        changesetLogic.deleteChangeset(isA(ChangesetFullModel.class));
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/changesets/1").request().delete();
        assertEquals(204, response.getStatus());
        verifyAll();
    }

    @Test
    public void testDeleteNotExistingChangeset() throws Exception {
        ChangesetFullModel fullModel = new ChangesetFullModel(TestDataUtil.getChangesetRecord());
        fullModel.setId(null);
        expect(changesetLogic.getFullChangeset(eq((long) 1))).andReturn(fullModel);
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/changesets/1").request().delete();
        assertEquals(409, response.getStatus());
        verifyAll();
    }

    @Test
    public void testDeleteChangesetThrowException() throws Exception {
        expect(changesetLogic.getFullChangeset(eq((long) 1))).andThrow(new DataAccessException(new SQLException()));
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/changesets/1").request().delete();
        assertEquals(500, response.getStatus());
        verifyAll();
    }

    @Test
    public void testPutUpdateNotExistingChangeset() throws DataAccessException {
        ChangesetFullModel fullModel = new ChangesetFullModel(TestDataUtil.getChangesetRecord());
        expect(changesetLogic.hasChangeset(eq((long) 1))).andReturn(false);
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Entity<ChangesetFullModel> model = Entity.entity(fullModel, MediaType.APPLICATION_JSON);
        Response response = jerseyTest.target("/changesets/1").request().put(model);
        assertEquals(409, response.getStatus()); // HTTP Status Code 409 - Conflict
        verifyAll();
    }

    @Test
    public void testPutUpdateChangeset() throws DataAccessException {
        ChangesetFullModel fullModel = new ChangesetFullModel(TestDataUtil.getChangesetRecord());
        expect(changesetLogic.hasChangeset(eq((long) 1))).andReturn(true);
        changesetLogic.updateChangeset(isA(ChangesetFullModel.class));
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Entity<ChangesetFullModel> model = Entity.entity(fullModel, MediaType.APPLICATION_JSON);
        Response response = jerseyTest.target("/changesets/1").request().put(model);
        assertEquals(204, response.getStatus());
        verifyAll();
    }

    @Test
    public void testPutUpdateChangesetThrowException() throws DataAccessException {
        ChangesetFullModel fullModel = new ChangesetFullModel(TestDataUtil.getChangesetRecord());
        expect(changesetLogic.hasChangeset(eq((long) 1))).andThrow(new DataAccessException(new SQLException()));
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Entity<ChangesetFullModel> model = Entity.entity(fullModel, MediaType.APPLICATION_JSON);
        Response response = jerseyTest.target("/changesets/1").request().put(model);
        assertEquals(500, response.getStatus());
        verifyAll();
    }
}