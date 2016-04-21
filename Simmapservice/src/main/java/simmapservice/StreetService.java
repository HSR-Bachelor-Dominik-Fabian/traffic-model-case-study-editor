package simmapservice;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import simmapservice.resources.ChangesetResource;
import simmapservice.resources.QuadtileResource;
import simmapservice.resources.StreetServiceResource;
import simmapservice.resources.XMLImportResource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.EnumSet;
import java.util.Properties;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

/**
 * Created by dohee on 15.03.2016.
 */
public class StreetService extends Application<StreetServiceConfiguration>{
    public static void main(String[] args) throws Exception {
        new StreetService().run(args);
    }

    @Override
    public void run(StreetServiceConfiguration configuration, Environment environment) throws Exception {
        Properties properties = new Properties();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream("config.properties"));
        properties.load(stream);
        stream.close();

        StreetServiceResource streetserviceResource = new StreetServiceResource(properties);
        environment.jersey().register(streetserviceResource);
        environment.jersey().register(MultiPartFeature.class);
        environment.jersey().register(new XMLImportResource(properties));
        environment.jersey().register(new QuadtileResource(properties));
        environment.jersey().register(new ChangesetResource(properties));

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
