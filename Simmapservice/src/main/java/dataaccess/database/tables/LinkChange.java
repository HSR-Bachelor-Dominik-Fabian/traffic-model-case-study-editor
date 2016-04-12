/**
 * This class is generated by jOOQ
 */
package dataaccess.database.tables;


import dataaccess.database.Keys;
import dataaccess.database.Public;
import dataaccess.database.tables.records.LinkChangeRecord;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class LinkChange extends TableImpl<LinkChangeRecord> {

	private static final long serialVersionUID = -603606636;

	/**
	 * The reference instance of <code>public.Link_Change</code>
	 */
	public static final LinkChange LINK_CHANGE = new LinkChange();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<LinkChangeRecord> getRecordType() {
		return LinkChangeRecord.class;
	}

	/**
	 * The column <code>public.Link_Change.Id</code>.
	 */
	public final TableField<LinkChangeRecord, String> ID = createField("Id", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * The column <code>public.Link_Change.ChangesetNr</code>.
	 */
	public final TableField<LinkChangeRecord, Long> CHANGESETNR = createField("ChangesetNr", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>public.Link_Change.NetworkId</code>.
	 */
	public final TableField<LinkChangeRecord, Integer> NETWORKID = createField("NetworkId", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>public.Link_Change.QuadKey</code>.
	 */
	public final TableField<LinkChangeRecord, String> QUADKEY = createField("QuadKey", org.jooq.impl.SQLDataType.VARCHAR.length(18), this, "");

	/**
	 * The column <code>public.Link_Change.Length</code>.
	 */
	public final TableField<LinkChangeRecord, BigDecimal> LENGTH = createField("Length", org.jooq.impl.SQLDataType.NUMERIC, this, "");

	/**
	 * The column <code>public.Link_Change.Freespeed</code>.
	 */
	public final TableField<LinkChangeRecord, BigDecimal> FREESPEED = createField("Freespeed", org.jooq.impl.SQLDataType.NUMERIC, this, "");

	/**
	 * The column <code>public.Link_Change.Capacity</code>.
	 */
	public final TableField<LinkChangeRecord, BigDecimal> CAPACITY = createField("Capacity", org.jooq.impl.SQLDataType.NUMERIC, this, "");

	/**
	 * The column <code>public.Link_Change.Permlanes</code>.
	 */
	public final TableField<LinkChangeRecord, BigDecimal> PERMLANES = createField("Permlanes", org.jooq.impl.SQLDataType.NUMERIC, this, "");

	/**
	 * The column <code>public.Link_Change.Oneway</code>.
	 */
	public final TableField<LinkChangeRecord, Boolean> ONEWAY = createField("Oneway", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

	/**
	 * The column <code>public.Link_Change.Modes</code>.
	 */
	public final TableField<LinkChangeRecord, String> MODES = createField("Modes", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * The column <code>public.Link_Change.From</code>.
	 */
	public final TableField<LinkChangeRecord, String> FROM = createField("From", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * The column <code>public.Link_Change.To</code>.
	 */
	public final TableField<LinkChangeRecord, String> TO = createField("To", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

	/**
	 * The column <code>public.Link_Change.MinLevel</code>.
	 */
	public final TableField<LinkChangeRecord, Integer> MINLEVEL = createField("MinLevel", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>public.Link_Change.LastModified</code>.
	 */
	public final TableField<LinkChangeRecord, Date> LASTMODIFIED = createField("LastModified", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>public.Link_Change.Long1</code>.
	 */
	public final TableField<LinkChangeRecord, BigDecimal> LONG1 = createField("Long1", org.jooq.impl.SQLDataType.NUMERIC, this, "");

	/**
	 * The column <code>public.Link_Change.Lat1</code>.
	 */
	public final TableField<LinkChangeRecord, BigDecimal> LAT1 = createField("Lat1", org.jooq.impl.SQLDataType.NUMERIC, this, "");

	/**
	 * The column <code>public.Link_Change.Long2</code>.
	 */
	public final TableField<LinkChangeRecord, BigDecimal> LONG2 = createField("Long2", org.jooq.impl.SQLDataType.NUMERIC, this, "");

	/**
	 * The column <code>public.Link_Change.Lat2</code>.
	 */
	public final TableField<LinkChangeRecord, BigDecimal> LAT2 = createField("Lat2", org.jooq.impl.SQLDataType.NUMERIC, this, "");

	/**
	 * Create a <code>public.Link_Change</code> table reference
	 */
	public LinkChange() {
		this("Link_Change", null);
	}

	/**
	 * Create an aliased <code>public.Link_Change</code> table reference
	 */
	public LinkChange(String alias) {
		this(alias, LINK_CHANGE);
	}

	private LinkChange(String alias, Table<LinkChangeRecord> aliased) {
		this(alias, aliased, null);
	}

	private LinkChange(String alias, Table<LinkChangeRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<LinkChangeRecord> getPrimaryKey() {
		return Keys.LINK_CHANGE_LINKID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<LinkChangeRecord>> getKeys() {
		return Arrays.<UniqueKey<LinkChangeRecord>>asList(Keys.LINK_CHANGE_LINKID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<LinkChangeRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<LinkChangeRecord, ?>>asList(Keys.LINK_CHANGE__LINK_CHANGE_CHANGESET, Keys.LINK_CHANGE__LINK_CHANGE_LINK_NETWORK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LinkChange as(String alias) {
		return new LinkChange(alias, this);
	}

	/**
	 * Rename this table
	 */
	public LinkChange rename(String name) {
		return new LinkChange(name, null);
	}
}
