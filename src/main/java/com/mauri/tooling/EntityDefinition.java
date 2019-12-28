package com.mauri.tooling;

import com.mauri.utils.FileUtil;
import com.sforce.soap.tooling.QueryResult;
import com.sforce.soap.tooling.sobject.SObject;
import com.sforce.ws.ConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class EntityDefinition {
    private LoginTooling loginTooling;
    private FileUtil fileUtil = null;
    private Logger logger = LoggerFactory.getLogger(EntityDefinition.class);

    public EntityDefinition() {
        this.loginTooling = LoginTooling.getInstance();
    }
    /**
     * get Custom Settings using the EntityDefinition
     * @return list of IDs of entities where IsCustomSetting = true
     */
    public ArrayList<String> getCustomSettingIds() {
        ArrayList<String> sObjects = new ArrayList<String>();
        try {
            String query = "select DeveloperName,DurableId,KeyPrefix,Label,MasterLabel,QualifiedApiName,NamespacePrefix\n" +
                    "from EntityDefinition where IsCustomSetting = true";
            QueryResult qResult = this.loginTooling.getConnection().query(query);

            boolean done = false;
            if (qResult.getSize() > 0) {
                while (!done) {
                    SObject[] records = qResult.getRecords();
                    for (int i = 0; i < records.length; ++i) {
                        com.sforce.soap.tooling.sobject.EntityDefinition con = (com.sforce.soap.tooling.sobject.EntityDefinition) records[i];
                        if ((con.getNamespacePrefix() == null)||(con.getNamespacePrefix().isEmpty())){
                            sObjects.add(con.getDurableId());
                            logger.info("Custom Setting {} has DurableId {}", con.getDeveloperName(), con.getDurableId());
                        }
                    }
                    if (qResult.isDone()) {
                        done = true;
                    } else {
                        qResult = this.loginTooling.getConnection().queryMore(qResult.getQueryLocator());
                    }
                }
            } else {
                logger.info("No records found");
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }
        return sObjects;
    }
}
