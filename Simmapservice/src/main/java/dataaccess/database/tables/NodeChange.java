/**
 * This class is generated by jOOQ
 */
package dataaccess.database.tables;


import dataaccess.database.Keys;
import dataaccess.database.Public;
import dataaccess.database.tables.records.NodeChangeRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.math.BigDecimal;
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
public class NodeChange extends TableImpl<NodeChangeRecord> {

    private static final long serialVersionUID = 2055154803;

    /**
     * The reference instance of <code>public.Node_Change</code>
     */
    public static final NodeChange NODE_CHANGE = new NodeChange();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<NodeChangeRecord> getRecordType() {
        return NodeChangeRecord.class;
    }

    /**
     * The column <code>public.Node_Change.Id</code>.
     */
    public final TableField<NodeChangeRecord, String> ID = createField("Id", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

    /**
     * The column <code>public.Node_Change.ChangesetNr</code>.
     */
    public final TableField<NodeChangeRecord, Long> CHANGESETNR = createField("ChangesetNr", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.Node_Change.NetworkId</code>.
     */
    public final TableField<NodeChangeRecord, Integer> NETWORKID = createField("NetworkId", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.Node_Change.QuadKey</code>.
     */
    public final TableField<NodeChangeRecord, String> QUADKEY = createField("QuadKey", org.jooq.impl.SQLDataType.VARCHAR.length(18), this, "");

    /**
     * The column <code>public.Node_Change.X</code>.
     */
    public final TableField<NodeChangeRecord, BigDecimal> X = createField("X", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.Node_Change.Y</code>.
     */
    public final TableField<NodeChangeRecord, BigDecimal> Y = createField("Y", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.Node_Change.Lat</code>.
     */
    public final TableField<NodeChangeRecord, BigDecimal> LAT = createField("Lat", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * The column <code>public.Node_Change.Long</code>.
     */
    public final TableField<NodeChangeRecord, BigDecimal> LONG = createField("Long", org.jooq.impl.SQLDataType.NUMERIC, this, "");

    /**
     * Create a <code>public.Node_Change</code> table reference
     */
    public NodeChange() {
        this("Node_Change", null);
    }

    /**
     * Create an aliased <code>public.Node_Change</code> table reference
     */
    public NodeChange(String alias) {
        this(alias, NODE_CHANGE);
    }

    private NodeChange(String alias, Table<NodeChangeRecord> aliased) {
        this(alias, aliased, null);
    }

    private NodeChange(String alias, Table<NodeChangeRecord> aliased, Field<?>[] parameters) {
        super(alias, Public.PUBLIC, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<NodeChangeRecord> getPrimaryKey() {
        return Keys.NODE_CHANGEID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<NodeChangeRecord>> getKeys() {
        return Arrays.<UniqueKey<NodeChangeRecord>>asList(Keys.NODE_CHANGEID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<NodeChangeRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<NodeChangeRecord, ?>>asList(Keys.NODE_CHANGE__NODE_CHANGE_CHANGESETNR, Keys.NODE_CHANGE__NODE_CHANGE_NETWORKID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeChange as(String alias) {
        return new NodeChange(alias, this);
    }

    /**
     * Rename this table
     */
    public NodeChange rename(String name) {
        return new NodeChange(name, null);
    }
}
