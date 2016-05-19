package businesslogic.changeset;

import dataaccess.SimmapDataAccessFacade;
import dataaccess.database.tables.records.*;
import dataaccess.utils.ProdConnection;
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
        this.dataAccess = new SimmapDataAccessFacade(properties, new ProdConnection());
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
        ChangesetRecord changesetRecord = dataAccess.getChangesetFromNumber(changsetNr);
        if(changesetRecord !=null){
            ChangesetFullModel fullModel = new ChangesetFullModel(changesetRecord);

            Result<NodeChangeRecord> nodeChange = dataAccess.getNodeChangefromChangeset(changsetNr);
            List<Node_ChangeModel> node_changeModels = new ArrayList();
            for (NodeChangeRecord record:  nodeChange) {
                Node_ChangeModel temp = new Node_ChangeModel();
                NodeRecord node = dataAccess.getNodeFromId(record.getId());
                temp.fillModel(record,node);
                node_changeModels.add(temp);
            }

            fullModel.setNode_changeModels(node_changeModels);

            Result<LinkChangeRecord> linkChange = dataAccess.getLinkChangesfromChangeset(changsetNr);
            List<Link_ChangeModel> link_changeModels = new ArrayList();
            for(LinkChangeRecord record : linkChange){
                Link_ChangeModel temp = new Link_ChangeModel();
                LinkRecord link = dataAccess.getLinkFromId(record.getId());
                temp.fillModel(record,link);
                link_changeModels.add(temp);
            }

            fullModel.setLink_changeModels(link_changeModels);
            return fullModel;
        }
        return null;
    }

    public boolean hasChangeset(long changesetNumber){
        return dataAccess.hasChangeset(changesetNumber);
    }

    public Long insertChangeset(ChangesetFullModel fullModel) throws IllegalArgumentException{
        if(fullModel.getId() != null){
            throw new IllegalArgumentException("Changeset has already an id");
        }

        Long newID = dataAccess.insertChangeset(fullModel.getRecord());
        fullModel.updateChangesetNr(newID);
        dataAccess.deleteLink_Changes(fullModel.getLink_changeModelsToDelete());
        dataAccess.deleteNode_Changes(fullModel.getNode_changeModelsToDelete());
        dataAccess.updateLink_Changes(fullModel.getLink_changeModelsToUpdate());
        dataAccess.updateNode_Changes(fullModel.getNode_changeModelsToUpdate());

        return newID;
    }

    public void updateChangeset(ChangesetFullModel fullModel){
        ChangesetRecord changesetRecord = fullModel.getRecord();
        dataAccess.updateChangeset(changesetRecord);
        dataAccess.deleteLink_Changes(fullModel.getLink_changeModelsToDelete());
        dataAccess.deleteNode_Changes(fullModel.getNode_changeModelsToDelete());
        dataAccess.updateLink_Changes(fullModel.getLink_changeModelsToUpdate());
        dataAccess.updateNode_Changes(fullModel.getNode_changeModelsToUpdate());
    }

    public void deleteChangeset(ChangesetFullModel fullModel){
        ChangesetRecord changesetRecord = fullModel.getRecord();
        dataAccess.deleteLink_Changes(fullModel.getAllLink_changeModelsAsRecord());
        dataAccess.deleteNode_Changes(fullModel.getAllNode_changeModelsAsRecord());
        dataAccess.deleteChangeset(changesetRecord);
    }
}
