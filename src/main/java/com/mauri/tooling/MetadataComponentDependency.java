package com.mauri.tooling;

import com.mauri.utils.FileUtil;
import com.sforce.soap.tooling.QueryResult;
import com.sforce.soap.tooling.sobject.SObject;
import com.sforce.ws.ConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class MetadataComponentDependency {

    private LoginTooling loginTooling;
    private FileUtil fileUtil = null;
    private Logger logger = LoggerFactory.getLogger(MetadataComponentDependency.class);


    public MetadataComponentDependency() {
        this.loginTooling = LoginTooling.getInstance();
    }

    /**
     * find out the dependency using the MetadataComponentDependency and write the output into the folder output
     * @param sObject Id of sObjects
     */
    public void getDependencyByMetadataComponentId(ArrayList<String> sObject) {
        StringBuffer writeToFile = new StringBuffer();
        writeToFile.append("MetadataComponentId;MetadataComponentName;MetadataComponentType;MetadataComponentNamespace;" +
                "RefMetadataComponentId ;RefMetadataComponentName;RefMetadataComponentNamespace;RefMetadataComponentType");
        writeToFile.append(System.getProperty("line.separator"));
        for (int j = 0; j < sObject.size(); j++) {
            try {
                String query = "SELECT MetadataComponentId, MetadataComponentName, MetadataComponentType,MetadataComponentNamespace," +
                        "RefMetadataComponentId,RefMetadataComponentName,RefMetadataComponentNamespace, RefMetadataComponentType\n" +
                        "    FROM MetadataComponentDependency WHERE RefMetadataComponentId" +
                        " = \'"+sObject.get(j)+"\'";
                QueryResult qResult = this.loginTooling.getConnection().query(query);


                boolean done = false;
                if (qResult.getSize() > 0) {
                    logger.info("For the ComponentId {} found {} dependencies.",sObject.get(j),qResult.getSize());
                    while (!done) {
                        SObject[] records = qResult.getRecords();
                        for (int i = 0; i < records.length; ++i) {
                            com.sforce.soap.tooling.sobject.MetadataComponentDependency con = (com.sforce.soap.tooling.sobject.MetadataComponentDependency) records[i];
                            writeToFile.append(
                                    con.getMetadataComponentId()+";"+
                                            con.getMetadataComponentName()+";"+
                                            con.getMetadataComponentNamespace()+";"+
                                            con.getMetadataComponentType()+";"+
                                            con.getRefMetadataComponentId()+";"+
                                            con.getRefMetadataComponentName()+";"+
                                            con.getRefMetadataComponentNamespace()+";"+
                                            con.getRefMetadataComponentType()
                            );
                            writeToFile.append(System.getProperty("line.separator"));
                        }
                        if (qResult.isDone()) {
                            done = true;
                        } else {
                            qResult = this.loginTooling.getConnection().queryMore(qResult.getQueryLocator());
                        }
                    }
                } else {
                    logger.info("No records found for {}",sObject.get(j));
                }
            } catch (ConnectionException ce) {
                ce.printStackTrace();
            }
        }
        fileUtil = new FileUtil();
        fileUtil.writeToCSV("ToolingAPI",writeToFile);

    }

}
