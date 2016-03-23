package dataaccess;

import dataaccess.database.tables.*;
import dataaccess.database.tables.records.LinkRecord;
import dataaccess.database.tables.records.NetworkOptionsRecord;
import dataaccess.database.tables.records.NetworkRecord;
import dataaccess.database.tables.records.NodeRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
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

    public Result<LinkRecord> getLinkFromQuadKey(String QuadKey, int NetworkId){
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            //return context.select().from(Tables.LINK).where(Tables.LINK.QUADKEY.startsWith("123123")).fetch();


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
                queries.add(context.insertInto(table).set(rec).onDuplicateKeyUpdate().set(rec));
            }
            output = context.batch(queries).execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output;
    }
}
