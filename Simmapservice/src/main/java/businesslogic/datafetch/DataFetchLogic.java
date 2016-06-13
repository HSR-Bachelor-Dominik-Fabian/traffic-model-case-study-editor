package businesslogic.datafetch;

import businesslogic.changeset.LinkModel;
import businesslogic.utils.GeoJSONUtil;
import businesslogic.utils.QuadTileUtils;
import dataaccess.DataAccessException;
import dataaccess.DataAccessLogic;
import dataaccess.database.tables.records.LinkRecord;
import dataaccess.utils.ProdConnection;
import org.jooq.Record;
import org.jooq.Result;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataFetchLogic {

    private Properties properties;

    public DataFetchLogic(Properties properties) {
        this.properties = properties;
    }

    public JSONObject getDataForTile(int x, int y, int zoom, int networkid) throws DataAccessException {
        JSONObject output;

        String quadKey = QuadTileUtils.getQuadTileKey(x, y, zoom);
        DataAccessLogic dataAccess = new DataAccessLogic(this.properties, new ProdConnection());
        Result<Record> links = dataAccess.getLinksFromQuadKey(quadKey, networkid, zoom);

        output = GeoJSONUtil.getGeoJsonFromLinkRequest(links, zoom);

        return output;
    }

    public JSONObject getDataForTileWithNodes(int x, int y, int zoom, int networkid) throws DataAccessException {
        JSONObject output = null;

        if (zoom > 15) {
            String quadKey = QuadTileUtils.getQuadTileKey(x, y, zoom);
            DataAccessLogic dataAccess = new DataAccessLogic(this.properties, new ProdConnection());
            Result<Record> links = dataAccess.getLinksFromQuadKey(quadKey, networkid, zoom);
            List<String> nodeIds = new ArrayList<>();
            for (Record link : links) {
                nodeIds.add((String) link.getValue("From"));
                nodeIds.add((String) link.getValue("To"));
            }

            Result<Record> nodes = dataAccess.getNodesFromIds(nodeIds, networkid);

            output = GeoJSONUtil.getGeoJsonFromLinkAndNodeRequest(links, nodes, zoom);
        }

        return output;
    }

    public Date getLastModified(int x, int y, int zoom, int networkid) throws DataAccessException {
        String QuadKey = QuadTileUtils.getQuadTileKey(x, y, zoom);
        DataAccessLogic dataAccess = new DataAccessLogic(this.properties, new ProdConnection());

        return dataAccess.getLastModifiedQuadKey(QuadKey, networkid, zoom);
    }

    public LinkModel getLinkById(String id) throws DataAccessException {
        JSONObject output;

        DataAccessLogic dataAccess = new DataAccessLogic(this.properties, new ProdConnection());
        LinkRecord link = dataAccess.getLinkFromId(id);
        LinkModel linkModel = new LinkModel(link);

        return linkModel;
    }
}
