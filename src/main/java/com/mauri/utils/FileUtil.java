package com.mauri.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileUtil {
    Logger logger = LoggerFactory.getLogger(FileUtil.class);
    public FileUtil() {
        //do something

    }

    public Map<String,String> readKeyValueFromCSV(String pathToCsv) throws IOException {
        Map<String,String> returnKeyValue = new HashMap<String,String>();
        BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
        boolean firstLine = true;
        File csvFile = new File(pathToCsv);
        String row;
        if (csvFile.isFile()) {
            while ((row = csvReader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                } else {
                    String[] data = row.split(",");
                        if (data.length > 0) {
                            returnKeyValue.put(data[0],data[1]);
                        }
                    }
                }
            }
        return returnKeyValue;
    }

    public void printKeyValue (Map<String,String> getCSVKeyValue) {
        for(Map.Entry m:getCSVKeyValue.entrySet()){
            logger.info("{} , {}",m.getKey(),m.getValue());
        }
    }

    /**
     * writeToCSV write a StringBuffer into a CSV file
     * @param className
     * @param stringBuffer
     */
    public void writeToCSV(String className, StringBuffer stringBuffer) {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileOutputName = "OutputFiles_"+className+"_"+timeStamp+".csv";
        BufferedWriter bw = null;
        try {

            //Specify the file name and path here
            File file = new File("output/"+fileOutputName);

            /* This logic will make sure that the file
             * gets created if it is not present at the
             * specified location*/
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(String.valueOf(stringBuffer));
            logger.info("File written Successfully");
            //flush the stream
            bw.flush();
        } catch (IOException ioe) {
            logger.error("IOException Error in writing the BufferedWriter",ioe);

        }
        finally
        {
            try{
                if(bw!=null)
                    bw.close();
            }catch(Exception ex){
                logger.error("Error in closing the BufferedWriter",ex);
            }
        }

    }


}