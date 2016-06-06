package businesslogic.changeset;

import dataaccess.database.tables.records.ChangesetRecord;
import dataaccess.database.tables.records.LinkChangeRecord;
import dataaccess.database.tables.records.NodeChangeRecord;
import org.junit.Test;
import testenvironment.TestDataUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static testenvironment.AssertionUtils.*;

public class ChangesetFullModelTests {
    @Test
    public void testConstructor() {
        ChangesetFullModel model = new ChangesetFullModel();
        assertNull(model.getId());
        assertNull(model.getName());
        assertEquals(0, model.getNetworkId());
        assertEquals(0, model.getUserNr());
        assertNull(model.getLastModified());
    }

    @Test
    public void testConstructorRecord() {
        ChangesetRecord record = TestDataUtil.getChangesetRecord();
        ChangesetFullModel model = new ChangesetFullModel(record);
        assertEquals(record.getId(), model.getId());
        assertEquals(record.getName(), model.getName());
        assertEquals(record.getUsernr(), (Integer) model.getUserNr());
        assertEquals(record.getNetworkid(), (Integer) model.getNetworkId());
        assertEquals(record.getLastmodified(), model.getLastModified());
    }

    @Test
    public void testGetterSetterLinkAndNodeChangeModel() {
        List<Link_ChangeModel> link_changeModels = TestDataUtil.getListLinkChangeModels();
        List<Node_ChangeModel> node_changeModels = TestDataUtil.getListNodeChangeModels();

        ChangesetFullModel model = new ChangesetFullModel();
        model.setLink_changeModels(link_changeModels);
        assertEquals(TestDataUtil.getListLinkChangeModels().size(), model.getLink_changeModels().size());
        for (int i = 0; i < model.getLink_changeModels().size(); i++) {
            assertLink_ChangeIsEquals(TestDataUtil.getListLinkChangeModels().get(i), model.getLink_changeModels().get(i));
        }

        model.setNode_changeModels(node_changeModels);
        assertEquals(TestDataUtil.getListNodeChangeModels().size(), model.getNode_changeModels().size());
        for (int i = 0; i < model.getLink_changeModels().size(); i++) {
            assertNode_ChangeIsEquals(TestDataUtil.getListNodeChangeModels().get(i), model.getNode_changeModels().get(i));
        }
    }

    @Test
    public void testUpdateChangesetNr() {
        ChangesetRecord record = TestDataUtil.getChangesetRecord();
        ChangesetFullModel model = new ChangesetFullModel(record);
        assertEquals(record.getId(), model.getId());
        List<Link_ChangeModel> link_changeModels = TestDataUtil.getListLinkChangeModels();
        List<Node_ChangeModel> node_changeModels = TestDataUtil.getListNodeChangeModels();
        model.setLink_changeModels(link_changeModels);
        model.setNode_changeModels(node_changeModels);

        model.updateChangesetNr((long) 45);

        assertEquals(45, (long) model.getId());
        for (Link_ChangeModel link : model.getLink_changeModels()) {
            assertEquals(45, (long) link.getChangesetNr());
        }

        for (Node_ChangeModel node : model.getNode_changeModels()) {
            assertEquals(45, (long) node.getChangesetNr());
        }
    }

    @Test
    public void testGetAllLink_changeModelsAsRecord() {
        ChangesetRecord record = TestDataUtil.getChangesetRecord();
        ChangesetFullModel model = new ChangesetFullModel(record);
        List<Link_ChangeModel> link_changeModels = new ArrayList<>();
        LinkModel defaultModel = new LinkModel(TestDataUtil.getSingleSelectLinkTestRecord());
        LinkChangeRecord linkRecord = TestDataUtil.getSingleSelectLinkChangeTestRecord();
        linkRecord.setId("L1");
        linkRecord.setFreespeed(new BigDecimal(3.333));
        linkRecord.setChangesetnr((long) 12);

        Link_ChangeModel link_changeModel = new Link_ChangeModel();
        link_changeModel.fillModel(linkRecord, TestDataUtil.getSingleSelectLinkTestRecord());
        link_changeModel.setDefaultValues(defaultModel);
        link_changeModel.setDeleted(false);
        link_changeModels.add(link_changeModel);
        model.setLink_changeModels(link_changeModels);

        LinkChangeRecord expectedRecord = new LinkChangeRecord();
        expectedRecord.setId("L1");
        expectedRecord.setFreespeed(new BigDecimal(3.333));
        expectedRecord.setChangesetnr((long) 12);
        expectedRecord.setMinlevel(1);
        expectedRecord.setNetworkid(TestDataUtil.getSingleSelectLinkTestRecord().getNetworkid());

        List<LinkChangeRecord> allLink_changeModelsAsRecord = model.getAllLink_changeModelsAsRecord();
        for (int i = 0; i < allLink_changeModelsAsRecord.size(); i++) {
            LinkChangeRecord newRecord = allLink_changeModelsAsRecord.get(i);
            assertLink_ChangeRecordEquals(expectedRecord, newRecord);
        }
    }

