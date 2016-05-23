package businesslogic.Utils;

import dataaccess.database.tables.records.NodeRecord;
import org.jooq.Record;
import org.jooq.Result;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

public class GeoJSONUtil {
    public static JSONObject getGeoFromLinkAndNodeRequest(Result<Record> links, Result<Record> nodes, int zoom){
        JSONObject baseJSON = new JSONObject();

        baseJSON.put("type", "FeatureCollection");
        baseJSON.put("features", new JSONArray());

        for (Record link : links){
            JSONObject newFeature = new JSONObject();
            JSONObject geometry = new JSONObject();

            geometry.put("coordinates", new JSONArray(new Object[]{new BigDecimal[]{(BigDecimal)link.getValue("Long1"), (BigDecimal)link.getValue("Lat1")}
                    , new BigDecimal[]{(BigDecimal)link.getValue("Long2"), (BigDecimal) link.getValue("Lat2")}}));
            geometry.put("type", "LineString");
            newFeature.put("geometry",geometry);

            newFeature.put("type", "Feature");
            JSONObject props = new JSONObject();
            props.put("modes", link.getValue("Modes"));
            props.put("length", link.getValue("Length"));
            props.put("permlanes", link.getValue("Permlanes"));
            props.put("oneway", link.getValue("Oneway"));
            props.put("freespeed", link.getValue("Freespeed"));
            props.put("id", link.getValue("Id"));
            props.put("zoomlevel", zoom);
            props.put("capacity", link.getValue("Capacity"));
            newFeature.put("properties", props);

            baseJSON.append("features", newFeature);
        }
        
        for (Record node : nodes) {
            JSONObject newFeature = new JSONObject();
            JSONObject geometry = new JSONObject();

            geometry.put("coordinates", new BigDecimal[]{(BigDecimal)node.getValue("Long"), (BigDecimal)node.getValue("Lat")});
            geometry.put("type", "Point");
            newFeature.put("geometry",geometry);

            newFeature.put("type", "Feature");
            JSONObject props = new JSONObject();
            props.put("id", node.getValue("Id"));
            props.put("zoomlevel", zoom);
            newFeature.put("properties", props);

            baseJSON.append("features", newFeature);
        }
        
        return baseJSON;
    }
}
