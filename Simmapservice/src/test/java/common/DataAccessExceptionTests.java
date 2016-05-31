package common;

import org.jooq.exception.DataAccessException;
import org.junit.Test;
import testenvironment.TestDataUtil;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by dohee on 23.05.2016.
 */
public class DataAccessExceptionTests {
    @Test
    public void getMessageTest(){
        DataAccessLayerException exception = new DataAccessLayerException(TestDataUtil.getSQLException());

        assertEquals(TestDataUtil.getSQLException().getMessage(), exception.getMessage());
    }

    @Test
    public void getSQLStateTest(){
        DataAccessLayerException exception = new DataAccessLayerException(TestDataUtil.getSQLException());

        assertEquals(TestDataUtil.getSQLException().getSQLState(), exception.getSqlState());
    }
    @Test
    public void getCauseTest(){
        SQLException expected = TestDataUtil.getSQLException();
        DataAccessLayerException exception = new DataAccessLayerException(expected);

        assertEquals(expected.getCause(), exception.getCause());
    }

    @Test
    public void constructorJooqTest(){
        DataAccessException expected = new DataAccessException("Test", TestDataUtil.getSQLException());
        DataAccessLayerException exception = new DataAccessLayerException(expected);

        assertEquals(expected.getMessage(), exception.getMessage());
        assertNull(exception.getSqlState());
        assertEquals(expected.getCause(), exception.getCause());
    }
}
