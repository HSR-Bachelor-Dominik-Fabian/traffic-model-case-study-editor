package simmapservice.resources;

import businesslogic.xmlImport.XMLImportLogic;
import dataaccess.expection.DataAccessLayerException;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

@RunWith(PowerMockRunner.class)
@PrepareForTest({XMLImportResource.class, XMLImportLogic.class})
public class XMLImportResourceTests {

    private XMLImportLogic xmlImportLogic;

    @Rule
    public ResourceTestRule resource = ResourceTestRule.builder().setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addProvider(MultiPartFeature.class)
            .addResource(new XMLImportResource(TestDataUtil.getTestProperties()))
            .build();

    @Before
    public void setup() throws Exception {
        xmlImportLogic = PowerMock.createMock(XMLImportLogic.class);
        PowerMock.expectNew(XMLImportLogic.class, new Class[] {Properties.class},
                TestDataUtil.getTestProperties()).andReturn(xmlImportLogic);
    }

    @Test
    public void testPostImport() throws FileNotFoundException, DataAccessLayerException {
        InputStream inputStream = TestDataUtil.getInputStreamOfData();
        xmlImportLogic.importNetwork2DB(isA(InputStream.class), isA(String.class), isA(String.class));
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        FormDataBodyPart body = new FormDataBodyPart("file", inputStream, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        FormDataMultiPart multiPart = new FormDataMultiPart().field("fileName", "test.xml").field("format", "XML")
                .field("name", "testNetwork");
        multiPart.bodyPart(body);
        Response response = jerseyTest.target("/xml").register(MultiPartFeature.class).request().post(Entity.entity(multiPart, multiPart.getMediaType()));
        assertEquals(200, response.getStatus());
        verifyAll();
    }

    @Test
    public void testPostImportThrowException() throws FileNotFoundException, DataAccessLayerException {
        InputStream inputStream = TestDataUtil.getInputStreamOfData();
        xmlImportLogic.importNetwork2DB(isA(InputStream.class), isA(String.class), isA(String.class));
        expectLastCall().andThrow(new DataAccessLayerException(new SQLException()));
        replayAll();
        JerseyTest jerseyTest = resource.getJerseyTest();
        assertNotNull(jerseyTest);
        FormDataBodyPart body = new FormDataBodyPart("file", inputStream, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        FormDataMultiPart multiPart = new FormDataMultiPart().field("fileName", "test.xml").field("format", "XML")
                .field("name", "testNetwork");
        multiPart.bodyPart(body);
        Response response = jerseyTest.target("/xml").register(MultiPartFeature.class).request().post(Entity.entity(multiPart, multiPart.getMediaType()));
        assertEquals(500, response.getStatus());
        verifyAll();
    }

}
