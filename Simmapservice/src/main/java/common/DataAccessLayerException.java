package common;


import org.jooq.exception.DataAccessException;

import java.sql.SQLException;

/**
 * Created by dohee on 23.05.2016.
 */
public class DataAccessLayerException extends Exception {
    private String message;
    private Throwable cause;
    private String sqlState;

    public DataAccessLayerException(SQLException sqlException){
        super(sqlException);
        cause = sqlException.getCause();
        message = sqlException.getMessage();
        sqlState = sqlException.getSQLState();
    }

    public DataAccessLayerException(DataAccessException dataAccessExcpetion){
        super(dataAccessExcpetion);
        cause = dataAccessExcpetion.getCause();
        message = dataAccessExcpetion.getMessage();
        sqlState = null;
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
