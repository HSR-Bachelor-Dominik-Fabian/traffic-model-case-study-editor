package dataaccess;

import org.junit.Test;
import testenvironment.TestDataUtil;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DataAccessExceptionTests {
    @Test
    public void getMessageTest() {
        DataAccessException exception = new DataAccessException(TestDataUtil.getSQLException());

        assertEquals(TestDataUtil.getSQLException().getMessage(), exception.getMessage());
    }

    @Test
    public void getSQLStateTest() {
        DataAccessException exception = new DataAccessException(TestDataUtil.getSQLException());

        assertEquals(TestDataUtil.getSQLException().getSQLState(), exception.getSqlState());
    }

    @Test
    public void getCauseTest() {
        SQLException expected = TestDataUtil.getSQLException();
        DataAccessException exception = new DataAccessException(expected);

        assertEquals(expected.getCause(), exception.getCause());
    }

    @Test
    public void constructorJooqTest() {
        org.jooq.exception.DataAccessException expected = new org.jooq.exception.DataAccessException("Test", TestDataUtil.getSQLException());
        DataAccessException exception = new DataAccessException(expected);

        assertEquals(expected.getMessage(), exception.getMessage());
        assertNull(exception.getSqlState());
        assertEquals(expected.getCause(), exception.getCause());
    }
}
