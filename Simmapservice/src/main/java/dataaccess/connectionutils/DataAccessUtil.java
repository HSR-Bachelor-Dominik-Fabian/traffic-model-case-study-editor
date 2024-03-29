package dataaccess.connectionutils;

import dataaccess.DataAccessException;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public final class DataAccessUtil {
    public static Result getRecords(Properties properties, Table table, IConnection connectionUtil) throws DataAccessException {
        try (Connection conn = connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            return context.select().from(table).fetch();
        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    public static int[] insertOrUpdate(Properties properties, Record[] records, Table table, IConnection connectionUtil) throws DataAccessException {
        int[] output;
        try (Connection conn = connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            ArrayList<Query> queries = new ArrayList<>();

            for (Record rec : records) {
                if (rec != null) {
                    queries.add(context.insertInto(table).set(rec).onDuplicateKeyUpdate().set(rec));
                }
            }
            output = context.batch(queries).execute();

        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }

        return output;
    }

    public static int[] insertOrUpdate(Properties properties, List<? extends UpdatableRecord<?>> records, Table table, IConnection connectionUtil) throws DataAccessException {
        return insertOrUpdate(properties, records.toArray(new Record[records.size()]), table, connectionUtil);
    }

    public static int[] deleteRecords(Properties properties, List<? extends UpdatableRecord<?>> records, IConnection connectionUtil) throws DataAccessException {
        int[] output;
        try (Connection conn = connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            output = context.batchDelete(records).execute();
        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }

        return output;
    }

    public static int updateRecord(Properties properties, UpdatableRecord<?> record, IConnection connectionUtil) throws DataAccessException {
        int output;
        try (Connection conn = connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            output = context.batchUpdate(record).execute()[0];
        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }

        return output;
    }

    public static Record insertRecord(Properties properties, Record record, Table table, Collection<? extends Field<?>> returningFields, IConnection connectionUtil) throws DataAccessException {
        Record output;

        try (Connection conn = connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            output = context.insertInto(table).set(record).returning(returningFields).fetchOne();
        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }

        return output;
    }

    public static int deleteRecord(Properties properties, UpdatableRecord<?> record, IConnection connectionUtil) throws DataAccessException {
        int output;

        try (Connection conn = connectionUtil.getConnectionFromProps(properties)) {
            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
            output = context.batchDelete(record).execute()[0];
        } catch (SQLException | org.jooq.exception.DataAccessException e) {
            throw new DataAccessException(e);
        }

        return output;
    }
}
