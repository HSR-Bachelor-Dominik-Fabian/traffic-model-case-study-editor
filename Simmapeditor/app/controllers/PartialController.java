package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.partials.loadChangesetMenu;
import views.html.partials.rootMenu;


public class PartialController extends Controller {

    public static Result rootMenu(){
        return ok(rootMenu.render());
    }

    public static Result loadChangesetMenu(){
        return ok(loadChangesetMenu.render());
    }
}
