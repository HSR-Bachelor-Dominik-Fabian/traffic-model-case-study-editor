package businesslogic.datafetch;

import businesslogic.Utils.GeoJSONUtil;
import businesslogic.Utils.QuadTileUtils;
import businesslogic.changeset.LinkModel;
import common.DataAccessLayerException;
import dataaccess.SimmapDataAccessFacade;
import dataaccess.database.tables.records.LinkRecord;
import dataaccess.utils.ProdConnection;
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

    public JSONObject getDataForTile(int x, int y, int zoom, int networkid) throws DataAccessLayerException {
        JSONObject output;

        String QuadKey = QuadTileUtils.getQuadTileKey(x, y, zoom);
        SimmapDataAccessFacade dataAccess = new SimmapDataAccessFacade(this.properties, new ProdConnection());
        Result<Record> result = dataAccess.getLinkFromQuadKey(QuadKey, networkid, zoom);

        output = GeoJSONUtil.getGeoFromLinkRequest(result, zoom);

        return output;
    }

    public Date getLastModified(int x, int y, int zoom, int networkid) throws DataAccessLayerException {
        String QuadKey = QuadTileUtils.getQuadTileKey(x, y, zoom);
        SimmapDataAccessFacade dataAccess = new SimmapDataAccessFacade(this.properties, new ProdConnection());

        return dataAccess.getLastModifiedQuadKey(QuadKey, networkid, zoom);
    }

    public LinkModel getLinkById(String id) throws DataAccessLayerException {
        JSONObject output;

        SimmapDataAccessFacade dataAccess = new SimmapDataAccessFacade(this.properties, new ProdConnection());
        LinkRecord link = dataAccess.getLinkFromId(id);
        LinkModel linkModel = new LinkModel(link);

        return linkModel;
    }
}
