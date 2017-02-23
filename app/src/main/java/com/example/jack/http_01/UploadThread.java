package com.example.jack.http_01;


import android.os.Environment;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UploadThread extends Thread {
    private String fileNmae;
    private String url;

    public UploadThread(String fileNmae, String url) {
        this.fileNmae = fileNmae;
        this.url = url;
    }

    private void HTTPUpload() {
        String boundary = "----WebKitFormBoundary3XPsDUpm2qnViNWf"; //分割线
        String prefix = "--"; //前缀
        String end = "\r\n";
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("POST");
            //由于要个Server端之间读写数据，所以需要创建输出流和输入流
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary); // 设置请求标头
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(prefix + boundary + end);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileNmae + "\"" + end);
            outputStream.writeBytes(end);
            FileInputStream fileInputStream = new FileInputStream(new File(fileNmae));
            byte[] b = new byte[1024 * 4]; // 4k缓存区
            int len;
            while ((len = fileInputStream.read(b)) != -1) {
                outputStream.write(b, 0, len);
            }
            outputStream.writeBytes(end);
            outputStream.writeBytes(prefix + boundary + prefix + end);
            outputStream.flush(); //写完了
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            System.out.println("response:" + sb.toString());

            if (outputStream != null) {
                outputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadHttpClient() {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        MultipartEntity entity = new MultipartEntity();
        File parent = Environment.getExternalStorageDirectory();
        File fileAbs = new File(parent, "timg.jpg");

        FileBody fileBody = new FileBody(fileAbs);
        entity.addPart("file", fileBody);
        post.setEntity(entity);
        HttpResponse response = null;
        try {
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {
        uploadHttpClient();
    }

}
