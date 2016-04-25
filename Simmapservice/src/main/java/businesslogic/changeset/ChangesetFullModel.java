package businesslogic.changeset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dataaccess.database.tables.records.ChangesetRecord;
import org.jooq.tools.json.JSONObject;

import java.util.List;

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

    public List<Link_ChangeModel> getLink_changeModels() {
        return link_changeModels;
    }

    public void setLink_changeModels(List<Link_ChangeModel> link_changeModels) {
        this.link_changeModels = link_changeModels;
    }

    public List<Node_ChangeModel> getNode_changeModels() {
        return node_changeModels;
    }

    public void setNode_changeModels(List<Node_ChangeModel> node_changeModels) {
        this.node_changeModels = node_changeModels;
    }
}
