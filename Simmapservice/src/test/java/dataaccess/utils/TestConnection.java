package dataaccess.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by dohee on 11.05.2016.
 */
public class TestConnection implements IConnection {
    @Override
    public Connection getConnectionFromProps(Properties properties) throws SQLException {
        return null;
    }
}
