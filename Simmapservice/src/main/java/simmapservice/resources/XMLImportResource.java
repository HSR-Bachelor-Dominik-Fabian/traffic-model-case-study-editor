package simmapservice.resources;
import businesslogic.xmlImport.XMLImportLogic;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.Properties;

/**
 * Created by dohee on 17.03.2016.
 */
@Path("/xml")
@Consumes(MediaType.MULTIPART_FORM_DATA)
public class XMLImportResource {

    public XMLImportResource(Properties props){
        this.props = props;
    }

    private final Properties props;

    @POST
    public Response postImport(@FormDataParam("file") InputStream inputStream, @FormDataParam("fileName") String fileName,
                               @FormDataParam("format") String format, @Context HttpServletResponse response,
                               @FormDataParam("name") String networkName) throws IOException {

        XMLImportLogic importLogic = new XMLImportLogic(props);
        importLogic.importNetwork2DB(inputStream, format, networkName);

        return Response.ok().build();
    }
}
