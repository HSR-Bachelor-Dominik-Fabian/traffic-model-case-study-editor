package testenvironment;


import dataaccess.database.Tables;
import dataaccess.database.tables.records.LinkRecord;
import dataaccess.database.tables.records.NodeRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;
import org.jooq.tools.jdbc.MockStatement;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by dohee on 11.05.2016.
 */
public class TestDatabaseProviderPositive implements MockDataProvider {
    private ConnectionMode connectionMode = ConnectionMode.ONE;
    public void setConnectionMode(ConnectionMode mode){
        this.connectionMode = mode;
    }

    @Override
    public MockResult[] execute(MockExecuteContext ctx) throws SQLException {
        DSLContext create = DSL.using(SQLDialect.POSTGRES);
        MockResult[] mock = new MockResult[1];

        // The execute context contains SQL string(s), bind values, and other meta-data

        if (ctx.batch()) {
            String[] sqls = ctx.batchSQL();
            if(connectionMode.equals(ConnectionMode.MULTIPLE)){
                mock = new MockResult[sqls.length];
                for (int i = 0; i < sqls.length; i++) {
                    String sql = sqls[i];
                    if (sql.toLowerCase().startsWith("insert")) {
                        mock[i] = calculateInsertStatements(create, sql);
                    }
                    else if(sql.toLowerCase().startsWith("delete")){
                        Object[][] batchBindings = ctx.batchBindings();
                        mock = new MockResult[batchBindings.length];
                        for (int i1 = 0; i1 < batchBindings.length; i1++) {
                            mock[i1] = calculateDeleteStatements(create, sql);
                        }

                    }
                }
            }
        }
        else {
            String sql = ctx.sql();
            if(sql.toLowerCase().startsWith("select")){
                mock[0] = calculateSelectStatements(create, mock, sql);
            }
            else if(sql.toLowerCase().startsWith("insert")){
                mock[0] = calculateInsertStatements(create, sql);
            }
            else if(sql.toLowerCase().startsWith("delete")){
                mock[0] = calculateDeleteStatements(create, sql);
            }
            else if(sql.toLowerCase().startsWith("update")){
                mock[0] = calculateUpdateStatements(create, sql);
            }
        }
        return mock;
    }

    private MockResult calculateUpdateStatements(DSLContext create, String sql) throws SQLException {
        if(sql.toLowerCase().contains("update \"public\".\"link\"")){
            if(connectionMode.equals(ConnectionMode.ONE)){
                return new MockResult(create.newRecord());
            }
            else if(connectionMode.equals(ConnectionMode.ERROR)){
                throw TestDataUtil.getUpdateSQLException();
            }
        }
        return null;
    }

    private MockResult calculateDeleteStatements(DSLContext create, String sql) throws SQLException {
        if(sql.toLowerCase().contains("from \"public\".\"link\"")){
            if(connectionMode.equals(ConnectionMode.ONE)){
                return new MockResult(create.newRecord());
            }
            else if(connectionMode.equals(ConnectionMode.ERROR)){
                throw new SQLException("Test Error");
            }
        }
        else if(sql.toLowerCase().contains("from \"public\".\"node\"")){
            if(connectionMode.equals(ConnectionMode.MULTIPLE)){
                return new MockResult(create.newRecord());
            }
        }
        return null;
    }

    private MockResult calculateInsertStatements(DSLContext create, String sql) throws SQLException {
        if(sql.toLowerCase().contains("into \"public\".\"link\"") && !sql.toLowerCase().contains("returning")){
            if(connectionMode.equals(ConnectionMode.ONE)){
                return new MockResult(create.newRecord());
            }
            else if(connectionMode.equals(ConnectionMode.ERROR)){
                throw TestDataUtil.getInsertSQLException();
            }
            else if(connectionMode.equals(ConnectionMode.MULTIPLE)){
                return new MockResult(create.newRecord());
            }
        }
        else if (sql.toLowerCase().contains("into \"public\".\"link\"")){
            if(connectionMode.equals(ConnectionMode.ONE)){
                Record1<String> record = create.newRecord(Tables.LINK.ID);
                return new MockResult(record.value1("1"));
            }
        }
        return null;
    }

    private MockResult calculateSelectStatements(DSLContext create, MockResult[] mock, String sql) throws SQLException {
        if(sql.toLowerCase().contains("from \"public\".\"link\"")){
            if(connectionMode.equals(ConnectionMode.ONE)){
                Result<LinkRecord> result = create.newResult(Tables.LINK);
                LinkRecord record = TestDataUtil.getSingleSelectLinkTestRecord();
                result.add(record);
                return new MockResult(1,result);
            }
            else if(connectionMode.equals(ConnectionMode.NONE)){
                Result<LinkRecord> result = create.newResult(Tables.LINK);
                return new MockResult(1,result);
            }
        }
        else if(sql.toLowerCase().contains("from \"public\".\"node\"")){
            if(connectionMode.equals(ConnectionMode.MULTIPLE)) {
                Result<NodeRecord> result = create.newResult(Tables.NODE);
                List<NodeRecord> records = TestDataUtil.getMultipleSelectNodeTestRecords();
                result.addAll(records);
                return new MockResult(records.size(), result);
            }
        }
        else{
            throw new SQLException("Table not found");
        }
        return null;
    }
}
