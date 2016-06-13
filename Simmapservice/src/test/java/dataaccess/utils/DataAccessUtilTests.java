package dataaccess.utils;

import dataaccess.DataAccessException;
import dataaccess.database.Tables;
import dataaccess.database.tables.records.LinkRecord;
import dataaccess.database.tables.records.NodeRecord;
import org.jooq.Record;
import org.jooq.Result;
import org.junit.Test;
import testenvironment.ConnectionMode;
import testenvironment.NotExistingTable;
import testenvironment.TestConnection;
import testenvironment.TestDataUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class DataAccessUtilTests {

    //region GetRecord Tests
    @Test
    public void testGetRecordsPositiveSelect() throws DataAccessException {
        Result<LinkRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.LINK, new TestConnection(ConnectionMode.ONE));
        assertEquals(1, records.size());
        assertEquals(TestDataUtil.getSingleSelectLinkTestRecord().valuesRow(), records.get(0).valuesRow());
    }

    @Test
    public void testGetMultipleRecordsPositiveSelect() throws DataAccessException {
        Result<NodeRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.NODE, new TestConnection(ConnectionMode.MULTIPLE));
        assertEquals(TestDataUtil.getMultipleSelectNodeTestRecords().size(), records.size());
        assertArrayEquals(TestDataUtil.getMultipleSelectNodeTestRecords().toArray(), records.toArray());
    }

    @Test(expected = DataAccessException.class)
    public void testGetRecordsNegativSelect() throws DataAccessException {
        DataAccessUtil.getRecords(new Properties(), new NotExistingTable("NotExisting"), new TestConnection(ConnectionMode.NONE));
        fail();
    }

    @Test
    public void testGetRecordsNotFound() throws DataAccessException {
        Result<LinkRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.LINK, new TestConnection(ConnectionMode.NONE));
        assertEquals(0, records.size());
    }

    @Test(expected = DataAccessException.class)
    public void testGetRecordsNoConnection() throws DataAccessException {
        Result<LinkRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.LINK, new TestConnection(ConnectionMode.NOCONNECTION));
        assertNull(records);
    }
    //endregion

    //region insertOrUpdate Tests

    @Test
    public void insertSingleRecordArrayPositive() throws DataAccessException {
        Record[] records = new Record[]{TestDataUtil.getSingleSelectLinkTestRecord()};
        int[] results = DataAccessUtil.insertOrUpdate(new Properties(), records, Tables.LINK, new TestConnection(ConnectionMode.ONE));
        assertEquals(1, results.length);
        assertEquals(1, results[0]);
    }

    @Test(expected = DataAccessException.class)
    public void insertSingleRecordArrayNegative() throws DataAccessException {
        Record[] records = new Record[]{TestDataUtil.getSingleSelectLinkTestRecord()};
        DataAccessUtil.insertOrUpdate(new Properties(), records, Tables.LINK, new TestConnection(ConnectionMode.ERROR));
    }

    @Test(expected = DataAccessException.class)
    public void insertSingleRecordArrayNoConnection() throws DataAccessException {
        Record[] records = new Record[]{TestDataUtil.getSingleSelectLinkTestRecord()};
        DataAccessUtil.insertOrUpdate(new Properties(), records, Tables.LINK, new TestConnection(ConnectionMode.NOCONNECTION));
    }

    @Test
    public void insertMultipleRecordArrayPositive() throws DataAccessException {
        int[] results = DataAccessUtil.insertOrUpdate(new Properties(), TestDataUtil.getMultipleInsertLinksTestRecords(), Tables.LINK, new TestConnection(ConnectionMode.MULTIPLE));
        assertEquals(2, results.length);
        assertEquals(1, results[0]);
    }

    @Test
    public void insertSingleRecordListPositive() throws DataAccessException {
        List<LinkRecord> records = new ArrayList(Arrays.asList(TestDataUtil.getSingleSelectLinkTestRecord()));
        int[] results = DataAccessUtil.insertOrUpdate(new Properties(), records, Tables.LINK, new TestConnection(ConnectionMode.ONE));
        assertEquals(1, results.length);
        assertEquals(1, results[0]);
    }

    @Test(expected = DataAccessException.class)
    public void insertSingleRecordListNegative() throws DataAccessException {
        List<LinkRecord> records = new ArrayList(Arrays.asList(TestDataUtil.getSingleSelectLinkTestRecord()));
        DataAccessUtil.insertOrUpdate(new Properties(), records, Tables.LINK, new TestConnection(ConnectionMode.ERROR));
    }

    @Test(expected = DataAccessException.class)
    public void insertSingleRecordListNoConnection() throws DataAccessException {
        List<LinkRecord> records = new ArrayList(Arrays.asList(TestDataUtil.getSingleSelectLinkTestRecord()));
        DataAccessUtil.insertOrUpdate(new Properties(), records, Tables.LINK, new TestConnection(ConnectionMode.NOCONNECTION));
    }

    @Test
    public void insertMultipleRecordListPositive() throws DataAccessException {
        List<LinkRecord> records = new ArrayList(Arrays.asList(TestDataUtil.getMultipleInsertLinksTestRecords()));
        int[] results = DataAccessUtil.insertOrUpdate(new Properties(), records, Tables.LINK, new TestConnection(ConnectionMode.MULTIPLE));
        assertEquals(2, results.length);
        assertEquals(1, results[0]);
    }

    //endregion

    //region deleteRecords Tests

    @Test
    public void deleteRecordsSinglePositive() throws DataAccessException {

        int result = DataAccessUtil.deleteRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.ONE));
        assertEquals(1, result);
    }

    @Test(expected = DataAccessException.class)
    public void deleteRecordsSingleNegative() throws DataAccessException {
        DataAccessUtil.deleteRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.ERROR));
    }

    @Test(expected = DataAccessException.class)
    public void deleteRecordsSingleNoConnection() throws DataAccessException {
        DataAccessUtil.deleteRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.NOCONNECTION));
    }

    @Test
    public void deleteRecordsMultiplePositive() throws DataAccessException {
        int[] results = DataAccessUtil.deleteRecords(new Properties(), TestDataUtil.getMultipleSelectNodeTestRecords(), new TestConnection(ConnectionMode.MULTIPLE));
        assertEquals(TestDataUtil.getMultipleSelectNodeTestRecords().size(), results.length);
        for (int result : results) {
            assertEquals(1, result);
        }
    }

    @Test(expected = DataAccessException.class)
    public void deleteRecordsMultipleNegative() throws DataAccessException {
        DataAccessUtil.deleteRecords(new Properties(), TestDataUtil.getMultipleSelectNodeTestRecords(), new TestConnection(ConnectionMode.ERROR));
    }

    @Test(expected = DataAccessException.class)
    public void deleteRecordsMultipleNoConnection() throws DataAccessException {
        DataAccessUtil.deleteRecords(new Properties(), TestDataUtil.getMultipleSelectNodeTestRecords(), new TestConnection(ConnectionMode.NOCONNECTION));
    }

    //endregion

    //region updateRecord Tests

    @Test
    public void updateRecordSinglePositive() throws DataAccessException {
        int result = DataAccessUtil.updateRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.ONE));
        assertEquals(1, result);
    }

    @Test(expected = DataAccessException.class)
    public void updateRecordSingleNegative() throws DataAccessException {
        DataAccessUtil.updateRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.ERROR));
    }

    @Test(expected = DataAccessException.class)
    public void updateRecordSingleNoConnection() throws DataAccessException {
        DataAccessUtil.updateRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.NOCONNECTION));
    }

    //endregion

    //region insertRecord Tests

    @Test
    public void insertRecordSinglePositive() throws DataAccessException {
        LinkRecord result = (LinkRecord) DataAccessUtil.insertRecord(new Properties(), TestDataUtil.getSingleInsertLinkTestRecord(), Tables.LINK, Arrays.asList(Tables.LINK.ID), new TestConnection(ConnectionMode.ONE));
        assertEquals("1", result.getId());
    }

    @Test(expected = DataAccessException.class)
    public void insertRecordSingleNegative() throws DataAccessException {
        DataAccessUtil.insertRecord(new Properties(), TestDataUtil.getSingleInsertLinkTestRecord(), Tables.LINK, Arrays.asList(Tables.LINK.ID), new TestConnection(ConnectionMode.ERROR));
    }

    @Test(expected = DataAccessException.class)
    public void insertRecordSingleNoConnection() throws DataAccessException {
        DataAccessUtil.insertRecord(new Properties(), TestDataUtil.getSingleInsertLinkTestRecord(), Tables.LINK, Arrays.asList(Tables.LINK.ID), new TestConnection(ConnectionMode.NOCONNECTION));
    }
    //endregion
}
