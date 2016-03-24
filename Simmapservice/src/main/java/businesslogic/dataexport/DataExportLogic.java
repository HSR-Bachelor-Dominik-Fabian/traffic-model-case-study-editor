package businesslogic.dataexport;

import businesslogic.Utils.QuadTileUtils;
import dataaccess.SimmapDataAccessFacade;
import dataaccess.database.tables.records.LinkRecord;
import org.jooq.Result;
import org.json.JSONObject;

import java.util.Properties;

/**
 * Created by dohee on 24.03.2016.
 */
public class DataExportLogic {

    public JSONObject getDataForTile(int x, int y , int zoom, int networkid, Properties props){
        JSONObject output = new JSONObject();

        String QuadKey = QuadTileUtils.getQuadTileKey(x, y, zoom);
        QuadKey = "23101222222";
        SimmapDataAccessFacade facade = new SimmapDataAccessFacade(props);
        Result<LinkRecord> result = facade.getLinkFromQuadKey(QuadKey, networkid);



        return output;
    }
}
