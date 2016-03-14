package businessLogic.matsimconverter;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by dohee on 07.03.2016.
 */
public class ConvertContext {
    private MatSimConvertStrategy strategy;
    ConvertContext(MatSimConvertStrategy strategy){
        this.strategy = strategy;
    }

    public JSONObject execute(JSONObject matSimData, String coordinateSystem){
        JSONObject baseJSON = new JSONObject();

        baseJSON.put("type", "FeatureCollection");
        baseJSON.put("features", new JSONArray());


        this.strategy.setBaseJSON(baseJSON);
        return this.strategy.convertMatsim(matSimData, coordinateSystem);
    }
}
