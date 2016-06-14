/**
 * This class is generated by jOOQ
 */
package dataaccess.database.tables.records;


import dataaccess.database.tables.Network;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;


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
public class NetworkRecord extends UpdatableRecordImpl<NetworkRecord> implements Record2<String, Integer> {

    private static final long serialVersionUID = -1836953755;

    /**
     * Setter for <code>public.Network.Name</code>.
     */
    public void setName(String value) {
        setValue(0, value);
    }

    /**
     * Getter for <code>public.Network.Name</code>.
     */
    public String getName() {
        return (String) getValue(0);
    }

    /**
     * Setter for <code>public.Network.Id</code>.
     */
    public void setId(Integer value) {
        setValue(1, value);
    }

    /**
     * Getter for <code>public.Network.Id</code>.
     */
    public Integer getId() {
        return (Integer) getValue(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<String, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<String, Integer> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Network.NETWORK.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Network.NETWORK.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NetworkRecord value1(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NetworkRecord value2(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NetworkRecord values(String value1, Integer value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached NetworkRecord
     */
    public NetworkRecord() {
        super(Network.NETWORK);
    }

    /**
     * Create a detached, initialised NetworkRecord
     */
    public NetworkRecord(String name, Integer id) {
        super(Network.NETWORK);

        setValue(0, name);
        setValue(1, id);
    }
}
