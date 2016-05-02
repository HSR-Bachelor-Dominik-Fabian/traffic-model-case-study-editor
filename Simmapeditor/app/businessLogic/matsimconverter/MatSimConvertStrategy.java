package businessLogic.matsimconverter;
import org.json.*;

/**
 * Created by dohee on 07.03.2016.
 */
public interface MatSimConvertStrategy {
    void setBaseJSON(JSONObject baseJSON);
    JSONObject convertMatsim(JSONObject json, String coordinateSystem);

}
