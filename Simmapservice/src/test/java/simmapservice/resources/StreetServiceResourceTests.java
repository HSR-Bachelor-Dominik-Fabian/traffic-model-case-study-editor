package simmapservice.resources;

import businesslogic.changeset.LinkModel;
import businesslogic.datafetch.DataFetchLogic;
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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Properties;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

@RunWith(PowerMockRunner.class)
@PrepareForTest({StreetServiceResource.class, DataFetchLogic.class})
public class StreetServiceResourceTests {

    private DataFetchLogic dataFetchLogic;

    @Rule
    public ResourceTestRule resource = ResourceTestRule.builder().setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addResource(new StreetServiceResource(TestDataUtil.getTestProperties())).build();

    @Before
    public void setup() throws Exception {
        dataFetchLogic = PowerMock.createMock(DataFetchLogic.class);
        PowerMock.expectNew(DataFetchLogic.class, new Class[]{Properties.class},
                TestDataUtil.getTestProperties()).andReturn(dataFetchLogic);
    }

    @Test
    public void testConstructor() {
        StreetServiceResource streetServiceResource = new StreetServiceResource(TestDataUtil.getTestProperties());
        assertNotNull(streetServiceResource);
    }

    @Test
    public void testGetStreetById() throws DataAccessException {
        expect(dataFetchLogic.getLinkById(anyString())).andReturn(new LinkModel(TestDataUtil.getSingleInsertLinkTestRecord()));
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/link/1").request()
                .accept(MediaType.APPLICATION_JSON).get(Response.class);
        assertEquals(200, response.getStatus());
        verifyAll();
    }

    @Test
    public void testGetStreetByIdThrowException() throws DataAccessException {
        expect(dataFetchLogic.getLinkById(anyString())).andThrow(new DataAccessException(new SQLException()));
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        Response response = jerseyTest.target("/link/1").request()
                .accept(MediaType.APPLICATION_JSON).get(Response.class);
        assertEquals(500, response.getStatus());
        verifyAll();
    }
}
