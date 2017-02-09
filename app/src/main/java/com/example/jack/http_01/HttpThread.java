package com.example.jack.http_01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 运用HttpUrlConnection 显示 ImageView 和 WebView
 */
public class HttpThread extends Thread {

    private WebView webView;
    private ImageView imageView;
    private String url;
    private Handler handler;

    public HttpThread(WebView webView, String url, Handler handler) {
        this.webView = webView;
        this.url = url;
        this.handler = handler;
    }

    public HttpThread(ImageView imageView, String url,Handler handler){
        this.imageView = imageView;
        this.url = url;
        this.handler = handler;
    }
    @Override
    public void run() {
        try {
            URL httpUrl = new URL(url);
            try {
                HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
                connection.setReadTimeout(5000);
                connection.setRequestMethod("GET");

                connection.setDoInput(true);
                InputStream in = connection.getInputStream();
                FileOutputStream out = null;
                File downloadFile = null;
                String fileName = String.valueOf(System.currentTimeMillis());

                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    File parent = Environment.getExternalStorageDirectory();
                    downloadFile = new File(parent,fileName);
                    out = new FileOutputStream(downloadFile);
                }

                byte[] b = new byte[1024*2];
                int len;
                if (out != null){
                    while ((len=in.read(b))!=-1){
                        out.write(b,0,len);
                    }
                }
                final Bitmap bitmap = BitmapFactory.decodeFile(downloadFile.getAbsolutePath());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });

               /* final StringBuffer sb = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String str;
                if ((str = reader.readLine()) != null) {
                    sb.append(str);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadData(sb.toString(), "text/html;charset=utf-8", null);
                    }
                });*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
