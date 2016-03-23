package businesslogic.xmlImport;

import businesslogic.xmlImport.Utils.EPSGTransformUtil;
import businesslogic.xmlImport.Utils.QuadTileUtils;
import dataaccess.SimmapDataAccessFacade;
import dataaccess.database.tables.records.NetworkRecord;
import dataaccess.database.tables.records.NodeRecord;
import org.json.JSONArray;
import org.json.JSONObject;
import org.opengis.referencing.FactoryException;

import java.util.Properties;

/**
 * Created by fke on 22.03.2016.
 */
public class XMLImportLogic {

    private final Properties properties;
    private final SimmapDataAccessFacade dataAccess;

    public XMLImportLogic(Properties properties) {
        this.properties = properties;
        this.dataAccess = new SimmapDataAccessFacade(properties);
    }

    public void importNetwork2DB(JSONObject jsonObject, String format, String networkName) {
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

        // process network
        int networkId = importNetwork2DB(networkName);

        // process nodes
        JSONArray nodesArray = network.getJSONObject("nodes").getJSONArray("node");
        importNodes2DB(nodesArray, transformer, networkId);

        // process links
        JSONObject linksElement = network.getJSONObject("links");
        importLinksProperties2DB(linksElement, transformer);

        JSONArray linksArray = linksElement.getJSONArray("link");
        importLinks2DB(linksArray, transformer);
    }

    private int importNetwork2DB(String networkName) {
        // TODO: dynamically detect id, after autoincrement
        NetworkRecord network = new NetworkRecord();
        network.setId(1);
        network.setName(networkName);
        dataAccess.setNetwork(new NetworkRecord[] {network});
        return 1;
    }

    private void importNodes2DB(JSONArray nodes, EPSGTransformUtil transformer, int networkId) {
        for (int i = 0; i < nodes.length(); i+=1000) {
            NodeRecord[] records = new NodeRecord[1000];
            for (int j = i; j < i + 1000; j++) {
                JSONObject node = nodes.getJSONObject(j);
                NodeRecord nodeRecord = new NodeRecord();
                nodeRecord.setId(node.getString("id"));
                nodeRecord.setNetworkid(networkId);
                String x = node.getString("x"), y = node.getString("y");
                nodeRecord.setQuadkey(QuadTileUtils.getQuadTileKeyFromLatLong(Double.parseDouble(x), Double.parseDouble(y)));
                nodeRecord.setX(x);
                nodeRecord.setY(y);
                records[i] = nodeRecord;
            }
            dataAccess.setNode(records);
        }
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
