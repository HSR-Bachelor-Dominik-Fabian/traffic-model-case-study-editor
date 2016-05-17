import org.junit.*;
import play.test.*;
import util.FakeApplicationUtil;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.*;

public class FrontendTest extends WithBrowser {

    private static FakeApplication fakeApplication;

    @BeforeClass
    public static void startFakeApplication() {
        fakeApplication = FakeApplicationUtil.startFakeApplication();
    }

    @AfterClass
    public static void stopFakeApplication() {
        FakeApplicationUtil.stopFakeApplication(fakeApplication);
    }

    @Test
    public void testMainPage() {
        /*assertNotNull(browser);
        browser.goTo("/");
        assertThat(browser.pageSource(), containsString(""));*/
    }
}
