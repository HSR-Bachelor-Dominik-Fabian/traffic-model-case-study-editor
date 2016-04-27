package dataaccess.utils;

import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Created by dohee on 11.04.2016.
 */
public class DataAccessUtil {
    public static Result getRecords(Properties properties, Table table){
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
    //TODO: Duplicated Code!!!
    public static int[] insertOrUpdate(Properties properties, Record[] records, Table table){
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
    public static int[] insertOrUpdate(Properties properties,List<? extends UpdatableRecord<?>> records, Table table){
        return insertOrUpdate(properties, records.toArray(new Record[records.size()]), table);
    }

    public static int[] deleteRecords(Properties properties, List<? extends UpdatableRecord<?>> records, Table table){
        int[] output = null;
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            output = context.batchDelete(records).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output;
    }

    public static int[] updateRecords(Properties properties, List<? extends UpdatableRecord<?>> records, Table table){
        int[] output = null;
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            output = context.batchUpdate(records).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output;
    }

    public static int updateRecord(Properties properties, UpdatableRecord<?> record, Table table){
        int output = 0;
        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            output = context.batchUpdate(record).execute()[0];
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output;
    }

    public static Record insertRecord(Properties properties, Record record, Table table, Collection<? extends Field<?>> returningFields){
        Record output = null;

        String url = properties.getProperty("psqlpath");
        String user = properties.getProperty("psqluser");
        String password = properties.getProperty("psqlpassword");
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            output = context.insertInto(table).set(record).returning(returningFields).fetchOne();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output;
    }
}
