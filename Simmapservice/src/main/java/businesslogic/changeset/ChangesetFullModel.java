package businesslogic.changeset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dataaccess.database.tables.records.ChangesetRecord;
import dataaccess.database.tables.records.LinkChangeRecord;
import dataaccess.database.tables.records.NodeChangeRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dohee on 13.04.2016.
 */
@JsonIgnoreProperties({"geoJson"})
public class ChangesetFullModel extends ChangesetModel {
    ChangesetFullModel(){super();}
    ChangesetFullModel(ChangesetRecord record){
        super(record);
    }
    private List<Link_ChangeModel> link_changeModels;
    private List<Node_ChangeModel> node_changeModels;

    public void updateChangesetNr(Long nr){
        setId(nr);
        link_changeModels.parallelStream().forEach(model->model.setChangesetNr(nr));
        node_changeModels.parallelStream().forEach(model->model.setChangesetNr(nr));
    }
    public List<Link_ChangeModel> getLink_changeModels() {
        return link_changeModels;
    }
    public void setLink_changeModels(List<Link_ChangeModel> link_changeModels) {
        this.link_changeModels = link_changeModels;
    }
    @JsonIgnore
    public List<LinkChangeRecord> getAllLink_changeModelsAsRecord(){
        return link_changeModels.parallelStream().map(Link_ChangeModel::getLinkChangeRecord).collect(Collectors.toList());
    }
    @JsonIgnore
    public List<LinkChangeRecord> getLink_changeModelsToUpdate(){
        return link_changeModels.parallelStream().filter(model->!model.isDeleted())
                .map(Link_ChangeModel::getLinkChangeRecord).collect(Collectors.toList());
    }
    @JsonIgnore
    public List<LinkChangeRecord> getLink_changeModelsToDelete(){
        return link_changeModels.parallelStream().filter(model->model.isDeleted())
                .map(Link_ChangeModel::getLinkChangeRecord).collect(Collectors.toList());
    }
    public List<Node_ChangeModel> getNode_changeModels() {
        return node_changeModels;
    }
    public void setNode_changeModels(List<Node_ChangeModel> node_changeModels) {
        this.node_changeModels = node_changeModels;
    }
    @JsonIgnore
    public List<NodeChangeRecord> getAllNode_changeModelsAsRecord(){
        return node_changeModels.parallelStream().map(Node_ChangeModel::getNodeChangeRecord).collect(Collectors.toList());
    }
    @JsonIgnore
    public List<NodeChangeRecord> getNode_changeModelsToUpdate(){
        return node_changeModels.parallelStream().filter(model->!model.isDeleted())
                .map(Node_ChangeModel::getNodeChangeRecord).collect(Collectors.toList());
    }
    @JsonIgnore
    public List<NodeChangeRecord> getNode_changeModelsToDelete(){
        return node_changeModels.parallelStream().filter(model->model.isDeleted())
                .map(Node_ChangeModel::getNodeChangeRecord).collect(Collectors.toList());
    }
}
