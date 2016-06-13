/**
 * This class is generated by jOOQ
 */
package dataaccess.database.tables;


import dataaccess.database.Keys;
import dataaccess.database.Public;
import dataaccess.database.tables.records.LinkRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Link extends TableImpl<LinkRecord> {

    private static final long serialVersionUID = 845532178;

    /**
     * The reference instance of <code>public.Link</code>
     */
    public static final Link LINK = new Link();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LinkRecord> getRecordType() {
        return LinkRecord.class;
    }

    /**
     * The column <code>public.Link.Id</code>.
     */
    public final TableField<LinkRecord, String> ID = createField("Id", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

    /**
     * The column <code>public.Link.NetworkId</code>.
     */
    public final TableField<LinkRecord, Integer> NETWORKID = createField("NetworkId", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.Link.QuadKey</code>.
     */
    public final TableField<LinkRecord, String> QUADKEY = createField("QuadKey", org.jooq.impl.SQLDataType.VARCHAR.length(18), this, "");

    /**
     * The column <code>public.Link.Length</code>.
     */
    public final TableField<LinkRecord, BigDecimal> LENGTH = createField("Length", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.Link.Freespeed</code>.
     */
    public final TableField<LinkRecord, BigDecimal> FREESPEED = createField("Freespeed", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.Link.Capacity</code>.
     */
    public final TableField<LinkRecord, BigDecimal> CAPACITY = createField("Capacity", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.Link.Permlanes</code>.
     */
    public final TableField<LinkRecord, BigDecimal> PERMLANES = createField("Permlanes", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.Link.Oneway</code>.
     */
    public final TableField<LinkRecord, Boolean> ONEWAY = createField("Oneway", org.jooq.impl.SQLDataType.BOOLEAN, this, "\r\n");

    /**
     * The column <code>public.Link.Modes</code>.
     */
    public final TableField<LinkRecord, String> MODES = createField("Modes", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

    /**
     * The column <code>public.Link.From</code>.
     */
    public final TableField<LinkRecord, String> FROM = createField("From", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

    /**
     * The column <code>public.Link.To</code>.
     */
    public final TableField<LinkRecord, String> TO = createField("To", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

    /**
     * The column <code>public.Link.MinLevel</code>.
     */
    public final TableField<LinkRecord, Integer> MINLEVEL = createField("MinLevel", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.Link.LastModified</code>.
     */
    public final TableField<LinkRecord, Date> LASTMODIFIED = createField("LastModified", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>public.Link.Long1</code>.
     */
    public final TableField<LinkRecord, BigDecimal> LONG1 = createField("Long1", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.Link.Lat1</code>.
     */
    public final TableField<LinkRecord, BigDecimal> LAT1 = createField("Lat1", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.Link.Long2</code>.
     */
    public final TableField<LinkRecord, BigDecimal> LONG2 = createField("Long2", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.Link.Lat2</code>.
     */
    public final TableField<LinkRecord, BigDecimal> LAT2 = createField("Lat2", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * Create a <code>public.Link</code> table reference
     */
    public Link() {
        this("Link", null);
    }

    /**
     * Create an aliased <code>public.Link</code> table reference
     */
    public Link(String alias) {
        this(alias, LINK);
    }

    private Link(String alias, Table<LinkRecord> aliased) {
        this(alias, aliased, null);
    }

    private Link(String alias, Table<LinkRecord> aliased, Field<?>[] parameters) {
        super(alias, Public.PUBLIC, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LinkRecord> getPrimaryKey() {
        return Keys.LINKID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LinkRecord>> getKeys() {
        return Arrays.<UniqueKey<LinkRecord>>asList(Keys.LINKID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<LinkRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<LinkRecord, ?>>asList(Keys.LINK__LINK_NETWORK, Keys.LINK__LINK_NODE_FROM, Keys.LINK__LINK_NODE_TO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Link as(String alias) {
        return new Link(alias, this);
    }

    /**
     * Rename this table
     */
    public Link rename(String name) {
        return new Link(name, null);
    }
}
