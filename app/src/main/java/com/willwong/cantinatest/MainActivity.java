package com.willwong.cantinatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.text_view);
        textView = findViewById(R.id.output);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseData();
            }
        });

    }
    public void parseData() {
        try {
            ArrayList<JSONObject> inputList;
            String myJson = inputStreamToString(getResources().openRawResource(R.raw.systemviewcontroller));
            String input = editText.getText().toString();
            JSONObject json = new JSONObject(myJson);
            if (input.charAt(0) == '.') {
                inputList = new ArrayList<>();
                findClassName(json.getJSONArray("subviews"), inputList, input.substring(1));
                showData(textView, inputList);

            } else if (input.charAt(0) == '#') {
                inputList = new ArrayList<>();
                findIdentifier(json, inputList, input.substring(1));
                showData(textView, inputList);
            } else {
                inputList = new ArrayList<>();
                findClass(json.getJSONArray("subviews"),inputList, input);
                showData(textView, inputList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showData(TextView textView, ArrayList<JSONObject> list){
        textView.setText("");
        for (int i = 0; i < list.size(); i++) {
            textView.append(list.get(i).toString());
            textView.append("\n");
        }

    }


    public void findIdentifier(JSONObject obj, ArrayList<JSONObject> identifierList, String input) {
        try {
            if (obj.has("identifier")) {
                if (obj.getString("identifier").equals(input)) {
                    identifierList.add(new JSONObject().put("identifier", input));
                }
            }
            findIdentifierHelper(obj.getJSONArray("subviews"),identifierList,input);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void findIdentifierHelper(JSONArray array, ArrayList<JSONObject> identifierList, String input) {
        try {
            for (int i = 0; i < array.length(); i++) {
                if (array.getJSONObject(i).has("identifier")) {
                    if (array.getJSONObject(i).getString("identifier").equals(input)) {
                        identifierList.add(new JSONObject().put("identifier",input));
                        System.out.println(input);
                    }
                }
                if (array.getJSONObject(i).has("control")) {
                    if (array.getJSONObject(i).getJSONObject("control").has("identifier")){
                        if (array.getJSONObject(i).getJSONObject("control").getString("identifier").equals(input))
                            identifierList.add(new JSONObject().put("identifier",input));
                            System.out.println(input);
                    }
                }
                if (array.getJSONObject(i).has("contentView")) {
                    if (array.getJSONObject(i).getJSONObject("contentView").has("subviews")) {
                        findIdentifierHelper(array.getJSONObject(i).getJSONObject("contentView").getJSONArray("subviews"),identifierList,input);
                    }
                }
                if (array.getJSONObject(i).has("subviews")) {
                    findIdentifierHelper(array.getJSONObject(i).getJSONArray("subviews"), identifierList, input);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void findClass(JSONArray array,ArrayList<JSONObject> list, String input) {
        try {
            for (int i = 0; i < array.length(); i++) {
                if (array.getJSONObject(i).has("class")) {
                    if (array.getJSONObject(i).getString("class").equals(input)) {
                        System.out.println(input);
                        list.add(new JSONObject().put("class",input));
                    }
                }
                if (array.getJSONObject(i).has("control")) {
                    if (array.getJSONObject(i).getJSONObject("control").has("class")) {
                        if (array.getJSONObject(i).getJSONObject("control").getString("class").equals(input)) {
                            list.add(new JSONObject().put("class",input));
                            System.out.println(input);
                        }
                    }
                }
                if (array.getJSONObject(i).has("contentView")) {
                    if (array.getJSONObject(i).getJSONObject("contentView").has("subviews")) {
                        findClass(array.getJSONObject(i).getJSONObject("contentView").getJSONArray("subviews"),list, input);
                    }
                }
                if (array.getJSONObject(i).has("subviews")) {
                    findClass(array.getJSONObject(i).getJSONArray("subviews"),list,input);
                }
            }

        } catch (JSONException e){
            e.printStackTrace();
        }
    }
    public void findClassName(JSONArray array, ArrayList<JSONObject> list, String input) {
        try {
            for (int i = 0; i < array.length(); i++) {
                if (array.getJSONObject(i).has("classNames")) {
                     JSONArray classNameArray = array.getJSONObject(i).getJSONArray("classNames");
                     for (int j = 0; j < classNameArray.length(); j++) {
                         if (classNameArray.getString(j).equals(input)) {
                             System.out.println("nice" + i);
                             list.add(new JSONObject().put("classNames",input));
                         }
                    }
                }
                if (array.getJSONObject(i).has("subviews")){
                    findClassName(array.getJSONObject(i).getJSONArray("subviews"),list,input);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

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
}
