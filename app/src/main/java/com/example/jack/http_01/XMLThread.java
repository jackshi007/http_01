package com.example.jack.http_01;


import android.os.Handler;
import android.widget.TextView;

import com.example.jack.http_01.bean.Girl;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class XMLThread extends Thread {

    private String url;
    private Handler handler;
    private TextView textView;

    public XMLThread(String url, Handler handler, TextView textView) {
        this.url = url;
        this.handler = handler;
        this.textView = textView;
    }

    @Override
    public void run() {
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            InputStream in = connection.getInputStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(in,"UTF-8");
            int evenType = parser.getEventType();
            final List<Girl> list = new ArrayList<Girl>();
            Girl girl = null;
            while (evenType!=XmlPullParser.END_DOCUMENT){
                String data = parser.getName();

                switch (evenType){
                    case XmlPullParser.START_TAG:
                        if ("girl".equals(data)){
                            girl = new Girl();
                        }
                        if ("name".equals(data)){
                            girl.setName(parser.nextText());
                        }
                        if ("age".equals(data)){
                            girl.setAge(Integer.parseInt(parser.nextText()));
                        }
                        if ("school".equals(data)){
                            girl.setSchool(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("girl".equals(data)&&girl!=null){
                            list.add(girl);
                        }
                        break;
                }
                evenType = parser.next();
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(list.toString());
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}
