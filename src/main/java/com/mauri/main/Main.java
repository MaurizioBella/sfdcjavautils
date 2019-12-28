package com.mauri.main;


import com.mauri.partner.FetchSObject;
import com.mauri.soap.LoginSoap;
import com.mauri.tooling.LoginTooling;
import com.mauri.tooling.ToolingAPI;
import com.sforce.soap.partner.PartnerConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    //logger.info("client {} requested to {} the following list: {}", clientId, operationName, list);

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        // make a Scanner object to read from keyboard
        Scanner input = new Scanner(System.in);
        // Prompt the user to enter a radius
        System.out.print("What do you want to do? \n");
        System.out.print("1. Get the sessionId\n");
        System.out.print("2. Core Calls - SOAP API Developer Guide \n");
        System.out.print("3. Get Metadata API \n");
        System.out.print("4. REST API \n");
        System.out.print("5. BULK API \n");
        System.out.print("6. Tooling API \n");
        System.out.print("7. Utils Class \n");
        System.out.print("8. Check Org Limits \n");
        System.out.print("98. test\n");
        System.out.print("99. exit\n");
        System.out.print("Digit a number: ");
        int customerChoice = input.nextInt(); //Store keyboard input in the variable

        if (customerChoice == 1) {
            LoginSoap loginSoap = LoginSoap.getInstance();
            if (loginSoap.getIsSuccessful()) {
                logger.info("SESSION ID: {}",loginSoap.getSessionId());
                loginSoap.doLogout();
            } else {
                logger.info("Something goes wrong with the authentication");
            }

        } else if (customerChoice == 99) {
            System.out.print("Exit \n");
        } else if (customerChoice == 2) {
//            MainCoreCall.dispatchUserInput();
        } else if (customerChoice == 3) {
//            MainMetadataAPI.dispatchUserInput();
        } else if (customerChoice == 4) {
//            MainRestAPI.dispatchUserInput();
        } else if (customerChoice == 5) {
//            MainBulkAPI.dispatchUserInput();
        } else if (customerChoice == 6) {
            ToolingAPI toolingAPI = new ToolingAPI();
            toolingAPI.runMenu();
            toolingAPI.logout();
            System.out.print("Exit \n");
        } else if (customerChoice == 7) {
            //MainUtil.dispatchUserInput();
        } else if (customerChoice == 8) {
//            MainOrgLimits.dispatchUserInput();
        } else if (customerChoice == 98) {
//            util.FileUtil.main();
        }
        input.close();



    }
}
