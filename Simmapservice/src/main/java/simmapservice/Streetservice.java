package simmapservice;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import simmapservice.resources.QuadtileResource;
import simmapservice.resources.StreetserviceResource;
import simmapservice.resources.XMLImportResource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;
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

        Properties properties = new Properties();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream("config.properties"));
        properties.load(stream);
        stream.close();



        StreetserviceResource streetserviceResource = new StreetserviceResource();
        environment.jersey().register(streetserviceResource);
        environment.jersey().register(MultiPartFeature.class);
        environment.jersey().register(new XMLImportResource(properties));
        QuadtileResource quadtileResource = new QuadtileResource();
        environment.jersey().register(quadtileResource);
        XYToQuadtileResource xyToQuadtileResource = new XYToQuadtileResource();
        environment.jersey().register(xyToQuadtileResource);
    }
}
