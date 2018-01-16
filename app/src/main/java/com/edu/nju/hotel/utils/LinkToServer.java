package com.edu.nju.hotel.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zcy on 2017/6/7.
 *
 */

public class LinkToServer {
    /**
     * 向指定URL发送GET方法的请求
     * @param url  发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    private final static String IP = "192.168.1.102";
    public static String sendGet(String url, String param) {
        String header="http://"+IP+":8080/api";
        StringBuilder responseData = new StringBuilder();
        try {
            String urlString = header+ url;
            URL realUrl = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);//设置允许输出
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Charset", "utf-8");
            OutputStream os = conn.getOutputStream();
            os.write(param.getBytes());
            os.close();
            /*服务器返回的响应码*/
            int code = conn.getResponseCode();
            if(code==200){
                System.out.println("success!");
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String retData = null;
                while((retData = in.readLine()) != null)
                {
                    responseData.append(retData);
                }
            }else{
                System.out.println("fail");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseData.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        String header="http://192.168.1.102:8080/api";
        StringBuilder responseData = new StringBuilder();
        try {
            String urlString = header+ url;
            URL realUrl = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);//设置允许输出
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Charset", "utf-8");
            OutputStream os = conn.getOutputStream();
            os.write(param.getBytes());
            os.close();
            /*服务器返回的响应码*/
            int code = conn.getResponseCode();
//            Log.d("yum",code+"");
            if(code==200){
                System.out.println("success!");
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String retData = null;
                while((retData = in.readLine()) != null)
                {
                    responseData.append(retData);
                }
            }else{
                System.out.println("fail");
                return null;
            }

        } catch (IOException | AssertionError e) {
            e.printStackTrace();
        }
        return responseData.toString();
    }

    public static void main(String args[]){
        String login="/login";
//        String userName="userName=aaa&password=11111";
//        String param = "{\"userId\":\"1\",\"address\" :\"南京市南大\",\"hotelType\" :\"Business\",\"roomType\":\"Business\",\"roomNum\":\"1\",\"minPrice\":\"100\",\"maxPrice\":\"900\",\"facilities\":[\"wifi\"],\"orderTime\":\"2016-06-10\",\"startTime\":\"2016-06-11\",\"endTime\":\"2016-06-12\",}";
        String param = "{ 'name':'hqq', 'password' :'123', 'type' :'tenant'}";
        System.out.print(LinkToServer.sendPost(login,param));
//        String modifyPw = "/modifyPassword";
//        System.out.print(link.sendPost(modifyPw,"username=aaa&newpw=12345"));

    }
}
