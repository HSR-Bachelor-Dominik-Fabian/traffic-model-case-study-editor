package dataaccesstests;

import dataaccess.SimmapDataAccessFacade;
import dataaccess.database.Tables;
import dataaccess.database.tables.records.*;
import dataaccess.utils.DataAccessUtil;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Ignore;
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
import static org.powermock.api.easymock.PowerMock.*;

/**
 * Created by dohee on 18.05.2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({DataAccessUtil.class})
public class SimmapDataAccessFacadeTests {

    @Test
    public void setNetworkMultiplePositive() {
        final TestConnection connection = new TestConnection(ConnectionMode.MULTIPLE);
        final Properties props = new Properties();
        final NetworkRecord[] records = TestDataUtil.getTestInsertNetworks();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records
                , Tables.NETWORK, connection)).andReturn(new int[]{1, 1});

        replay(DataAccessUtil.class);

        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(props, connection);
        int[] results = facade.setNetwork(records);
        assertEquals(records.length, results.length);
        for (int result : results) {
            assertEquals(1, result);
        }

    }

    @Test
    @Ignore("Exception Handling not ready yet")
    public void setNetworkMultipleNegative() {

    }

    @Test
    public void setNodeMultiplePositive() {
        final TestConnection connection = new TestConnection(ConnectionMode.MULTIPLE);
        final Properties props = new Properties();
        final NodeRecord[] records = TestDataUtil.getMultipleInsertNodeTestRecords();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records
                , Tables.NODE, connection)).andReturn(new int[]{1, 1});

        replay(DataAccessUtil.class);

        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(props, connection);
        int[] results = facade.setNode(records);
        assertEquals(records.length, results.length);
        for (int result : results) {
            assertEquals(1, result);
        }

    }

    @Test
    @Ignore("Exception Handling not ready yet")
    public void setNodeMultipleNegative() {

    }

    @Test
    public void setLinkPositive() {
        final TestConnection connection = new TestConnection(ConnectionMode.MULTIPLE);
        final Properties props = new Properties();
        final LinkRecord[] records = TestDataUtil.getMultipleInsertLinksTestRecords();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records
                , Tables.LINK, connection)).andReturn(new int[]{1, 1});
        replay(DataAccessUtil.class);

        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(props, connection);
        int[] results = facade.setLink(records);
        assertEquals(records.length, results.length);
        for (int result : results) {
            assertEquals(1, result);
        }
    }

    @Test
    @Ignore("Exception Handling not ready yet")
    public void setLinkNegative() {

    }

    @Test
    public void setNetworkOptionsPositive() {
        final TestConnection connection = new TestConnection(ConnectionMode.MULTIPLE);
        final Properties props = new Properties();
        final NetworkOptionsRecord[] records = TestDataUtil.getTestInsertNetworkOptions();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records
                , Tables.NETWORK_OPTIONS, connection)).andReturn(new int[]{1, 1, 1});
        replay(DataAccessUtil.class);

        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(props, connection);
        int[] results = facade.setNetworkOptions(records);
        assertEquals(records.length, results.length);
        for (int result : results) {
            assertEquals(1, result);
        }
    }

    @Test
    @Ignore("Exception Handling not ready yet")
    public void setNetworkOptionsNegative() {

    }

    @Test
    public void getAllNodesTest() {
        DSLContext ctx = DSL.using(SQLDialect.POSTGRES);
        Result<NodeRecord> expectedResult = ctx.newResult(Tables.NODE);
        expectedResult.addAll(TestDataUtil.getMultipleSelectNodeTestRecords());
        final TestConnection connection = new TestConnection(ConnectionMode.MULTIPLE);
        final Properties props = new Properties();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.getRecords(props, Tables.NODE, connection)).andReturn(expectedResult);
        replay(DataAccessUtil.class);

        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(props, connection);
        Result<NodeRecord> result = facade.getAllNodes();
        assertEquals(expectedResult.size(), result.size());
        assertEquals(expectedResult, result);
    }

    @Test
    public void getAllChangesetsPerUserPositive() {
        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(new Properties(), new TestConnection(ConnectionMode.MULTIPLE));
        Result<ChangesetRecord> records = facade.getAllChangesetsPerUser(1);
        assertEquals(TestDataUtil.getMultipleSelectChangesetTestRecords().size(), records.size());
        assertArrayEquals(TestDataUtil.getMultipleSelectChangesetTestRecords().toArray(), records.toArray());
    }

    @Test
    @Ignore("Exception Handling not ready yet")
    public void getAllChangesetsPerUserNegative() {

    }

    @Test
    public void getChangesetFromNumberPositive() {
        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(new Properties(), new TestConnection(ConnectionMode.ONE));
        ChangesetRecord record = facade.getChangesetFromNumber(1);
        assertEquals(TestDataUtil.getSingleSelectChangesetTestRecord(), record);
    }

    @Test
    @Ignore("Exception Handling not ready yet")
    public void getChangesetFromNumberNegative() {

    }

    @Test
    public void getNodeFromIdPositive() {
        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(new Properties(), new TestConnection(ConnectionMode.ONE));
        NodeRecord record = facade.getNodeFromId("1");
        assertEquals(TestDataUtil.getSingleSelectNodeTestRecord(), record);
    }

    @Test
    @Ignore("Exception Handling not ready yet")
    public void getNodeFromIdNegative() {

    }

    @Test
    public void getLinkFromIdPositive() {
        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(new Properties(), new TestConnection(ConnectionMode.ONE));
        LinkRecord record = facade.getLinkFromId("1");
        LinkRecord expected = TestDataUtil.getSingleSelectLinkTestRecord();
        expected.setId("1");
        assertEquals(expected, record);
    }

    @Test
    @Ignore("Exception Handling not ready yet")
    public void getLinkFromIdNegative() {

    }

    @Test
    public void getLinkChangesfromChangesetPositive() {
        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(new Properties(), new TestConnection(ConnectionMode.ONE));
        Result<LinkChangeRecord> record = facade.getLinkChangesfromChangeset(1);
        assertEquals(1, record.size());
        assertEquals(TestDataUtil.getSingleSelectLinkChangeTestRecord(), record.get(0));
    }

    @Test
    @Ignore("Exception Handling not ready yet")
    public void getLinkChangesfromChangesetNegative() {

    }

    @Test
    public void getNodeChangefromChangesetPositive() {
        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(new Properties(), new TestConnection(ConnectionMode.MULTIPLE));
        Result<NodeChangeRecord> record = facade.getNodeChangefromChangeset(1);
        assertEquals(TestDataUtil.getMultipleSelectNodeChangeTestRecords().size(), record.size());
        List<NodeChangeRecord> expected = TestDataUtil.getMultipleSelectNodeChangeTestRecords();
        assertArrayEquals(expected.toArray(), record.toArray());
    }

    @Test
    @Ignore("Exception Handling not ready yet")
    public void getNodeChangefromChangesetNegative() {

    }

    @Test
    public void getLinkFromQuadKeyPositive() {
        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(new Properties(), new TestConnection(ConnectionMode.MULTIPLE));
        Result<Record> record = facade.getLinkFromQuadKey("123", 1, 12);
        assertEquals(TestDataUtil.getMultipleSelectLinkTestRecords().size(), record.size());
        List<Record> expected = TestDataUtil.getMultipleSelectLinkTestRecords();
        assertEquals(expected, record);
    }

    @Test
    @Ignore("Exception Handling not ready yet")
    public void getLinkFromQuadKeyNegative() {

    }

    @Test
    public void getLastModifiedQuadKeyPositive() {
        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(new Properties(), new TestConnection(ConnectionMode.MULTIPLE));
        Date record = facade.getLastModifiedQuadKey("123", 1, 12);
        Date expected = TestDataUtil.getDateLastModifiedTestRecord();
        assertEquals(expected, record);
    }

    @Test
    @Ignore("Exception Handling not ready yet")
    public void getLastModifiedQuadKeyNegative() {

    }

    @Test
    public void hasChangesetPositive() {
        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(new Properties(), new TestConnection(ConnectionMode.MULTIPLE));
        boolean record = facade.hasChangeset(1);
        assertTrue(record);
    }

    @Test
    @Ignore("Exception Handling not ready yet")
    public void hasChangesetNegative() {

    }

    @Test
    public void deleteLink_ChangesPositive() {
        final TestConnection connection = new TestConnection(ConnectionMode.ONE);
        final Properties props = new Properties();
        List<LinkChangeRecord> records = new ArrayList<>();
        records.add(TestDataUtil.getSingleSelectLinkChangeTestRecord());
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.deleteRecords(props, records
                , connection)).andReturn(new int[]{1});

        replay(DataAccessUtil.class);

        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(props, connection);
        int[] results = facade.deleteLink_Changes(records);
        assertEquals(records.size(), results.length);
        for (int result : results) {
            assertEquals(1, result);
        }

    }

    @Test
    public void deleteNode_ChangesPositive() {
        final TestConnection connection = new TestConnection(ConnectionMode.ONE);
        final Properties props = new Properties();
        final List<NodeChangeRecord> records = TestDataUtil.getMultipleSelectNodeChangeTestRecords();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.deleteRecords(props, records, connection)).andReturn(new int[]{1, 1});
        replay(DataAccessUtil.class);

        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(props, connection);
        int[] results = facade.deleteNode_Changes(records);
        assertEquals(records.size(), results.length);
        for (int result : results) {
            assertEquals(1, result);
        }
    }

    @Test
    public void updateLink_ChangesPositive() {
        final TestConnection connection = new TestConnection(ConnectionMode.ONE);
        final Properties props = new Properties();
        List<LinkChangeRecord> records = new ArrayList<>();
        records.add(TestDataUtil.getSingleSelectLinkChangeTestRecord());
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records, Tables.LINK_CHANGE
                , connection)).andReturn(new int[]{1});

        replay(DataAccessUtil.class);

        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(props, connection);
        int[] results = facade.updateLink_Changes(records);
        assertEquals(records.size(), results.length);
        for (int result : results) {
            assertEquals(1, result);
        }
    }

    @Test
    public void updateNode_ChangesPositive() {
        final TestConnection connection = new TestConnection(ConnectionMode.ONE);
        final Properties props = new Properties();
        final List<NodeChangeRecord> records = TestDataUtil.getMultipleSelectNodeChangeTestRecords();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertOrUpdate(props, records, Tables.NODE_CHANGE, connection)).andReturn(new int[]{1, 1});
        replay(DataAccessUtil.class);

        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(props, connection);
        int[] results = facade.updateNode_Changes(records);
        assertEquals(records.size(), results.length);
        for (int result : results) {
            assertEquals(1, result);
        }
    }

    @Test
    public void updateChangesetPositive(){
        final TestConnection connection = new TestConnection(ConnectionMode.ONE);
        final Properties props = new Properties();
        final ChangesetRecord record = TestDataUtil.getSingleSelectChangesetTestRecord();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.updateRecord(props, record, connection)).andReturn(1);
        replay(DataAccessUtil.class);

        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(props, connection);
        int result = facade.updateChangeset(record);
        assertEquals(1, result);
    }

    @Test
    public void insertChangesetPositive(){
        final TestConnection connection = new TestConnection(ConnectionMode.ONE);
        final Properties props = new Properties();
        final ChangesetRecord record = TestDataUtil.getSingleSelectChangesetTestRecord();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.insertRecord(props, record,Tables.CHANGESET, Arrays.asList(Tables.CHANGESET.ID) , connection))
                .andReturn(TestDataUtil.getChangesetIDReturnRecord());
        replay(DataAccessUtil.class);

        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(props, connection);
        long result = facade.insertChangeset(record);
        assertEquals(1, result);
    }

    @Test
    public void deleteChangesetPositive(){
        final TestConnection connection = new TestConnection(ConnectionMode.ONE);
        final Properties props = new Properties();
        final ChangesetRecord record = TestDataUtil.getSingleSelectChangesetTestRecord();
        mockStatic(DataAccessUtil.class);
        expect(DataAccessUtil.deleteRecord(props, record, connection)).andReturn(1);
        replay(DataAccessUtil.class);

        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(props, connection);
        int result = facade.deleteChangeset(record);
        assertEquals(1, result);
    }
}
