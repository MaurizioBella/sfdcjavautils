package com.mauri.main;

public interface ILogin {

    /**
     *
     * @return the user info details
     */
    public String getUserInfo();

    /**
     *
     * @return the Session ID of user
     */
    public String getSessionId();

    /**
     * Execute the SOAP User Logout
     */
    public void doLogout();

    /**
     *
     * @return boolean - if the login session goes well
     */
    public boolean getIsSuccessful();

    /**
     * Set to true if a login session goes well
     * @param isSuccessful
     */
    public void setIsSuccessful(boolean isSuccessful);
}
