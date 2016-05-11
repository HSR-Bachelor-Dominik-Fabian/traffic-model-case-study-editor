package dataaccess.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by dohee on 09.05.2016.
 */

public interface IConnection {
    Connection getConnectionFromProps(Properties properties) throws SQLException;
}
