package testenvironment;


import dataaccess.database.Tables;
import dataaccess.database.tables.records.LinkRecord;
import dataaccess.database.tables.records.NodeRecord;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by dohee on 11.05.2016.
 */
public class TestDatabaseProviderPositive implements MockDataProvider {
    private SelectMode selectMode = SelectMode.GETONE;
    public void setSelectMode(SelectMode mode){
        this.selectMode = mode;
    }

    @Override
    public MockResult[] execute(MockExecuteContext ctx) throws SQLException {
        DSLContext create = DSL.using(SQLDialect.POSTGRES);
        MockResult[] mock = new MockResult[1];

        // The execute context contains SQL string(s), bind values, and other meta-data
        String sql = ctx.sql();
        if (ctx.batch()) {

        }
        else {
            try {
                Statement stmt = CCJSqlParserUtil.parse(sql);
                if (stmt instanceof Select) {
                    calculateSelectStatements(create, mock, (Select) stmt);
                }
            } catch (JSQLParserException exc) {
                fail("Statement not OK");
            }
        }
        return mock;
    }

    private void calculateSelectStatements(DSLContext create, MockResult[] mock, Select stmt) throws SQLException {
        Select selectStatement = stmt;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(selectStatement);
        if(tableList.size() == 1 && tableList.get(0).contains("Link")){
            if(selectMode.equals(SelectMode.GETONE)){
                Result<LinkRecord> result = create.newResult(Tables.LINK);
                LinkRecord record = TestDataUtil.getSingleSelectLinkTestRecord();
                result.add(record);
                mock[0] = new MockResult(1,result);
            }
            else if(selectMode.equals(SelectMode.GETNONE)){
                Result<LinkRecord> result = create.newResult(Tables.LINK);
                mock[0] = new MockResult(1,result);
            }

        }
        else if(tableList.size() == 1 && tableList.get(0).contains("Node")){
            if(selectMode.equals(SelectMode.GETMULTIPLE)) {
                Result<NodeRecord> result = create.newResult(Tables.NODE);
                List<NodeRecord> records = TestDataUtil.getMultipleSelectNodeTestRecords();
                result.addAll(records);
                mock[0] = new MockResult(records.size(), result);
            }
        }
        else{
            throw new SQLException("Table not found");
        }
    }
}
