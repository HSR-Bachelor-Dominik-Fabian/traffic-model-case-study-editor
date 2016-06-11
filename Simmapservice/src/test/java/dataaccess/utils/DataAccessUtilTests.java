package dataaccess.utils;

import dataaccess.database.Tables;
import dataaccess.database.tables.records.LinkRecord;
import dataaccess.database.tables.records.NodeRecord;
import org.jooq.*;
import dataaccess.expection.DataAccessLayerException;
import org.junit.Test;
import testenvironment.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created by dohee on 11.05.2016.
 */
public class DataAccessUtilTests {

    //region GetRecord Tests
    @Test
    public void testGetRecordsPositiveSelect() throws DataAccessLayerException {
        Result<LinkRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.LINK, new TestConnection(ConnectionMode.ONE));
        assertEquals(1, records.size());
        assertEquals(TestDataUtil.getSingleSelectLinkTestRecord().valuesRow(), records.get(0).valuesRow());
    }

    @Test
    public void testGetMultipleRecordsPositiveSelect() throws DataAccessLayerException {
        Result<NodeRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.NODE, new TestConnection(ConnectionMode.MULTIPLE));
        assertEquals(TestDataUtil.getMultipleSelectNodeTestRecords().size(), records.size());
        assertArrayEquals(TestDataUtil.getMultipleSelectNodeTestRecords().toArray(), records.toArray());
    }

    @Test(expected = DataAccessLayerException.class)
    public void testGetRecordsNegativSelect() throws DataAccessLayerException {
        DataAccessUtil.getRecords(new Properties(), new NotExistingTable("NotExisting"), new TestConnection(ConnectionMode.NONE));
        fail();
    }

    @Test
    public void testGetRecordsNotFound() throws DataAccessLayerException {
        Result<LinkRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.LINK, new TestConnection(ConnectionMode.NONE));
        assertEquals(0, records.size());
    }

    @Test(expected = DataAccessLayerException.class)
    public void testGetRecordsNoConnection() throws DataAccessLayerException {
        Result<LinkRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.LINK, new TestConnection(ConnectionMode.NOCONNECTION));
        assertNull(records);
    }
    //endregion

    //region insertOrUpdate Tests

    @Test
    public void insertSingleRecordArrayPositive() throws DataAccessLayerException {
        Record[] records = new Record[]{TestDataUtil.getSingleSelectLinkTestRecord()};
        int[] results = DataAccessUtil.insertOrUpdate(new Properties(), records, Tables.LINK, new TestConnection(ConnectionMode.ONE));
        assertEquals(1, results.length);
        assertEquals(1, results[0]);
    }

    @Test(expected = DataAccessLayerException.class)
    public void insertSingleRecordArrayNegative() throws DataAccessLayerException {
        Record[] records = new Record[]{TestDataUtil.getSingleSelectLinkTestRecord()};
        DataAccessUtil.insertOrUpdate(new Properties(), records, Tables.LINK, new TestConnection(ConnectionMode.ERROR));
    }

    @Test(expected = DataAccessLayerException.class)
    public void insertSingleRecordArrayNoConnection() throws DataAccessLayerException {
        Record[] records = new Record[]{TestDataUtil.getSingleSelectLinkTestRecord()};
        DataAccessUtil.insertOrUpdate(new Properties(), records, Tables.LINK, new TestConnection(ConnectionMode.NOCONNECTION));
    }

    @Test
    public void insertMultipleRecordArrayPositive() throws DataAccessLayerException {
        int[] results = DataAccessUtil.insertOrUpdate(new Properties(), TestDataUtil.getMultipleInsertLinksTestRecords(), Tables.LINK, new TestConnection(ConnectionMode.MULTIPLE));
        assertEquals(2, results.length);
        assertEquals(1, results[0]);
    }

    @Test
    public void insertSingleRecordListPositive() throws DataAccessLayerException {
        List<LinkRecord> records = new ArrayList(Arrays.asList(TestDataUtil.getSingleSelectLinkTestRecord()));
        int[] results = DataAccessUtil.insertOrUpdate(new Properties(), records, Tables.LINK, new TestConnection(ConnectionMode.ONE));
        assertEquals(1, results.length);
        assertEquals(1, results[0]);
    }

    @Test(expected = DataAccessLayerException.class)
    public void insertSingleRecordListNegative() throws DataAccessLayerException {
        List<LinkRecord> records = new ArrayList(Arrays.asList(TestDataUtil.getSingleSelectLinkTestRecord()));
        DataAccessUtil.insertOrUpdate(new Properties(), records, Tables.LINK, new TestConnection(ConnectionMode.ERROR));
    }

    @Test(expected = DataAccessLayerException.class)
    public void insertSingleRecordListNoConnection() throws DataAccessLayerException {
        List<LinkRecord> records = new ArrayList(Arrays.asList(TestDataUtil.getSingleSelectLinkTestRecord()));
        DataAccessUtil.insertOrUpdate(new Properties(), records, Tables.LINK, new TestConnection(ConnectionMode.NOCONNECTION));
    }

    @Test
    public void insertMultipleRecordListPositive() throws DataAccessLayerException {
        List<LinkRecord> records = new ArrayList(Arrays.asList(TestDataUtil.getMultipleInsertLinksTestRecords()));
        int[] results = DataAccessUtil.insertOrUpdate(new Properties(), records, Tables.LINK, new TestConnection(ConnectionMode.MULTIPLE));
        assertEquals(2, results.length);
        assertEquals(1, results[0]);
    }

    //endregion

    //region deleteRecords Tests

    @Test
    public void deleteRecordsSinglePositive() throws DataAccessLayerException {

        int result = DataAccessUtil.deleteRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.ONE));
        assertEquals(1, result);
    }

    @Test(expected = DataAccessLayerException.class)
    public void deleteRecordsSingleNegative() throws DataAccessLayerException {
        DataAccessUtil.deleteRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.ERROR));
    }

    @Test(expected = DataAccessLayerException.class)
    public void deleteRecordsSingleNoConnection() throws DataAccessLayerException {
        DataAccessUtil.deleteRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.NOCONNECTION));
    }

    @Test
    public void deleteRecordsMultiplePositive() throws DataAccessLayerException {
        int[] results = DataAccessUtil.deleteRecords(new Properties(), TestDataUtil.getMultipleSelectNodeTestRecords(), new TestConnection(ConnectionMode.MULTIPLE));
        assertEquals(TestDataUtil.getMultipleSelectNodeTestRecords().size(), results.length);
        for (int result : results) {
            assertEquals(1, result);
        }
    }

    @Test(expected = DataAccessLayerException.class)
    public void deleteRecordsMultipleNegative() throws DataAccessLayerException {
        DataAccessUtil.deleteRecords(new Properties(), TestDataUtil.getMultipleSelectNodeTestRecords(), new TestConnection(ConnectionMode.ERROR));
    }

    @Test(expected = DataAccessLayerException.class)
    public void deleteRecordsMultipleNoConnection() throws DataAccessLayerException {
        DataAccessUtil.deleteRecords(new Properties(), TestDataUtil.getMultipleSelectNodeTestRecords(), new TestConnection(ConnectionMode.NOCONNECTION));
    }

    //endregion

    //region updateRecord Tests

    @Test
    public void updateRecordSinglePositive() throws DataAccessLayerException {
        int result = DataAccessUtil.updateRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.ONE));
        assertEquals(1, result);
    }

    @Test(expected = DataAccessLayerException.class)
    public void updateRecordSingleNegative() throws DataAccessLayerException {
        DataAccessUtil.updateRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.ERROR));
    }

    @Test(expected = DataAccessLayerException.class)
    public void updateRecordSingleNoConnection() throws DataAccessLayerException {
        DataAccessUtil.updateRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.NOCONNECTION));
    }

    //endregion

    //region insertRecord Tests

    @Test
    public void insertRecordSinglePositive() throws DataAccessLayerException {
        LinkRecord result = (LinkRecord) DataAccessUtil.insertRecord(new Properties(), TestDataUtil.getSingleInsertLinkTestRecord(), Tables.LINK, Arrays.asList(Tables.LINK.ID), new TestConnection(ConnectionMode.ONE));
        assertEquals("1", result.getId());
    }

    @Test(expected = DataAccessLayerException.class)
    public void insertRecordSingleNegative() throws DataAccessLayerException {
        DataAccessUtil.insertRecord(new Properties(), TestDataUtil.getSingleInsertLinkTestRecord(), Tables.LINK, Arrays.asList(Tables.LINK.ID), new TestConnection(ConnectionMode.ERROR));
    }

    @Test(expected = DataAccessLayerException.class)
    public void insertRecordSingleNoConnection() throws DataAccessLayerException {
        DataAccessUtil.insertRecord(new Properties(), TestDataUtil.getSingleInsertLinkTestRecord(), Tables.LINK, Arrays.asList(Tables.LINK.ID), new TestConnection(ConnectionMode.NOCONNECTION));
    }
    //endregion
}
