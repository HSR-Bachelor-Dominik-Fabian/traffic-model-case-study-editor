package businesslogic.changeset;

import dataaccess.database.tables.records.NodeRecord;
import org.junit.Test;
import testenvironment.TestDataUtil;

import static org.junit.Assert.assertNull;
import static testenvironment.AssertionUtils.assertNodeModelToRecord;

public class NodeModelTests {
    @Test
    public void testConstructor() {
        NodeModel model = new NodeModel();
        assertNull(model.getId());
        assertNull(model.getNetworkId());
        assertNull(model.getQuadKey());
        assertNull(model.getX());
        assertNull(model.getY());
        assertNull(model.getLatitude());
        assertNull(model.getLongitude());
    }

    @Test
    public void testConstructorWithRecord() {
        NodeRecord record = TestDataUtil.getSingleSelectNodeTestRecord();
        NodeModel model = new NodeModel(record);
        assertNodeModelToRecord(record, model);
    }

    @Test
    public void testGetterSetter() {
        NodeRecord record = TestDataUtil.getSingleSelectNodeTestRecord();
        NodeModel model = new NodeModel();
        model.setId(record.getId());
        model.setNetworkId(record.getNetworkid());
        model.setQuadKey(record.getQuadkey());
        model.setX(record.getX());
        model.setY(record.getY());
        model.setLatitude(record.getLat());
        model.setLongitude(record.getLong());

        assertNodeModelToRecord(record, model);
    }
}
