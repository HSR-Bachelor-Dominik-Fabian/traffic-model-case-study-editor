package businesslogic.changeset;

import dataaccess.database.tables.records.LinkChangeRecord;
import dataaccess.database.tables.records.LinkRecord;

import java.util.Date;

/**
 * Created by dohee on 13.04.2016.
 */
public class Link_ChangeModel {
    //region Private Variables
    private String id = null;
    private Long changesetNr = null;
    private Integer networkId = null;
    private String quadKey = null;
    private Number length = null;
    private Number freespeed = null;
    private Number capacity = null;
    private Number permlanes = null;
    private Boolean oneway = null;
    private String modes = null;
    private String from = null;
    private String to = null;
    private Integer minlevel = null;
    private Date lastModified = null;
    private Number long1 = null;
    private Number lat1 = null;
    private Number long2 = null;
    private Number lat2 = null;
    //endregion

    //region Getter/Setter
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

    public String getQuadKey() {
        return quadKey;
    }

    public void setQuadKey(String quadKey) {
        this.quadKey = quadKey;
    }

    public Number getLength() {
        return length;
    }

    public void setLength(Number length) {
        this.length = length;
    }

    public Number getFreespeed() {
        return freespeed;
    }

    public void setFreespeed(Number freespeed) {
        this.freespeed = freespeed;
    }

    public Number getCapacity() {
        return capacity;
    }

    public void setCapacity(Number capacity) {
        this.capacity = capacity;
    }

    public Number getPermlanes() {
        return permlanes;
    }

    public void setPermlanes(Number permlanes) {
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

    public Number getLong1() {
        return long1;
    }

    public void setLong1(Number long1) {
        this.long1 = long1;
    }

    public Number getLat1() {
        return lat1;
    }

    public void setLat1(Number lat1) {
        this.lat1 = lat1;
    }

    public Number getLong2() {
        return long2;
    }

    public void setLong2(Number long2) {
        this.long2 = long2;
    }

    public Number getLat2() {
        return lat2;
    }

    public void setLat2(Number lat2) {
        this.lat2 = lat2;
    }
    //endregion

    //region Methods

    boolean isEverythingSet(){
        return (id!=null) && (changesetNr!=null) && (networkId!=null) && (quadKey!= null) && (length != null)
                && (freespeed != null) && (freespeed != null) && (capacity != null) && (permlanes != null)
                && (oneway != null) && (modes != null) && (minlevel != null)
                && (lastModified != null) && (long1 != null) && (lat1 != null) && (long2 != null) && (lat2 != null);
    }

    void fillModel(LinkChangeRecord record){
        id = (id == null)?record.getId():id;
        changesetNr = (changesetNr == null)?record.getChangesetnr():changesetNr;
        networkId = (networkId == null)?record.getNetworkid():networkId;
        quadKey = (quadKey == null)?record.getQuadkey():quadKey;
        length = (length == null)?record.getLength():length;
        freespeed = (freespeed == null)?record.getFreespeed():freespeed;
        capacity = (capacity == null)? record.getCapacity():capacity;
        permlanes = (permlanes == null)? record.getPermlanes():permlanes;
        oneway = (oneway == null)?record.getOneway():oneway;
        modes = (modes == null)? record.getModes():modes;
        from = (from == null)?record.getFrom():from;
        to = (to == null)? record.getTo():to;
        minlevel = (minlevel == null)?record.getMinlevel():minlevel;
        lastModified = (lastModified == null)?record.getLastmodified():lastModified;
        long1 = (long1 == null)?record.getLong1():long1;
        lat1 = (lat1 == null)?record.getLat1():lat1;
        long2 = (long2 == null)?record.getLong2():long2;
        lat2 = (lat2 == null)?record.getLat2():lat2;
    }

    void fillModel(LinkRecord record){
        id = (id == null)?record.getId():id;
        networkId = (networkId == null)?record.getNetworkid():networkId;
        quadKey = (quadKey == null)?record.getQuadkey():quadKey;
        length = (length == null)?record.getLength():length;
        freespeed = (freespeed == null)?record.getFreespeed():freespeed;
        capacity = (capacity == null)? record.getCapacity():capacity;
        permlanes = (permlanes == null)? record.getPermlanes():permlanes;
        oneway = (oneway == null)?record.getOneway():oneway;
        modes = (modes == null)? record.getModes():modes;
        from = (from == null)?record.getFrom():from;
        to = (to == null)? record.getTo():to;
        minlevel = (minlevel == null)?record.getMinlevel():minlevel;
        lastModified = (lastModified == null)?record.getLastmodified():lastModified;
        long1 = (long1 == null)?record.getLong1():long1;
        lat1 = (lat1 == null)?record.getLat1():lat1;
        long2 = (long2 == null)?record.getLong2():long2;
        lat2 = (lat2 == null)?record.getLat2():lat2;
    }

    //endregion
}
