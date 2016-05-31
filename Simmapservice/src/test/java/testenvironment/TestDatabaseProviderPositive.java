package testenvironment;


import dataaccess.database.Tables;
import dataaccess.database.tables.records.*;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by dohee on 11.05.2016.
 */
public class TestDatabaseProviderPositive implements MockDataProvider {
    private ConnectionMode connectionMode = ConnectionMode.ONE;

    public void setConnectionMode(ConnectionMode mode) {
        this.connectionMode = mode;
    }

    @Override
    public MockResult[] execute(MockExecuteContext ctx) throws SQLException {
        DSLContext dslContext = DSL.using(SQLDialect.POSTGRES);
        MockResult[] mock = new MockResult[1];
        if(connectionMode != ConnectionMode.ERROR) {
            if (ctx.batch()) {
                String[] sqls = ctx.batchSQL();
                if (ctx.batchMultiple()) {
                    mock = new MockResult[sqls.length];
                    for (int i = 0; i < sqls.length; i++) {
                        String sql = sqls[i];
                        if (sql.toLowerCase().startsWith("insert")) {
                            mock[i] = calculateNonBatchInsertStatement(dslContext, sql, ctx.bindings());
                        }
                    }
                } else {
                    String sql = sqls[0];
                    Object[][] bindings = ctx.batchBindings();
                    mock = new MockResult[bindings.length];
                    for (int i = 0; i < bindings.length; i++) {
                        if (sql.toLowerCase().startsWith("delete")) {
                            mock[i] = calculateNonBatchDeleteStatement(dslContext, sql, ctx.batchBindings()[i]);
                        }
                    }
                }
            } else {
                String sql = ctx.sql();
                System.out.println(ctx.sql());
                if (sql.toLowerCase().startsWith("select")) {
                    mock[0] = calculateNonBatchSelectStatement(dslContext, ctx.sql(), ctx.bindings());
                } else if (sql.toLowerCase().startsWith("insert")) {
                    mock[0] = calculateNonBatchInsertStatement(dslContext, ctx.sql(), ctx.bindings());
                } else if (sql.toLowerCase().startsWith("delete")) {
                    mock[0] = calculateNonBatchDeleteStatement(dslContext, ctx.sql(), ctx.bindings());
                } else if (sql.toLowerCase().startsWith("update")) {
                    mock[0] = calculateNonBatchUpdateStatement(dslContext, ctx.sql(), ctx.bindings());
                }


            }
        }
        else{
            throw TestDataUtil.getSQLException();
        }
        return mock;
    }

