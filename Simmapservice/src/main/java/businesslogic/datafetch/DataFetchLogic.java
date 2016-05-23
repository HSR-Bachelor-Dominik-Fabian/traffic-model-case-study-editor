package businesslogic.datafetch;

import businesslogic.Utils.GeoJSONUtil;
import businesslogic.Utils.QuadTileUtils;
import businesslogic.changeset.LinkModel;
import dataaccess.SimmapDataAccessFacade;
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

    public JSONObject getDataForTile(int x, int y , int zoom, int networkid){
        JSONObject output;

        String quadKey = QuadTileUtils.getQuadTileKey(x, y, zoom);
        SimmapDataAccessFacade dataAccess = new SimmapDataAccessFacade(this.properties, new ProdConnection());
        Result<Record> links = dataAccess.getLinksFromQuadKey(quadKey, networkid, zoom);
        List<String> nodeIds = new ArrayList<>();
        for(Record link : links) {
            nodeIds.add((String) link.getValue("From"));
            nodeIds.add((String) link.getValue("To"));
        }

        Result<Record> nodes = dataAccess.getNodesFromIds(nodeIds, networkid);

        output = GeoJSONUtil.getGeoFromLinkAndNodeRequest(links, nodes, zoom);

        return output;
    }

    public Date getLastModified(int x, int y , int zoom, int networkid){
        String QuadKey = QuadTileUtils.getQuadTileKey(x, y, zoom);
        SimmapDataAccessFacade dataAccess = new SimmapDataAccessFacade(this.properties, new ProdConnection());

        return dataAccess.getLastModifiedQuadKey(QuadKey, networkid, zoom);
    }

    public LinkModel getLinkById(String id) {
        JSONObject output;

        SimmapDataAccessFacade dataAccess = new SimmapDataAccessFacade(this.properties, new ProdConnection());
        LinkRecord link = dataAccess.getLinkFromId(id);
        LinkModel linkModel = new LinkModel(link);

        return linkModel;
    }
}
