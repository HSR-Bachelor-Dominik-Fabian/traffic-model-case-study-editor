package simmapservice;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import simmapservice.resources.QuadtileResource;
import simmapservice.resources.StreetserviceResource;
import simmapservice.resources.XMLImportResource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.EnumSet;
import java.util.Properties;
import simmapservice.resources.XYToQuadtileResource;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

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
        environment.jersey().register(new QuadtileResource(properties));
        XYToQuadtileResource xyToQuadtileResource = new XYToQuadtileResource();
        environment.jersey().register(xyToQuadtileResource);

        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
