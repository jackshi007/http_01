package com.example.jack.http_01;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jack.http_01.bean.Person;
import com.example.jack.http_01.bean.SchoolInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HttpJson extends Thread {

    private String url;
    private Context context;
    private ListView listView;
    private JsonAdapter adapter;
    private Handler handler;

    public HttpJson(String url, ListView listView, JsonAdapter adapter, Handler handler , Context context) {
        this.url = url;
        this.listView = listView;
        this.adapter = adapter;
        this.handler = handler;
        this.context = context;
    }

    @Override
    public void run() {
        URL httpurl;
        try {
            httpurl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpurl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str=reader.readLine())!=null){
                sb.append(str);
            }
            final List<Person> data = parseJson(sb.toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    adapter.setData(data);
                    listView.setAdapter(adapter);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Person> parseJson(String json){
        try {
            JSONObject object = new JSONObject(json);
            List<Person> persons = new ArrayList<Person>();
            int result = object.getInt("result");
            if(result ==1){
                JSONArray personData = object.getJSONArray("personsData");
                for (int i =0;i<personData.length();i++){
                    Person personObject = new Person();
                    persons.add(personObject);
                    JSONObject person = personData.getJSONObject(i);
                    String name = person.getString("name");
                    int age = person.getInt("age");
                    String url = person.getString("url");
                    personObject.setName(name);
                    personObject.setAge(age);
                    personObject.setUrl(url);
                    //schoolInfos为一个数组，继续进行for循环解析
                    JSONArray schoolInfos = person.getJSONArray("schoolInfo");
                    List<SchoolInfo> schInfos = new ArrayList<SchoolInfo>();
                    personObject.setSchoolInfo(schInfos);
                    for (int j = 0 ;j<schoolInfos.length();j++){
                        JSONObject school = schoolInfos.getJSONObject(j);
                        String schoolName = school.getString("school_name");
                        SchoolInfo info = new SchoolInfo();
                        info.setSchool_name(schoolName);
                        schInfos.add(info);
                    }
                }
                return persons;
            }else {
                Toast.makeText(context,"error",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
