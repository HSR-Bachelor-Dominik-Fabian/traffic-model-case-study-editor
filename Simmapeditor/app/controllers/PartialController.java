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

    public static Result importMenu(){
        Properties properties = new Properties();
        BufferedInputStream stream = null;
        try {
            String resource = Play.application().classloader().getResource("config.properties").getFile();
            stream = new BufferedInputStream(new FileInputStream(resource));
            properties.load(stream);
            stream.close();
        } catch (FileNotFoundException e) {
            return internalServerError(String.valueOf(e));
        } catch (IOException e) {
            return internalServerError(String.valueOf(e));
        }
        return ok(importMenu.render(properties));
    }
}

