package businesslogic.changeset;

import businesslogic.changeset.ChangesetModel;
import dataaccess.database.tables.records.ChangesetRecord;
import org.junit.Test;
import testenvironment.TestDataUtil;

import static org.junit.Assert.*;

/**
 * Created by dohee on 31.05.2016.
 */
public class ChangesetModelTests {
    @Test
    public void testConstructor() {
        ChangesetModel model = new ChangesetModel();
        assertNull(model.getId());
        assertNull(model.getName());
        assertEquals(0, model.getNetworkId());
        assertEquals(0, model.getUserNr());
        assertNull(model.getLastModified());
    }

    @Test
    public void testConstructorRecord() {
        ChangesetRecord record = TestDataUtil.getChangesetRecord();
        ChangesetModel model = new ChangesetModel(record);
        assertEquals(record.getId(), model.getId());
        assertEquals(record.getName(), model.getName());
        assertEquals(record.getUsernr(), (Integer) model.getUserNr());
        assertEquals(record.getNetworkid(), (Integer) model.getNetworkId());
        assertEquals(record.getLastmodified(), model.getLastModified());
    }

    @Test
    public void testGetterAndSetter(){
        ChangesetRecord record = TestDataUtil.getChangesetRecord();
        ChangesetModel model = new ChangesetModel();
        model.setId(record.getId());
        model.setName(record.getName());
        model.setNetworkId(record.getNetworkid());
        model.setUserNr(record.getUsernr());
        model.setLastModified(record.getLastmodified());

        assertEquals(record.getId(), model.getId());
        assertEquals(record.getName(), model.getName());
        assertEquals(record.getUsernr(), (Integer)model.getUserNr());
        assertEquals(record.getNetworkid(), (Integer)model.getNetworkId());
        assertEquals(record.getLastmodified(), model.getLastModified());
    }

    @Test
    public void testGetRecord(){
        ChangesetRecord record = TestDataUtil.getChangesetRecord();
        ChangesetModel model = new ChangesetModel();
        model.setId(record.getId());
        model.setName(record.getName());
        model.setNetworkId(record.getNetworkid());
        model.setUserNr(record.getUsernr());
        model.setLastModified(record.getLastmodified());

        ChangesetRecord newRecord = model.getRecord();
        assertEquals(record, newRecord);
    }
}
