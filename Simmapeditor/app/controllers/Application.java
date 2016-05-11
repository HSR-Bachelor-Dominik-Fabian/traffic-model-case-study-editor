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

    public static Result map() {
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
        return ok(map.render(properties));
    }
}
