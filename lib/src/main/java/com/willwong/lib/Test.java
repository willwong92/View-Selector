/*package com.willwong.lib;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Test {
    public static void main(String[] args) {
        String myJson = inputStreamToString(getResources().openRawResource(R.raw.systemviewcontroller));
        try {
            JSONObject json = new JSONObject(myJson);
            ArrayList<String> identifierList = new ArrayList<>();
            //findIdentifier(json.getJSONArray("subviews"),identifierList);
            findClass(json.getJSONArray("subviews"), identifierList, "Input");
            //System.out.println(identifierList.get(4));
            //System.out.println(json.getJSONArray("subviews").getJSONObject(0).getJSONArray("subviews"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void findClass(JSONArray array, ArrayList<String> identifierList, String input) {
        try {
            for (int i = 0; i < array.length(); i++) {
                if (array.getJSONObject(i).has("class")) {
                    if (array.getJSONObject(i).getString("class") == input) {
                        identifierList.add(input);
                    }
                } else if (array.getJSONObject(i).has("contentView")) {
                    findClass(array.getJSONObject(i).getJSONArray("subviews"),identifierList, input);
                } else if (array.getJSONObject(i).has("subviews")) {
                    findClass(array.getJSONArray(i), identifierList,input);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    //public boolean hasIdentifier(JSONObject jobject) {
    //  return jobject.has("identifier") && jobject.has("identifier") != JSONObject.NULL;

    //}
    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }
}*/
