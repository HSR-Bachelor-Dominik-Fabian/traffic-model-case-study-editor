package businesslogic.utils;

import org.jooq.Record;
import org.jooq.Result;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import testenvironment.TestDataUtil;

import java.util.List;

import static org.junit.Assert.*;

public class GeoJSONUtilTests {

    @Test
    public void testGetGeoJsonFromLinkAndNodeRequest() {
        List<Record> links = TestDataUtil.getMultipleSelectLinkTestRecords();
        List<Record> nodes = TestDataUtil.getMultipleNodeTestRecords();
        JSONObject result = GeoJSONUtil.getGeoJsonFromLinkAndNodeRequest(links, nodes, 10);

        JSONArray features = (JSONArray) result.get("features");
        assertEquals(links.size() + nodes.size(), features.length());
        assertEquals("{\"features\":[{\"geometry\":{\"coordinates\":[[12,12],[23,23]],\"type\":\"LineString\"},\"type\":\"Feature\",\"properties\":{\"modes\":\"car\",\"zoomlevel\":10,\"length\":1200,\"freespeed\":33.3299999999999982946974341757595539093017578125,\"permlanes\":2,\"from\":\"N1\",\"id\":\"L1\",\"to\":\"N2\",\"oneway\":true,\"capacity\":1000}},{\"geometry\":{\"coordinates\":[[12,12],[23,23]],\"type\":\"LineString\"},\"type\":\"Feature\",\"properties\":{\"modes\":\"car\",\"zoomlevel\":10,\"length\":1200,\"freespeed\":33.3299999999999982946974341757595539093017578125,\"permlanes\":2,\"id\":\"L2\",\"oneway\":true,\"capacity\":1000}},{\"geometry\":{\"coordinates\":[23,12],\"type\":\"Point\"},\"type\":\"Feature\",\"properties\":{\"zoomlevel\":10,\"id\":\"N1\"}},{\"geometry\":{\"coordinates\":[23,12],\"type\":\"Point\"},\"type\":\"Feature\",\"properties\":{\"zoomlevel\":10,\"id\":\"N2\"}}],\"type\":\"FeatureCollection\"}", result.toString());
    }

    @Test
    public void testGetGeoJsonFromLinkRequest() {
        List<Record> links = TestDataUtil.getMultipleSelectLinkTestRecords();
        JSONObject result = GeoJSONUtil.getGeoJsonFromLinkRequest(links, 10);

        JSONArray features = (JSONArray) result.get("features");
        assertEquals(links.size(), features.length());
        assertEquals("{\"features\":[{\"geometry\":{\"coordinates\":[[12,12],[23,23]],\"type\":\"LineString\"},\"type\":\"Feature\",\"properties\":{\"modes\":\"car\",\"zoomlevel\":10,\"length\":1200,\"freespeed\":33.3299999999999982946974341757595539093017578125,\"permlanes\":2,\"from\":\"N1\",\"id\":\"L1\",\"to\":\"N2\",\"oneway\":true,\"capacity\":1000}},{\"geometry\":{\"coordinates\":[[12,12],[23,23]],\"type\":\"LineString\"},\"type\":\"Feature\",\"properties\":{\"modes\":\"car\",\"zoomlevel\":10,\"length\":1200,\"freespeed\":33.3299999999999982946974341757595539093017578125,\"permlanes\":2,\"id\":\"L2\",\"oneway\":true,\"capacity\":1000}}],\"type\":\"FeatureCollection\"}", result.toString());
    }
}