    private MockResult calculateNonBatchSelectStatement(DSLContext dslContext, String sql, Object[] bindings) throws SQLException {
        Query query = DSL.query(sql, bindings);
        MockResult mockResult;
        System.out.println(query.toString());
        Record record;
        Result result;
        switch (query.toString()) {
            //getLinkFromID
            case "select \"public\".\"Link\".\"Id\", \"public\".\"Link\".\"NetworkId\", \"public\".\"Link\".\"QuadKey\", \"public\".\"Link\".\"Length\", \"public\".\"Link\".\"Freespeed\", \"public\".\"Link\".\"Capacity\", \"public\".\"Link\".\"Permlanes\", \"public\".\"Link\".\"Oneway\", \"public\".\"Link\".\"Modes\", \"public\".\"Link\".\"From\", \"public\".\"Link\".\"To\", \"public\".\"Link\".\"MinLevel\", \"public\".\"Link\".\"LastModified\", \"public\".\"Link\".\"Long1\", \"public\".\"Link\".\"Lat1\", \"public\".\"Link\".\"Long2\", \"public\".\"Link\".\"Lat2\" from \"public\".\"Link\" where \"public\".\"Link\".\"Id\" = '1'":
                record = TestDataUtil.getSingleSelectLinkTestRecord();
                record.setValue(Tables.LINK.ID, "1");
                result = dslContext.newResult(Tables.LINK);
                result.add(record);
                mockResult = new MockResult(1, result);
                break;
            //getChangesetFromNumber
            case "select \"public\".\"Changeset\".\"Id\", \"public\".\"Changeset\".\"Name\", \"public\".\"Changeset\".\"UserNr\", \"public\".\"Changeset\".\"NetworkId\", \"public\".\"Changeset\".\"LastModified\" from \"public\".\"Changeset\" where \"public\".\"Changeset\".\"Id\" = 1":
                record = TestDataUtil.getSingleSelectChangesetTestRecord();
                result = dslContext.newResult(Tables.CHANGESET);
                result.add(record);
                mockResult = new MockResult(1, result);
                break;
            //getAllChangesetsPerUser
            case "select \"public\".\"Changeset\".\"Id\", \"public\".\"Changeset\".\"Name\", \"public\".\"Changeset\".\"UserNr\", \"public\".\"Changeset\".\"NetworkId\", \"public\".\"Changeset\".\"LastModified\" from \"public\".\"Changeset\" where \"public\".\"Changeset\".\"UserNr\" = 1 order by \"public\".\"Changeset\".\"LastModified\" desc":
                List<ChangesetRecord> recordList = TestDataUtil.getMultipleSelectChangesetTestRecords();
                result = dslContext.newResult(Tables.CHANGESET);
                result.addAll(recordList);
                mockResult = new MockResult(1, result);
                break;
            //getLinkChangesFromChangeset
            case "select \"public\".\"Link_Change\".\"Id\", \"public\".\"Link_Change\".\"ChangesetNr\", \"public\".\"Link_Change\".\"NetworkId\", \"public\".\"Link_Change\".\"QuadKey\", \"public\".\"Link_Change\".\"Length\", \"public\".\"Link_Change\".\"Freespeed\", \"public\".\"Link_Change\".\"Capacity\", \"public\".\"Link_Change\".\"Permlanes\", \"public\".\"Link_Change\".\"Oneway\", \"public\".\"Link_Change\".\"Modes\", \"public\".\"Link_Change\".\"From\", \"public\".\"Link_Change\".\"To\", \"public\".\"Link_Change\".\"MinLevel\", \"public\".\"Link_Change\".\"LastModified\", \"public\".\"Link_Change\".\"Long1\", \"public\".\"Link_Change\".\"Lat1\", \"public\".\"Link_Change\".\"Long2\", \"public\".\"Link_Change\".\"Lat2\" from \"public\".\"Link_Change\" where \"public\".\"Link_Change\".\"ChangesetNr\" = 1":
                record = TestDataUtil.getSingleSelectLinkChangeTestRecord();
                result = dslContext.newResult(Tables.LINK_CHANGE);
                result.add(record);
                mockResult = new MockResult(1, result);
                break;
            //getNodeChangeFromChangeset
            case "select \"public\".\"Node_Change\".\"Id\", \"public\".\"Node_Change\".\"ChangesetNr\", \"public\".\"Node_Change\".\"NetworkId\", \"public\".\"Node_Change\".\"QuadKey\", \"public\".\"Node_Change\".\"X\", \"public\".\"Node_Change\".\"Y\", \"public\".\"Node_Change\".\"Lat\", \"public\".\"Node_Change\".\"Long\" from \"public\".\"Node_Change\" where \"public\".\"Node_Change\".\"ChangesetNr\" = 1":
                List<NodeChangeRecord> nodeChangeRecords = TestDataUtil.getMultipleSelectNodeChangeTestRecords();
                result = dslContext.newResult(Tables.NODE_CHANGE);
                result.addAll(nodeChangeRecords);
                mockResult = new MockResult(1, result);
                break;
            //getNodeFromId
            case "select \"public\".\"Node\".\"Id\", \"public\".\"Node\".\"NetworkId\", \"public\".\"Node\".\"QuadKey\", \"public\".\"Node\".\"X\", \"public\".\"Node\".\"Y\", \"public\".\"Node\".\"Lat\", \"public\".\"Node\".\"Long\" from \"public\".\"Node\" where \"public\".\"Node\".\"Id\" = '1'":
                record = TestDataUtil.getSingleSelectNodeTestRecord();
                result = dslContext.newResult(Tables.NODE);
                result.add(record);
                mockResult = new MockResult(1, result);
                break;
            //getRecords
            case "select \"public\".\"Link\".\"Id\", \"public\".\"Link\".\"NetworkId\", \"public\".\"Link\".\"QuadKey\", \"public\".\"Link\".\"Length\", \"public\".\"Link\".\"Freespeed\", \"public\".\"Link\".\"Capacity\", \"public\".\"Link\".\"Permlanes\", \"public\".\"Link\".\"Oneway\", \"public\".\"Link\".\"Modes\", \"public\".\"Link\".\"From\", \"public\".\"Link\".\"To\", \"public\".\"Link\".\"MinLevel\", \"public\".\"Link\".\"LastModified\", \"public\".\"Link\".\"Long1\", \"public\".\"Link\".\"Lat1\", \"public\".\"Link\".\"Long2\", \"public\".\"Link\".\"Lat2\" from \"public\".\"Link\"":
                record = TestDataUtil.getSingleSelectLinkTestRecord();
                result = dslContext.newResult(Tables.LINK);
                if (!connectionMode.equals(ConnectionMode.NONE)) {
                    result.add(record);
                }
                mockResult = new MockResult(1, result);
                break;
            case "select * from \"NotExisting\"":
                throw new SQLException("Table not found");
                //testGetMultipleRecords
            case "select \"public\".\"Node\".\"Id\", \"public\".\"Node\".\"NetworkId\", \"public\".\"Node\".\"QuadKey\", \"public\".\"Node\".\"X\", \"public\".\"Node\".\"Y\", \"public\".\"Node\".\"Lat\", \"public\".\"Node\".\"Long\" from \"public\".\"Node\"":
                List<NodeRecord> nodeRecords = TestDataUtil.getMultipleSelectNodeTestRecords();
                result = dslContext.newResult(Tables.NODE);
                result.addAll(nodeRecords);
                mockResult = new MockResult(nodeRecords.size(), result);
                break;
            //getLinksFromQuadKey
            case "select \"l\".\"Id\", \"l\".\"Length\", \"l\".\"Freespeed\", \"l\".\"Capacity\", \"l\".\"Permlanes\", \"l\".\"Oneway\", \"l\".\"Modes\", \"l\".\"From\", \"l\".\"To\", \"l\".\"Long1\", \"l\".\"Lat1\", \"l\".\"Long2\", \"l\".\"Lat2\" from \"public\".\"Link\" as \"l\" where ((\"l\".\"QuadKey\" like '123%' or \"l\".\"QuadKey\" = '' or \"l\".\"QuadKey\" = '1' or \"l\".\"QuadKey\" = '12') and \"l\".\"NetworkId\" = 1 and \"l\".\"MinLevel\" <= 12)":
                List<Record> linkRecords = TestDataUtil.getMultipleSelectLinkTestRecords();
                result = dslContext.newResult(Tables.LINK.as("l"));
                result.addAll(linkRecords);
                mockResult = new MockResult(linkRecords.size(), result);
                break;
            case "select max(\"l\".\"LastModified\") from \"public\".\"Link\" as \"l\" where ((\"l\".\"QuadKey\" like '123%' or \"l\".\"QuadKey\" = '' or \"l\".\"QuadKey\" = '1') and \"l\".\"NetworkId\" = 1 and \"l\".\"MinLevel\" <= 12)":
                Date date = TestDataUtil.getDateLastModifiedTestRecord();

                record = dslContext.newRecord(DSL.field("max", Date.class));
                record.setValue(DSL.field("max", Date.class), date);
                mockResult = new MockResult(record);
                break;
            case "select count(*) from \"public\".\"Changeset\" where \"public\".\"Changeset\".\"Id\" = 1":
                record = dslContext.newRecord(DSL.field("count(*)", Integer.class));
                record.setValue(DSL.field("count(*)", Integer.class), 1);
                mockResult = new MockResult(record);
                break;
            default:
                mockResult = new MockResult(0, null);
                break;
        }

        return mockResult;
    }

