package route;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import play.test.FakeApplication;
import util.FakeApplicationUtil;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class RouteTest {

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
    public void testMainMapRoute() {
        Http.RequestBuilder request = new Http.RequestBuilder().method(GET).uri("/");
        Result result = route(request);
        assertThat(result.status()).isEqualTo(OK);
    }

    @Test
    public void testPartialRootMenuRoute() {
        Http.RequestBuilder request = new Http.RequestBuilder().method(GET).uri("/partials/rootmenu");
        Result result = route(request);
        assertThat(result.status()).isEqualTo(OK);
    }

    @Test
    public void testPartialChangeSetMenuRoute() {
        Http.RequestBuilder request = new Http.RequestBuilder().method(GET).uri("/partials/loadchangesetmenu");
        Result result = route(request);
        assertThat(result.status()).isEqualTo(OK);
    }
}
