package com.ronglian.hh.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.File;
import java.io.FileWriter;

public class Test {

    public static void main(String[] args) {

        String url = "https://blog.csdn.net/wangpeng047/article/details/19624529";
        String filePath = "E://xslogs/20200303";
        String fileName = "example.txt";
        createFile(url, filePath, fileName);

    }

    public static void createFile(String url, String filePath, String fileName){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);

        String result = "";
        try{
            HttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if(entity!=null){
                result = EntityUtils.toString(entity, "utf-8");
                //关闭
                EntityUtils.consume(entity);
            }
            System.out.println("读取的结果："+result);
            client.close();
        }catch (Exception e){
            e.getMessage();
        }

        File dir = new File(filePath);
        if(!dir.exists()){
            System.out.println("createDir");
            dir.mkdirs();
        }

        try{
            File file = new File(filePath, fileName);
            if(!file.exists()){
                System.out.println("createFile");
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            fw.write(result);
            fw.flush();
            fw.close();
        }catch (Exception e){
            e.getMessage();
        }
    }
}
