package com.mauri.tooling;

import com.mauri.soap.LoginConfig;
import com.mauri.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolingAPI extends LoginConfig {
    private LoginTooling loginTooling;
    FileUtil fileUtil = null;
    Logger logger = LoggerFactory.getLogger(ToolingAPI.class);

    public ToolingAPI() {
        this.loginTooling = LoginTooling.getInstance();
    }

    public void runMenu() {
        findOutDependency();
        // User logout
    }

    /**
     * uses the Tooling API to get the dependencies using the MetadataComponentDependency sObject.
     * It gets all the Custom Settings (EntityDefinition where IsCustomSetting = true) without any managed package and check
     * for dependencies using the MetadataComponentDependency
     */
    private void findOutDependency() {
        MetadataComponentDependency metadataComponentDependency = new MetadataComponentDependency();
        EntityDefinition entityDefinition = new EntityDefinition();
        // find out the dependency using the MetadataComponentDependency and write the output into the folder output
        metadataComponentDependency.getDependencyByMetadataComponentId(
                //get the list of IDs of entities where IsCustomSetting = true
                entityDefinition.getCustomSettingIds()
        );
    }

    public void logout(){
        this.loginTooling.doLogout();
    }
}
