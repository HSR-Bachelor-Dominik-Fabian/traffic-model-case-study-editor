package dataaccess;

import dataaccess.database.Tables;
import dataaccess.database.tables.Link;
import dataaccess.database.tables.Node;
import dataaccess.database.tables.records.*;
import dataaccess.utils.DataAccessUtil;
import dataaccess.utils.IConnection;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DataAccessLogic {
    public DataAccessLogic(Properties props, IConnection connectionUtil) {
        this.properties = props;
        this.connectionUtil = connectionUtil;
    }

    private final Properties properties;
    private final IConnection connectionUtil;

    public int[] setNetworks(NetworkRecord[] records) throws DataAccessException {
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.NETWORK, this.connectionUtil);
    }

    public int[] setNodes(NodeRecord[] records) throws DataAccessException {
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.NODE, this.connectionUtil);
    }

    public int[] setLinks(LinkRecord[] records) throws DataAccessException {
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.LINK, this.connectionUtil);
    }

    public int[] setNetworkOptions(NetworkOptionsRecord[] records) throws DataAccessException {
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.NETWORK_OPTIONS, this.connectionUtil);
    }

    public Result<NodeRecord> getAllNodes() throws DataAccessException {
        return DataAccessUtil.getRecords(this.properties, Tables.NODE, this.connectionUtil);
    }

    public Result getAllChangesetsPerUser(int userNr) throws DataAccessException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return context.select().from(Tables.CHANGESET).where(Tables.CHANGESET.USERNR.eq(userNr))
                    .orderBy(Tables.CHANGESET.LASTMODIFIED.desc()).fetch();
        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    public ChangesetRecord getChangesetFromNumber(long changsetNr) throws DataAccessException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return (ChangesetRecord) context.select().from(Tables.CHANGESET).where(Tables.CHANGESET.ID.eq(changsetNr)).fetchOne();
        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    public NodeRecord getNodeFromId(String id) throws DataAccessException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return (NodeRecord) context.select().from(Tables.NODE).where(Tables.NODE.ID.eq(id)).fetchOne();
        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    public LinkRecord getLinkFromId(String id) throws DataAccessException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return (LinkRecord) context.select().from(Tables.LINK).where(Tables.LINK.ID.eq(id)).fetchOne();
        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    public Result getLinkChangesfromChangeset(long changesetNr) throws DataAccessException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return context.select().from(Tables.LINK_CHANGE).where(Tables.LINK_CHANGE.CHANGESETNR.eq(changesetNr)).fetch();
        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    public Result getNodeChangefromChangeset(long changesetNr) throws DataAccessException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return context.select().from(Tables.NODE_CHANGE).where(Tables.NODE_CHANGE.CHANGESETNR.eq(changesetNr)).fetch();
        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    public Result getLinksFromQuadKey(String quadKey, int networkId, int zoomLevel) throws DataAccessException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

            Link l = Tables.LINK.as("l");

            SelectJoinStep query = context.select(l.ID, l.LENGTH, l.FREESPEED, l.CAPACITY, l.PERMLANES, l.ONEWAY,
                    l.MODES, l.FROM, l.TO, l.LONG1, l.LAT1, l.LONG2, l.LAT2)
                    .from(l);
            Condition where = l.QUADKEY.like(quadKey + "%");
            for (int i = 0; i <= quadKey.length() - 1; i++) {
                where = where.or(l.QUADKEY.eq(quadKey.substring(0, i)));
            }
            query.where(where).and(l.NETWORKID.eq(networkId)).and(l.MINLEVEL.lessOrEqual(zoomLevel));
            return query.fetch();

        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    public Result getNodesFromIds(List<String> nodeIds, int networkId) throws DataAccessException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

            Node n = Tables.NODE.as("n");

            SelectConditionStep query = context.select(n.ID, n.NETWORKID, n.QUADKEY, n.LAT, n.LONG).from(n)
                    .where(n.ID.in(nodeIds)).and(n.NETWORKID.eq(networkId));
            return query.fetch();

        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    public Date getLastModifiedQuadKey(String quadKey, int networkId, int zoomLevel) throws DataAccessException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            Link l = Tables.LINK.as("l");

            SelectJoinStep<Record1<Date>> query = context.select(l.LASTMODIFIED.max()).from(l);
            Condition where = l.QUADKEY.like(quadKey + "%");
            for (int i = 0; i < quadKey.length() - 1; i++) {
                where = where.or(l.QUADKEY.eq(quadKey.substring(0, i)));
            }

            query.where(where).and(l.NETWORKID.eq(networkId)).and(l.MINLEVEL.lessOrEqual(zoomLevel));
            return query.fetch().get(0).value1();
        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    public boolean hasChangeset(long changesetNr) throws DataAccessException {
        try (Connection conn = this.connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return context.selectCount().from(Tables.CHANGESET).where(Tables.CHANGESET.ID.eq(changesetNr)).fetchOne().value1() > 0;
        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    public int[] deleteLink_Changes(List<LinkChangeRecord> records) throws DataAccessException {
        return DataAccessUtil.deleteRecords(this.properties, records, this.connectionUtil);
    }

    public int[] deleteNode_Changes(List<NodeChangeRecord> records) throws DataAccessException {
        return DataAccessUtil.deleteRecords(this.properties, records, this.connectionUtil);
    }

    public int[] updateLink_Changes(List<LinkChangeRecord> records) throws DataAccessException {
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.LINK_CHANGE, this.connectionUtil);
    }

    public int[] updateNode_Changes(List<NodeChangeRecord> records) throws DataAccessException {
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.NODE_CHANGE, this.connectionUtil);
    }

    public int updateChangeset(ChangesetRecord record) throws DataAccessException {
        return DataAccessUtil.updateRecord(this.properties, record, this.connectionUtil);
    }

    public Long insertChangeset(ChangesetRecord record) throws DataAccessException {
        return (Long) DataAccessUtil.insertRecord(this.properties, record, Tables.CHANGESET, Arrays.asList(Tables.CHANGESET.ID), this.connectionUtil).getValue(0);
    }

    public int deleteChangeset(ChangesetRecord record) throws DataAccessException {
        return DataAccessUtil.deleteRecord(this.properties, record, this.connectionUtil);
    }
}
