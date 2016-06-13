package dataaccess;

import dataaccess.database.Tables;
import dataaccess.database.tables.records.*;
import dataaccess.connectionutils.DataAccessUtil;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import testenvironment.ConnectionMode;
import testenvironment.TestConnection;
import testenvironment.TestDataUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DataAccessUtil.class})
public class SimmapDataAccessFacadeTests {

    @Test
    public void setNetworkMultiplePositive() throws DataAccessException {
        final TestConnection connection = new TestConnection(ConnectionMode.MULTIPLE);
        final Properties props = new Properties();
        final NetworkRecord[] records = TestDataUtil.getTestInsertNetworks();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records
                , Tables.NETWORK, connection)).andReturn(new int[]{1, 1});

        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        int[] results = facade.setNetworks(records);
        assertEquals(records.length, results.length);
        for (int result : results) {
            assertEquals(1, result);
        }

    }

    @Test(expected = DataAccessException.class)
    public void setNetworkMultipleNegative() throws DataAccessException {
        final TestConnection connection = new TestConnection(ConnectionMode.ERROR);
        final Properties props = new Properties();
        final NetworkRecord[] records = TestDataUtil.getTestInsertNetworks();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records
                , Tables.NETWORK, connection)).andThrow(new DataAccessException(TestDataUtil.getSQLException()));

        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        facade.setNetworks(records);
    }

    @Test
    public void setNodeMultiplePositive() throws DataAccessException {
        final TestConnection connection = new TestConnection(ConnectionMode.MULTIPLE);
        final Properties props = new Properties();
        final NodeRecord[] records = TestDataUtil.getMultipleInsertNodeTestRecords();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records
                , Tables.NODE, connection)).andReturn(new int[]{1, 1});

        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        int[] results = facade.setNodes(records);
        assertEquals(records.length, results.length);
        for (int result : results) {
            assertEquals(1, result);
        }

    }

    @Test(expected = DataAccessException.class)
    public void setNodeMultipleNegative() throws DataAccessException {
        final TestConnection connection = new TestConnection(ConnectionMode.ERROR);
        final Properties props = new Properties();
        final NodeRecord[] records = TestDataUtil.getMultipleInsertNodeTestRecords();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records
                , Tables.NODE, connection)).andThrow(new DataAccessException(TestDataUtil.getSQLException()));

        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        facade.setNodes(records);
    }

    @Test
    public void setLinkPositive() throws DataAccessException {
        final TestConnection connection = new TestConnection(ConnectionMode.MULTIPLE);
        final Properties props = new Properties();
        final LinkRecord[] records = TestDataUtil.getMultipleInsertLinksTestRecords();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records
                , Tables.LINK, connection)).andReturn(new int[]{1, 1});
        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        int[] results = facade.setLinks(records);
        assertEquals(records.length, results.length);
        for (int result : results) {
            assertEquals(1, result);
        }
    }

    @Test(expected = DataAccessException.class)
    public void setLinkNegative() throws DataAccessException {
        final TestConnection connection = new TestConnection(ConnectionMode.ERROR);
        final Properties props = new Properties();
        final LinkRecord[] records = TestDataUtil.getMultipleInsertLinksTestRecords();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records
                , Tables.LINK, connection)).andThrow(new DataAccessException(TestDataUtil.getSQLException()));
        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        facade.setLinks(records);
    }

    @Test
    public void setNetworkOptionsPositive() throws DataAccessException {
        final TestConnection connection = new TestConnection(ConnectionMode.MULTIPLE);
        final Properties props = new Properties();
        final NetworkOptionsRecord[] records = TestDataUtil.getTestInsertNetworkOptions();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records
                , Tables.NETWORK_OPTIONS, connection)).andReturn(new int[]{1, 1, 1});
        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        int[] results = facade.setNetworkOptions(records);
        assertEquals(records.length, results.length);
        for (int result : results) {
            assertEquals(1, result);
        }
    }

    @Test(expected = DataAccessException.class)
    public void setNetworkOptionsNegative() throws DataAccessException {
        final TestConnection connection = new TestConnection(ConnectionMode.ERROR);
        final Properties props = new Properties();
        final NetworkOptionsRecord[] records = TestDataUtil.getTestInsertNetworkOptions();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records
                , Tables.NETWORK_OPTIONS, connection)).andThrow(new DataAccessException(TestDataUtil.getSQLException()));
        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        facade.setNetworkOptions(records);
    }

    @Test
    public void getAllNodesTest() throws DataAccessException {
        DSLContext ctx = DSL.using(SQLDialect.POSTGRES);
        Result<NodeRecord> expectedResult = ctx.newResult(Tables.NODE);
        expectedResult.addAll(TestDataUtil.getMultipleSelectNodeTestRecords());
        final TestConnection connection = new TestConnection(ConnectionMode.MULTIPLE);
        final Properties props = new Properties();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.getRecords(props, Tables.NODE, connection)).andReturn(expectedResult);
        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        Result<NodeRecord> result = facade.getAllNodes();
        assertEquals(expectedResult.size(), result.size());
        assertEquals(expectedResult, result);
    }

    @Test
    public void getAllChangesetsPerUserPositive() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.MULTIPLE));
        Result<ChangesetRecord> records = facade.getAllChangesetsPerUser(1);
        assertEquals(TestDataUtil.getMultipleSelectChangesetTestRecords().size(), records.size());
        assertArrayEquals(TestDataUtil.getMultipleSelectChangesetTestRecords().toArray(), records.toArray());
    }

    @Test(expected = DataAccessException.class)
    public void getAllChangesetsPerUserNegative() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.ERROR));
        facade.getAllChangesetsPerUser(1);
    }

    @Test(expected = DataAccessException.class)
    public void getAllChangesetsPerUserNoConnection() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.NOCONNECTION));
        facade.getAllChangesetsPerUser(1);
    }

    @Test
    public void getChangesetFromNumberPositive() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.ONE));
        ChangesetRecord record = facade.getChangesetFromNumber(1);
        assertEquals(TestDataUtil.getSingleSelectChangesetTestRecord(), record);
    }

    @Test(expected = DataAccessException.class)
    public void getChangesetFromNumberNegative() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.ERROR));
        facade.getChangesetFromNumber(1);
    }

    @Test(expected = DataAccessException.class)
    public void getChangesetFromNumberNoConnection() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.NOCONNECTION));
        facade.getChangesetFromNumber(1);
    }

    @Test
    public void getNodeFromIdPositive() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.ONE));
        NodeRecord record = facade.getNodeFromId("1");
        assertEquals(TestDataUtil.getSingleSelectNodeTestRecord(), record);
    }

    @Test(expected = DataAccessException.class)
    public void getNodeFromIdNegative() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.ERROR));
        facade.getNodeFromId("1");
    }

    @Test(expected = DataAccessException.class)
    public void getNodeFromIdNoConnection() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.NOCONNECTION));
        facade.getNodeFromId("1");
    }

    @Test
    public void getLinkFromIdPositive() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.ONE));
        LinkRecord record = facade.getLinkFromId("1");
        LinkRecord expected = TestDataUtil.getSingleSelectLinkTestRecord();
        expected.setId("1");
        assertEquals(expected, record);
    }

    @Test(expected = DataAccessException.class)
    public void getLinkFromIdNegative() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.ERROR));
        facade.getLinkFromId("1");
    }

    @Test(expected = DataAccessException.class)
    public void getLinkFromIdNoConnection() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.NOCONNECTION));
        facade.getLinkFromId("1");
    }

    @Test
    public void getLinkChangesfromChangesetPositive() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.ONE));
        Result<LinkChangeRecord> record = facade.getLinkChangesfromChangeset(1);
        assertEquals(1, record.size());
        assertEquals(TestDataUtil.getSingleSelectLinkChangeTestRecord(), record.get(0));
    }

    @Test(expected = DataAccessException.class)
    public void getLinkChangesfromChangesetNegative() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.ERROR));
        facade.getLinkChangesfromChangeset(1);
    }

    @Test(expected = DataAccessException.class)
    public void getLinkChangesfromChangesetNoConnection() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.NOCONNECTION));
        facade.getLinkChangesfromChangeset(1);
    }

    @Test
    public void getNodeChangefromChangesetPositive() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.MULTIPLE));
        Result<NodeChangeRecord> record = facade.getNodeChangefromChangeset(1);
        assertEquals(TestDataUtil.getMultipleSelectNodeChangeTestRecords().size(), record.size());
        List<NodeChangeRecord> expected = TestDataUtil.getMultipleSelectNodeChangeTestRecords();
        assertArrayEquals(expected.toArray(), record.toArray());
    }

    @Test(expected = DataAccessException.class)
    public void getNodeChangefromChangesetNegative() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.ERROR));
        facade.getNodeChangefromChangeset(1);
    }

    @Test(expected = DataAccessException.class)
    public void getNodeChangefromChangesetNoConnection() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.NOCONNECTION));
        facade.getNodeChangefromChangeset(1);
    }

    @Test
    public void getLinkFromQuadKeyPositive() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.MULTIPLE));
        Result<Record> record = facade.getLinksFromQuadKey("123", 1, 12);
        assertEquals(TestDataUtil.getMultipleSelectLinkTestRecords().size(), record.size());
        List<Record> expected = TestDataUtil.getMultipleSelectLinkTestRecords();
        assertEquals(expected, record);
    }

    @Test(expected = DataAccessException.class)
    public void getLinkFromQuadKeyNegative() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.ERROR));
        facade.getLinksFromQuadKey("123", 1, 12);
    }

    @Test(expected = DataAccessException.class)
    public void getLinkFromQuadKeyNoConnection() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.NOCONNECTION));
        facade.getLinksFromQuadKey("123", 1, 12);
    }

    @Test
    public void getLastModifiedQuadKeyPositive() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.MULTIPLE));
        Date record = facade.getLastModifiedQuadKey("123", 1, 12);
        Date expected = TestDataUtil.getDateLastModifiedTestRecord();
        assertEquals(expected, record);
    }

    @Test(expected = DataAccessException.class)
    public void getLastModifiedQuadKeyNegative() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.ERROR));
        facade.getLastModifiedQuadKey("123", 1, 12);
    }

    @Test(expected = DataAccessException.class)
    public void getLastModifiedQuadKeyNoConnection() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.NOCONNECTION));
        facade.getLastModifiedQuadKey("123", 1, 12);
    }

    @Test
    public void hasChangesetPositive() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.MULTIPLE));
        boolean record = facade.hasChangeset(1);
        assertTrue(record);
    }

    @Test(expected = DataAccessException.class)
    public void hasChangesetNegative() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.ERROR));
        facade.hasChangeset(1);
    }

    @Test(expected = DataAccessException.class)
    public void hasChangesetNoConnection() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.NOCONNECTION));
        facade.hasChangeset(1);
    }

    @Test
    public void deleteLink_ChangesPositive() throws DataAccessException {
        final TestConnection connection = new TestConnection(ConnectionMode.ONE);
        final Properties props = new Properties();
        List<LinkChangeRecord> records = new ArrayList<>();
        records.add(TestDataUtil.getSingleSelectLinkChangeTestRecord());
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.deleteRecords(props, records
                , connection)).andReturn(new int[]{1});

        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        int[] results = facade.deleteLink_Changes(records);
        assertEquals(records.size(), results.length);
        for (int result : results) {
            assertEquals(1, result);
        }

    }

    @Test
    public void deleteNode_ChangesPositive() throws DataAccessException {
        final TestConnection connection = new TestConnection(ConnectionMode.ONE);
        final Properties props = new Properties();
        final List<NodeChangeRecord> records = TestDataUtil.getMultipleSelectNodeChangeTestRecords();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.deleteRecords(props, records, connection)).andReturn(new int[]{1, 1});
        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        int[] results = facade.deleteNode_Changes(records);
        assertEquals(records.size(), results.length);
        for (int result : results) {
            assertEquals(1, result);
        }
    }

    @Test
    public void updateLink_ChangesPositive() throws DataAccessException {
        final TestConnection connection = new TestConnection(ConnectionMode.ONE);
        final Properties props = new Properties();
        List<LinkChangeRecord> records = new ArrayList<>();
        records.add(TestDataUtil.getSingleSelectLinkChangeTestRecord());
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records, Tables.LINK_CHANGE
                , connection)).andReturn(new int[]{1});

        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        int[] results = facade.updateLink_Changes(records);
        assertEquals(records.size(), results.length);
        for (int result : results) {
            assertEquals(1, result);
        }
    }

    @Test
    public void updateNode_ChangesPositive() throws DataAccessException {
        final TestConnection connection = new TestConnection(ConnectionMode.ONE);
        final Properties props = new Properties();
        final List<NodeChangeRecord> records = TestDataUtil.getMultipleSelectNodeChangeTestRecords();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records, Tables.NODE_CHANGE, connection)).andReturn(new int[]{1, 1});
        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        int[] results = facade.updateNode_Changes(records);
        assertEquals(records.size(), results.length);
        for (int result : results) {
            assertEquals(1, result);
        }
    }

    @Test
    public void updateChangesetPositive() throws DataAccessException {
        final TestConnection connection = new TestConnection(ConnectionMode.ONE);
        final Properties props = new Properties();
        final ChangesetRecord record = TestDataUtil.getSingleSelectChangesetTestRecord();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.updateRecord(props, record, connection)).andReturn(1);
        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        int result = facade.updateChangeset(record);
        assertEquals(1, result);
    }

    @Test
    public void insertChangesetPositive() throws DataAccessException {
        final TestConnection connection = new TestConnection(ConnectionMode.ONE);
        final Properties props = new Properties();
        final ChangesetRecord record = TestDataUtil.getSingleSelectChangesetTestRecord();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertRecord(props, record, Tables.CHANGESET, Arrays.asList(Tables.CHANGESET.ID), connection))
                .andReturn(TestDataUtil.getChangesetIDReturnRecord());
        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        long result = facade.insertChangeset(record);
        assertEquals(1, result);
    }

    @Test
    public void deleteChangesetPositive() throws DataAccessException {
        final TestConnection connection = new TestConnection(ConnectionMode.ONE);
        final Properties props = new Properties();
        final ChangesetRecord record = TestDataUtil.getSingleSelectChangesetTestRecord();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.deleteRecord(props, record, connection)).andReturn(1);
        replay(DataAccessUtil.class);

        DataAccessLogic facade = new DataAccessLogic(props, connection);
        int result = facade.deleteChangeset(record);
        assertEquals(1, result);
    }

    @Test
    public void testGetNodesFromIdsPositive() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.MULTIPLE));
        List<String> nodeIds = new ArrayList<>();
        nodeIds.add("N1");
        nodeIds.add("N2");
        Result result = facade.getNodesFromIds(nodeIds, 1);
        for (int i = 0; i < result.size(); i++) {
            Record nodeRecord = (Record) result.get(i);
            NodeRecord expectedRecord = TestDataUtil.getMultipleSelectNodeTestRecords().get(i);
            for (Field<?> field : nodeRecord.fields()) {
                assertEquals(expectedRecord.getValue(field), nodeRecord.getValue(field));
            }

        }

    }

    @Test(expected = DataAccessException.class)
    public void testGetNodesFromIdsNegative() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.ERROR));
        List<String> nodeIds = new ArrayList<>();
        nodeIds.add("N1");
        nodeIds.add("N2");
        facade.getNodesFromIds(nodeIds, 1);
    }

    @Test(expected = DataAccessException.class)
    public void testGetNodesFromIdsNoConnection() throws DataAccessException {
        DataAccessLogic facade = new DataAccessLogic(new Properties(), new TestConnection(ConnectionMode.NOCONNECTION));
        List<String> nodeIds = new ArrayList<>();
        nodeIds.add("N1");
        nodeIds.add("N2");
        facade.getNodesFromIds(nodeIds, 1);
    }
}
