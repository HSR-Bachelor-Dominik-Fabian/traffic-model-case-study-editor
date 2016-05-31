package businesslogic.changeset;

import dataaccess.database.tables.Changeset;
import dataaccess.database.tables.records.ChangesetRecord;

import java.sql.Timestamp;
import java.util.Date;

public class ChangesetModel {
    ChangesetModel() {
    }

    ChangesetModel(ChangesetRecord record) {
        fillModel(record);
    }

    private Long id;
    private String name;
    private int networkId;
    private int userNr;
    private Date lastModified;

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

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    void fillModel(ChangesetRecord record) {
        id = record.getId();
        name = record.getName();
        networkId = record.getNetworkid();
        userNr = record.getUsernr();
        lastModified = record.getLastmodified();
    }

    ChangesetRecord getRecord() {
        ChangesetRecord record = new ChangesetRecord();
        if (id != null) {
            record.setId(id);
        }
        record.setName(name);
        record.setNetworkid(networkId);
        record.setUsernr(userNr);
        record.setLastmodified(new Timestamp(lastModified.getTime()));

        return record;
    }
}
