package testenvironment;

import dataaccess.database.Tables;
import dataaccess.database.tables.records.*;

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
    public static LinkRecord getSingleSelectLinkTestRecord() {
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

    public static LinkRecord getSingleInsertLinkTestRecord() {
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

    public static LinkRecord[] getMultipleInsertLinksTestRecords() {


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

    public static NodeRecord[] getMultipleInsertNodeTestRecords() {
        NodeRecord record = Tables.NODE.newRecord();
        record.setId(null);
        record.setNetworkid(1);
        record.setQuadkey("123");
        record.setX(new BigDecimal(12));
        record.setY(new BigDecimal(23));
        record.setLat(new BigDecimal(12));
        record.setLong(new BigDecimal(23));

        NodeRecord record2 = Tables.NODE.newRecord();
        record2.setId(null);
        record2.setNetworkid(1);
        record2.setQuadkey("123323");
        record2.setX(new BigDecimal(12));
        record2.setY(new BigDecimal(23));
        record2.setLat(new BigDecimal(12));
        record2.setLong(new BigDecimal(23));

        return new NodeRecord[]{record, record2};
    }

    public static Properties getTestProperties() {
        Properties props = new Properties();

        props.setProperty("psqlpath", getTestPSQLPath());
        props.setProperty("psqluser", getTestUsername());
        props.setProperty("psqlpassword", getTestPassword());

        return props;
    }

    public static NetworkRecord[] getTestInsertNetworks() {
        NetworkRecord record = new NetworkRecord();
        record.setId(null);
        record.setName("Network1");

        NetworkRecord record2 = new NetworkRecord();
        record2.setId(null);
        record2.setName("Network2");


        return new NetworkRecord[]{record, record2};
    }

    public static NetworkOptionsRecord[] getTestInsertNetworkOptions() {
        NetworkOptionsRecord record = new NetworkOptionsRecord();
        record.setNetworkid(1);
        record.setOptionname("TestOption");
        record.setValue("TestValue");

        NetworkOptionsRecord record2 = new NetworkOptionsRecord();
        record2.setNetworkid(1);
        record2.setOptionname("TestOption2");
        record2.setValue("TestValue2");

        NetworkOptionsRecord record3 = new NetworkOptionsRecord();
        record3.setNetworkid(1);
        record3.setOptionname("TestOption3");
        record3.setValue("TestValue3");

        return new NetworkOptionsRecord[]{record, record2, record3};
    }

    public static String getTestPSQLPath() {
        return "jdbc:postgresql://localhost:1234/notworkingdata";
    }

    public static String getTestUsername() {
        return "testuser";
    }

    public static String getTestPassword() {
        return "testpassword";
    }

    public static List<ChangesetRecord> getMultipleSelectChangesetTestRecords() {
        List<ChangesetRecord> records = new ArrayList();

        ChangesetRecord record = new ChangesetRecord();
        record.setId((long) 1);
        record.setNetworkid(1);
        record.setUsernr(1);
        record.setLastmodified(null);
        record.setName("Changeset1");
        records.add(record);

        ChangesetRecord record2 = new ChangesetRecord();
        record2.setId((long) 2);
        record2.setNetworkid(1);
        record2.setUsernr(1);
        record2.setLastmodified(null);
        record2.setName("Changeset2");
        records.add(record2);

        return records;
    }

    public static ChangesetRecord getSingleSelectChangesetTestRecord(){
        ChangesetRecord record = new ChangesetRecord();
        record.setId((long) 1);
        record.setNetworkid(1);
        record.setUsernr(1);
        record.setLastmodified(null);
        record.setName("Changeset1");
        return  record;
    }

    public static NodeRecord getSingleSelectNodeTestRecord(){
        NodeRecord record = new NodeRecord();
        record.setId("1");
        record.setNetworkid(1);
        record.setQuadkey("123");
        record.setX(new BigDecimal(12));
        record.setY(new BigDecimal(23));
        record.setLat(new BigDecimal(12));
        record.setLong(new BigDecimal(23));

        return record;
    }

    public static LinkChangeRecord getSingleSelectLinkChangeTestRecord(){
        LinkChangeRecord record = new LinkChangeRecord();

        record.setId("1");
        record.setChangesetnr((long) 1);
        record.setNetworkid(1);
        record.setMinlevel(1);

        return record;
    }

    public static List<NodeChangeRecord> getMultipleSelectNodeChangeTestRecords() {
        List<NodeChangeRecord> nodeChangeRecords = new ArrayList();
        NodeChangeRecord record1 = new NodeChangeRecord();
        record1.setId("N1");
        record1.setNetworkid(1);
        record1.setChangesetnr((long) 1);
        record1.setX(new BigDecimal(12.3));
        nodeChangeRecords.add(record1);
        NodeChangeRecord record2 = new NodeChangeRecord();
        record2.setId("N2");
        record2.setNetworkid(1);
        record2.setChangesetnr((long) 1);
        record2.setY(new BigDecimal(32.3));
        nodeChangeRecords.add(record2);

        return nodeChangeRecords;
    }
}
