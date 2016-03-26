package dataaccess;

import dataaccess.database.Tables;
import dataaccess.database.tables.Link;
import dataaccess.database.tables.Node;
import dataaccess.database.tables.records.LinkRecord;
import dataaccess.database.tables.records.NetworkOptionsRecord;
import dataaccess.database.tables.records.NetworkRecord;
import dataaccess.database.tables.records.NodeRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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
        return this.insertOrUpdate(records, Tables.NETWORK);
    }

    public int[] setNode(NodeRecord[] records){
        return this.insertOrUpdate(records, Tables.NODE);
    }

    public int[] setLink(LinkRecord[] records){
        return this.insertOrUpdate(records, Tables.LINK);
    }

    public int[] setNetworkOptions(NetworkOptionsRecord[] records){
        return this.insertOrUpdate(records, Tables.NETWORK_OPTIONS);
    }

    public Result<NetworkRecord> getAllNetworks(){
        return this.getRecords(Tables.NETWORK);
    }

    public Result<LinkRecord> getAllLinks(){
        return this.getRecords(Tables.LINK);
    }

    public Result<NodeRecord> getAllNodes(){
        return this.getRecords(Tables.NODE);
    }

    public Result<NetworkOptionsRecord> getAllNetworkOptions(){
        return this.getRecords(Tables.NETWORK_OPTIONS);
    }

    public Result getLinkFromQuadKey(String QuadKey, int NetworkId, int zoomLevel){
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

            Link l = Tables.LINK.as("l");
            Node n1 = Tables.NODE.as("n1");
            Node n2 = Tables.NODE.as("n2");
            String regexp = "^";
            for(int i = 0; i < QuadKey.length()-1; i++){

                regexp +="(" + QuadKey.charAt(i);
            }
            for(int i=0; i < QuadKey.length()-1; i++){
                regexp +=")?";
            }
            regexp += "$";

            return context.select(l.ID, l.LENGTH, l.FREESPEED, l.CAPACITY, l.PERMLANES, l.ONEWAY, l.MODES, n1.LONG.as("Long1"),
                    n1.LAT.as("Lat1"), n2.LONG.as("Long2"), n2.LAT.as("Lat2")).from(l).join(n1).on(l.FROM.eq(n1.ID))
                    .join(n2).on(l.TO.eq(n2.ID)).where(l.QUADKEY.like(QuadKey+"%")).or(l.QUADKEY.likeRegex(regexp)).and(l.NETWORKID.eq(NetworkId))
                    .and(l.MINLEVEL.lessOrEqual(zoomLevel)).fetch();

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
            String regexp = "^";
            for(int i = 0; i < QuadKey.length()-1; i++){

                regexp +="(" + QuadKey.charAt(i);
            }
            for(int i=0; i < QuadKey.length()-1; i++){
                regexp +=")?";
            }
            regexp += "$";
            return context.select(l.LASTMODIFIED.max()).from(l).where(l.QUADKEY.like(QuadKey+"%")).or(l.QUADKEY.likeRegex(regexp))
                    .and(l.NETWORKID.eq(NetworkId)).and(l.MINLEVEL.lessOrEqual(zoomLevel)).fetch().get(0).value1();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Result getRecords(Table table){
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return  context.select().from(table).fetch();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private int[] insertOrUpdate(Record[] records, Table table){
        int[] output = null;
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            ArrayList<Query> queries = new ArrayList<>();

            for(Record rec: records){
                if (rec != null) {
                    queries.add(context.insertInto(table).set(rec).onDuplicateKeyUpdate().set(rec));
                }
            }
            output = context.batch(queries).execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output;
    }
}
