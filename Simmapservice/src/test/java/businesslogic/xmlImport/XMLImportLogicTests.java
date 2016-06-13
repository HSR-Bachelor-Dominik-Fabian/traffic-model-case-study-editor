package businesslogic.xmlImport;

import dataaccess.DataAccessException;
import dataaccess.DataAccessLogic;
import dataaccess.database.tables.records.NetworkRecord;
import dataaccess.connectionutils.IConnection;
import dataaccess.connectionutils.ProdConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import testenvironment.TestDataUtil;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import static org.easymock.EasyMock.aryEq;
import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DataAccessLogic.class, XMLImportLogic.class})
public class XMLImportLogicTests {
    private final Properties properties = TestDataUtil.getTestProperties();
    private final ProdConnection prodConnection = new ProdConnection();
    private DataAccessLogic dataAccessLogic;

    @Before
    public void setup() throws Exception {
        dataAccessLogic = createMock(DataAccessLogic.class);
        expectNew(ProdConnection.class).andReturn(prodConnection);
        expectNew(DataAccessLogic.class, new Class[]{Properties.class, IConnection.class},
                properties, prodConnection)
                .andReturn(dataAccessLogic);
    }

    @Test
    public void testImportNetwork2DB() throws DataAccessException, FileNotFoundException, FactoryException, TransformException, XMLStreamException {
        NetworkRecord network = new NetworkRecord();
        network.setId(1);
        network.setName("TestNetwork");
        InputStream stream = TestDataUtil.getInputStreamOfData();
        expect(dataAccessLogic.setNetworks(aryEq(new NetworkRecord[]{network}))).andReturn(new int[]{1});
        expect(dataAccessLogic.setNodes(aryEq(TestDataUtil.getStreamNodesAsArray()))).andReturn(new int[]{1});
        expect(dataAccessLogic.setNetworkOptions(aryEq(TestDataUtil.getStreamOptionsAsArray()))).andReturn(new int[]{1});
        expect(dataAccessLogic.setLinks(TestDataUtil.linkStreamEq(TestDataUtil.getStreamLinksAsArray()))).andReturn(new int[]{1});
        expect(dataAccessLogic.getAllNodes()).andReturn(TestDataUtil.getStreamNodesAsResult());
        replayAll();
        XMLImportLogic importLogic = new XMLImportLogic(properties);
        importLogic.importNetwork2DB(stream, "EPSG:26914", "TestNetwork");
        verifyAll();
    }

}