    @Test
    public void testGetLink_changeModelsToUpdate() {
        ChangesetRecord record = TestDataUtil.getChangesetRecord();
        ChangesetFullModel model = new ChangesetFullModel(record);
        List<Link_ChangeModel> link_changeModels = new ArrayList<>();
        LinkModel defaultModel = new LinkModel(TestDataUtil.getSingleSelectLinkTestRecord());
        LinkChangeRecord linkRecord = TestDataUtil.getSingleSelectLinkChangeTestRecord();
        linkRecord.setId("L1");
        linkRecord.setFreespeed(new BigDecimal(3.333));
        linkRecord.setChangesetnr((long) 12);

        Link_ChangeModel link_changeModel = new Link_ChangeModel();
        link_changeModel.fillModel(linkRecord, TestDataUtil.getSingleSelectLinkTestRecord());
        link_changeModel.setDefaultValues(defaultModel);
        link_changeModel.setDeleted(true);
        link_changeModels.add(link_changeModel);
        model.setLink_changeModels(link_changeModels);

        List<LinkChangeRecord> allLink_changeModelsAsRecord = model.getLink_changeModelsToUpdate();
        assertEquals(0, allLink_changeModelsAsRecord.size());
    }

    @Test
    public void getLink_changeModelsToDelete() {
        ChangesetRecord record = TestDataUtil.getChangesetRecord();
        ChangesetFullModel model = new ChangesetFullModel(record);
        List<Link_ChangeModel> link_changeModels = new ArrayList<>();
        LinkModel defaultModel = new LinkModel(TestDataUtil.getSingleSelectLinkTestRecord());
        LinkChangeRecord linkRecord = TestDataUtil.getSingleSelectLinkChangeTestRecord();
        linkRecord.setId("L1");
        linkRecord.setFreespeed(new BigDecimal(3.333));
        linkRecord.setChangesetnr((long) 12);

        Link_ChangeModel link_changeModel = new Link_ChangeModel();
        link_changeModel.fillModel(linkRecord, TestDataUtil.getSingleSelectLinkTestRecord());
        link_changeModel.setDefaultValues(defaultModel);
        link_changeModel.setDeleted(true);
        link_changeModels.add(link_changeModel);
        model.setLink_changeModels(link_changeModels);

        LinkChangeRecord expectedRecord = new LinkChangeRecord();
        expectedRecord.setId("L1");
        expectedRecord.setFreespeed(new BigDecimal(3.333));
        expectedRecord.setChangesetnr((long) 12);
        expectedRecord.setMinlevel(1);
        expectedRecord.setNetworkid(TestDataUtil.getSingleSelectLinkTestRecord().getNetworkid());

        List<LinkChangeRecord> allLink_changeModelsAsRecord = model.getLink_changeModelsToDelete();
        for (int i = 0; i < allLink_changeModelsAsRecord.size(); i++) {
            LinkChangeRecord newRecord = allLink_changeModelsAsRecord.get(i);
            assertLink_ChangeRecordEquals(expectedRecord, newRecord);
        }
    }

