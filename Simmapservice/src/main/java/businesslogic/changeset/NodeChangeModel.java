package businesslogic.changeset;

import dataaccess.database.tables.records.NodeChangeRecord;
import dataaccess.database.tables.records.NodeRecord;

import java.math.BigDecimal;

public class NodeChangeModel {
    //region Private Variables
    private NodeModel defaultValues = null;
    private String id = null;
    private Long changesetNr = null;
    private Integer networkId = null;
    private boolean deleted = false;
    private String quadKey = null;
    private BigDecimal x = null;
    private BigDecimal y = null;
    private BigDecimal latitude = null;
    private BigDecimal longitude = null;
    //endregion

    //region Getter/Setter


    public NodeModel getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(NodeModel defaultValues) {
        this.defaultValues = defaultValues;
    }

    public Long getChangesetNr() {
        return changesetNr;
    }

    public void setChangesetNr(Long changesetNr) {
        this.changesetNr = changesetNr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Integer networkId) {
        this.networkId = networkId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getQuadKey() {
        return quadKey;
    }

    public void setQuadKey(String quadKey) {
        this.quadKey = quadKey;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    //endregion

    NodeChangeRecord getNodeChangeRecord() {
        NodeChangeRecord record = new NodeChangeRecord();

        record.setId(id);
        record.setChangesetnr(changesetNr);
        record.setNetworkid(networkId);
        record.setQuadkey((!quadKey.equals(defaultValues.getQuadKey())) ? quadKey : null);
        record.setX((!x.equals(defaultValues.getX())) ? x : null);
        record.setY((!y.equals(defaultValues.getY())) ? y : null);
        record.setLat((!latitude.equals(defaultValues.getLatitude())) ? latitude : null);
        record.setLong((!longitude.equals(defaultValues.getLongitude())) ? longitude : null);

        return record;
    }

    //region Methods
    void fillModel(NodeChangeRecord record, NodeRecord nodeRecord) {
        id = (id == null) ? record.getId() : id;
        changesetNr = (changesetNr == null) ? record.getChangesetnr() : changesetNr;
        networkId = (networkId == null) ? record.getNetworkid() : networkId;
        quadKey = (quadKey == null) ? record.getQuadkey() : quadKey;
        x = (x == null) ? record.getX() : x;
        y = (y == null) ? record.getY() : y;
        latitude = (latitude == null) ? record.getLat() : latitude;
        longitude = (longitude == null) ? record.getLong() : longitude;
        defaultValues = new NodeModel(nodeRecord);
        fillModel();
    }

    private void fillModel() {
        id = (id == null) ? defaultValues.getId() : id;
        networkId = (networkId == null) ? defaultValues.getNetworkId() : networkId;
        quadKey = (quadKey == null) ? defaultValues.getQuadKey() : quadKey;
        x = (x == null) ? defaultValues.getX() : x;
        y = (y == null) ? defaultValues.getY() : y;
        latitude = (latitude == null) ? defaultValues.getLatitude() : latitude;
        longitude = (longitude == null) ? defaultValues.getLongitude() : longitude;
    }
    //endregion
}
