/**
 * This class is generated by jOOQ
 */
package dataaccess.database.tables.records;


import dataaccess.database.tables.NodeChange;

import java.math.BigDecimal;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


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
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class NodeChangeRecord extends UpdatableRecordImpl<NodeChangeRecord> implements Record8<String, Long, Integer, String, BigDecimal, BigDecimal, BigDecimal, BigDecimal> {

	private static final long serialVersionUID = 1720772193;

	/**
	 * Setter for <code>public.Node_Change.Id</code>.
	 */
	public void setId(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>public.Node_Change.Id</code>.
	 */
	public String getId() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>public.Node_Change.ChangesetNr</code>.
	 */
	public void setChangesetnr(Long value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>public.Node_Change.ChangesetNr</code>.
	 */
	public Long getChangesetnr() {
		return (Long) getValue(1);
	}

	/**
	 * Setter for <code>public.Node_Change.NetworkId</code>.
	 */
	public void setNetworkid(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>public.Node_Change.NetworkId</code>.
	 */
	public Integer getNetworkid() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>public.Node_Change.QuadKey</code>.
	 */
	public void setQuadkey(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>public.Node_Change.QuadKey</code>.
	 */
	public String getQuadkey() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>public.Node_Change.X</code>.
	 */
	public void setX(BigDecimal value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>public.Node_Change.X</code>.
	 */
	public BigDecimal getX() {
		return (BigDecimal) getValue(4);
	}

	/**
	 * Setter for <code>public.Node_Change.Y</code>.
	 */
	public void setY(BigDecimal value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>public.Node_Change.Y</code>.
	 */
	public BigDecimal getY() {
		return (BigDecimal) getValue(5);
	}

	/**
	 * Setter for <code>public.Node_Change.Lat</code>.
	 */
	public void setLat(BigDecimal value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>public.Node_Change.Lat</code>.
	 */
	public BigDecimal getLat() {
		return (BigDecimal) getValue(6);
	}

	/**
	 * Setter for <code>public.Node_Change.Long</code>.
	 */
	public void setLong(BigDecimal value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>public.Node_Change.Long</code>.
	 */
	public BigDecimal getLong() {
		return (BigDecimal) getValue(7);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record3<String, Long, Integer> key() {
		return (Record3) super.key();
	}

	// -------------------------------------------------------------------------
	// Record8 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row8<String, Long, Integer, String, BigDecimal, BigDecimal, BigDecimal, BigDecimal> fieldsRow() {
		return (Row8) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row8<String, Long, Integer, String, BigDecimal, BigDecimal, BigDecimal, BigDecimal> valuesRow() {
		return (Row8) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return NodeChange.NODE_CHANGE.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field2() {
		return NodeChange.NODE_CHANGE.CHANGESETNR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return NodeChange.NODE_CHANGE.NETWORKID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return NodeChange.NODE_CHANGE.QUADKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field5() {
		return NodeChange.NODE_CHANGE.X;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field6() {
		return NodeChange.NODE_CHANGE.Y;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field7() {
		return NodeChange.NODE_CHANGE.LAT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigDecimal> field8() {
		return NodeChange.NODE_CHANGE.LONG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value2() {
		return getChangesetnr();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getNetworkid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getQuadkey();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value5() {
		return getX();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value6() {
		return getY();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value7() {
		return getLat();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal value8() {
		return getLong();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NodeChangeRecord value1(String value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NodeChangeRecord value2(Long value) {
		setChangesetnr(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NodeChangeRecord value3(Integer value) {
		setNetworkid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NodeChangeRecord value4(String value) {
		setQuadkey(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NodeChangeRecord value5(BigDecimal value) {
		setX(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NodeChangeRecord value6(BigDecimal value) {
		setY(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NodeChangeRecord value7(BigDecimal value) {
		setLat(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NodeChangeRecord value8(BigDecimal value) {
		setLong(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NodeChangeRecord values(String value1, Long value2, Integer value3, String value4, BigDecimal value5, BigDecimal value6, BigDecimal value7, BigDecimal value8) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached NodeChangeRecord
	 */
	public NodeChangeRecord() {
		super(NodeChange.NODE_CHANGE);
	}

	/**
	 * Create a detached, initialised NodeChangeRecord
	 */
	public NodeChangeRecord(String id, Long changesetnr, Integer networkid, String quadkey, BigDecimal x, BigDecimal y, BigDecimal lat, BigDecimal long_) {
		super(NodeChange.NODE_CHANGE);

		setValue(0, id);
		setValue(1, changesetnr);
		setValue(2, networkid);
		setValue(3, quadkey);
		setValue(4, x);
		setValue(5, y);
		setValue(6, lat);
		setValue(7, long_);
	}
}