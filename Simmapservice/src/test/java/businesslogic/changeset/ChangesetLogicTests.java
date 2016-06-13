package businesslogic.changeset;

import dataaccess.DataAccessLayerException;
import dataaccess.DataAccessLogic;
import dataaccess.database.tables.records.ChangesetRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import testenvironment.TestDataUtil;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.*;
import static testenvironment.AssertionUtils.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DataAccessLogic.class)
public class ChangesetLogicTests {
    private DataAccessLogic facadeMock;
    private ChangesetLogic changesetLogic;

    @Before
    public void setupFacade() throws IllegalAccessException, DataAccessLayerException {
        changesetLogic = new ChangesetLogic(TestDataUtil.getTestProperties());
        facadeMock = createMock(DataAccessLogic.class);
        MemberModifier.field(ChangesetLogic.class, "dataAccess").set(changesetLogic, facadeMock);
    }

    @Test
    public void testGetAllChangesets() throws DataAccessLayerException {
        expect(facadeMock.getAllChangesetsPerUser(1)).andReturn(TestDataUtil.getChangesetRecordsResult());
        replayAll();
        List<ChangesetModel> models = this.changesetLogic.getAllChangesets(1);
        assertEquals(TestDataUtil.getChangesetRecordsResult().size(), models.size());
        for (int i = 0; i < models.size(); i++) {
            ChangesetModel model = models.get(i);
            ChangesetRecord record = TestDataUtil.getChangesetRecordsResult().get(i);
            assertChangesetRecordEqualsModel(record, model);
        }
        verifyAll();
    }

    @Test
    public void testGetFullChangeset() throws DataAccessLayerException {
        expect(facadeMock.getChangesetFromNumber(1)).andReturn(TestDataUtil.getSingleSelectChangesetTestRecord());
        expect(facadeMock.getNodeChangefromChangeset(1)).andReturn(TestDataUtil.getNodeChangeRecordResult());
        expect(facadeMock.getLinkChangesfromChangeset(1)).andReturn(TestDataUtil.getLinkChangeRecordResult());
        expect(facadeMock.getNodeFromId("N1")).andReturn(TestDataUtil.getMultipleSelectNodeTestRecords().get(0));
        expect(facadeMock.getNodeFromId("N2")).andReturn(TestDataUtil.getMultipleSelectNodeTestRecords().get(1));
        expect(facadeMock.getLinkFromId("1")).andReturn(TestDataUtil.getSingleSelectLinkTestRecord());

        replayAll();

        ChangesetFullModel model = this.changesetLogic.getFullChangeset(1);
        assertChangesetRecordEqualsModel(TestDataUtil.getSingleSelectChangesetTestRecord(), model);
        List<NodeChangeModel> expected = new ArrayList<>();
        NodeChangeModel node_changeModel1 = new NodeChangeModel();
        node_changeModel1.fillModel(TestDataUtil.getNodeChangeRecordResult().get(0), TestDataUtil.getMultipleSelectNodeTestRecords().get(0));
        expected.add(node_changeModel1);
        NodeChangeModel node_changeModel2 = new NodeChangeModel();
        node_changeModel2.fillModel(TestDataUtil.getNodeChangeRecordResult().get(1), TestDataUtil.getMultipleSelectNodeTestRecords().get(1));
        expected.add(node_changeModel2);
        List<NodeChangeModel> node_changeModels = model.getNode_changeModels();
        for (int i = 0; i < node_changeModels.size(); i++) {
            NodeChangeModel nodeChangeModel = node_changeModels.get(i);
            assertNode_ChangeIsEquals(expected.get(i), nodeChangeModel);
        }
        assertEquals(1, model.getLink_changeModels().size());
        LinkChangeModel linkexpected = new LinkChangeModel();
        linkexpected.fillModel(TestDataUtil.getSingleSelectLinkChangeTestRecord(), TestDataUtil.getSingleSelectLinkTestRecord());

        assertLink_ChangeIsEquals(linkexpected, model.getLink_changeModels().get(0));
        verifyAll();
    }

