package com.example.jack.http_01;


import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DownLoad {

    private Handler handler;

    public DownLoad(Handler handler) {
        this.handler = handler;
    }

    //创建线程池
    private Executor threadPool = Executors.newFixedThreadPool(3);

    static class DownLoadRunnable implements Runnable {

        private String url;
        private String fileName;
        private long start;
        private long end;
        private Handler handler;

        public DownLoadRunnable(String url, String fileName, long end, long start,Handler handler) {
            this.url = url;
            this.fileName = fileName;
            this.end = end;
            this.start = start;
            this.handler =handler;
        }

        @Override
        public void run() {
            try {
                URL httpUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000);
                connection.setRequestProperty("Range", "bytes=" + start + "-" + end); //设置当前线程下载的起点、终点
                RandomAccessFile accessFile = new RandomAccessFile(new File(fileName), "rwd");
                accessFile.seek(start);//从文件的什么位置开始写入数据

                InputStream inputStream = connection.getInputStream();
                byte[] b = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(b)) != -1) {
                    accessFile.write(b, 0, len);
                }
                if (accessFile != null) {
                    accessFile.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void downLoadFile(String url) {
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            int count = connection.getContentLength(); //获取下载文件的长度
            if (count <= 0){
                System.out.println("读取文件失败");
                return;
            }
            int block = count / 3; //每条线程下载的数据长度

            String fileName = getFileName(url);
            File parent = Environment.getExternalStorageDirectory();
            File fileDownLoad = new File(parent,fileName);
            /**
             * 假设需要下载11个字节的数据用3个线程 11/3
             * 第一个线程 0-2
             * 第二个线程 3-5
             * 第三个线程 6-10
             */
            for (int i = 0; i < 3; i++) {
                long start = i * block;          //下载开始位置：线程id*每条线程下载的数据长度
                long end = (i + 1) * block - 1;  //下载结束位置：（线程id+1）*每条线程下载的数据长度-1
                if (i == 2) {
                    end = count;
                }
                DownLoadRunnable downLoadRunnable = new DownLoadRunnable(url,fileDownLoad.getAbsolutePath(),start,end,handler);
                threadPool.execute(downLoadRunnable);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getFileName(String url){
        return url.substring(url.lastIndexOf("/")+1);
    }
}
