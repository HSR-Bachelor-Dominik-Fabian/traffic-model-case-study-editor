package businesslogic.datafetch;

import businesslogic.changeset.LinkModel;
import dataaccess.DataAccessException;
import dataaccess.DataAccessLogic;
import dataaccess.database.Tables;
import dataaccess.connectionutils.IConnection;
import dataaccess.connectionutils.ProdConnection;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import testenvironment.TestDataUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.powermock.api.easymock.PowerMock.*;
import static testenvironment.AssertionUtils.assertLinkModelToRecord;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DataAccessLogic.class, DataFetchLogic.class})
public class DataFetchLogicTests {
    private DataAccessLogic dataAccessLogic;
    private final Properties properties = TestDataUtil.getTestProperties();
    private final ProdConnection prodConnection = new ProdConnection();

    @Before
    public void setup() throws Exception {
        dataAccessLogic = createMock(DataAccessLogic.class);
        expectNew(ProdConnection.class).andReturn(prodConnection);
        expectNew(DataAccessLogic.class, new Class[]{Properties.class, IConnection.class},
                properties, prodConnection)
                .andReturn(dataAccessLogic);
    }

    @Test
    public void testGetDataForTile() throws DataAccessException {
        Result<Record> result = DSL.using(SQLDialect.POSTGRES).newResult(Tables.LINK.fields());
        result.addAll(TestDataUtil.getMultipleSelectLinkTestRecords());
        expect(dataAccessLogic.getLinksFromQuadKey("000000030313", 1, 12)).andReturn(result);
        replayAll();
        DataFetchLogic dataFetchLogic = new DataFetchLogic(properties);
        JSONObject jsonObject = dataFetchLogic.getDataForTile(23, 21, 12, 1);
        verifyAll();
        assertEquals("{\"features\":[{\"geometry\":{\"coordinates\":[[12,12],[23,23]],\"type\":\"LineString\"},\"type\":\"Feature\",\"properties\":{\"modes\":\"car\",\"zoomlevel\":12,\"length\":1200,\"freespeed\":33.3299999999999982946974341757595539093017578125,\"permlanes\":2,\"from\":\"N1\",\"id\":\"L1\",\"to\":\"N2\",\"oneway\":true,\"capacity\":1000}},{\"geometry\":{\"coordinates\":[[12,12],[23,23]],\"type\":\"LineString\"},\"type\":\"Feature\",\"properties\":{\"modes\":\"car\",\"zoomlevel\":12,\"length\":1200,\"freespeed\":33.3299999999999982946974341757595539093017578125,\"permlanes\":2,\"id\":\"L2\",\"oneway\":true,\"capacity\":1000}}],\"type\":\"FeatureCollection\"}", jsonObject.toString());
    }

    @Test
    public void testGetDataForTileWithNodesZoomNegative() throws DataAccessException {
        replayAll();
        resetAll();
        DataFetchLogic dataFetchLogic = new DataFetchLogic(properties);
        JSONObject jsonObject = dataFetchLogic.getDataForTileWithNodes(1, 32, 11, 1);
        assertNull(jsonObject);
    }

    @Test
    public void testGetDataFroTileWithNodes() throws DataAccessException {
        Result<Record> result = DSL.using(SQLDialect.POSTGRES).newResult(Tables.LINK.fields());
        result.addAll(TestDataUtil.getMultipleSelectLinkTestRecords());
        Result<Record> nodeResult = DSL.using(SQLDialect.POSTGRES).newResult(Tables.NODE.fields());
        nodeResult.addAll(TestDataUtil.getMultipleNodeTestRecords());
        List<String> nodeIds = new ArrayList<>();
        for (Record link : result) {
            nodeIds.add((String) link.getValue("From"));
            nodeIds.add((String) link.getValue("To"));
        }
        expect(dataAccessLogic.getLinksFromQuadKey("0000000000030313", 1, 16)).andReturn(result);
        expect(dataAccessLogic.getNodesFromIds(nodeIds, 1)).andReturn(nodeResult);

        replayAll();
        DataFetchLogic dataFetchLogic = new DataFetchLogic(properties);
        JSONObject jsonObject = dataFetchLogic.getDataForTileWithNodes(23, 21, 16, 1);
        assertEquals("{\"features\":[{\"geometry\":{\"coordinates\":[[12,12],[23,23]],\"type\":\"LineString\"},\"type\":\"Feature\",\"properties\":{\"modes\":\"car\",\"zoomlevel\":16,\"length\":1200,\"freespeed\":33.3299999999999982946974341757595539093017578125,\"permlanes\":2,\"from\":\"N1\",\"id\":\"L1\",\"to\":\"N2\",\"oneway\":true,\"capacity\":1000}},{\"geometry\":{\"coordinates\":[[12,12],[23,23]],\"type\":\"LineString\"},\"type\":\"Feature\",\"properties\":{\"modes\":\"car\",\"zoomlevel\":16,\"length\":1200,\"freespeed\":33.3299999999999982946974341757595539093017578125,\"permlanes\":2,\"id\":\"L2\",\"oneway\":true,\"capacity\":1000}},{\"geometry\":{\"coordinates\":[23,12],\"type\":\"Point\"},\"type\":\"Feature\",\"properties\":{\"zoomlevel\":16,\"id\":\"N1\"}},{\"geometry\":{\"coordinates\":[23,12],\"type\":\"Point\"},\"type\":\"Feature\",\"properties\":{\"zoomlevel\":16,\"id\":\"N2\"}}],\"type\":\"FeatureCollection\"}", jsonObject.toString());
        verifyAll();
    }

    @Test
    public void testGetLastModified() throws DataAccessException {
        Date testDate = Date.valueOf("2003-12-1");
        expect(dataAccessLogic.getLastModifiedQuadKey("0000000000030313", 1, 16)).andReturn(testDate);
        replayAll();
        DataFetchLogic dataFetchLogic = new DataFetchLogic(properties);
        assertEquals(testDate, dataFetchLogic.getLastModified(23, 21, 16, 1));
        verifyAll();
    }

    @Test
    public void testGetLinkById() throws DataAccessException {
        expect(dataAccessLogic.getLinkFromId("L1")).andReturn(TestDataUtil.getSingleSelectLinkTestRecord());
        replayAll();
        DataFetchLogic dataFetchLogic = new DataFetchLogic(properties);
        LinkModel model = dataFetchLogic.getLinkById("L1");
        assertLinkModelToRecord(TestDataUtil.getSingleSelectLinkTestRecord(), model);
        verifyAll();
    }
}

