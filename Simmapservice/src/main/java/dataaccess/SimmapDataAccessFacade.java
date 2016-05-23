package dataaccess;

import common.DataAccessLayerException;
import dataaccess.database.Tables;
import dataaccess.database.tables.Link;
import dataaccess.database.tables.records.*;
import dataaccess.utils.DataAccessUtil;
import dataaccess.utils.IConnection;
import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by dohee on 22.03.2016.
 */
public class SimmapDataAccessFacade {
    public SimmapDataAccessFacade(Properties props, IConnection connectionUtil) {
        this.properties = props;
        this.connectionUtil = connectionUtil;
    }

    private final Properties properties;
    private final IConnection connectionUtil;

    public int[] setNetwork(NetworkRecord[] records) throws DataAccessLayerException {
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.NETWORK, this.connectionUtil);
    }

    public int[] setNode(NodeRecord[] records) throws DataAccessLayerException {
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.NODE, this.connectionUtil);
    }

    public int[] setLink(LinkRecord[] records) throws DataAccessLayerException {
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.LINK, this.connectionUtil);
    }

    public int[] setNetworkOptions(NetworkOptionsRecord[] records) throws DataAccessLayerException {
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.NETWORK_OPTIONS, this.connectionUtil);
    }

    public Result<NodeRecord> getAllNodes() throws DataAccessLayerException {
        return DataAccessUtil.getRecords(this.properties, Tables.NODE, this.connectionUtil);
    }

    public Result getAllChangesetsPerUser(int userNr) throws DataAccessLayerException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return context.select().from(Tables.CHANGESET).where(Tables.CHANGESET.USERNR.eq(userNr))
                    .orderBy(Tables.CHANGESET.LASTMODIFIED.desc()).fetch();
        } catch (SQLException e) {
            throw new DataAccessLayerException(e);
        } catch (DataAccessException e) {
            throw new DataAccessLayerException(e);
        }
    }

    public ChangesetRecord getChangesetFromNumber(long changsetNr) throws DataAccessLayerException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return (ChangesetRecord) context.select().from(Tables.CHANGESET).where(Tables.CHANGESET.ID.eq(changsetNr)).fetchOne();
        } catch (SQLException e) {
            throw new DataAccessLayerException(e);
        } catch (DataAccessException e) {
            throw new DataAccessLayerException(e);
        }
    }

    public NodeRecord getNodeFromId(String id) throws DataAccessLayerException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return (NodeRecord) context.select().from(Tables.NODE).where(Tables.NODE.ID.eq(id)).fetchOne();
        } catch (SQLException e) {
            throw new DataAccessLayerException(e);
        } catch (DataAccessException e) {
            throw new DataAccessLayerException(e);
        }
    }

    public LinkRecord getLinkFromId(String id) throws DataAccessLayerException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return (LinkRecord) context.select().from(Tables.LINK).where(Tables.LINK.ID.eq(id)).fetchOne();
        } catch (SQLException e) {
            throw new DataAccessLayerException(e);
        } catch (DataAccessException e) {
            throw new DataAccessLayerException(e);
        }
    }

    public Result getLinkChangesfromChangeset(long changesetNr) throws DataAccessLayerException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return context.select().from(Tables.LINK_CHANGE).where(Tables.LINK_CHANGE.CHANGESETNR.eq(changesetNr)).fetch();
        } catch (SQLException e) {
            throw new DataAccessLayerException(e);
        } catch (DataAccessException e) {
            throw new DataAccessLayerException(e);
        }
    }

    public Result getNodeChangefromChangeset(long changesetNr) throws DataAccessLayerException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return context.select().from(Tables.NODE_CHANGE).where(Tables.NODE_CHANGE.CHANGESETNR.eq(changesetNr)).fetch();
        } catch (SQLException e) {
            throw new DataAccessLayerException(e);
        } catch (DataAccessException e) {
            throw new DataAccessLayerException(e);
        }
    }

    public Result getLinkFromQuadKey(String QuadKey, int NetworkId, int zoomLevel) throws DataAccessLayerException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

            Link l = Tables.LINK.as("l");

            SelectJoinStep query = context.select(l.ID, l.LENGTH, l.FREESPEED, l.CAPACITY, l.PERMLANES, l.ONEWAY,
                    l.MODES, l.LONG1, l.LAT1, l.LONG2, l.LAT2)
                    .from(l);
            Condition where = l.QUADKEY.like(QuadKey + "%");
            for (int i = 0; i < QuadKey.length() - 1; i++) {
                where = where.or(l.QUADKEY.eq(QuadKey.substring(0, i)));
            }
            query.where(where).and(l.NETWORKID.eq(NetworkId)).and(l.MINLEVEL.lessOrEqual(zoomLevel));
            return query.fetch();

        } catch (SQLException e) {
            throw new DataAccessLayerException(e);
        } catch (DataAccessException e) {
            throw new DataAccessLayerException(e);
        }
    }

    public Date getLastModifiedQuadKey(String QuadKey, int NetworkId, int zoomLevel) throws DataAccessLayerException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            Link l = Tables.LINK.as("l");

            SelectJoinStep<Record1<Date>> query = context.select(l.LASTMODIFIED.max()).from(l);
            Condition where = l.QUADKEY.like(QuadKey + "%");
            for (int i = 0; i < QuadKey.length() - 1; i++) {
                where = where.or(l.QUADKEY.eq(QuadKey.substring(0, i)));
            }

            query.where(where).and(l.NETWORKID.eq(NetworkId)).and(l.MINLEVEL.lessOrEqual(zoomLevel));
            return query.fetch().get(0).value1();
        } catch (SQLException e) {
            throw new DataAccessLayerException(e);
        } catch (DataAccessException e) {
            throw new DataAccessLayerException(e);
        }
    }

    public boolean hasChangeset(long changesetNr) throws DataAccessLayerException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return context.selectCount().from(Tables.CHANGESET).where(Tables.CHANGESET.ID.eq(changesetNr)).fetchOne().value1() > 0;
        } catch (SQLException e) {
            throw new DataAccessLayerException(e);
        } catch (DataAccessException e) {
            throw new DataAccessLayerException(e);
        }
    }

    public int[] deleteLink_Changes(List<LinkChangeRecord> records) throws DataAccessLayerException {
        return DataAccessUtil.deleteRecords(this.properties, records, this.connectionUtil);
    }

    public int[] deleteNode_Changes(List<NodeChangeRecord> records) throws DataAccessLayerException {
        return DataAccessUtil.deleteRecords(this.properties, records, this.connectionUtil);
    }

    public int[] updateLink_Changes(List<LinkChangeRecord> records) throws DataAccessLayerException {
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.LINK_CHANGE, this.connectionUtil);
    }

    public int[] updateNode_Changes(List<NodeChangeRecord> records) throws DataAccessLayerException {
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.NODE_CHANGE, this.connectionUtil);
    }

    public int updateChangeset(ChangesetRecord record) throws DataAccessLayerException {
        return DataAccessUtil.updateRecord(this.properties, record, this.connectionUtil);
    }

    public Long insertChangeset(ChangesetRecord record) throws DataAccessLayerException {
        return (Long) DataAccessUtil.insertRecord(this.properties, record, Tables.CHANGESET, Arrays.asList(Tables.CHANGESET.ID), this.connectionUtil).getValue(0);
    }

    public int deleteChangeset(ChangesetRecord record) throws DataAccessLayerException {
        return DataAccessUtil.deleteRecord(this.properties, record, this.connectionUtil);
    }
}