    @Test
    public void testGetFullChangesetNegative() throws DataAccessLayerException {
        expect(facadeMock.getChangesetFromNumber(2)).andReturn(null);
        replayAll();
        assertNull(this.changesetLogic.getFullChangeset(2));
        verifyAll();
    }

    @Test
    public void testHasChangeset() throws DataAccessLayerException {
        expect(facadeMock.hasChangeset(1)).andReturn(true);
        replayAll();
        assertTrue(this.changesetLogic.hasChangeset(1));
        verifyAll();
    }

    @Test
    public void testHasChangesetNegative() throws DataAccessLayerException {
        expect(facadeMock.hasChangeset(2)).andReturn(false);
        replayAll();
        assertFalse(this.changesetLogic.hasChangeset(2));
        verifyAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertChangesetNegative() throws DataAccessLayerException {
        ChangesetFullModel fullModel = new ChangesetFullModel(TestDataUtil.getChangesetRecord());
        this.changesetLogic.insertChangeset(fullModel);
    }

    @Test
    public void testInsertChangeset() throws DataAccessLayerException {
        ChangesetRecord record = TestDataUtil.getChangesetRecord();
        record.setId(null);
        ChangesetFullModel fullModel = new ChangesetFullModel(record);
        fullModel.setLink_changeModels(new ArrayList<>());
        fullModel.setNode_changeModels(new ArrayList<>());
        //facadeMock =
        expect(facadeMock.insertChangeset(anyObject())).andReturn(new Long(1));
        expect(facadeMock.deleteLink_Changes(new ArrayList<>())).andReturn(new int[]{});
        expect(facadeMock.deleteNode_Changes(new ArrayList<>())).andReturn(new int[]{});
        expect(facadeMock.updateLink_Changes(new ArrayList<>())).andReturn(new int[]{});
        expect(facadeMock.updateNode_Changes(new ArrayList<>())).andReturn(new int[]{});

        replayAll();
        assertEquals((long) 1, (long) this.changesetLogic.insertChangeset(fullModel));
        verifyAll();
    }

    @Test
    public void testUpdateChangeset() throws DataAccessLayerException {
        ChangesetRecord record = TestDataUtil.getChangesetRecord();
        ChangesetFullModel fullModel = new ChangesetFullModel(record);
        fullModel.setLink_changeModels(new ArrayList<>());
        fullModel.setNode_changeModels(new ArrayList<>());
        expect(facadeMock.updateChangeset(record)).andReturn(1);
        expect(facadeMock.deleteLink_Changes(new ArrayList<>())).andReturn(new int[]{});
        expect(facadeMock.deleteNode_Changes(new ArrayList<>())).andReturn(new int[]{});
        expect(facadeMock.updateLink_Changes(new ArrayList<>())).andReturn(new int[]{});
        expect(facadeMock.updateNode_Changes(new ArrayList<>())).andReturn(new int[]{});

        replayAll();

        this.changesetLogic.updateChangeset(fullModel);

        verifyAll();
    }

    @Test
    public void testDeleteChangeset() throws DataAccessLayerException {
        ChangesetRecord record = TestDataUtil.getChangesetRecord();
        ChangesetFullModel fullModel = new ChangesetFullModel(record);
        fullModel.setLink_changeModels(new ArrayList<>());
        fullModel.setNode_changeModels(new ArrayList<>());
        expect(facadeMock.deleteLink_Changes(new ArrayList<>())).andReturn(new int[]{});
        expect(facadeMock.deleteNode_Changes(new ArrayList<>())).andReturn(new int[]{});
        expect(facadeMock.deleteChangeset(record)).andReturn(1);

        replayAll();

        this.changesetLogic.deleteChangeset(fullModel);

        verifyAll();
    }
}
