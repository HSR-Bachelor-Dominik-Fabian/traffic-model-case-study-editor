package dataaccesstests.utils;

import dataaccess.database.Tables;
import dataaccess.database.tables.records.LinkRecord;
import dataaccess.database.tables.records.NodeRecord;
import dataaccess.utils.DataAccessUtil;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.junit.Test;
import org.postgresql.util.PSQLException;
import testenvironment.*;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created by dohee on 11.05.2016.
 */
public class DataAccessUtilTests {

    //region GetRecord Tests
    @Test
    public void testGetRecordsPositiveSelect(){
        Result<LinkRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.LINK, new TestConnection(SelectMode.GETONE));
        assertEquals(1, records.size());
        assertEquals(TestDataUtil.getSingleSelectLinkTestRecord().valuesRow(), records.get(0).valuesRow());
    }

    @Test
    public void testGetMultipleRecordsPositiveSelect(){
        Result<NodeRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.NODE, new TestConnection(SelectMode.GETMULTIPLE));
        assertEquals(TestDataUtil.getMultipleSelectNodeTestRecords().size(), records.size());
        assertArrayEquals(TestDataUtil.getMultipleSelectNodeTestRecords().toArray(), records.toArray());
    }

    @Test(expected = DataAccessException.class)
    public void testGetRecordsNegativSelect(){
        DataAccessUtil.getRecords(new Properties(), new NotExistingTable("NotExisting"), new TestConnection(SelectMode.GETNONE));
        fail();
    }

    @Test
    public void testGetRecordsNotFound(){
        Result<LinkRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.LINK, new TestConnection(SelectMode.GETNONE));
        assertEquals(0, records.size());
    }

    @Test
    public void testGetRecordsNoConnection(){
        Result<LinkRecord> records = DataAccessUtil.getRecords(new Properties(), Tables.LINK, new TestConnection(SelectMode.NOCONNECTION));
        assertNull(records);
    }
    //endregion

    //region insertOrUpdate Tests



    //endregion
}
