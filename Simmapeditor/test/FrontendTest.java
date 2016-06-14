import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import play.test.WithBrowser;
import util.FakeApplicationUtil;

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
