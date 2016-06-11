package businesslogic.changeset;

import dataaccess.database.tables.records.LinkChangeRecord;
import dataaccess.database.tables.records.LinkRecord;
import org.junit.Test;
import testenvironment.TestDataUtil;

import static org.junit.Assert.*;
import static testenvironment.AssertionUtils.*;

public class LinkChangeModelTests {
    @Test
    public void testGetterSetter() {
        LinkChangeRecord record = TestDataUtil.getSingleSelectLinkChangeTestRecord();
        LinkModel defaultValues = new LinkModel(TestDataUtil.getSingleSelectLinkTestRecord());

        LinkChangeModel model = new LinkChangeModel();
        model.setDefaultValues(defaultValues);
        model.setId(record.getId());
        model.setChangesetNr(record.getChangesetnr());
        model.setNetworkId(record.getNetworkid());
        model.setDeleted(false);
        model.setQuadKey(record.getQuadkey());
        model.setLength(record.getLength());
        model.setFreespeed(record.getFreespeed());
        model.setCapacity(record.getCapacity());
        model.setPermlanes(record.getPermlanes());
        model.setOneway(record.getOneway());
        model.setModes(record.getModes());
        model.setFrom(record.getFrom());
        model.setTo(record.getTo());
        model.setMinlevel(record.getMinlevel());
        model.setLastModified(record.getLastmodified());
        model.setLong1(record.getLong1());
        model.setLat1(record.getLat1());
        model.setLong2(record.getLong2());
        model.setLat2(record.getLat2());

        assertEquals(defaultValues, model.getDefaultValues());
        assertEquals(false, model.isDeleted());
        assertLink_ChangeRecordToModelIsEquals(record, model);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFillModelNegative() {
        LinkChangeModel model = new LinkChangeModel();
        model.fillModel(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFillModelNegative1() {
        LinkChangeModel model = new LinkChangeModel();
        model.fillModel(new LinkChangeRecord(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFillModelNegative2() {
        LinkChangeModel model = new LinkChangeModel();
        model.fillModel(null, new LinkRecord());
    }

    @Test
    public void testFillModel() {
        LinkChangeRecord linkChangeRecord = TestDataUtil.getSingleSelectLinkChangeTestRecord();
        LinkRecord linkRecord = TestDataUtil.getSingleSelectLinkTestRecord();
        LinkChangeModel model = new LinkChangeModel();
        model.fillModel(linkChangeRecord, linkRecord);

        LinkChangeRecord expectedRecord = TestDataUtil.getSingleSelectLinkChangeTestRecord();
        LinkRecord defaultValues = TestDataUtil.getSingleSelectLinkTestRecord();
        if (expectedRecord.getId() == null) {
            expectedRecord.setId(defaultValues.getId());
        }
        if (expectedRecord.getNetworkid() == null) {
            expectedRecord.setNetworkid(defaultValues.getNetworkid());
        }
        if (expectedRecord.getModes() == null) {
            expectedRecord.setModes(defaultValues.getModes());
        }
        if (expectedRecord.getCapacity() == null) {
            expectedRecord.setCapacity(defaultValues.getCapacity());
        }
        if (expectedRecord.getFreespeed() == null) {
            expectedRecord.setFreespeed(defaultValues.getFreespeed());
        }
        if (expectedRecord.getFrom() == null) {
            expectedRecord.setFrom(defaultValues.getFrom());
        }
        if (expectedRecord.getLastmodified() == null) {
            expectedRecord.setLastmodified(defaultValues.getLastmodified());
        }
        if (expectedRecord.getLat1() == null) {
            expectedRecord.setLat1(defaultValues.getLat1());
        }
        if (expectedRecord.getLat2() == null) {
            expectedRecord.setLat2(defaultValues.getLat2());
        }
        if (expectedRecord.getLength() == null) {
            expectedRecord.setLength(defaultValues.getLength());
        }
        if (expectedRecord.getLong1() == null) {
            expectedRecord.setLong1(defaultValues.getLong1());
        }
        if (expectedRecord.getLong2() == null) {
            expectedRecord.setLong2(defaultValues.getLong2());
        }
        if (expectedRecord.getMinlevel() == null) {
            expectedRecord.setMinlevel(defaultValues.getMinlevel());
        }
        if (expectedRecord.getOneway() == null) {
            expectedRecord.setOneway(defaultValues.getOneway());
        }
        if (expectedRecord.getPermlanes() == null) {
            expectedRecord.setPermlanes(defaultValues.getPermlanes());
        }
        if (expectedRecord.getTo() == null) {
            expectedRecord.setTo(defaultValues.getTo());
        }
        if (expectedRecord.getQuadkey() == null) {
            expectedRecord.setQuadkey(defaultValues.getQuadkey());
        }


        assertLink_ChangeRecordToModelIsEquals(expectedRecord, model);
        assertLinkModelToRecord(linkRecord, model.getDefaultValues());
    }

    @Test
    public void testGetLinkChangeRecord(){
        LinkChangeRecord linkChangeRecord = TestDataUtil.getSingleSelectLinkChangeTestRecord();
        LinkRecord linkRecord = TestDataUtil.getSingleSelectLinkTestRecord();
        LinkChangeModel model = new LinkChangeModel();
        model.fillModel(linkChangeRecord, linkRecord);

        LinkChangeRecord result = model.getLinkChangeRecord();

        assertLink_ChangeRecordEquals(linkChangeRecord, result);
    }
}
