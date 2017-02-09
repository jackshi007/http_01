package com.example.jack.http_01;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Entity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class HttpClientThread extends Thread {

    private String url;
    private String name;
    private String age;

    public HttpClientThread(String url) {
        this.url = url;
    }

    public HttpClientThread(String url, String name, String age) {
        this.url = url;
        this.name = name;
        this.age = age;
    }

    private void doHttpClientGet() {
        HttpGet httpGet = new HttpGet(url);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        try {
            //发送请求
            response = client.execute(httpGet);
            //判断服务器返回类型
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //取出服务器返回的数据
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity);
                httpGet.abort();

                System.out.println("content----------->" + content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            // 当HttpClient实例不再需要是，确保关闭connection manager，以释放其系统资源
            client.getConnectionManager().shutdown();
        }
    }

    private void doHttpClientPost() {
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        //通过NameValuePair去存贮数据
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("name",name));
        list.add(new BasicNameValuePair("age",age));
        try {
            //设置要发送的数据
            HttpEntity entity = new UrlEncodedFormEntity(list,HTTP.UTF_8);//设置编码，防止中文乱码
            httpPost.setEntity(entity);
            HttpResponse response =client.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String content = EntityUtils.toString((response.getEntity()),HTTP.UTF_8).trim();
                httpPost.abort();

                System.out.println("content----------->" + content);

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            client.getConnectionManager().shutdown();
        }


    }

    @Override
    public void run() {
        //doHttpClientGet();
        doHttpClientPost();
    }
}
