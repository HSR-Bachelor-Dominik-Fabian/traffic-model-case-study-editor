package testenvironment;

import dataaccess.database.Tables;
import dataaccess.database.tables.records.LinkRecord;
import dataaccess.database.tables.records.NodeRecord;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by dohee on 11.05.2016.
 */
public class TestDataUtil {
    public static LinkRecord getSingleSelectLinkTestRecord(){
        LinkRecord record = Tables.LINK.newRecord();
        record.setId("1");
        record.setNetworkid(1);
        record.setQuadkey("123123123");
        record.setCapacity(new BigDecimal(1000));
        record.setFreespeed(new BigDecimal(33.33));
        record.setPermlanes(new BigDecimal(2));
        record.setLength(new BigDecimal(1200));
        record.setLastmodified(new Date(0));
        record.setFrom("N1");
        record.setTo("N2");
        record.setLat1(new BigDecimal(12));
        record.setLong1(new BigDecimal(12));
        record.setLat2(new BigDecimal(23));
        record.setLong2(new BigDecimal(23));
        record.setModes("car");
        record.setOneway(true);
        record.setMinlevel(12);
        return record;
    }

    public static LinkRecord getSingleInsertLinkTestRecord(){
        LinkRecord record = Tables.LINK.newRecord();
        record.setId(null);
        record.setNetworkid(1);
        record.setQuadkey("123123123");
        record.setCapacity(new BigDecimal(1000));
        record.setFreespeed(new BigDecimal(33.33));
        record.setPermlanes(new BigDecimal(2));
        record.setLength(new BigDecimal(1200));
        record.setLastmodified(new Date(0));
        record.setFrom("N1");
        record.setTo("N2");
        record.setLat1(new BigDecimal(12));
        record.setLong1(new BigDecimal(12));
        record.setLat2(new BigDecimal(23));
        record.setLong2(new BigDecimal(23));
        record.setModes("car");
        record.setOneway(true);
        record.setMinlevel(12);
        return record;
    }

    public static LinkRecord[] getMultipleInsertLinksTestRecords(){


        LinkRecord record = Tables.LINK.newRecord();
        record.setNetworkid(1);
        record.setQuadkey("123123123");
        record.setCapacity(new BigDecimal(1000));
        record.setFreespeed(new BigDecimal(33.33));
        record.setPermlanes(new BigDecimal(2));
        record.setLength(new BigDecimal(1200));
        record.setLastmodified(new Date(0));
        record.setFrom("N1");
        record.setTo("N2");
        record.setLat1(new BigDecimal(12));
        record.setLong1(new BigDecimal(12));
        record.setLat2(new BigDecimal(23));
        record.setLong2(new BigDecimal(23));
        record.setModes("car");
        record.setOneway(true);
        record.setMinlevel(12);

        LinkRecord record2 = Tables.LINK.newRecord();
        record2.setNetworkid(1);
        record2.setQuadkey("123123123");
        record2.setCapacity(new BigDecimal(1000));
        record2.setFreespeed(new BigDecimal(33.33));
        record2.setPermlanes(new BigDecimal(2));
        record2.setLength(new BigDecimal(1200));
        record2.setLastmodified(new Date(0));
        record2.setFrom("N1");
        record2.setTo("N2");
        record2.setLat1(new BigDecimal(12));
        record2.setLong1(new BigDecimal(12));
        record2.setLat2(new BigDecimal(23));
        record2.setLong2(new BigDecimal(23));
        record2.setModes("car");
        record2.setOneway(true);
        record2.setMinlevel(12);

        return new LinkRecord[]{record, record2};
    }

    public static List<NodeRecord> getMultipleSelectNodeTestRecords() {
        List<NodeRecord> output = new ArrayList();

        NodeRecord record = Tables.NODE.newRecord();
        record.setId("N1");
        record.setNetworkid(1);
        record.setQuadkey("123");
        record.setX(new BigDecimal(12));
        record.setY(new BigDecimal(23));
        record.setLat(new BigDecimal(12));
        record.setLong(new BigDecimal(23));

        output.add(record);

        NodeRecord record2 = Tables.NODE.newRecord();
        record2.setId("N2");
        record2.setNetworkid(1);
        record2.setQuadkey("123323");
        record2.setX(new BigDecimal(12));
        record2.setY(new BigDecimal(23));
        record2.setLat(new BigDecimal(12));
        record2.setLong(new BigDecimal(23));

        output.add(record2);

        return output;
    }

    public static Properties getTestProperties(){
        Properties props = new Properties();

        props.setProperty("psqlpath", getTestPSQLPath());
        props.setProperty("psqluser", getTestUsername());
        props.setProperty("psqlpassword", getTestPassword());

        return props;
    }

    public static String getTestPSQLPath(){
        return "jdbc:postgresql://localhost:1234/notworkingdata";
    }

    public static String getTestUsername(){
        return "testuser";
    }

    public static String getTestPassword(){
        return "testpassword";
    }

    public static SQLException getInsertSQLException() {return new SQLException("Insert not possible during Test");}
    public static SQLException getUpdateSQLException() {return new SQLException("Update not possible during Test");}
}