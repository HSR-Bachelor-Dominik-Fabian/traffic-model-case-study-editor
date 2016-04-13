package businesslogic.changeset;

import dataaccess.SimmapDataAccessFacade;
import dataaccess.database.tables.records.*;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by dohee on 13.04.2016.
 */
public class ChangesetLogic {
    private SimmapDataAccessFacade dataAccess;
    public ChangesetLogic(Properties properties){
        this.dataAccess = new SimmapDataAccessFacade(properties);
    }

    public List<ChangesetModel> getAllChangesets(int userNr){
        final Result<ChangesetRecord> allChangesetsPerUser = dataAccess.getAllChangesetsPerUser(userNr);
        List<ChangesetModel> output = (allChangesetsPerUser.size() > 0)?new ArrayList():null;

        for (ChangesetRecord record :allChangesetsPerUser) {
            output.add(new ChangesetModel(record));
        }
        return output;
    }

    public ChangesetFullModel getFullChangeset(long changsetNr){
        ChangesetRecord changesetRecord = dataAccess.getChangsetFromNumber(changsetNr);
        ChangesetFullModel fullModel = new ChangesetFullModel(changesetRecord);

        Result<NodeChangeRecord> nodeChange = dataAccess.getNodeChangefromChangeset(changsetNr);
        List<Node_ChangeModel> node_changeModels = new ArrayList();
        for (NodeChangeRecord record:  nodeChange) {
            Node_ChangeModel temp = new Node_ChangeModel();
            temp.fillModel(record);
            if(!temp.isEverythingSet()){
                NodeRecord node = dataAccess.getNodeFromId(temp.getId());
                temp.fillModel(node);
            }
            node_changeModels.add(temp);
        }

        fullModel.setNode_changeModels(node_changeModels);

        Result<LinkChangeRecord> linkChange = dataAccess.getLinkChangesfromChangeset(changsetNr);
        List<Link_ChangeModel> link_changeModels = new ArrayList();
        for(LinkChangeRecord record : linkChange){
            Link_ChangeModel temp = new Link_ChangeModel();
            temp.fillModel(record);
            if(!temp.isEverythingSet()){
                LinkRecord link = dataAccess.getLinkFromId(temp.getId());
                temp.fillModel(link);
            }
            link_changeModels.add(temp);
        }

        fullModel.setLink_changeModels(link_changeModels);

        return fullModel;
    }
}
