package businesslogic.changeset;

import dataaccess.database.tables.records.NodeChangeRecord;
import dataaccess.database.tables.records.NodeRecord;

import java.math.BigDecimal;

/**
 * Created by dohee on 13.04.2016.
 */
public class Node_ChangeModel {
    //region Private Variables
    private String id = null;
    private Long changesetNr= null;
    private Integer networkId = null;
    private String quadKey = null;
    private BigDecimal x = null;
    private BigDecimal y = null;
    private Number latitude = null;
    private Number longitude = null;
    //endregion

    //region Getter/Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getChangeSetNr() {
        return changesetNr;
    }

    public void setChangeSetNr(Long changeSetNr) {
        this.changesetNr = changeSetNr;
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

    public Number getLatitude() {
        return latitude;
    }

    public void setLatitude(Number latitude) {
        this.latitude = latitude;
    }

    public Number getLongitude() {
        return longitude;
    }

    public void setLongitude(Number longitude) {
        this.longitude = longitude;
    }
    //endregion

    //region Methods
    boolean isEverythingSet(){
        return (id!=null)&&(changesetNr!=null)&&(networkId!=null)&&(quadKey!=null)&&(x!=null)&&(y!=null)&&(latitude!=null)
                &&(longitude!=null);
    }

    void fillModel(NodeChangeRecord record){
        id = (id == null)?record.getId():id;
        changesetNr = (changesetNr == null)?record.getChangesetnr():changesetNr;
        networkId = (networkId == null)?record.getNetworkid():networkId;
        quadKey = (quadKey == null)?record.getQuadkey():quadKey;
        x = (x == null)?record.getX():x;
        y = (y == null)?record.getY():y;
        latitude = (latitude == null)?record.getLat():latitude;
        longitude = (longitude == null)?record.getLong():longitude;
    }

    void fillModel(NodeRecord record){
        id = (id == null)?record.getId():id;
        networkId = (networkId == null)?record.getNetworkid():networkId;
        quadKey = (quadKey == null)?record.getQuadkey():quadKey;
        x = (x == null)?record.getX():x;
        y = (y == null)?record.getY():y;
        latitude = (latitude == null)?record.getLat():latitude;
        longitude = (longitude == null)?record.getLong():longitude;
    }
    //endregion
}
