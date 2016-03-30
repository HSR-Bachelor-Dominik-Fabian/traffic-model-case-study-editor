package simmapservice.resources;
import businesslogic.xmlImport.XMLImportLogic;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
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

        //String xml = IOUtils.toString(inputStream);
        //JSONObject jsonObject = XML.toJSONObject(xml);

        //StringWriter writer = new StringWriter();
        //IOUtils.copy(inputStream, writer);
        //String theString = writer.toString();
        //JSONObject jsonObject = XML.toJSONObject(theString);

        XMLImportLogic importLogic = new XMLImportLogic(props);
        importLogic.importNetwork2DB(inputStream, format, networkName);

        return Response.ok().build();
    }
}
