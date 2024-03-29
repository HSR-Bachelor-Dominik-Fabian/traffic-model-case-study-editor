package dataaccess.connectionutils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import testenvironment.TestDataUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.easymock.EasyMock.expect;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DriverManager.class, ProdConnection.class})
public class ProdConnectionTests {
    @Test
    public void testGetConnectionFromProps() throws SQLException {
        final Connection connection = mock(Connection.class);

        mockStatic(DriverManager.class);

        expect(DriverManager.getConnection(TestDataUtil.getTestPSQLPath(), TestDataUtil.getTestUsername()
                , TestDataUtil.getTestPassword())).andReturn(connection);
        replay(DriverManager.class);
        ProdConnection prodConnection = new ProdConnection();
        verify(prodConnection.getConnectionFromProps(TestDataUtil.getTestProperties()));

    }

    @Test(expected = SQLException.class)
    public void testGetConnectionFromPropsDatabaseError() throws SQLException {
        ProdConnection prodConnection = new ProdConnection();
        prodConnection.getConnectionFromProps(TestDataUtil.getTestProperties());
    }
}
