package dataaccess.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by dohee on 09.05.2016.
 */
public class ProdConnection implements IConnection {

    @Override
    public Connection getConnectionFromProps(Properties properties) throws SQLException {
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        return DriverManager.getConnection(url, user, password);
    }
}
