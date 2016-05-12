package testenvironment;

import dataaccess.utils.IConnection;
import org.hsqldb.jdbc.JDBCConnection;
import org.jooq.tools.jdbc.DefaultConnection;
import org.jooq.tools.jdbc.MockConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by dohee on 11.05.2016.
 */
public class TestConnection implements IConnection {
    public TestConnection(SelectMode selectMode){
        this.selectMode = selectMode;
    }
    private SelectMode selectMode;

    @Override
    public Connection getConnectionFromProps(Properties properties) throws SQLException {
        if(this.selectMode != SelectMode.NOCONNECTION) {
            TestDatabaseProviderPositive provider = new TestDatabaseProviderPositive();
            provider.setSelectMode(this.selectMode);
            return new MockConnection(provider);
        }
        else{
            return DriverManager.getConnection(TestDataUtil.getTestPSQLPath(), TestDataUtil.getTestUsername()
                    , TestDataUtil.getTestPassword());
        }
    }
}
