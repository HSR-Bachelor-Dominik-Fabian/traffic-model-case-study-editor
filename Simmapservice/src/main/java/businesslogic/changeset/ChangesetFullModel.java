package businesslogic.changeset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dataaccess.database.tables.records.ChangesetRecord;
import dataaccess.database.tables.records.LinkChangeRecord;
import dataaccess.database.tables.records.NodeChangeRecord;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties({"geoJson"})
public class ChangesetFullModel extends ChangesetModel {
    public ChangesetFullModel() {
        super();
    }

    public ChangesetFullModel(ChangesetRecord record) {
        super(record);
    }

    private List<LinkChangeModel> link_changeModels;
    private List<NodeChangeModel> node_changeModels;

    public void updateChangesetNr(Long nr) {
        setId(nr);
        link_changeModels.parallelStream().forEach(model -> model.setChangesetNr(nr));
        node_changeModels.parallelStream().forEach(model -> model.setChangesetNr(nr));
    }

    public List<LinkChangeModel> getLink_changeModels() {
        return link_changeModels;
    }

    public void setLink_changeModels(List<LinkChangeModel> link_changeModels) {
        this.link_changeModels = link_changeModels;
    }

    @JsonIgnore
    public List<LinkChangeRecord> getAllLink_changeModelsAsRecord() {
        return link_changeModels.parallelStream().map(LinkChangeModel::getLinkChangeRecord).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<LinkChangeRecord> getLink_changeModelsToUpdate() {
        return link_changeModels.parallelStream().filter(model -> !model.isDeleted())
                .map(LinkChangeModel::getLinkChangeRecord).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<LinkChangeRecord> getLink_changeModelsToDelete() {
        return link_changeModels.parallelStream().filter(model -> model.isDeleted())
                .map(LinkChangeModel::getLinkChangeRecord).collect(Collectors.toList());
    }

    public List<NodeChangeModel> getNode_changeModels() {
        return node_changeModels;
    }

    public void setNode_changeModels(List<NodeChangeModel> node_changeModels) {
        this.node_changeModels = node_changeModels;
    }

    @JsonIgnore
    public List<NodeChangeRecord> getAllNode_changeModelsAsRecord() {
        return node_changeModels.parallelStream().map(NodeChangeModel::getNodeChangeRecord).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<NodeChangeRecord> getNode_changeModelsToUpdate() {
        return node_changeModels.parallelStream().filter(model -> !model.isDeleted())
                .map(NodeChangeModel::getNodeChangeRecord).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<NodeChangeRecord> getNode_changeModelsToDelete() {
        return node_changeModels.parallelStream().filter(model -> model.isDeleted())
                .map(NodeChangeModel::getNodeChangeRecord).collect(Collectors.toList());
    }
}