    @Test
    public void testGetAllNode_changeModelsAsRecord() {
        ChangesetRecord record = TestDataUtil.getChangesetRecord();
        ChangesetFullModel model = new ChangesetFullModel(record);
        List<Node_ChangeModel> node_changeModels = new ArrayList<>();
        NodeModel defaultModel = new NodeModel(TestDataUtil.getSingleSelectNodeTestRecord());
        NodeChangeRecord nodeRecord = TestDataUtil.getMultipleSelectNodeChangeTestRecords().get(0);
        nodeRecord.setId("L1");
        nodeRecord.setLong(new BigDecimal(10.232));
        nodeRecord.setChangesetnr((long) 12);

        Node_ChangeModel node_changeModel = new Node_ChangeModel();
        node_changeModel.fillModel(nodeRecord, TestDataUtil.getSingleSelectNodeTestRecord());
        node_changeModel.setDefaultValues(defaultModel);
        node_changeModel.setDeleted(false);
        node_changeModels.add(node_changeModel);
        model.setNode_changeModels(node_changeModels);

        NodeChangeRecord expectedRecord = new NodeChangeRecord();
        expectedRecord.setId("L1");
        expectedRecord.setLong(new BigDecimal(10.232));
        expectedRecord.setX(new BigDecimal(12.3));
        expectedRecord.setChangesetnr((long) 12);
        expectedRecord.setNetworkid(TestDataUtil.getSingleSelectNodeTestRecord().getNetworkid());

        List<NodeChangeRecord> allNode_changeModelsAsRecord = model.getAllNode_changeModelsAsRecord();
        for (int i = 0; i < allNode_changeModelsAsRecord.size(); i++) {
            NodeChangeRecord newRecord = allNode_changeModelsAsRecord.get(i);
            assertNode_ChangeRecordIsEquals(expectedRecord, newRecord);
        }
    }

    @Test
    public void testGetNode_changeModelsToUpdate() {
        ChangesetRecord record = TestDataUtil.getChangesetRecord();
        ChangesetFullModel model = new ChangesetFullModel(record);
        List<Node_ChangeModel> node_changeModels = new ArrayList<>();
        NodeModel defaultModel = new NodeModel(TestDataUtil.getSingleSelectNodeTestRecord());
        NodeChangeRecord nodeRecord = TestDataUtil.getMultipleSelectNodeChangeTestRecords().get(0);
        nodeRecord.setId("L1");
        nodeRecord.setLong(new BigDecimal(10.232));
        nodeRecord.setChangesetnr((long) 12);

        Node_ChangeModel node_changeModel = new Node_ChangeModel();
        node_changeModel.fillModel(nodeRecord, TestDataUtil.getSingleSelectNodeTestRecord());
        node_changeModel.setDefaultValues(defaultModel);
        node_changeModel.setDeleted(true);
        node_changeModels.add(node_changeModel);
        model.setNode_changeModels(node_changeModels);

        List<NodeChangeRecord> allNode_changeModelsAsRecord = model.getNode_changeModelsToUpdate();
        assertEquals(0, allNode_changeModelsAsRecord.size());
    }

    @Test
    public void testGetNode_changeModelsToDelete() {
        ChangesetRecord record = TestDataUtil.getChangesetRecord();
        ChangesetFullModel model = new ChangesetFullModel(record);
        List<Node_ChangeModel> node_changeModels = new ArrayList<>();
        NodeModel defaultModel = new NodeModel(TestDataUtil.getSingleSelectNodeTestRecord());
        NodeChangeRecord nodeRecord = TestDataUtil.getMultipleSelectNodeChangeTestRecords().get(0);
        nodeRecord.setId("L1");
        nodeRecord.setLong(new BigDecimal(10.232));
        nodeRecord.setChangesetnr((long) 12);

        Node_ChangeModel node_changeModel = new Node_ChangeModel();
        node_changeModel.fillModel(nodeRecord, TestDataUtil.getSingleSelectNodeTestRecord());
        node_changeModel.setDefaultValues(defaultModel);
        node_changeModel.setDeleted(false);
        node_changeModels.add(node_changeModel);
        model.setNode_changeModels(node_changeModels);

        NodeChangeRecord expectedRecord = new NodeChangeRecord();
        expectedRecord.setId("L1");
        expectedRecord.setLong(new BigDecimal(10.232));
        expectedRecord.setX(new BigDecimal(12.3));
        expectedRecord.setChangesetnr((long) 12);
        expectedRecord.setNetworkid(TestDataUtil.getSingleSelectNodeTestRecord().getNetworkid());

        List<NodeChangeRecord> allNode_changeModelsAsRecord = model.getNode_changeModelsToDelete();
        for (int i = 0; i < allNode_changeModelsAsRecord.size(); i++) {
            NodeChangeRecord newRecord = allNode_changeModelsAsRecord.get(i);
            assertNode_ChangeRecordIsEquals(expectedRecord, newRecord);
        }
    }
}
