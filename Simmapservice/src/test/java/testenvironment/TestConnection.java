package testenvironment;

import dataaccess.utils.IConnection;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockFileDatabase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestConnection implements IConnection {
    public TestConnection(ConnectionMode connectionMode){
        this.connectionMode = connectionMode;
    }
    private ConnectionMode connectionMode;

    @Override
    public Connection getConnectionFromProps(Properties properties) throws SQLException {
        if(this.connectionMode != ConnectionMode.NOCONNECTION) {
            TestDatabaseProviderPositive provider = new TestDatabaseProviderPositive();
            provider.setConnectionMode(this.connectionMode);
            return new MockConnection(provider);
        }
        else{
            return DriverManager.getConnection(TestDataUtil.getTestPSQLPath(), TestDataUtil.getTestUsername()
                    , TestDataUtil.getTestPassword());
        }
    }
}
