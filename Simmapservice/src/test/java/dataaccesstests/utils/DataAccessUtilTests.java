package dataaccesstests.utils;

import dataaccess.database.Tables;
import dataaccess.database.tables.records.LinkRecord;
import dataaccess.database.tables.records.NodeRecord;
import dataaccess.utils.DataAccessUtil;
import org.geotools.data.DataAccess;
import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.junit.Ignore;
import org.junit.Test;
import testenvironment.*;

import java.sql.Connection;
import java.sql.SQLException;
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
    public void testGetRecordsPositiveSelect(){
        Result<LinkRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.LINK, new TestConnection(ConnectionMode.ONE));
        assertEquals(1, records.size());
        assertEquals(TestDataUtil.getSingleSelectLinkTestRecord().valuesRow(), records.get(0).valuesRow());
    }

    @Test
    public void testGetMultipleRecordsPositiveSelect(){
        Result<NodeRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.NODE, new TestConnection(ConnectionMode.MULTIPLE));
        assertEquals(TestDataUtil.getMultipleSelectNodeTestRecords().size(), records.size());
        assertArrayEquals(TestDataUtil.getMultipleSelectNodeTestRecords().toArray(), records.toArray());
    }

    @Test(expected = DataAccessException.class)
    public void testGetRecordsNegativSelect(){
        DataAccessUtil.getRecords(new Properties(), new NotExistingTable("NotExisting"), new TestConnection(ConnectionMode.NONE));
        fail();
    }

    @Test
    public void testGetRecordsNotFound(){
        Result<LinkRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.LINK, new TestConnection(ConnectionMode.NONE));
        assertEquals(0, records.size());
    }

    @Test
    public void testGetRecordsNoConnection(){
        Result<LinkRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.LINK, new TestConnection(ConnectionMode.NOCONNECTION));
        assertNull(records);
    }
    //endregion

    //region insertOrUpdate Tests

    @Test
    public void insertSingleRecordArrayPositive(){
        Record[] records = new Record[]{TestDataUtil.getSingleSelectLinkTestRecord()};
        int[] results = DataAccessUtil.insertOrUpdate(new Properties(), records,Tables.LINK, new TestConnection(ConnectionMode.ONE) );
        assertEquals(1, results.length);
        assertEquals(1, results[0]);
    }

    @Test(expected = SQLException.class) @Ignore("Exception Handling not ready yet")
    public void insertSingleRecordArrayNegative(){
        Record[] records = new Record[]{TestDataUtil.getSingleSelectLinkTestRecord()};
        DataAccessUtil.insertOrUpdate(new Properties(), records,Tables.LINK, new TestConnection(ConnectionMode.ERROR) );
    }

    @Test
    public void insertMultipleRecordArrayPositive(){
        int[] results = DataAccessUtil.insertOrUpdate(new Properties(), TestDataUtil.getMultipleInsertLinksTestRecords(),Tables.LINK, new TestConnection(ConnectionMode.MULTIPLE) );
        assertEquals(2, results.length);
        assertEquals(1, results[0]);
    }

    @Test
    public void insertSingleRecordListPositive(){
        List<LinkRecord> records = new ArrayList(Arrays.asList(TestDataUtil.getSingleSelectLinkTestRecord()));
        int[] results = DataAccessUtil.insertOrUpdate(new Properties(), records,Tables.LINK, new TestConnection(ConnectionMode.ONE) );
        assertEquals(1, results.length);
        assertEquals(1, results[0]);
    }

    @Test(expected = SQLException.class) @Ignore("Exception Handling not ready yet")
    public void insertSingleRecordListNegative(){
        List<LinkRecord> records = new ArrayList(Arrays.asList(TestDataUtil.getSingleSelectLinkTestRecord()));
        DataAccessUtil.insertOrUpdate(new Properties(), records,Tables.LINK, new TestConnection(ConnectionMode.ERROR) );
    }

    @Test
    public void insertMultipleRecordListPositive(){
        List<LinkRecord> records = new ArrayList(Arrays.asList(TestDataUtil.getMultipleInsertLinksTestRecords()));
        int[] results = DataAccessUtil.insertOrUpdate(new Properties(), records,Tables.LINK, new TestConnection(ConnectionMode.MULTIPLE) );
        assertEquals(2, results.length);
        assertEquals(1, results[0]);
    }

    //endregion

    //region deleteRecords Tests

    @Test
    public void deleteRecordsSinglePositive(){

        int result = DataAccessUtil.deleteRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.ONE));
        assertEquals(1, result);
    }

    @Test(expected = SQLException.class) @Ignore("Exception Handling not ready")
    public void deleteRecordsSingleNegative(){
        DataAccessUtil.deleteRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.ERROR));
    }

    @Test
    public void deleteRecordsMultiplePositive(){
        int[] results = DataAccessUtil.deleteRecords(new Properties(), TestDataUtil.getMultipleSelectNodeTestRecords(), new TestConnection(ConnectionMode.MULTIPLE));
        assertEquals(TestDataUtil.getMultipleSelectNodeTestRecords().size(), results.length);
        for(int result : results){
            assertEquals(1, result);
        }
    }

    //endregion

    //region updateRecord Tests

    @Test
    public void updateRecordSinglePositive(){
        int result = DataAccessUtil.updateRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.ONE));
        assertEquals(1, result);
    }

    @Test(expected = SQLException.class) @Ignore("Exception Handling not ready yet")
    public void updateRecordSingleNegative(){
        DataAccessUtil.updateRecord(new Properties(), TestDataUtil.getSingleSelectLinkTestRecord(), new TestConnection(ConnectionMode.ERROR));
    }

    //endregion

    //region insertRecord Tests

    @Test
    public void insertRecordSinglePositive(){
        LinkRecord result = (LinkRecord) DataAccessUtil.insertRecord(new Properties(), TestDataUtil.getSingleInsertLinkTestRecord(), Tables.LINK, Arrays.asList(Tables.LINK.ID), new TestConnection(ConnectionMode.ONE));
        assertEquals("1", result.getId());
    }

    @Test(expected = SQLException.class) @Ignore("Exception Handling not ready yet")
    public void insertRecordSingleNegative(){
        DataAccessUtil.insertRecord(new Properties(), TestDataUtil.getSingleInsertLinkTestRecord(), Tables.LINK, Arrays.asList(Tables.LINK.ID), new TestConnection(ConnectionMode.ONE));
    }

    //endregion
}
