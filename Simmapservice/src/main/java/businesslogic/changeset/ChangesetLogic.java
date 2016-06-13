package businesslogic.changeset;

import dataaccess.DataAccessLayerException;
import dataaccess.DataAccessLogic;
import dataaccess.database.tables.records.*;
import dataaccess.utils.ProdConnection;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ChangesetLogic {
    private final DataAccessLogic dataAccess;

    public ChangesetLogic(Properties properties) {
        this.dataAccess = new DataAccessLogic(properties, new ProdConnection());
    }

    public List<ChangesetModel> getAllChangesets(int userNr) throws DataAccessLayerException {
        Result<ChangesetRecord> allChangesetsPerUser = dataAccess.getAllChangesetsPerUser(userNr);
        List<ChangesetModel> output = (allChangesetsPerUser.size() > 0) ? new ArrayList() : null;
        for (ChangesetRecord record : allChangesetsPerUser) {
            output.add(new ChangesetModel(record));
        }
        return output;
    }

    public ChangesetFullModel getFullChangeset(long changesetNr) throws DataAccessLayerException {
        ChangesetRecord changesetRecord = dataAccess.getChangesetFromNumber(changesetNr);
        if (changesetRecord != null) {
            ChangesetFullModel fullModel = new ChangesetFullModel(changesetRecord);
            fullModel.setNode_changeModels(fillNodeChangeModels(changesetNr));
            fullModel.setLink_changeModels(fillLinkChangeModels(changesetNr));
            return fullModel;
        }
        return null;
    }

    private List<NodeChangeModel> fillNodeChangeModels(long changsetNr) throws DataAccessLayerException {
        Result<NodeChangeRecord> nodeChange = dataAccess.getNodeChangefromChangeset(changsetNr);
        List<NodeChangeModel> node_changeModels = new ArrayList();
        for (NodeChangeRecord record : nodeChange) {
            NodeChangeModel temp = new NodeChangeModel();
            NodeRecord node = dataAccess.getNodeFromId(record.getId());
            temp.fillModel(record, node);
            node_changeModels.add(temp);
        }
        return node_changeModels;
    }

    private List<LinkChangeModel> fillLinkChangeModels(long changesetNr) throws DataAccessLayerException {
        Result<LinkChangeRecord> linkChange = dataAccess.getLinkChangesfromChangeset(changesetNr);
        List<LinkChangeModel> link_changeModels = new ArrayList();
        for (LinkChangeRecord record : linkChange) {
            LinkChangeModel temp = new LinkChangeModel();
            LinkRecord link = dataAccess.getLinkFromId(record.getId());
            temp.fillModel(record, link);
            link_changeModels.add(temp);
        }
        return link_changeModels;
    }

    public boolean hasChangeset(long changesetNumber) throws DataAccessLayerException {
        return dataAccess.hasChangeset(changesetNumber);
    }

    public Long insertChangeset(ChangesetFullModel fullModel) throws IllegalArgumentException, DataAccessLayerException {
        if (fullModel.getId() != null) {
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

    public void updateChangeset(ChangesetFullModel fullModel) throws DataAccessLayerException {
        ChangesetRecord changesetRecord = fullModel.getRecord();
        dataAccess.updateChangeset(changesetRecord);
        dataAccess.deleteLink_Changes(fullModel.getLink_changeModelsToDelete());
        dataAccess.deleteNode_Changes(fullModel.getNode_changeModelsToDelete());
        dataAccess.updateLink_Changes(fullModel.getLink_changeModelsToUpdate());
        dataAccess.updateNode_Changes(fullModel.getNode_changeModelsToUpdate());
    }

    public void deleteChangeset(ChangesetFullModel fullModel) throws DataAccessLayerException {
        ChangesetRecord changesetRecord = fullModel.getRecord();
        dataAccess.deleteLink_Changes(fullModel.getAllLink_changeModelsAsRecord());
        dataAccess.deleteNode_Changes(fullModel.getAllNode_changeModelsAsRecord());
        dataAccess.deleteChangeset(changesetRecord);
    }
}
