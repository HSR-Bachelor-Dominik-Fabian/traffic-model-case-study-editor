package dataaccess;

import dataaccess.database.Tables;
import dataaccess.database.tables.Link;
import dataaccess.database.tables.LinkChange;
import dataaccess.database.tables.records.*;
import dataaccess.utils.DataAccessUtil;
import org.geotools.data.shapefile.index.Data;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    public Result<ChangesetRecord> getAllChangesets(){return DataAccessUtil.getRecords(this.properties, Tables.CHANGESET);}

    public Result getLinkChangesfromChangeset(long changesetNr) {
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return context.select(Tables.LINK_CHANGE.fields()).from(Tables.LINK_CHANGE).where(Tables.LINK_CHANGE.CHANGESETNR.eq(changesetNr)).fetch();
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



}
