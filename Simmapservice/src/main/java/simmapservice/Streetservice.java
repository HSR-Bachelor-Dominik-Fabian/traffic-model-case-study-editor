package simmapservice;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import simmapservice.resources.StreetserviceResource;

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
    }
}
