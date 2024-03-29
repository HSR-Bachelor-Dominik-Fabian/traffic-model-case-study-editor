package simmapservice.resources;

import businesslogic.xmlImport.XMLImportLogic;
import dataaccess.DataAccessException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Path("/xml")
@Consumes(MediaType.MULTIPART_FORM_DATA)
public class XMLImportResource {

    public XMLImportResource(Properties props) {
        this.props = props;
    }

    private final Properties props;

    @POST
    public Response postImport(@FormDataParam("file") InputStream inputStream, @FormDataParam("fileName") String fileName,
                               @FormDataParam("format") String format, @Context HttpServletResponse response,
                               @FormDataParam("name") String networkName) throws IOException {
        try {
            XMLImportLogic importLogic = new XMLImportLogic(props);
            importLogic.importNetwork2DB(inputStream, format, networkName);

            return Response.ok().build();
        } catch (DataAccessException | FactoryException | TransformException | XMLStreamException exc) {
            return Response.serverError().entity(exc).type(MediaType.APPLICATION_JSON).build();
        }
    }
}
