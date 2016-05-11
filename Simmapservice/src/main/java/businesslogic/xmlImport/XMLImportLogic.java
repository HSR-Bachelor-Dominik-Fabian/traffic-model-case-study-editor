package businesslogic.xmlImport;

import businesslogic.Utils.EPSGTransformUtil;
import businesslogic.Utils.QuadTileUtils;
import com.google.common.base.Stopwatch;
import com.vividsolutions.jts.geom.Coordinate;
import dataaccess.SimmapDataAccessFacade;
import dataaccess.database.tables.records.LinkRecord;
import dataaccess.database.tables.records.NetworkOptionsRecord;
import dataaccess.database.tables.records.NetworkRecord;
import dataaccess.database.tables.records.NodeRecord;
import dataaccess.utils.ProdConnection;
import org.jooq.Result;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by fke on 22.03.2016.
 */
public class XMLImportLogic {
    private final SimmapDataAccessFacade dataAccess;

    public XMLImportLogic(Properties properties) {
        this.dataAccess = new SimmapDataAccessFacade(properties, new ProdConnection());
    }

    public void importNetwork2DB(InputStream inputStream, String format, String networkName) {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();

            XMLStreamReader streamReader = factory.createXMLStreamReader(inputStream);

            int networkId = importNetwork2DB(networkName);

            EPSGTransformUtil transformer = null;
            try {
                transformer = new EPSGTransformUtil(format);
            } catch (FactoryException e) {
                e.printStackTrace();
            }

            if (transformer == null) {
                throw new IllegalArgumentException("GeoJSON format is not supported");
            }

            while(streamReader.hasNext()) {
                if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    if (streamReader.getName().getLocalPart().equals("nodes")) {
                        streamReader.next();
                        break;
                    }
                }
                streamReader.next();
            }

            importNodes2DB(streamReader, transformer, networkId);

