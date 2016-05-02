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
    public static JSONObject getGeoFromLinkRequest(Result<Record> result, int zoom){
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
            props.put("permlanes", record.getValue("Permlanes"));
            props.put("oneway", record.getValue("Oneway"));
            props.put("freespeed", record.getValue("Freespeed"));
            props.put("id", record.getValue("Id"));
            props.put("zoomlevel", zoom);
            props.put("capacity", record.getValue("Capacity"));
            newFeature.put("properties", props);

            baseJSON.append("features", newFeature);
        }
        return baseJSON;
    }
}
