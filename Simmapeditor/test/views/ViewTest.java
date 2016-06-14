package views;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Play;
import play.test.FakeApplication;
import play.twirl.api.Content;
import play.twirl.api.Html;
import util.FakeApplicationUtil;
import views.html.partials.loadChangesetMenu;
import views.html.partials.rootMenu;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static org.fest.assertions.Assertions.assertThat;

public class ViewTest {

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
    public void renderPartialViewRootMenu() {
        Content html = rootMenu.render();
        assertThat(html.contentType()).isEqualTo("text/html");
    }

    @Test
    public void renderPartialViewLoadChangesetMenu() {
        Content html = loadChangesetMenu.render();
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
}
