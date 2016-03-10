
package businessLogic.matsimconverter;

import com.vividsolutions.jts.geom.Coordinate;
import org.json.*;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;

/**
 * Created by dohee on 07.03.2016.
 */
public class NetworkConvertStrategy implements MatSimConvertStrategy{
    private JSONObject baseJSON;

    public JSONObject convertMatsim(JSONObject json, String coordinateSystem){
        JSONObject output = baseJSON;
        JSONObject links = (JSONObject) json.get("links");
        JSONArray nodes = json.getJSONObject("nodes").getJSONArray("node");
        JSONObject properties = new JSONObject();
        properties.put("capperiod",links.get("capperiod"));
        properties.put("effectivecellsize", links.get("effectivecellsize"));
        properties.put("effectivelanewidth", links.get("effectivelanewidth"));
        output.put("properties", properties);
        try {
            EpsgTransform transformer = new EpsgTransform(coordinateSystem);
            fillFeatures(output, links, nodes,transformer);
        } catch (FactoryException e) {
            e.printStackTrace();
        }




        return output;
    }

    private void fillFeatures(JSONObject output, JSONObject links, JSONArray nodes, EpsgTransform transformer) {
        JSONArray link = (JSONArray) links.get("link");
        for(int i = 0; i < link.length(); i++){
            JSONObject item = link.getJSONObject(i);

            String from = item.get("from").toString();
            String to = item.get("to").toString();

            JSONObject fromObj = findNode(from, nodes);
            JSONObject toObj = findNode(to,nodes);

            JSONObject geometry = new JSONObject();



            Coordinate fromCoord = new Coordinate(fromObj.getDouble("x"),fromObj.getDouble("y"));
            Coordinate toCoord = new Coordinate(toObj.getDouble("x"),toObj.getDouble("y"));
            try {
                Coordinate newFromCoord = transformer.transform(fromCoord).getCoordinate();
                Coordinate newToCoord = transformer.transform(toCoord).getCoordinate();


                geometry.put("type", "LineString");
                geometry.put("coordinates",new JSONArray(new Object[]{new Double[]{newFromCoord.x,newFromCoord.y},new Double[]{newToCoord.x,newToCoord.y}}));

                JSONObject props = new JSONObject();
                props.put("id", item.get("id"));
                props.put("oneway", item.get("oneway"));
                props.put("freespeed", item.get("freespeed"));
                props.put("capacity", item.get("capacity"));
                props.put("permlanes", item.get("permlanes"));
                props.put("modes", item.get("modes"));
                props.put("length", item.get("length"));

                JSONObject newLinkItem = new JSONObject();
                newLinkItem.put("properties", props);
                newLinkItem.put("type", "Feature");
                newLinkItem.put("geometry", geometry);

                output.append("features", newLinkItem);

            } catch (TransformException e) {
                e.printStackTrace();
            } catch (FactoryException e) {
                e.printStackTrace();
            }
            double progress = (100.0/link.length())*i;
            System.out.println((int)progress + "% ... Abgeschlossen");
        }
    }

    private JSONObject findNode(String id, JSONArray nodes){
        JSONObject output = null;

        for(Object obj : nodes){
            JSONObject jsonObject = (JSONObject)obj;
            String idProp = jsonObject.get("id").toString();
            if(idProp.equals(id)){
                output = jsonObject;
            }
        }

        return output;
    }

    public void setBaseJSON(JSONObject baseJSON) {
        this.baseJSON = baseJSON;
    }
}
