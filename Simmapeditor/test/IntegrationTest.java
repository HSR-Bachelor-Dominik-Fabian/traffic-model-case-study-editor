import org.junit.*;

import play.Application;
import play.GlobalSettings;
import play.Play;
import play.test.*;
import play.twirl.api.Content;
import play.twirl.api.Html;

import java.io.*;
import java.net.URL;
import java.util.Properties;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class IntegrationTest {

    private static FakeApplication fakeApplication;

    @BeforeClass
    public static void startFakeApplication() {
        fakeApplication = fakeApplication( new GlobalSettings() {
            @Override
            public void onStart(Application app) {
                System.out.println("Starting Fake Application");
            }
        });
        start(fakeApplication);
    }

    @AfterClass
    public static void stopFakeApplication() {
        stop(fakeApplication);
    }

    @Test
    public void renderPartialViewRootMenu() {
        Content html = views.html.partials.rootMenu.render();
        assertThat(html.contentType()).isEqualTo("text/html");
    }

    @Test
    public void renderPartialViewLoadChangesetMenu() {
        Content html = views.html.partials.loadChangesetMenu.render();
        assertThat(html.contentType()).isEqualTo("text/html");
    }

    @Test
    public void renderViewMain() {
        Properties properties = getProperties();
        Content html = views.html.main.render(properties, new Html("Dies ist ein Test"));
        assertThat(html.contentType()).isEqualTo("text/html");
    }

    @Test
    public void renderViewMap() {
        Properties properties = getProperties();
        Content html = views.html.map.render(properties);
        assertThat(html.contentType()).isEqualTo("text/html");
    }

    @SuppressWarnings("Duplicates")
    private Properties getProperties() {
        Properties properties = new Properties();
        BufferedInputStream stream = null;
        try {
            String resource = Play.application().classloader().getResource("config.properties").getFile();
            stream = new BufferedInputStream(new FileInputStream(resource));
            properties.load(stream);
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Test
    public void testStartOfWebPage() {
        /*running(testServer(9000, fakeApplication), HTMLUNIT, browser -> {
            browser.goTo("http://localhost:9000");
            System.out.println(browser.pageSource());
            assertThat(browser.pageSource()).contains("");
        });*/
    }
}
