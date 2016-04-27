package dataaccess;

import dataaccess.database.Tables;
import dataaccess.database.tables.Link;
import dataaccess.database.tables.LinkChange;
import dataaccess.database.tables.records.*;
import dataaccess.utils.DataAccessUtil;
import javafx.scene.control.Tab;
import org.geotools.data.shapefile.index.Data;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by dohee on 22.03.2016.
 */
public class SimmapDataAccessFacade {
    public SimmapDataAccessFacade(Properties props){
        this.properties = props;
    }
    private final Properties properties;
    public int[] setNetwork(NetworkRecord[] records){
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.NETWORK);
    }

    public int[] setNode(NodeRecord[] records){
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.NODE);
    }

    public int[] setLink(LinkRecord[] records){
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.LINK);
    }

    public int[] setNetworkOptions(NetworkOptionsRecord[] records){
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.NETWORK_OPTIONS);
    }

    public Result<NetworkRecord> getAllNetworks(){
        return DataAccessUtil.getRecords(this.properties, Tables.NETWORK);
    }

    public Result<LinkRecord> getAllLinks(){
        return DataAccessUtil.getRecords(this.properties, Tables.LINK);
    }

    public Result<NodeRecord> getAllNodes(){
        return DataAccessUtil.getRecords(this.properties, Tables.NODE);
    }

    public Result<NetworkOptionsRecord> getAllNetworkOptions(){
        return DataAccessUtil.getRecords(this.properties, Tables.NETWORK_OPTIONS);
    }

    public Result getAllChangesetsPerUser(int userNr){
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return context.select().from(Tables.CHANGESET).where(Tables.CHANGESET.USERNR.eq(userNr)).fetch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ChangesetRecord getChangsetFromNumber(long changsetNr){
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return (ChangesetRecord)context.select().from(Tables.CHANGESET).where(Tables.CHANGESET.ID.eq(changsetNr)).fetchOne();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public NodeRecord getNodeFromId(String id){
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return (NodeRecord)context.select().from(Tables.NODE).where(Tables.NODE.ID.eq(id)).fetchOne();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public LinkRecord getLinkFromId(String id){
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return (LinkRecord)context.select().from(Tables.LINK).where(Tables.LINK.ID.eq(id)).fetchOne();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Result getLinkChangesfromChangeset(long changesetNr) {
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return context.select().from(Tables.LINK_CHANGE).where(Tables.LINK_CHANGE.CHANGESETNR.eq(changesetNr)).fetch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Result getNodeChangefromChangeset(long changesetNr){
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return context.select().from(Tables.NODE_CHANGE).where(Tables.NODE_CHANGE.CHANGESETNR.eq(changesetNr)).fetch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Result getLinkFromQuadKey(String QuadKey, int NetworkId, int zoomLevel){
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

            Link l = Tables.LINK.as("l");

            SelectJoinStep query = context.select(l.ID, l.LENGTH, l.FREESPEED, l.CAPACITY, l.PERMLANES, l.ONEWAY,
                    l.MODES, l.LONG1, l.LAT1, l.LONG2, l.LAT2)
                    .from(l);
            Condition where = l.QUADKEY.like(QuadKey + "%");
            for(int i = 0; i < QuadKey.length()-1; i++){
                where = where.or(l.QUADKEY.eq(QuadKey.substring(0, i)));
            }
            query.where(where).and(l.NETWORKID.eq(NetworkId)).and(l.MINLEVEL.lessOrEqual(zoomLevel));
            return query.fetch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getLastModifiedQuadKey(String QuadKey, int NetworkId, int zoomLevel){
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            Link l = Tables.LINK.as("l");

            SelectJoinStep<Record1<Date>> query = context.select(l.LASTMODIFIED.max()).from(l);
            Condition where = l.QUADKEY.like(QuadKey + "%");
            for(int i = 0; i < QuadKey.length()-1; i++){
                where = where.or(l.QUADKEY.eq(QuadKey.substring(0, i)));
            }

            query.where(where).and(l.NETWORKID.eq(NetworkId)).and(l.MINLEVEL.lessOrEqual(zoomLevel));
            return query.fetch().get(0).value1();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean hasChangeset(long changesetNr){
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return context.selectCount().from(Tables.CHANGESET).where(Tables.CHANGESET.ID.eq(changesetNr)).fetchOne().value1() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int[] deleteLink_Changes(List<LinkChangeRecord> records){
        return DataAccessUtil.deleteRecords(this.properties, records, Tables.LINK_CHANGE);
    }

    public int[] deleteNode_Changes(List<NodeChangeRecord> records){
        return DataAccessUtil.deleteRecords(this.properties, records, Tables.NODE_CHANGE);
    }

    public int[] updateLink_Changes(List<LinkChangeRecord> records){
        return DataAccessUtil.insertOrUpdate(this.properties, records, Tables.LINK_CHANGE);
    }

    public int[] updateNode_Changes(List<NodeChangeRecord> records){
        return DataAccessUtil.insertOrUpdate(this.properties,records, Tables.NODE_CHANGE);
    }

    public int updateChangeset(ChangesetRecord record){
        return DataAccessUtil.updateRecord(this.properties, record, Tables.CHANGESET);
    }

    public Long insertChangeset(ChangesetRecord record){
        return ((Record1<Long>)DataAccessUtil.insertRecord(this.properties, record, Tables.CHANGESET, Arrays.asList(Tables.CHANGESET.ID))).value1();
    }
}
