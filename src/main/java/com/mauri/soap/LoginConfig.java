package com.mauri.soap;

import com.mauri.utils.FileUtil;
import com.sforce.ws.ConnectorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class LoginConfig {
    private ConnectorConfig config;
    private final Integer APIVersion = 47;
    Logger logger = LoggerFactory.getLogger(LoginConfig.class);

    protected ConnectorConfig getConfig(String typeAPI) {
        config = new ConnectorConfig();
        FileUtil files = new FileUtil();
        String token = "";
        Map<String,String> getCSVKeyValue = null;
        try {
            getCSVKeyValue = new HashMap<String,String>(files.readKeyValueFromCSV("input/username.txt"));    
        } catch(IOException i) {
            logger.error("IOException", i);
        }
        
        for(Map.Entry m:getCSVKeyValue.entrySet()){
            if (m.getKey().equals("token")) {
                token = m.getValue().toString().trim();
            } else if (m.getKey().equals("username")) {
                config.setUsername(m.getValue().toString().trim());
            } else if (m.getKey().equals("password")) {
                config.setPassword(m.getValue().toString().trim()+token);
            } else if (m.getKey().equals("domain")) {
                if (typeAPI.equals("LoginTooling")) {
                    config.setAuthEndpoint(m.getValue().toString().trim()+"/services/Soap/T/"+APIVersion.toString());
                } else if (typeAPI.equals("LoginSoap")) {
                    config.setAuthEndpoint(m.getValue().toString().trim()+"/services/Soap/u/"+APIVersion.toString());
                }

            }
        }
        //printConfig();
        return config;
    }

    protected void printConfig() {
        System.out.println(
                "getAuthEndpoint: "+config.getAuthEndpoint() +
                        //" getPassword: "+config.getPassword()+
                        " getUsername: "+config.getUsername()
        );
    }

}
