package businesslogic.changeset;

import dataaccess.database.tables.records.NodeChangeRecord;
import dataaccess.database.tables.records.NodeRecord;
import org.junit.Test;
import testenvironment.TestDataUtil;

import static org.junit.Assert.*;
import static testenvironment.AssertionUtils.assertNodeModelToRecord;
import static testenvironment.AssertionUtils.assertNode_ChangeRecordIsEquals;
import static testenvironment.AssertionUtils.assertNode_ChangeRecordToModelIsEquals;

public class Node_ChangeModelTests {
    @Test
    public void testGetterSetter(){
        NodeChangeRecord nodeChangeRecord = TestDataUtil.getSingleSelectNodeChangeTestRecord();
        NodeModel defaultValues = new NodeModel(TestDataUtil.getSingleSelectNodeTestRecord());

        Node_ChangeModel model = new Node_ChangeModel();
        model.setDefaultValues(defaultValues);
        model.setChangesetNr(nodeChangeRecord.getChangesetnr());
        model.setId(nodeChangeRecord.getId());
        model.setNetworkId(nodeChangeRecord.getNetworkid());
        model.setDeleted(false);
        model.setQuadKey(nodeChangeRecord.getQuadkey());
        model.setX(nodeChangeRecord.getX());
        model.setY(nodeChangeRecord.getY());
        model.setLatitude(nodeChangeRecord.getLat());
        model.setLongitude(nodeChangeRecord.getLong());

        assertEquals(defaultValues, model.getDefaultValues());
        assertEquals(false, model.isDeleted());
        assertNode_ChangeRecordToModelIsEquals(nodeChangeRecord, model);
    }

    @Test
    public void testFillModel(){
        NodeChangeRecord nodeChangeRecord = TestDataUtil.getSingleSelectNodeChangeTestRecord();
        NodeRecord nodeRecord = TestDataUtil.getSingleSelectNodeTestRecord();
        Node_ChangeModel model = new Node_ChangeModel();
        model.fillModel(nodeChangeRecord, nodeRecord);

        NodeChangeRecord expectedRecord = TestDataUtil.getSingleSelectNodeChangeTestRecord();
        NodeRecord defaultValues = TestDataUtil.getSingleSelectNodeTestRecord();

        if(expectedRecord.getId()==null){expectedRecord.setId(defaultValues.getId());}
        if(expectedRecord.getNetworkid()==null){expectedRecord.setNetworkid(defaultValues.getNetworkid());}
        if(expectedRecord.getQuadkey()==null){expectedRecord.setQuadkey(defaultValues.getQuadkey());}
        if(expectedRecord.getX()==null){expectedRecord.setX(defaultValues.getX());}
        if(expectedRecord.getY()==null){expectedRecord.setY(defaultValues.getY());}
        if(expectedRecord.getLat()==null){expectedRecord.setLat(defaultValues.getLat());}
        if(expectedRecord.getLong()==null){expectedRecord.setLong(defaultValues.getLong());}

        assertNode_ChangeRecordToModelIsEquals(expectedRecord, model);
        assertNodeModelToRecord(defaultValues, model.getDefaultValues());
    }

    @Test
    public void testGetNodeChangeRecord(){
        NodeChangeRecord nodeChangeRecord = TestDataUtil.getSingleSelectNodeChangeTestRecord();
        NodeRecord nodeRecord = TestDataUtil.getSingleSelectNodeTestRecord();
        Node_ChangeModel model = new Node_ChangeModel();
        model.fillModel(nodeChangeRecord, nodeRecord);

        NodeChangeRecord result = model.getNodeChangeRecord();
        assertNode_ChangeRecordIsEquals(nodeChangeRecord, result);
    }
}