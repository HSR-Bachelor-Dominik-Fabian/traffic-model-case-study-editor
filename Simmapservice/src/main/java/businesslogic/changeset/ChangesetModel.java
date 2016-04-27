package businesslogic.changeset;

import dataaccess.database.tables.Changeset;
import dataaccess.database.tables.records.ChangesetRecord;

/**
 * Created by dohee on 13.04.2016.
 */
public class ChangesetModel {
    ChangesetModel(){}
    ChangesetModel(ChangesetRecord record){
        fillModel(record);
    }

    private Long id;
    private String name;
    private int networkId;
    private int userNr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public int getUserNr() {
        return userNr;
    }

    public void setUserNr(int userNr) {
        this.userNr = userNr;
    }

    void fillModel(ChangesetRecord record){
        id = record.getId();
        name = record.getName();
        networkId = record.getNetworkid();
        userNr = record.getUsernr();
    }

    ChangesetRecord getRecord(){
        ChangesetRecord record = new ChangesetRecord();
        if(id != null) {
            record.setId(id);
        }
        record.setName(name);
        record.setNetworkid(networkId);
        record.setUsernr(userNr);

        return record;
    }
}
