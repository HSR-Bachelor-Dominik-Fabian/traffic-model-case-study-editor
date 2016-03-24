package businesslogic.Utils;

import org.jooq.Record;
import org.jooq.Result;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * Created by dohee on 24.03.2016.
 */
public class GeoJSONUtil {
    public static JSONObject getGeoFromLinkRequest(Result<Record> result){
        JSONObject baseJSON = new JSONObject();

        baseJSON.put("type", "FeatureCollection");
        baseJSON.put("features", new JSONArray());

        for(Record record : result){
            JSONObject newFeature = new JSONObject();
            JSONObject geometry = new JSONObject();

            geometry.put("coordinates", new JSONArray(new Object[]{new BigDecimal[]{(BigDecimal)record.getValue("Long1"), (BigDecimal)record.getValue("Lat1")}
                    , new BigDecimal[]{(BigDecimal)record.getValue("Long2"), (BigDecimal) record.getValue("Lat2")}}));
            geometry.put("type", "LineString");
            newFeature.put("geometry",geometry);

            newFeature.put("type", "Feature");
            JSONObject props = new JSONObject();
            props.put("modes", record.getValue("Modes"));
            props.put("length", record.getValue("Length"));
            props.put("modes", record.getValue("Modes"));
            props.put("id", record.getValue("Id"));
            //TODO: Add Properties
            newFeature.put("properties", props);

            baseJSON.append("features", newFeature);
        }
        return baseJSON;
    }
}
