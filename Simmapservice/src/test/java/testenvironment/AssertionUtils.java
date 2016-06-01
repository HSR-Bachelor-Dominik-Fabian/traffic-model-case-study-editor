package testenvironment;

import businesslogic.changeset.*;
import dataaccess.database.tables.records.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class AssertionUtils {
    public static void assertLink_ChangeIsEquals(Link_ChangeModel expected, Link_ChangeModel result) {
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

    public static void assertLink_ChangeRecordToModelIsEquals(LinkChangeRecord expected, Link_ChangeModel result) {
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getChangesetnr(), result.getChangesetNr());
        assertEquals(expected.getNetworkid(), result.getNetworkId());
        assertEquals(expected.getModes(), result.getModes());
        assertEquals(expected.getCapacity(), result.getCapacity());
        assertEquals(expected.getFreespeed(), result.getFreespeed());
        assertEquals(expected.getFrom(), result.getFrom());
        assertEquals(expected.getLastmodified(), result.getLastModified());
        assertEquals(expected.getLat1(), result.getLat1());
        assertEquals(expected.getLat2(), result.getLat2());
        assertEquals(expected.getLength(), result.getLength());
        assertEquals(expected.getLong1(), result.getLong1());
        assertEquals(expected.getLong2(), result.getLong2());
        assertEquals(expected.getMinlevel(), result.getMinlevel());
        assertEquals(expected.getOneway(), result.getOneway());
        assertEquals(expected.getPermlanes(), result.getPermlanes());
        assertEquals(expected.getQuadkey(), result.getQuadKey());
        assertEquals(expected.getTo(), result.getTo());
    }

    public static void assertLinkModelEquals(LinkModel expected, LinkModel result) {
        if (expected != null) {
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
        } else {
            assertNull(result);
        }
    }

    public static void assertNode_ChangeIsEquals(Node_ChangeModel expected, Node_ChangeModel result) {
        if (expected != null) {
            assertEquals(expected.getChangesetNr(), result.getChangesetNr());
            assertNodeModelEquals(expected.getDefaultValues(), result.getDefaultValues());
            assertEquals(expected.getId(), result.getId());
            assertEquals(expected.getLatitude(), result.getLatitude());
            assertEquals(expected.getLongitude(), result.getLongitude());
            assertEquals(expected.getNetworkId(), result.getNetworkId());
            assertEquals(expected.getQuadKey(), result.getQuadKey());
            assertEquals(expected.getX(), result.getX());
            assertEquals(expected.getY(), result.getY());
        } else {
            assertNull(result);
        }
    }

    public static void assertNode_ChangeRecordToModelIsEquals(NodeChangeRecord expected, Node_ChangeModel result) {
        if (expected != null) {
            assertEquals(expected.getChangesetnr(), result.getChangesetNr());
            assertEquals(expected.getId(), result.getId());
            assertEquals(expected.getLat(), result.getLatitude());
            assertEquals(expected.getLong(), result.getLongitude());
            assertEquals(expected.getNetworkid(), result.getNetworkId());
            assertEquals(expected.getQuadkey(), result.getQuadKey());
            assertEquals(expected.getX(), result.getX());
            assertEquals(expected.getY(), result.getY());
        } else {
            assertNull(result);
        }
    }

    public static void assertNodeModelEquals(NodeModel expected, NodeModel result) {
        if (expected != null) {
            assertEquals(expected.getId(), result.getId());
            assertEquals(expected.getLatitude(), result.getLatitude());
            assertEquals(expected.getLongitude(), result.getLongitude());
            assertEquals(expected.getNetworkId(), result.getNetworkId());
            assertEquals(expected.getQuadKey(), result.getQuadKey());
            assertEquals(expected.getX(), result.getX());
            assertEquals(expected.getY(), result.getY());
        } else {
            assertNull(result);
        }
    }

    public static void assertLink_ChangeRecordEquals(LinkChangeRecord expected, LinkChangeRecord result) {
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

    public static void assertNode_ChangeRecordIsEquals(NodeChangeRecord expected, NodeChangeRecord result) {
        assertEquals(expected.getChangesetnr(), result.getChangesetnr());
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getLat(), result.getLat());
        assertEquals(expected.getLong(), result.getLong());
        assertEquals(expected.getNetworkid(), result.getNetworkid());
        assertEquals(expected.getQuadkey(), result.getQuadkey());
        assertEquals(expected.getX(), result.getX());
        assertEquals(expected.getY(), result.getY());
    }

    public static void assertLinkModelToRecord(LinkRecord expected, LinkModel model) {
        assertEquals(expected.getId(), model.getId());
        assertEquals(expected.getNetworkid(), model.getNetworkId());
        assertEquals(expected.getQuadkey(), model.getQuadKey());
        assertEquals(expected.getLength(), model.getLength());
        assertEquals(expected.getCapacity(), model.getCapacity());
        assertEquals(expected.getFreespeed(), model.getFreespeed());
        assertEquals(expected.getCapacity(), model.getCapacity());
        assertEquals(expected.getPermlanes(), model.getPermlanes());
        assertEquals(expected.getOneway(), model.getOneway());
        assertEquals(expected.getModes(), model.getModes());
        assertEquals(expected.getFrom(), model.getFrom());
        assertEquals(expected.getTo(), model.getTo());
        assertEquals(expected.getMinlevel(), model.getMinlevel());
        assertEquals(expected.getLastmodified(), model.getLastModified());
        assertEquals(expected.getLong1(), model.getLong1());
        assertEquals(expected.getLat1(), model.getLat1());
        assertEquals(expected.getLong2(), model.getLong2());
        assertEquals(expected.getLat2(), model.getLat2());
    }

    public static void assertNodeModelToRecord(NodeRecord expected, NodeModel model) {
        assertEquals(expected.getId(), model.getId());
        assertEquals(expected.getNetworkid(), model.getNetworkId());
        assertEquals(expected.getQuadkey(), model.getQuadKey());
        assertEquals(expected.getX(), model.getX());
        assertEquals(expected.getY(), model.getY());
        assertEquals(expected.getLat(), model.getLatitude());
        assertEquals(expected.getLong(), model.getLongitude());
    }

    public static void assertChangesetRecordEqualsModel(ChangesetRecord expected, ChangesetModel model){
        assertEquals(expected.getId(), model.getId());
        assertEquals((long)expected.getNetworkid(), (long)model.getNetworkId());
        assertEquals(expected.getName(), model.getName());
        assertEquals((long)expected.getUsernr(), (long)model.getUserNr());
        assertEquals(expected.getLastmodified(), model.getLastModified());
    }
}
