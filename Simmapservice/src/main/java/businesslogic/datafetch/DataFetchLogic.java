package businesslogic.datafetch;

import businesslogic.Utils.GeoJSONUtil;
import businesslogic.Utils.QuadTileUtils;
import businesslogic.changeset.LinkModel;
import dataaccess.SimmapDataAccessFacade;
import dataaccess.database.tables.records.LinkRecord;
import org.jooq.Record;
import org.jooq.Result;
import org.json.JSONObject;

import java.sql.Date;
import java.util.Properties;

/**
 * Created by dohee on 24.03.2016.
 */
public class DataFetchLogic {

    private Properties properties;

    public DataFetchLogic(Properties properties) {
        this.properties = properties;
    }

    public JSONObject getDataForTile(int x, int y , int zoom, int networkid){
        JSONObject output;

        String QuadKey = QuadTileUtils.getQuadTileKey(x, y, zoom);
        SimmapDataAccessFacade dataAccess = new SimmapDataAccessFacade(this.properties);
        Result<Record> result = dataAccess.getLinkFromQuadKey(QuadKey, networkid, zoom);

        output = GeoJSONUtil.getGeoFromLinkRequest(result, zoom);

        return output;
    }

    public Date getLastModified(int x, int y , int zoom, int networkid){
        String QuadKey = QuadTileUtils.getQuadTileKey(x, y, zoom);
        SimmapDataAccessFacade dataAccess = new SimmapDataAccessFacade(this.properties);

        return dataAccess.getLastModifiedQuadKey(QuadKey, networkid, zoom);
    }

    public LinkModel getLinkById(String id) {
        JSONObject output;

        SimmapDataAccessFacade dataAccess = new SimmapDataAccessFacade(this.properties);
        LinkRecord link = dataAccess.getLinkFromId(id);
        LinkModel linkModel = new LinkModel(link);

        return linkModel;
    }
}
