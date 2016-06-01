package simmapservice.resources;

import businesslogic.datafetch.DataFetchLogic;
import common.DataAccessLayerException;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.Calendar;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class QuadtileResourceTests {

    private final Properties properties;

    private static final DataFetchLogic dataFetchLogic = mock(DataFetchLogic.class);

    private static final Date lastModifiedDate = new Date(Calendar.getInstance().getTimeInMillis());

    @ClassRule
    public static ResourceTestRule resource;

    public QuadtileResourceTests() {
        this.properties = new Properties();
        properties.setProperty("psqluser", "test");
        properties.setProperty("psqlpassword", "test");
        properties.setProperty("psqlpath", "test");
        resource = ResourceTestRule.builder().addResource(new QuadtileResource(this.properties, dataFetchLogic)).build();
    }

    @Before
    public void setup() throws DataAccessLayerException {
        when(dataFetchLogic.getLastModified(eq(10), eq(10), eq(10), eq(1))).thenReturn(lastModifiedDate);
    }

    @Test
    public void test() {
        Response response = resource.client().target("/1/1/1/1").request().get();
        assertEquals(10, response.getLength());
    }
}