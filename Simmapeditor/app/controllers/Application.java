package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Application extends Controller {

    public static Result map() throws IOException {
        Properties properties = new Properties();
        try (BufferedInputStream stream = new BufferedInputStream(
                new FileInputStream(Play.application().classloader().getResource("config.properties").getFile()))) {
            properties.load(stream);
            stream.close();
        } catch (IOException e) {
            throw e;
        }
        return ok(map.render(properties));
    }
}
