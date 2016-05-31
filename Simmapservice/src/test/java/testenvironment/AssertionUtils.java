package testenvironment;

import businesslogic.changeset.LinkModel;
import businesslogic.changeset.Link_ChangeModel;
import businesslogic.changeset.NodeModel;
import businesslogic.changeset.Node_ChangeModel;
import dataaccess.database.tables.records.LinkChangeRecord;
import dataaccess.database.tables.records.NodeChangeRecord;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by dohee on 31.05.2016.
 */
public class AssertionUtils {
    public static void assertLink_ChangeIsEquals(Link_ChangeModel expected, Link_ChangeModel result){
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getChangesetNr(), result.getChangesetNr());
        assertLinkModelEquals(expected.getDefaultValues(), expected.getDefaultValues());
        assertEquals(expected.getNetworkId(), result.getNetworkId());
        assertEquals(expected.getModes(), result.getModes());
        assertEquals(expected.getCapacity(), result.getCapacity());
        assertEquals(expected.getFreespeed(), result.getFreespeed());
        assertEquals(expected.getFrom(), result.getFrom());
        assertEquals(expected.getLastModified(), result.getLastModified());
        assertEquals(expected.getLat1(), result.getLat1());
        assertEquals(expected.getLat2(), result.getLat2());
        assertEquals(expected.getLength(), result.getLength());
        assertEquals(expected.getLong1(), result.getLong1());
        assertEquals(expected.getLong2(), result.getLong2());
        assertEquals(expected.getMinlevel(), result.getMinlevel());
        assertEquals(expected.getOneway(), result.getOneway());
        assertEquals(expected.getPermlanes(), result.getPermlanes());
        assertEquals(expected.getQuadKey(), result.getQuadKey());
        assertEquals(expected.getTo(), result.getTo());
    }

    public static void assertLinkModelEquals(LinkModel expected, LinkModel result){
        if(expected != null) {
            assertNotNull(result);
            assertEquals(expected.getId(), result.getId());
            assertEquals(expected.getNetworkId(), result.getNetworkId());
            assertEquals(expected.getModes(), result.getModes());
            assertEquals(expected.getCapacity(), result.getCapacity());
            assertEquals(expected.getFreespeed(), result.getFreespeed());
            assertEquals(expected.getFrom(), result.getFrom());
            assertEquals(expected.getLastModified(), result.getLastModified());
            assertEquals(expected.getLat1(), result.getLat1());
            assertEquals(expected.getLat2(), result.getLat2());
            assertEquals(expected.getLength(), result.getLength());
            assertEquals(expected.getLong1(), result.getLong1());
            assertEquals(expected.getLong2(), result.getLong2());
            assertEquals(expected.getMinlevel(), result.getMinlevel());
            assertEquals(expected.getOneway(), result.getOneway());
            assertEquals(expected.getPermlanes(), result.getPermlanes());
            assertEquals(expected.getQuadKey(), result.getQuadKey());
            assertEquals(expected.getTo(), result.getTo());
        }
        else{
            assertNull(result);
        }
    }

    public static void assertNode_ChangeIsEquals(Node_ChangeModel expected, Node_ChangeModel result){
        if(expected != null) {
            assertEquals(expected.getChangesetNr(), result.getChangesetNr());
            assertNodeModelEquals(expected.getDefaultValues(), result.getDefaultValues());
            assertEquals(expected.getId(), result.getId());
            assertEquals(expected.getLatitude(), result.getLatitude());
            assertEquals(expected.getLongitude(), result.getLongitude());
            assertEquals(expected.getNetworkId(), result.getNetworkId());
            assertEquals(expected.getQuadKey(), result.getQuadKey());
            assertEquals(expected.getX(), result.getX());
            assertEquals(expected.getY(), result.getY());
        }
        else{
            assertNull(result);
        }
    }

    public static void assertNodeModelEquals(NodeModel expected, NodeModel result){
        if(expected != null){
            assertEquals(expected.getId(), result.getId());
            assertEquals(expected.getLatitude(), result.getLatitude());
            assertEquals(expected.getLongitude(), result.getLongitude());
            assertEquals(expected.getNetworkId(), result.getNetworkId());
            assertEquals(expected.getQuadKey(), result.getQuadKey());
            assertEquals(expected.getX(), result.getX());
            assertEquals(expected.getY(), result.getY());
        }
        else{
            assertNull(result);
        }
    }

    public static void assertLink_ChangeRecordEquals(LinkChangeRecord expected, LinkChangeRecord result){
        assertNotNull(result);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getNetworkid(), result.getNetworkid());
        assertEquals(expected.getModes(), result.getModes());
        assertEquals(expected.getCapacity(), result.getCapacity());
        assertEquals(expected.getFreespeed(), result.getFreespeed());
        assertEquals(expected.getFrom(), result.getFrom());
        assertEquals(expected.getLastmodified(), result.getLastmodified());
        assertEquals(expected.getLat1(), result.getLat1());
        assertEquals(expected.getLat2(), result.getLat2());
        assertEquals(expected.getLength(), result.getLength());
        assertEquals(expected.getLong1(), result.getLong1());
        assertEquals(expected.getLong2(), result.getLong2());
        assertEquals(expected.getMinlevel(), result.getMinlevel());
        assertEquals(expected.getOneway(), result.getOneway());
        assertEquals(expected.getPermlanes(), result.getPermlanes());
        assertEquals(expected.getQuadkey(), result.getQuadkey());
        assertEquals(expected.getTo(), result.getTo());
    }

    public static void assertNode_ChangeRecordIsEquals(NodeChangeRecord expected, NodeChangeRecord result){
        assertEquals(expected.getChangesetnr(), result.getChangesetnr());
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getLat(), result.getLat());
        assertEquals(expected.getLong(), result.getLong());
        assertEquals(expected.getNetworkid(), result.getNetworkid());
        assertEquals(expected.getQuadkey(), result.getQuadkey());
        assertEquals(expected.getX(), result.getX());
        assertEquals(expected.getY(), result.getY());
    }
}
