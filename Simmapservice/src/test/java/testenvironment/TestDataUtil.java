package testenvironment;

import businesslogic.changeset.*;
import dataaccess.database.Tables;
import dataaccess.database.tables.Link;
import dataaccess.database.tables.LinkChange;
import dataaccess.database.tables.NetworkOptions;
import dataaccess.database.tables.records.*;
import org.easymock.EasyMock;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Properties;

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

    public static ChangesetRecord getSingleSelectChangesetTestRecord() {
        ChangesetRecord record = new ChangesetRecord();
        record.setId((long) 1);
        record.setNetworkid(1);
        record.setUsernr(1);
        record.setLastmodified(null);
        record.setName("Changeset1");
        return record;
    }

    public static NodeRecord getSingleSelectNodeTestRecord() {
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

    public static LinkChangeRecord getSingleSelectLinkChangeTestRecord() {
        LinkChangeRecord record = new LinkChangeRecord();

        record.setId("1");
        record.setChangesetnr((long) 1);
        record.setNetworkid(1);
        record.setMinlevel(1);

        return record;
    }

    public static NodeChangeRecord getSingleSelectNodeChangeTestRecord() {
        NodeChangeRecord record1 = new NodeChangeRecord();
        record1.setId("N1");
        record1.setNetworkid(1);
        record1.setChangesetnr((long) 1);
        record1.setX(new BigDecimal(12.3));
        return record1;
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

    public static List<Record> getMultipleSelectLinkTestRecords() {
        List<Record> linkRecords = new ArrayList();
        DSLContext context = DSL.using(SQLDialect.POSTGRES);
        Link l = Tables.LINK.as("l");
        Record record = context.newRecord(l.ID, l.LENGTH, l.FREESPEED, l.CAPACITY, l.PERMLANES, l.ONEWAY,
                l.MODES, l.FROM, l.TO, l.LONG1, l.LAT1, l.LONG2, l.LAT2);
        record.setValue(l.ID, "L1");
        record.setValue(l.LENGTH, new BigDecimal(1200));
        record.setValue(l.FREESPEED, new BigDecimal(33.33));
        record.setValue(l.CAPACITY, new BigDecimal(1000));
        record.setValue(l.PERMLANES, new BigDecimal(2));
        record.setValue(l.ONEWAY, true);
        record.setValue(l.MODES, "car");
        record.setValue(l.FROM, "N1");
        record.setValue(l.TO, "N2");
        record.setValue(l.LONG1, new BigDecimal(12));
        record.setValue(l.LAT1, new BigDecimal(12));
        record.setValue(l.LONG2, new BigDecimal(23));
        record.setValue(l.LAT2, new BigDecimal(23));

        linkRecords.add(record);

        Record record2 = context.newRecord(l.ID, l.LENGTH, l.FREESPEED, l.CAPACITY, l.PERMLANES, l.ONEWAY,
                l.MODES, l.FROM, l.TO, l.LONG1, l.LAT1, l.LONG2, l.LAT2);
        record2.setValue(l.ID, "L2");
        record2.setValue(l.LENGTH, new BigDecimal(1200));
        record2.setValue(l.FREESPEED, new BigDecimal(33.33));
        record2.setValue(l.CAPACITY, new BigDecimal(1000));
        record2.setValue(l.PERMLANES, new BigDecimal(2));
        record2.setValue(l.ONEWAY, true);
        record2.setValue(l.MODES, "car");
        record.setValue(l.FROM, "N1");
        record.setValue(l.TO, "N2");
        record2.setValue(l.LONG1, new BigDecimal(12));
        record2.setValue(l.LAT1, new BigDecimal(12));
        record2.setValue(l.LONG2, new BigDecimal(23));
        record2.setValue(l.LAT2, new BigDecimal(23));

        linkRecords.add(record2);
        return linkRecords;
    }

    public static List<Record> getMultipleNodeTestRecords() {
        List<Record> output = new ArrayList();

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

    public static Date getDateLastModifiedTestRecord() {

        return Date.valueOf(LocalDate.of(1992, 11, 19));
    }

    public static Record getChangesetIDReturnRecord() {
        DSLContext context = DSL.using(SQLDialect.POSTGRES);
        Record1<Long> record = context.newRecord(Tables.CHANGESET.ID);
        record.value1((long) 1);
        return record;
    }

    public static SQLException getSQLException() {
        Throwable throwable = new IllegalStateException("BadState");
        return new SQLException("SQL Test Exception", "Test2", 2, throwable);
    }

    public static ChangesetRecord getChangesetRecord() {
        ChangesetRecord record = DSL.using(SQLDialect.POSTGRES).newRecord(Tables.CHANGESET);
        record.setId((long) 1);
        record.setName("Changeset1");
        record.setNetworkid(1);
        record.setUsernr(1);
        record.setLastmodified(Timestamp.valueOf("2009-03-12 12:30:23"));
        return record;
    }

    public static List<Link_ChangeModel> getListLinkChangeModels() {
        List<Link_ChangeModel> models = new ArrayList<>();
        LinkModel defaultModel = new LinkModel(TestDataUtil.getSingleSelectLinkTestRecord());

        Link_ChangeModel model = new Link_ChangeModel();
        model.setId("L1");
        model.setDefaultValues(defaultModel);
        model.setDeleted(false);
        model.setFreespeed(new BigDecimal(3.333));
        models.add(model);

        return models;
    }

    public static List<Node_ChangeModel> getListNodeChangeModels() {
        List<Node_ChangeModel> models = new ArrayList<>();
        List<NodeRecord> nodeRecords = TestDataUtil.getMultipleSelectNodeTestRecords();


        Node_ChangeModel model = new Node_ChangeModel();
        model.setId("N1");
        model.setChangesetNr((long) 3);
        model.setDefaultValues(new NodeModel(nodeRecords.get(0)));
        model.setNetworkId(1);
        model.setDeleted(false);
        model.setLatitude(new BigDecimal(123));
        model.setY(new BigDecimal(123));
        models.add(model);

        Node_ChangeModel model1 = new Node_ChangeModel();
        model1.setId("N2");
        model1.setChangesetNr((long) 3);
        model1.setDefaultValues(new NodeModel(nodeRecords.get(1)));
        model.setNetworkId(1);
        model1.setDeleted(false);
        model1.setLongitude(new BigDecimal(332));
        model1.setX(new BigDecimal(3323));
        models.add(model1);

        return models;
    }

    public static List<ChangesetModel> getListChangesetModels() {
        List<ChangesetModel> models = new ArrayList<>();

        List<ChangesetRecord> changesetRecords = TestDataUtil.getMultipleSelectChangesetTestRecords();

        for (ChangesetRecord model : changesetRecords) {
            models.add(new ChangesetModel(model));
        }

        return models;
    }

    public static InputStream getInputStreamOfData() throws FileNotFoundException {
        return new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("test.xml").getPath());
    }

    public static Result<NodeRecord> getStreamNodesAsResult() {
        DSLContext context = DSL.using(SQLDialect.POSTGRES);
        Result<NodeRecord> result = context.newResult(Tables.NODE);

        for (NodeRecord record : getStreamNodesAsArray()) {
            if(record != null){
                result.add(record);
            }
        }

        return result;
    }

    public static NodeRecord[] getStreamNodesAsArray() {
        DSLContext context = DSL.using(SQLDialect.POSTGRES);
        NodeRecord record1 = context.newRecord(Tables.NODE);
        record1.setNetworkid(1);
        record1.setId("4");
        record1.setX(new BigDecimal("680827.0275615142"));
        record1.setY(new BigDecimal("4825897.167274702"));
        record1.setQuadkey("021332132011020100");
        record1.setLat(new BigDecimal(43.5641113988393));
        record1.setLong(new BigDecimal(-96.76092772102633));


        NodeRecord record2 = context.newRecord(Tables.NODE);
        record2.setNetworkid(1);
        record2.setId("4_101");
        record2.setX(new BigDecimal("680841.192323177"));
        record2.setY(new BigDecimal("4825447.326025019"));
        record2.setQuadkey("021332132011020300");
        record2.setLat(new BigDecimal(43.56006060145680436335169360972940921783447265625));
        record2.setLong(new BigDecimal(-96.760902433791017074327100999653339385986328125));

        NodeRecord record3 = context.newRecord(Tables.NODE);
        record3.setNetworkid(1);
        record3.setId("4_102");
        record3.setX(new BigDecimal("680855.3570848398"));
        record3.setY(new BigDecimal("4824997.484775337"));
        record3.setQuadkey("021332132011022100");
        record3.setLat(new BigDecimal(43.55600980148104639511075220070779323577880859375));
        record3.setLong(new BigDecimal(-96.7608771387973547462024725973606109619140625));

        NodeRecord[] records = new NodeRecord[25000];
        records[0] = record1;
        records[1] = record2;
        records[2] = record3;
        return records;
    }

    public static NetworkOptionsRecord[] getStreamOptionsAsArray(){
        NetworkOptionsRecord[] options = new NetworkOptionsRecord[3];
        DSLContext context = DSL.using(SQLDialect.POSTGRES);
        NetworkOptionsRecord record1 = context.newRecord(Tables.NETWORK_OPTIONS);
        record1.setNetworkid(1);
        record1.setOptionname("capperiod");
        record1.setValue("01:00:00");
        options[0] = record1;

        NetworkOptionsRecord record2 = context.newRecord(Tables.NETWORK_OPTIONS);
        record2.setNetworkid(1);
        record2.setOptionname("effectivecellsize");
        record2.setValue("7.5");
        options[1] = record2;

        NetworkOptionsRecord record3 = context.newRecord(Tables.NETWORK_OPTIONS);
        record3.setNetworkid(1);
        record3.setOptionname("effectivelanewidth");
        record3.setValue("3.75");
        options[2] = record3;

        return options;
    }

    public static LinkRecord[] getStreamLinksAsArray(){
        DSLContext context = DSL.using(SQLDialect.POSTGRES);
        LinkRecord[] linkRecords = new LinkRecord[25000];

        LinkRecord linkRecord1 = context.newRecord(Tables.LINK);
        linkRecord1.setId("10_1");
        linkRecord1.setFrom("4");
        linkRecord1.setTo("4_101");
        linkRecord1.setQuadkey("021332132011020");
        linkRecord1.setOneway(true);
        linkRecord1.setNetworkid(1);
        linkRecord1.setFreespeed(new BigDecimal("13.9"));
        linkRecord1.setCapacity(new BigDecimal("1964.0"));
        linkRecord1.setPermlanes(new BigDecimal("2.0"));
        linkRecord1.setModes("car");
        linkRecord1.setLength(new BigDecimal("450.06420696267253"));
        linkRecord1.setMinlevel(15);
        linkRecord1.setLastmodified(Date.valueOf(LocalDate.now()));
        linkRecord1.setLat1(new BigDecimal("43.5641113988393016143163549713790416717529296875"));
        linkRecord1.setLong1(new BigDecimal("-96.7609277210263343249607714824378490447998046875"));
        linkRecord1.setLat2(new BigDecimal("43.56006060145680436335169360972940921783447265625"));
        linkRecord1.setLong2(new BigDecimal("-96.760902433791017074327100999653339385986328125"));
        linkRecords[0] = linkRecord1;

        LinkRecord linkRecord2 = context.newRecord(Tables.LINK);
        linkRecord2.setId("10_2");
        linkRecord2.setFrom("4_101");
        linkRecord2.setTo("4_102");
        linkRecord2.setQuadkey("02133213201102");
        linkRecord2.setOneway(true);
        linkRecord2.setNetworkid(1);
        linkRecord2.setFreespeed(new BigDecimal("13.9"));
        linkRecord2.setCapacity(new BigDecimal("1964.0"));
        linkRecord2.setPermlanes(new BigDecimal("2.0"));
        linkRecord2.setModes("car");
        linkRecord2.setLength(new BigDecimal("450.06420696267253"));
        linkRecord2.setMinlevel(15);
        linkRecord2.setLastmodified(Date.valueOf(LocalDate.now()));
        linkRecord2.setLat1(new BigDecimal("43.56006060145680436335169360972940921783447265625"));
        linkRecord2.setLong1(new BigDecimal("-96.760902433791017074327100999653339385986328125"));
        linkRecord2.setLat2(new BigDecimal("43.55600980148104639511075220070779323577880859375"));
        linkRecord2.setLong2(new BigDecimal("-96.7608771387973547462024725973606109619140625"));
        linkRecords[1] = linkRecord2;

        return linkRecords;
    }

    public static LinkRecord[] linkStreamEq(LinkRecord[] expectedRecords){
        EasyMock.reportMatcher(new StreamLinkEquals(expectedRecords));
        return null;
    }

    public static boolean matchStreamLinkToLinks(LinkRecord[] expected, LinkRecord[] result){
        boolean output = true;
        if(expected.length == result.length){
            for(int i = 0; i < result.length; i++){
                LinkRecord expectedRecord = expected[i];
                LinkRecord resultRecord = result[i];
                if(expectedRecord != null) {
                    for (Field<?> field : expectedRecord.fields()) {
                        if (field.getName() != "LastModified") {
                            if (!expectedRecord.getValue(field).equals(resultRecord.getValue(field))) {
                                output = false;
                            }
                        }
                    }
                }
                else{
                    if(!(resultRecord == null)){
                        output = false;
                    }
                }
            }
        }
        else{
            output = false;
        }

        return output;
    }

    public static Result<ChangesetRecord> getChangesetRecordsResult() {
        Result<ChangesetRecord> result = DSL.using(SQLDialect.POSTGRES).newResult(Tables.CHANGESET);
        result.addAll(TestDataUtil.getMultipleSelectChangesetTestRecords());
        return result;
    }

    public static Result<NodeChangeRecord> getNodeChangeRecordResult() {
        Result<NodeChangeRecord> result = DSL.using(SQLDialect.POSTGRES).newResult(Tables.NODE_CHANGE);
        result.addAll(TestDataUtil.getMultipleSelectNodeChangeTestRecords());
        return result;
    }

    public static Result<LinkChangeRecord> getLinkChangeRecordResult() {
        Result<LinkChangeRecord> result = DSL.using(SQLDialect.POSTGRES).newResult(Tables.LINK_CHANGE);
        result.add(TestDataUtil.getSingleSelectLinkChangeTestRecord());
        return result;
    }
}
