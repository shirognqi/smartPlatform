package com.tencent.smartplatform.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

public class FileUtils {


    public static String uniteFile(ArrayList<String> fileNames, String targetFileName) throws Exception {
        File inFile = null;
        File outFile = new File(targetFileName);
        FileOutputStream out = new FileOutputStream(outFile);
        for (String str:fileNames) {
            inFile = new File(str);
            FileReader in = new FileReader(inFile);
            BufferedReader bufIn = new BufferedReader(in);
            String line = null;
            while ( (line = bufIn.readLine()) != null) {
                if(line.replaceAll("\r|[ ]","").equals("packagecom.smartplatform.smartplatform.OriSource.helloWorld")){
                    line = "\n";
                }else {
                    line = line + "\n";
                }
                out.write(line.getBytes());
            }
            bufIn.close();
            in.close();
        }
        out.close();
        return outFile.getAbsolutePath();
    }
}
