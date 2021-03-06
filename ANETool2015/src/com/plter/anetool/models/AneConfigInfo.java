package com.plter.anetool.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by plter on 11/20/15.
 */
public class AneConfigInfo {

    public String swcPath;
    public String airVersion,aneVersion,aneId;

    public boolean supportAndroid=false;
    public String jarPath;
    public String androidInitializer;

    public boolean supportiOS=false;

    public boolean supportWindows=false;

    public boolean supportMac=false;

    public String certPath,certPassword;
    public boolean useTimestamp;

    public String aneOutputPath;

    public String toJSONString(){
        JSONObject jo = new JSONObject();
        try {
            jo.put("swcPath",swcPath);
            jo.put("airVersion",airVersion);
            jo.put("aneVersion",aneVersion);
            jo.put("aneId",aneId);

            jo.put("supportAndroid",supportAndroid);
            if (supportAndroid){
                jo.put("jarPath", jarPath);
                jo.put("androidInitializer",androidInitializer);
            }

            //TODO support ios,windows,mac

            jo.put("certPath",certPath);
            jo.put("certPassword",certPassword);
            jo.put("useTimestamp",useTimestamp);

            jo.put("aneOutputPath",aneOutputPath);
        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage());
        }

        return jo.toString();
    }

    public String toExtensionXMLContent(){
        StringBuilder content = new StringBuilder();
        content.append(String.format("<extension xmlns=\"http://ns.adobe.com/air/extension/%s\">",airVersion));
        content.append(String.format("<id>%s</id>",aneId));
        content.append(String.format("<versionNumber>%s</versionNumber>",aneVersion));
        content.append("<platforms>");
        if (supportAndroid){
            content.append(String.format("<platform name=\"%s\">","Android-ARM"));
            content.append("<applicationDeployment>");
            content.append(String.format("<nativeLibrary>%s</nativeLibrary>",new File(jarPath).getName()));
            content.append(String.format("<initializer>%s</initializer>",androidInitializer));
            content.append("</applicationDeployment>");
            content.append("</platform>");
        }
        content.append("</platforms>");
        content.append("</extension>");
        return content.toString();
    }

    public static AneConfigInfo fromJsonString(String jsonString) throws JSONException {
        JSONObject obj = new JSONObject(jsonString);
        AneConfigInfo info = new AneConfigInfo();
        info.swcPath = obj.getString("swcPath");
        info.airVersion = obj.getString("airVersion");
        info.aneVersion = obj.getString("aneVersion");
        info.aneId = obj.getString("aneId");
        info.supportAndroid = obj.getBoolean("supportAndroid");
        if (info.supportAndroid){
            info.jarPath = obj.getString("jarPath");
            info.androidInitializer = obj.getString("androidInitializer");
        }
        info.certPath = obj.getString("certPath");
        info.certPassword = obj.getString("certPassword");
        info.useTimestamp = obj.getBoolean("useTimestamp");
        info.aneOutputPath = obj.getString("aneOutputPath");
        return info;
    }
}
