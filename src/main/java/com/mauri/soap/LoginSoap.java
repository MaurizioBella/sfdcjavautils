package com.mauri.soap;

import com.mauri.main.ILogin;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginSoap extends LoginConfig implements ILogin {
    //https://stackify.com/slf4j-java/
    Logger logger = LoggerFactory.getLogger(LoginSoap.class);
    private PartnerConnection connection = null;
    private static LoginSoap uniqueInstance;
    private boolean isSuccessful = false;

    private LoginSoap() {
        try {
            connection = Connector.newConnection(getConfig("LoginSoap"));
            printConfig();
        } catch (ConnectionException c) {
            logger.error("There was a ConnectionException: ", c);
        } catch (Exception e) {
            logger.error("There was a Exception: ", e);
        }
    }

    public static LoginSoap getInstance() {
//        return instance;
        if (uniqueInstance == null) {
            uniqueInstance = new LoginSoap();
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


    public PartnerConnection getConnection() {
        return connection;
    }


}
