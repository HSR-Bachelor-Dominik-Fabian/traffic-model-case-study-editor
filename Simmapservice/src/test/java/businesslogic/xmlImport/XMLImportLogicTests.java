package businesslogic.xmlImport;

import common.DataAccessLayerException;
import dataaccess.SimmapDataAccessFacade;
import dataaccess.database.tables.records.LinkRecord;
import dataaccess.database.tables.records.NetworkRecord;
import dataaccess.database.tables.records.NodeRecord;
import dataaccess.utils.IConnection;
import dataaccess.utils.ProdConnection;
import org.jooq.Result;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import testenvironment.TestDataUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import static org.easymock.EasyMock.aryEq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.anyObject;
import static org.mockito.Matchers.argThat;
import static org.powermock.api.easymock.PowerMock.*;
@RunWith(PowerMockRunner.class)
@PrepareForTest({SimmapDataAccessFacade.class, XMLImportLogic.class})
public class XMLImportLogicTests {
    Properties properties = TestDataUtil.getTestProperties();
    ProdConnection prodConnection = new ProdConnection();
    SimmapDataAccessFacade simmapDataAccessFacade;

    @Before
    public void setup() throws Exception {
        simmapDataAccessFacade = createMock(SimmapDataAccessFacade.class);
        expectNew(ProdConnection.class).andReturn(prodConnection);
        expectNew(SimmapDataAccessFacade.class, new Class[]{Properties.class, IConnection.class},
                properties, prodConnection)
                .andReturn(simmapDataAccessFacade);
    }

    @Test
    public void testImportNetwork2DB() throws DataAccessLayerException, FileNotFoundException {
        NetworkRecord network = new NetworkRecord();
        network.setId(1);
        network.setName("TestNetwork");
        InputStream stream = TestDataUtil.getInputStreamOfData();
        expect(simmapDataAccessFacade.setNetworks(aryEq(new NetworkRecord[]{network}))).andReturn(new int[]{1});
        expect(simmapDataAccessFacade.setNodes(aryEq(TestDataUtil.getStreamNodesAsArray()))).andReturn(new int[]{1});
        expect(simmapDataAccessFacade.setNetworkOptions(aryEq(TestDataUtil.getStreamOptionsAsArray()))).andReturn(new int[]{1});
        expect(simmapDataAccessFacade.setLinks(TestDataUtil.linkStreamEq(TestDataUtil.getStreamLinksAsArray()))).andReturn(new int[]{1});
        expect(simmapDataAccessFacade.getAllNodes()).andReturn(TestDataUtil.getStreamNodesAsResult());
        replayAll();
        XMLImportLogic importLogic = new XMLImportLogic(properties);
        importLogic.importNetwork2DB(stream, "EPSG:26914", "TestNetwork");
        verifyAll();
    }

}
