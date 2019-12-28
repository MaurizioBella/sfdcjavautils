package com.mauri.tooling;

import com.mauri.main.ILogin;
import com.mauri.soap.LoginConfig;
import com.sforce.soap.tooling.Connector;
import com.sforce.soap.tooling.ToolingConnection;
import com.sforce.ws.ConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginTooling extends LoginConfig implements ILogin {
    Logger logger = LoggerFactory.getLogger(LoginTooling.class);
    private ToolingConnection connection = null;
    private static LoginTooling uniqueInstance;
    private boolean isSuccessful = false;

    private LoginTooling() {
        try {
            connection = Connector.newConnection(getConfig("LoginTooling"));
            printConfig();
        } catch (ConnectionException c) {
            logger.error("There was a ConnectionException: ", c);
        } catch (Exception e) {
            logger.error("There was a Exception: ", e);
        }
    }

    public static LoginTooling getInstance() {
//        return instance;
        if (uniqueInstance == null) {
            uniqueInstance = new LoginTooling();
            uniqueInstance.isSuccessful = true;
        }
        return uniqueInstance;
    }

    @Override
    public String getUserInfo() {
        if (!(connection == null)) {
            try {
                return connection.getUserInfo().toString();
            } catch (ConnectionException c) {
                return  null;
            }

        } else {
            return null;
        }
    }

    @Override
    public String getSessionId() {
        if (!(connection == null)) {
            return connection.getSessionHeader().getSessionId().toString();
        } else {
            return null;
        }
    }

    /**
     * complete the user Logout
     */

    @Override
    public void doLogout() {
        try {
            connection.logout();
            logger.info("Logout!");
        } catch (ConnectionException e) {
            logger.info("ConnectionException", e);
        }
    }

    @Override
    public boolean getIsSuccessful() {
        return isSuccessful;
    }

    @Override
    public void setIsSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public ToolingConnection getConnection() {
        return connection;
    }
}
