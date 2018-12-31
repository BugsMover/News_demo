package com.example.new_demo;
import android.content.Context;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


public class Https {
    private static Context mContext;

    public Https(Context context){
        mContext=context;
    }

   public static String https(String URL) throws IOException {
       String apiUrl = String.format(URL);
       //开始请求
       URL url= new URL(apiUrl);
       HttpsURLConnection json = (HttpsURLConnection) url.openConnection();
       // 设置连接主机超时（单位：毫秒）
       json.setConnectTimeout(2000);
       // 设置从主机读取数据超时（单位：毫秒）
       json.setReadTimeout(2000);
       int code = json.getResponseCode();
       if (code==200){
           InputStream input = json.getInputStream();
           String result = convertStreamToString(input);
           input.close();
          return result;
       }else {
           Toast.makeText(mContext,"数据加载失败!",Toast.LENGTH_LONG).show();
           return null;
       }

   }

    public static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the
         * BufferedReader.readLine() method. We iterate until the BufferedReader
         * return null which means there's no more data to read. Each line will
         * appended to a StringBuilder and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line); // + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