    private MockResult calculateNonBatchInsertStatement(DSLContext dslContext, String sql, Object[] bindings) {
        Query query = DSL.query(sql, bindings);
        MockResult mockResult;
        System.out.println(query.toString());
        Record record;
        switch (query.toString()) {
            case "insert into \"public\".\"Link\" (\"Id\", \"NetworkId\", \"QuadKey\", \"Length\", \"Freespeed\", \"Capacity\", \"Permlanes\", \"Oneway\", \"Modes\", \"From\", \"To\", \"MinLevel\", \"LastModified\", \"Long1\", \"Lat1\", \"Long2\", \"Lat2\") values ('1', 1, '123123123', 1200, 33.3299999999999982946974341757595539093017578125, 1000, 2, true, 'car', 'N1', 'N2', 12, date '1970-01-01', 12, 12, 23, 23) on conflict (\"Id\", \"NetworkId\") do update set \"Id\" = '1', \"NetworkId\" = 1, \"QuadKey\" = '123123123', \"Length\" = 1200, \"Freespeed\" = 33.3299999999999982946974341757595539093017578125, \"Capacity\" = 1000, \"Permlanes\" = 2, \"Oneway\" = true, \"Modes\" = 'car', \"From\" = 'N1', \"To\" = 'N2', \"MinLevel\" = 12, \"LastModified\" = date '1970-01-01', \"Long1\" = 12, \"Lat1\" = 12, \"Long2\" = 23, \"Lat2\" = 23":
                mockResult = new MockResult(1, null);
                break;
            case "insert into \"public\".\"Link\" (\"Id\", \"NetworkId\", \"QuadKey\", \"Length\", \"Freespeed\", \"Capacity\", \"Permlanes\", \"Oneway\", \"Modes\", \"From\", \"To\", \"MinLevel\", \"LastModified\", \"Long1\", \"Lat1\", \"Long2\", \"Lat2\") values (null, 1, '123123123', 1200, 33.3299999999999982946974341757595539093017578125, 1000, 2, true, 'car', 'N1', 'N2', 12, cast(date '1970-01-01' as date), 12, 12, 23, 23) returning \"public\".\"Link\".\"Id\"":
                record = dslContext.newRecord(Tables.LINK.ID);
                record.setValue(Tables.LINK.ID, "1");
                mockResult = new MockResult(record);
                break;
            case "insert into \"public\".\"Link\" (\"NetworkId\", \"QuadKey\", \"Length\", \"Freespeed\", \"Capacity\", \"Permlanes\", \"Oneway\", \"Modes\", \"From\", \"To\", \"MinLevel\", \"LastModified\", \"Long1\", \"Lat1\", \"Long2\", \"Lat2\") values (1, '123123123', 1200, 33.3299999999999982946974341757595539093017578125, 1000, 2, true, 'car', 'N1', 'N2', 12, date '1970-01-01', 12, 12, 23, 23) on conflict (\"Id\", \"NetworkId\") do update set \"NetworkId\" = 1, \"QuadKey\" = '123123123', \"Length\" = 1200, \"Freespeed\" = 33.3299999999999982946974341757595539093017578125, \"Capacity\" = 1000, \"Permlanes\" = 2, \"Oneway\" = true, \"Modes\" = 'car', \"From\" = 'N1', \"To\" = 'N2', \"MinLevel\" = 12, \"LastModified\" = date '1970-01-01', \"Long1\" = 12, \"Lat1\" = 12, \"Long2\" = 23, \"Lat2\" = 23":
                record = dslContext.newRecord(Tables.LINK.ID);
                record.setValue(Tables.LINK.ID, "1");
                mockResult = new MockResult(record);
                break;
            default:
                mockResult = new MockResult(0, null);
                break;
        }

        return mockResult;
    }

