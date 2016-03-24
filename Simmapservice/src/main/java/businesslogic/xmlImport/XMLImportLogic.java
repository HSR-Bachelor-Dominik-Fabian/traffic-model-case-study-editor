package businesslogic.xmlImport;

import businesslogic.xmlImport.Utils.EPSGTransformUtil;
import businesslogic.xmlImport.Utils.QuadTileUtils;
import com.vividsolutions.jts.geom.Coordinate;
import dataaccess.SimmapDataAccessFacade;
import dataaccess.database.tables.records.LinkRecord;
import dataaccess.database.tables.records.NetworkOptionsRecord;
import dataaccess.database.tables.records.NetworkRecord;
import dataaccess.database.tables.records.NodeRecord;
import org.jooq.Result;
import org.json.JSONArray;
import org.json.JSONObject;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
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
        importOptions2DB(linksElement, networkId);

        JSONArray linksArray = linksElement.getJSONArray("link");
        importLinks2DB(linksArray, transformer, networkId);
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
        long start = System.currentTimeMillis();
        /*
        for (int i = 0; i < nodes.length(); i+=1000) {
            NodeRecord[] records = new NodeRecord[1000];
            for (int j = i; (j < (i + 1000)) && j < nodes.length(); j++) {
                try {
                    JSONObject node = nodes.getJSONObject(j);
                    NodeRecord nodeRecord = new NodeRecord();
                    Object id = node.get("id");
                    nodeRecord.setId(node.get("id").toString());
                    nodeRecord.setNetworkid(networkId);
                    double x = node.getDouble("x"), y = node.getDouble("y");
                    Coordinate nodeCoord = new Coordinate(x, y);
                    Coordinate newNodeCoord = transformer.transform(nodeCoord).getCoordinate();
                    nodeRecord.setQuadkey(QuadTileUtils.getQuadTileKeyFromLatLong(newNodeCoord.x, newNodeCoord.y));
                    nodeRecord.setX(new BigDecimal(x));
                    nodeRecord.setY(new BigDecimal(y));
                    records[j%1000] = nodeRecord;
                } catch (TransformException e) {
                    e.printStackTrace();
                } catch (FactoryException e) {
                    e.printStackTrace();
                }
            }
            dataAccess.setNode(records);
        }
        */

        NodeRecord[] records = new NodeRecord[nodes.length()];
        for (int i = 0; i < nodes.length(); i++) {
            try {
                JSONObject node = nodes.getJSONObject(i);
                NodeRecord nodeRecord = new NodeRecord();
                Object id = node.get("id");
                nodeRecord.setId(node.get("id").toString());
                nodeRecord.setNetworkid(networkId);
                double x = node.getDouble("x"), y = node.getDouble("y");
                Coordinate nodeCoord = new Coordinate(x, y);
                Coordinate newNodeCoord = transformer.transform(nodeCoord).getCoordinate();
                nodeRecord.setQuadkey(QuadTileUtils.getQuadTileKeyFromLatLong(newNodeCoord.y, newNodeCoord.x));
                nodeRecord.setX(new BigDecimal(x));
                nodeRecord.setY(new BigDecimal(y));
                records[i] = nodeRecord;
            } catch (TransformException e) {
                e.printStackTrace();
            } catch (FactoryException e) {
                e.printStackTrace();
            }
        }
        dataAccess.setNode(records);
        System.out.println("Time: " + (System.currentTimeMillis() - start));
    }

    private void importOptions2DB(JSONObject linksElement, int networkId) {
        NetworkOptionsRecord[] options = new NetworkOptionsRecord[3];
        NetworkOptionsRecord capperiod = new NetworkOptionsRecord();
        capperiod.setNetworkid(networkId);
        capperiod.setOptionname("capperiod");
        capperiod.setValue(linksElement.get("capperiod").toString());
        options[0] = capperiod;

        NetworkOptionsRecord effectivecellsize = new NetworkOptionsRecord();
        effectivecellsize.setNetworkid(networkId);
        effectivecellsize.setOptionname("effectivecellsize");
        effectivecellsize.setValue(linksElement.get("effectivecellsize").toString());
        options[1] = effectivecellsize;

        NetworkOptionsRecord effectivelanewidth = new NetworkOptionsRecord();
        effectivelanewidth.setNetworkid(networkId);
        effectivelanewidth.setOptionname("effectivelanewidth");
        effectivelanewidth.setValue(linksElement.get("effectivelanewidth").toString());
        options[2] = effectivelanewidth;

        dataAccess.setNetworkOptions(options);
    }

    private void importLinks2DB(JSONArray links, EPSGTransformUtil transformer, int networkId) {
        long start = System.currentTimeMillis();
        LinkRecord[] linkRecords = new LinkRecord[links.length()];
        Result<NodeRecord> nodes = dataAccess.getAllNodes();
        Map<String, NodeRecord> nodesMap = new HashMap<>();
        for (NodeRecord node : nodes) {
            nodesMap.put(node.getId(), node);
        }

        for (int i = 0; i < links.length(); i++) {
            try {
                JSONObject link = links.getJSONObject(i);
                LinkRecord newLink = new LinkRecord();

                newLink.setId(link.get("id").toString());

                String from = link.get("from").toString();
                NodeRecord fromNode = nodesMap.get(from);
                Coordinate fromCoord = new Coordinate(fromNode.getX().doubleValue(), fromNode.getY().doubleValue());
                fromCoord = transformer.transform(fromCoord).getCoordinate();
                newLink.setFrom(fromNode.getId());

                String to = link.get("to").toString();
                NodeRecord toNode = nodesMap.get(to);
                Coordinate toCoord = new Coordinate(toNode.getX().doubleValue(), toNode.getY().doubleValue());
                toCoord = transformer.transform(toCoord).getCoordinate();
                newLink.setTo(toNode.getId());

                newLink.setQuadkey(QuadTileUtils
                        .getMinCommonQuadTileKeyFromLatLong(fromCoord.y, fromCoord.x, toCoord.y, toCoord.x));
                newLink.setOneway(Boolean.parseBoolean(link.get("oneway").toString()));
                newLink.setNetworkid(networkId);
                newLink.setFreespeed(new BigDecimal(link.get("freespeed").toString()));
                newLink.setCapacity(new BigDecimal(link.get("capacity").toString()));
                newLink.setPermlanes(new BigDecimal(link.get("permlanes").toString()));
                newLink.setModes(link.get("modes").toString());
                newLink.setLength(new BigDecimal(link.get("length").toString()));

                linkRecords[i] = newLink;
            } catch (TransformException e) {
                e.printStackTrace();
            } catch (FactoryException e) {
                e.printStackTrace();
            }
        }
        dataAccess.setLink(linkRecords);
        System.out.println("Time: " + (System.currentTimeMillis() - start));
    }
}
