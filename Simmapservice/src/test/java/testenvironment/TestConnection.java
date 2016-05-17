package testenvironment;

import dataaccess.utils.IConnection;
import org.jooq.tools.jdbc.MockConnection;

import java.sql.Connection;
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
        TestDatabaseProviderPositive provider = new TestDatabaseProviderPositive();
        provider.setSelectMode(this.selectMode);
        MockConnection connection = new MockConnection(provider);
        return connection;
    }
}
