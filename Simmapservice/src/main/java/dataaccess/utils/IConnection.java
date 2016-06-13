package dataaccess.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public interface IConnection {
    Connection getConnectionFromProps(Properties properties) throws SQLException;
}
