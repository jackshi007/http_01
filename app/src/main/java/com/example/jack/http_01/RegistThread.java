package com.example.jack.http_01;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class RegistThread extends Thread {

    String url;
    String name;
    String age;

    public RegistThread(String url, String name, String age) {
        this.url = url;
        this.name = name;
        this.age = age;
    }

    /**
     * doPost通过OutputStream方式发送数据
     * 数据量较大使用doPost方法
     * 通过post发送中文不需要转码（由于Android系统是用utf-8进行编码的）
     */
    private void doPost(){
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            OutputStream out = connection.getOutputStream();
            String content = "name="+name+"&age="+age;
            out.write(content.getBytes());
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str=reader.readLine())!=null){
                sb.append(str);
            }
            reader.close();
            connection.disconnect();
            System.out.println(sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * doGet数据通过url向服务器发送数据的（数据暴露出来）
     * 数据量较小的情况下使用（几kb）
     * 通过get发送中文数据需要转码  URLEncoder.encode(name,"utf-8")
     */
    private void doGet(){
        try {
            url = url+"?name="+ URLEncoder.encode(name,"utf-8")+"&age="+age;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str=reader.readLine())!=null){
                sb.append(str);
            }
            System.out.println("result:"+sb.toString());
            reader.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        //doGet();
        doPost();
    }

}
