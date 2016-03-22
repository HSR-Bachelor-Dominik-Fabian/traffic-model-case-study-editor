package simmapservice;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import simmapservice.resources.QuadtileResource;
import simmapservice.resources.StreetserviceResource;
import simmapservice.resources.XYToQuadtileResource;

/**
 * Created by dohee on 15.03.2016.
 */
public class Streetservice extends Application<StreetserviceConfiguration>{
    public static void main(String[] args) throws Exception {
        new Streetservice().run(args);
    }

    @Override
    public void run(StreetserviceConfiguration configuration, Environment environment) throws Exception {
        StreetserviceResource streetserviceResource = new StreetserviceResource();
        environment.jersey().register(streetserviceResource);
        QuadtileResource quadtileResource = new QuadtileResource();
        environment.jersey().register(quadtileResource);
        XYToQuadtileResource xyToQuadtileResource = new XYToQuadtileResource();
        environment.jersey().register(xyToQuadtileResource);
    }
}
