package businesslogic.changeset;

import dataaccess.database.tables.records.LinkRecord;
import org.junit.Test;
import testenvironment.TestDataUtil;

import static org.junit.Assert.assertNull;
import static testenvironment.AssertionUtils.assertLinkModelToRecord;


public class LinkModelTests {
    @Test
    public void testConstructor() {
        LinkModel model = new LinkModel();
        assertNull(model.getId());
        assertNull(model.getNetworkId());
        assertNull(model.getQuadKey());
        assertNull(model.getLength());
        assertNull(model.getCapacity());
        assertNull(model.getFreespeed());
        assertNull(model.getCapacity());
        assertNull(model.getPermlanes());
        assertNull(model.getOneway());
        assertNull(model.getModes());
        assertNull(model.getFrom());
        assertNull(model.getTo());
        assertNull(model.getMinlevel());
        assertNull(model.getLastModified());
        assertNull(model.getLong1());
        assertNull(model.getLat1());
        assertNull(model.getLong2());
        assertNull(model.getLat2());
    }

    @Test
    public void testConstructorWithRecord() {
        LinkRecord record = TestDataUtil.getSingleSelectLinkTestRecord();
        LinkModel model = new LinkModel(record);

        assertLinkModelToRecord(record, model);
    }

    @Test
    public void testGetterSetter() {
        LinkRecord record = TestDataUtil.getSingleSelectLinkTestRecord();
        LinkModel model = new LinkModel();
        model.setId(record.getId());
        model.setNetworkId(record.getNetworkid());
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

        assertLinkModelToRecord(record, model);
    }
}