    private MockResult calculateNonBatchDeleteStatement(DSLContext dslContext, String sql, Object[] bindings) {
        Query query = DSL.query(sql, bindings);
        MockResult mockResult;
        System.out.println(query.toString());
        switch (query.toString()) {
            case "delete from \"public\".\"Link\" where (\"public\".\"Link\".\"Id\" = '1' and \"public\".\"Link\".\"NetworkId\" = 1)":
                mockResult = new MockResult(1, null);
                break;
            case "delete from \"public\".\"Node\" where (\"public\".\"Node\".\"Id\" = 'N1' and \"public\".\"Node\".\"NetworkId\" = 1)":
            case "delete from \"public\".\"Node\" where (\"public\".\"Node\".\"Id\" = 'N2' and \"public\".\"Node\".\"NetworkId\" = 1)":
                mockResult = new MockResult(1, null);
                break;
            default:
                mockResult = new MockResult(0, null);
                ;
                break;
        }

        return mockResult;
    }

    private MockResult calculateNonBatchUpdateStatement(DSLContext dslContext, String sql, Object[] bindings) {
        Query query = DSL.query(sql, bindings);
        MockResult mockResult;
        System.out.println(query.toString());
        switch (query.toString()) {
            case "update \"public\".\"Link\" set \"Id\" = '1', \"NetworkId\" = 1, \"QuadKey\" = '123123123', \"Length\" = 1200, \"Freespeed\" = 33.3299999999999982946974341757595539093017578125, \"Capacity\" = 1000, \"Permlanes\" = 2, \"Oneway\" = true, \"Modes\" = 'car', \"From\" = 'N1', \"To\" = 'N2', \"MinLevel\" = 12, \"LastModified\" = cast(date '1970-01-01' as date), \"Long1\" = 12, \"Lat1\" = 12, \"Long2\" = 23, \"Lat2\" = 23 where (\"public\".\"Link\".\"Id\" = '1' and \"public\".\"Link\".\"NetworkId\" = 1)":
                mockResult = new MockResult(1, null);
                break;
            default:
                mockResult = new MockResult(0, null);
                ;
                break;
        }

        return mockResult;
    }
}
