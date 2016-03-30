package businesslogic.datafetch;

import businesslogic.Utils.GeoJSONUtil;
import businesslogic.Utils.QuadTileUtils;
import dataaccess.SimmapDataAccessFacade;
import org.jooq.Record;
import org.jooq.Result;
import org.json.JSONObject;

import java.sql.Date;
import java.util.Properties;

/**
 * Created by dohee on 24.03.2016.
 */
public class DataFetchLogic {

    public JSONObject getDataForTile(int x, int y , int zoom, int networkid, Properties props){
        JSONObject output = new JSONObject();

        String QuadKey = QuadTileUtils.getQuadTileKey(x, y, zoom);
        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(props);
        Result<Record> result = facade.getLinkFromQuadKey(QuadKey, networkid, zoom);

        output = GeoJSONUtil.getGeoFromLinkRequest(result, zoom);

        return output;
    }

    public Date getLastModified(int x, int y , int zoom, int networkid, Properties props){
        String QuadKey = QuadTileUtils.getQuadTileKey(x, y, zoom);
        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(props);

        return facade.getLastModifiedQuadKey(QuadKey, networkid, zoom);
    }
}
