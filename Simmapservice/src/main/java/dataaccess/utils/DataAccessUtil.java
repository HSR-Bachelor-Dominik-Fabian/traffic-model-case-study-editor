package dataaccess.utils;

import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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
}
