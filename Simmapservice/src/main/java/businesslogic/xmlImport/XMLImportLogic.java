package businesslogic.xmlImport;

import businesslogic.xmlImport.Utils.EPSGTransformUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.opengis.referencing.FactoryException;

import java.util.Properties;

/**
 * Created by fke on 22.03.2016.
 */
public class XMLImportLogic {

    private final Properties properties;

    public XMLImportLogic(Properties properties) {
        this.properties = properties;
    }

    public void importNetwork2DB(JSONObject jsonObject, String format) {
        JSONObject network = jsonObject.getJSONObject("network");
        EPSGTransformUtil transformer = null;
        try {
            transformer = new EPSGTransformUtil(format);
        } catch (FactoryException e) {
            e.printStackTrace();
        }

        if (transformer == null) {
            throw new IllegalArgumentException("GeoJSON format is not supported");
        }

        // process nodes
        JSONArray nodesArray = network.getJSONObject("nodes").getJSONArray("node");
        importNodes2DB(nodesArray, transformer);

        // process links
        JSONObject linksElement = network.getJSONObject("links");
        importLinksProperties2DB(linksElement, transformer);

        JSONArray linksArray = linksElement.getJSONArray("link");
        importLinks2DB(linksArray, transformer);
    }

    private void importNodes2DB(JSONArray nodes, EPSGTransformUtil transformer) {

    }

    private void importLinksProperties2DB(JSONObject linksElement, EPSGTransformUtil transformer) {
        linksElement.get("capperiod");
        linksElement.get("effectivecellsize");
        linksElement.get("effectivelanewidth");
    }

    private void importLinks2DB(JSONArray links, EPSGTransformUtil transformer) {
        for (int i = 0; i < links.length(); i++) {
            JSONObject link = links.getJSONObject(i);

            String from = link.get("from").toString();
            String to = link.get("to").toString();

            link.get("id");
            link.get("oneway");
            link.get("freespeed");
            link.get("capacity");
            link.get("permlanes");
            link.get("modes");
            link.get("length");
        }
    }
}
