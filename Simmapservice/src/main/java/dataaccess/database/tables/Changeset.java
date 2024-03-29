/**
 * This class is generated by jOOQ
 */
package dataaccess.database.tables;


import dataaccess.database.Keys;
import dataaccess.database.Public;
import dataaccess.database.tables.records.ChangesetRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;
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
public class Changeset extends TableImpl<ChangesetRecord> {

    private static final long serialVersionUID = 1840405046;

    /**
     * The reference instance of <code>public.Changeset</code>
     */
    public static final Changeset CHANGESET = new Changeset();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ChangesetRecord> getRecordType() {
        return ChangesetRecord.class;
    }

    /**
     * The column <code>public.Changeset.Id</code>.
     */
    public final TableField<ChangesetRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaulted(true), this, "");

    /**
     * The column <code>public.Changeset.Name</code>.
     */
    public final TableField<ChangesetRecord, String> NAME = createField("Name", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.Changeset.UserNr</code>.
     */
    public final TableField<ChangesetRecord, Integer> USERNR = createField("UserNr", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.Changeset.NetworkId</code>.
     */
    public final TableField<ChangesetRecord, Integer> NETWORKID = createField("NetworkId", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.Changeset.LastModified</code>.
     */
    public final TableField<ChangesetRecord, Timestamp> LASTMODIFIED = createField("LastModified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

    /**
     * Create a <code>public.Changeset</code> table reference
     */
    public Changeset() {
        this("Changeset", null);
    }

    /**
     * Create an aliased <code>public.Changeset</code> table reference
     */
    public Changeset(String alias) {
        this(alias, CHANGESET);
    }

    private Changeset(String alias, Table<ChangesetRecord> aliased) {
        this(alias, aliased, null);
    }

    private Changeset(String alias, Table<ChangesetRecord> aliased, Field<?>[] parameters) {
        super(alias, Public.PUBLIC, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ChangesetRecord, Long> getIdentity() {
        return Keys.IDENTITY_CHANGESET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ChangesetRecord> getPrimaryKey() {
        return Keys.CHANGESET_PRIMARYKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ChangesetRecord>> getKeys() {
        return Arrays.<UniqueKey<ChangesetRecord>>asList(Keys.CHANGESET_PRIMARYKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ChangesetRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ChangesetRecord, ?>>asList(Keys.CHANGESET__CHANGESET_NETWORKID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Changeset as(String alias) {
        return new Changeset(alias, this);
    }

    /**
     * Rename this table
     */
    public Changeset rename(String name) {
        return new Changeset(name, null);
    }
}
