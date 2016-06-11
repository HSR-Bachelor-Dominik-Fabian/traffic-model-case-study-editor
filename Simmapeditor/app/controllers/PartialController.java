package controllers;

import play.Play;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.partials.loadChangesetMenu;
import views.html.partials.rootMenu;
import views.html.partials.importMenu;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PartialController extends Controller {

    public static Result rootMenu(){
        return ok(rootMenu.render());
    }

    public static Result loadChangesetMenu(){
        return ok(loadChangesetMenu.render());
    }

    public static Result importMenu() throws IOException {
        Properties properties = new Properties();
        try (BufferedInputStream stream = new BufferedInputStream(
                new FileInputStream(Play.application().classloader().getResource("config.properties").getFile()))) {
            properties.load(stream);
            stream.close();
        } catch (IOException e) {
            throw e;
        }
        return ok(importMenu.render(properties));
    }
}

