package dataaccess;

import java.sql.SQLException;

public class DataAccessLayerException extends Exception {
    private final String message;
    private final Throwable cause;
    private final String sqlState;

    public DataAccessLayerException(Exception exception) {
        super(exception);
        cause = exception.getCause();
        message = exception.getMessage();
        if (exception instanceof SQLException) {
            sqlState = ((SQLException) exception).getSQLState();
        } else {
            sqlState = null;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getSqlState() {
        return sqlState;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
