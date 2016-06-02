package businesslogic.changeset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dataaccess.database.tables.records.LinkChangeRecord;
import dataaccess.database.tables.records.LinkRecord;

import java.math.BigDecimal;
import java.sql.Date;

@JsonIgnoreProperties({"zoomlevel"})
public class Link_ChangeModel {
    //region Private Variables
    private LinkModel defaultValues = null;
    private String id = null;
    private Long changesetNr = null;
    private Integer networkId = null;
    private boolean deleted = false;
    private String quadKey = null;
    private BigDecimal length = null;
    private BigDecimal freespeed = null;
    private BigDecimal capacity = null;
    private BigDecimal permlanes = null;
    private Boolean oneway = null;
    private String modes = null;
    private String from = null;
    private String to = null;
    private Integer minlevel = null;
    private Date lastModified = null;
    private BigDecimal long1 = null;
    private BigDecimal lat1 = null;
    private BigDecimal long2 = null;
    private BigDecimal lat2 = null;
    //endregion

    //region Getter/Setter

    public LinkModel getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(LinkModel defaultValues) {
        this.defaultValues = defaultValues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getChangesetNr() {
        return changesetNr;
    }

    public void setChangesetNr(Long changesetNr) {
        this.changesetNr = changesetNr;
    }

    public Integer getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Integer networkId) {
        this.networkId = networkId;
    }

    public boolean isDeleted() { return deleted; }

    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public String getQuadKey() {
        return quadKey;
    }

    public void setQuadKey(String quadKey) {
        this.quadKey = quadKey;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getFreespeed() {
        return freespeed;
    }

    public void setFreespeed(BigDecimal freespeed) {
        this.freespeed = freespeed;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPermlanes() {
        return permlanes;
    }

    public void setPermlanes(BigDecimal permlanes) {
        this.permlanes = permlanes;
    }

    public Boolean getOneway() {
        return oneway;
    }

    public void setOneway(Boolean oneway) {
        this.oneway = oneway;
    }

    public String getModes() {
        return modes;
    }

    public void setModes(String modes) {
        this.modes = modes;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getMinlevel() {
        return minlevel;
    }

    public void setMinlevel(Integer minlevel) {
        this.minlevel = minlevel;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public BigDecimal getLong1() {
        return long1;
    }

    public void setLong1(BigDecimal long1) {
        this.long1 = long1;
    }

    public BigDecimal getLat1() {
        return lat1;
    }

    public void setLat1(BigDecimal lat1) {
        this.lat1 = lat1;
    }

    public BigDecimal getLong2() {
        return long2;
    }

    public void setLong2(BigDecimal long2) {
        this.long2 = long2;
    }

    public BigDecimal getLat2() {
        return lat2;
    }

    public void setLat2(BigDecimal lat2) {
        this.lat2 = lat2;
    }
    //endregion

    //region Methods

    void fillModel(LinkChangeRecord record, LinkRecord linkRecord) {
        if (record != null && linkRecord != null) {
            id = (id == null) ? record.getId() : id;
            changesetNr = (changesetNr == null) ? record.getChangesetnr() : changesetNr;
            networkId = (networkId == null) ? record.getNetworkid() : networkId;
            quadKey = (quadKey == null) ? record.getQuadkey() : quadKey;
            length = (length == null) ? record.getLength() : length;
            freespeed = (freespeed == null) ? record.getFreespeed() : freespeed;
            capacity = (capacity == null) ? record.getCapacity() : capacity;
            permlanes = (permlanes == null) ? record.getPermlanes() : permlanes;
            oneway = (oneway == null) ? record.getOneway() : oneway;
            modes = (modes == null) ? record.getModes() : modes;
            from = (from == null) ? record.getFrom() : from;
            to = (to == null) ? record.getTo() : to;
            minlevel = (minlevel == null) ? record.getMinlevel() : minlevel;
            lastModified = (lastModified == null) ? record.getLastmodified() : lastModified;
            long1 = (long1 == null) ? record.getLong1() : long1;
            lat1 = (lat1 == null) ? record.getLat1() : lat1;
            long2 = (long2 == null) ? record.getLong2() : long2;
            lat2 = (lat2 == null) ? record.getLat2() : lat2;
            defaultValues = new LinkModel(linkRecord);
            fillModel();
        } else {
            throw new IllegalArgumentException("record and linkRecord can't be null");
        }
    }

    LinkChangeRecord getLinkChangeRecord() {
        LinkChangeRecord record = new LinkChangeRecord();

        record.setId(id);
        record.setChangesetnr(changesetNr);
        record.setNetworkid(networkId);
        record.setQuadkey((!quadKey.equals(defaultValues.getQuadKey())) ? quadKey : null);
        record.setLength((!length.equals(defaultValues.getLength())) ? length : null);
        record.setFreespeed((!freespeed.equals(defaultValues.getFreespeed())) ? freespeed : null);
        record.setCapacity((!capacity.equals(defaultValues.getCapacity())) ? capacity : null);
        record.setPermlanes((!permlanes.equals(defaultValues.getPermlanes())) ? permlanes : null);
        record.setOneway((!oneway.equals(defaultValues.getOneway())) ? oneway : null);
        record.setModes((!modes.equals(defaultValues.getModes())) ? modes : null);
        record.setFrom((!from.equals(defaultValues.getFrom())) ? from : null);
        record.setTo((!to.equals(defaultValues.getTo())) ? to : null);
        record.setMinlevel((!minlevel.equals(defaultValues.getMinlevel())) ? minlevel : null);
        record.setLastmodified((!lastModified.equals(defaultValues.getLastModified())) ? lastModified : null);
        record.setLong1((!long1.equals(defaultValues.getLong1())) ? long1 : null);
        record.setLong2((!long2.equals(defaultValues.getLong2())) ? long2 : null);
        record.setLat1((!lat1.equals(defaultValues.getLat1())) ? lat1 : null);
        record.setLat2((!lat2.equals(defaultValues.getLat2())) ? lat2 : null);

        return record;
    }

    private void fillModel() {
        id = (id == null) ? defaultValues.getId() : id;
        networkId = (networkId == null) ? defaultValues.getNetworkId() : networkId;
        quadKey = (quadKey == null) ? defaultValues.getQuadKey() : quadKey;
        length = (length == null) ? defaultValues.getLength() : length;
        freespeed = (freespeed == null) ? defaultValues.getFreespeed() : freespeed;
        capacity = (capacity == null) ? defaultValues.getCapacity() : capacity;
        permlanes = (permlanes == null) ? defaultValues.getPermlanes() : permlanes;
        oneway = (oneway == null) ? defaultValues.getOneway() : oneway;
        modes = (modes == null) ? defaultValues.getModes() : modes;
        from = (from == null) ? defaultValues.getFrom() : from;
        to = (to == null) ? defaultValues.getTo() : to;
        minlevel = (minlevel == null) ? defaultValues.getMinlevel() : minlevel;
        lastModified = (lastModified == null) ? defaultValues.getLastModified() : lastModified;
        long1 = (long1 == null) ? defaultValues.getLong1() : long1;
        lat1 = (lat1 == null) ? defaultValues.getLat1() : lat1;
        long2 = (long2 == null) ? defaultValues.getLong2() : long2;
        lat2 = (lat2 == null) ? defaultValues.getLat2() : lat2;
    }

    //endregion
}
