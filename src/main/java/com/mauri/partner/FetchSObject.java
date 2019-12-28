package com.mauri.partner;

import com.mauri.soap.LoginSoap;
import com.sforce.soap.partner.*;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;


public class FetchSObject {

    private ConnectorConfig config;
    private LoginSoap loginSoap;
    Logger logger = LoggerFactory.getLogger(FetchSObject.class);

    public FetchSObject() {
        this.loginSoap = LoginSoap.getInstance();

    }

    public ArrayList<String> fetchCustomsObjects() {

        try {
            ArrayList<String> sObjects = new ArrayList<String>();
            DescribeGlobalResult describeGlobalResult = this.loginSoap.getConnection().describeGlobal();

            DescribeGlobalSObjectResult[] sObjectResults = describeGlobalResult.getSobjects();

            for (int i = 0; i < sObjectResults.length; i++) {
                if ((sObjectResults[i].isCustom() == true) && (sObjectResults[i].isCustomSetting() == true)) {
                    sObjects.add(sObjectResults[i].getLabel());

                }
            }
            logger.info("Here the sObject found {}", sObjects);
            return sObjects;
        }
        catch (Exception e) {
            logger.error("Exception ",e);
            return null;
        }

    }

    public String describeSObjectsWithAllFields(ArrayList<String> sObjects) {

        String output = "";

        try {
            for (int i = 0; i < sObjects.size(); i++) {
                DescribeSObjectResult describeSObjectResult = this.loginSoap.getConnection().describeSObject(sObjects.get(i));
                System.out.println("Reading the sObject : "+describeSObjectResult.getName());

                if (describeSObjectResult != null) {
                    Field[] fields = describeSObjectResult.getFields();
                    int counter = 1;
                    for (int j = 0; j < fields.length; j++) {
                        Field field = fields[j];
                        field.getNameField()
                        ;
                        output += "\r\n";
                    }
                }
            }

            return output;

        }

        catch (ConnectionException ce) {

            ce.printStackTrace();

            return null;

        }

    }

}