            while(streamReader.hasNext()) {
                streamReader.next();
                if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    if (streamReader.getName().getLocalPart().equals("links")) {
                        break;
                    }
                }
            }

            importOptions2DB(streamReader, networkId);

            importLinks2DB(streamReader, transformer, networkId);

        } catch (XMLStreamException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int importNetwork2DB(String networkName) {
        // TODO: dynamically detect id, after autoincrement
        NetworkRecord network = new NetworkRecord();
        network.setId(1);
        network.setName(networkName);
        dataAccess.setNetwork(new NetworkRecord[] {network});
        return 1;
    }

    private void importNodes2DB(XMLStreamReader streamReader, EPSGTransformUtil transformer, int networkId) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        try {
            NodeRecord[] nodeRecords = new NodeRecord[25000];
            int index = 0;
            while(streamReader.hasNext()) {
                if (streamReader.getEventType() == XMLStreamReader.SPACE
                        || streamReader.getEventType() == XMLStreamReader.END_ELEMENT) {
                    streamReader.next();
                    continue;
                } else if (streamReader.getEventType() != XMLStreamReader.START_ELEMENT) {
                    break;
                }
                NodeRecord newNodeRecord = new NodeRecord();
                newNodeRecord.setNetworkid(networkId);
                String id = streamReader.getAttributeValue(0);
                newNodeRecord.setId(id);
                String x = streamReader.getAttributeValue(1);
                newNodeRecord.setX(new BigDecimal(x));
                String y = streamReader.getAttributeValue(2);
                newNodeRecord.setY(new BigDecimal(y));
                Coordinate nodeCoord = new Coordinate(Double.parseDouble(x), Double.parseDouble(y));
                nodeCoord = transformer.transform(nodeCoord).getCoordinate();
                newNodeRecord.setQuadkey(QuadTileUtils.getQuadTileKeyFromLatLong(nodeCoord.y, nodeCoord.x));
                newNodeRecord.setLat(new BigDecimal(nodeCoord.y));
                newNodeRecord.setLong(new BigDecimal(nodeCoord.x));
                nodeRecords[index%25000] = newNodeRecord;
                index++;
                if (index != 0 && index%25000 == 0) {
                    dataAccess.setNode(nodeRecords);
                    nodeRecords = new NodeRecord[25000];
                    index = 0;
                }
                streamReader.next();
            }
            dataAccess.setNode(nodeRecords);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (TransformException e) {
            e.printStackTrace();
        } catch (FactoryException e) {
            e.printStackTrace();
        }
        System.out.println("Time: " + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }

    private void importOptions2DB(XMLStreamReader streamReader, int networkId) {
        NetworkOptionsRecord[] options = new NetworkOptionsRecord[3];

        if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
            NetworkOptionsRecord capperiod = new NetworkOptionsRecord();
            capperiod.setNetworkid(networkId);
            capperiod.setOptionname("capperiod");
            capperiod.setValue(streamReader.getAttributeValue(0));
            options[0] = capperiod;

            NetworkOptionsRecord effectivecellsize = new NetworkOptionsRecord();
            effectivecellsize.setNetworkid(networkId);
            effectivecellsize.setOptionname("effectivecellsize");
            effectivecellsize.setValue(streamReader.getAttributeValue(1));
            options[1] = effectivecellsize;

            NetworkOptionsRecord effectivelanewidth = new NetworkOptionsRecord();
            effectivelanewidth.setNetworkid(networkId);
            effectivelanewidth.setOptionname("effectivelanewidth");
            effectivelanewidth.setValue(streamReader.getAttributeValue(2));
            options[2] = effectivelanewidth;
        }
        dataAccess.setNetworkOptions(options);
        try {
            streamReader.next();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void importLinks2DB(XMLStreamReader streamReader, EPSGTransformUtil transformer, int networkId) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        Result<NodeRecord> nodes = dataAccess.getAllNodes();
        Map<String, NodeRecord> nodesMap = new HashMap<>();
        for (NodeRecord node : nodes) {
            nodesMap.put(node.getId(), node);
        }

        try {
            LinkRecord[] linkRecords = new LinkRecord[25000];
            int index = 0;
            while(streamReader.hasNext()) {
                if (streamReader.getEventType() == XMLStreamReader.SPACE
                        || streamReader.getEventType() == XMLStreamReader.END_ELEMENT) {
                    streamReader.next();
                    continue;
                } else if (streamReader.getEventType() != XMLStreamReader.START_ELEMENT) {
                    break;
                }
                LinkRecord newLink = fillLinkRecord(streamReader, transformer, networkId, nodesMap);
                linkRecords[index%25000] = newLink;
                index++;
                if (index != 0 && index%25000 == 0) {
                    dataAccess.setLink(linkRecords);
                    linkRecords = new LinkRecord[25000];
                    index = 0;
                }
                streamReader.next();
            }
            dataAccess.setLink(linkRecords);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (TransformException e) {
            e.printStackTrace();
        } catch (FactoryException e) {
            e.printStackTrace();
        }
        System.out.println("Time: " + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
    }

    private LinkRecord fillLinkRecord(XMLStreamReader streamReader, EPSGTransformUtil transformer, int networkId, Map<String,
            NodeRecord> nodesMap) throws TransformException, FactoryException {

        LinkRecord newLink = new LinkRecord();
        newLink.setId(streamReader.getAttributeValue(0));
        String from = streamReader.getAttributeValue(1);
        NodeRecord fromNode = nodesMap.get(from);
        Coordinate fromCoord = new Coordinate(fromNode.getX().doubleValue(), fromNode.getY().doubleValue());
        fromCoord = transformer.transform(fromCoord).getCoordinate();
        newLink.setFrom(fromNode.getId());

        String to = streamReader.getAttributeValue(2);
        NodeRecord toNode = nodesMap.get(to);
        Coordinate toCoord = new Coordinate(toNode.getX().doubleValue(), toNode.getY().doubleValue());
        toCoord = transformer.transform(toCoord).getCoordinate();
        newLink.setTo(toNode.getId());

        newLink.setQuadkey(QuadTileUtils
                .getMinCommonQuadTileKeyFromLatLong(fromCoord.y, fromCoord.x, toCoord.y, toCoord.x));
        newLink.setOneway(Boolean.parseBoolean(streamReader.getAttributeValue(7)));
        newLink.setNetworkid(networkId);
        newLink.setFreespeed(new BigDecimal(streamReader.getAttributeValue(4)));
        newLink.setCapacity(new BigDecimal(streamReader.getAttributeValue(5)));
        newLink.setPermlanes(new BigDecimal(streamReader.getAttributeValue(6)));
        newLink.setModes(streamReader.getAttributeValue(8));
        newLink.setLength(new BigDecimal(streamReader.getAttributeValue(3)));
        newLink.setMinlevel(calculateMinLevel(newLink));
        newLink.setLastmodified(Date.valueOf(LocalDate.now()));
        newLink.setLat1(fromNode.getLat());
        newLink.setLong1(fromNode.getLong());
        newLink.setLat2(toNode.getLat());
        newLink.setLong2(toNode.getLong());

        return newLink;
    }

    private int calculateMinLevel(LinkRecord newLink) {
        if ("pt".equals(newLink.getModes())) {
            return 22;
        }
        BigDecimal capacity = newLink.getCapacity();
        BigDecimal speed = newLink.getFreespeed();
        BigDecimal permlanes = newLink.getLength();
        if (speed.doubleValue() > 30.0 && permlanes.doubleValue() > 2) {
            return 10;
        } else if (speed.doubleValue() > 30.0) {
            return 11;
        } else if (speed.doubleValue() > 23.0) {
            return 12;
        } else if (speed.doubleValue() > 14.0) {
            return 13;
        } else if (speed.doubleValue() > 13.0 && capacity.doubleValue() >= 4000.0) {
            return 14;
        } else if (speed.doubleValue() > 13.0) {
            return 15;
        } else {
            return 16;
        }
    }
}
