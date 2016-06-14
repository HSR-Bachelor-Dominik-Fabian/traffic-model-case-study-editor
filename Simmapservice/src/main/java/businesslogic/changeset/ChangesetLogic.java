package businesslogic.changeset;

import dataaccess.DataAccessException;
import dataaccess.DataAccessLogic;
import dataaccess.database.tables.records.*;
import dataaccess.connectionutils.ProdConnection;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ChangesetLogic {
    private final DataAccessLogic dataAccess;

    public ChangesetLogic(Properties properties) {
        this.dataAccess = new DataAccessLogic(properties, new ProdConnection());
    }

    public List<ChangesetModel> getAllChangesets(int userNr) throws DataAccessException, NullPointerException {
        Result<ChangesetRecord> allChangesetsPerUser = dataAccess.getAllChangesetsPerUser(userNr);
        List<ChangesetModel> output = (allChangesetsPerUser.size() > 0) ? new ArrayList() : null;
        if (output != null) {
            output.addAll(allChangesetsPerUser.stream().map(ChangesetModel::new).collect(Collectors.toList()));
        }
        return output;
    }

    public ChangesetFullModel getFullChangeset(long changesetNr) throws DataAccessException {
        ChangesetRecord changesetRecord = dataAccess.getChangesetFromNumber(changesetNr);
        if (changesetRecord != null) {
            ChangesetFullModel fullModel = new ChangesetFullModel(changesetRecord);
            fullModel.setNode_changeModels(fillNodeChangeModels(changesetNr));
            fullModel.setLink_changeModels(fillLinkChangeModels(changesetNr));
            return fullModel;
        }
        return null;
    }

    private List<NodeChangeModel> fillNodeChangeModels(long changsetNr) throws DataAccessException {
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

    private List<LinkChangeModel> fillLinkChangeModels(long changesetNr) throws DataAccessException {
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

    public boolean hasChangeset(long changesetNumber) throws DataAccessException {
        return dataAccess.hasChangeset(changesetNumber);
    }

    public Long insertChangeset(ChangesetFullModel fullModel) throws IllegalArgumentException, DataAccessException {
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

    public void updateChangeset(ChangesetFullModel fullModel) throws DataAccessException {
        ChangesetRecord changesetRecord = fullModel.getRecord();
        dataAccess.updateChangeset(changesetRecord);
        dataAccess.deleteLink_Changes(fullModel.getLink_changeModelsToDelete());
        dataAccess.deleteNode_Changes(fullModel.getNode_changeModelsToDelete());
        dataAccess.updateLink_Changes(fullModel.getLink_changeModelsToUpdate());
        dataAccess.updateNode_Changes(fullModel.getNode_changeModelsToUpdate());
    }

    public void deleteChangeset(ChangesetFullModel fullModel) throws DataAccessException {
        ChangesetRecord changesetRecord = fullModel.getRecord();
        dataAccess.deleteLink_Changes(fullModel.getAllLink_changeModelsAsRecord());
        dataAccess.deleteNode_Changes(fullModel.getAllNode_changeModelsAsRecord());
        dataAccess.deleteChangeset(changesetRecord);
    }
}
