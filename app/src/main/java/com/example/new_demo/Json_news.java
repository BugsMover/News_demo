package com.example.new_demo;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Json_news {
    /**
     * json解析
     * @param json
     * @throws JSONException
     */
    public static void json(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        JSONObject obj1 = obj.getJSONObject("data");

        JSONArray tech_ary = obj1.getJSONArray("tech");
        For(tech_ary);
        JSONArray auto_ary = obj1.getJSONArray("auto");
        For(auto_ary);
        JSONArray money_ary = obj1.getJSONArray("money");
        For(money_ary);
        JSONArray sports_ary = obj1.getJSONArray("sports");
        For(sports_ary);
        JSONArray dy_ary = obj1.getJSONArray("dy");
        For(dy_ary);
        JSONArray war_ary = obj1.getJSONArray("war");
        For(war_ary);
        JSONArray ent_ary = obj1.getJSONArray("ent");
        For(ent_ary);
        JSONArray toutiao_ary = obj1.getJSONArray("toutiao");
        For(toutiao_ary);

    }

    private static void For(JSONArray ary) throws JSONException {
        for(int i=0;i<ary.length();i++) {
            JSONObject obj2 = ary.getJSONObject(i);
            JSONArray ary1 = obj2.getJSONArray("picInfo");

            if (ary1.length()==0){

            }else {
                String link = obj2.getString("link");
                String source = obj2.getString("source");
                String title = obj2.getString("title");
                String category = obj2.getString("category");
                String ptime = obj2.getString("ptime").substring(10,16);

                MeiziFactory.names = insert(MeiziFactory.names,source);
                MeiziFactory.titles = insert(MeiziFactory.titles,title);
                MeiziFactory.favorites = insert(MeiziFactory.favorites,category);
                MeiziFactory.comments = insert(MeiziFactory.comments,ptime);
                MeiziFactory.links =insert(MeiziFactory.links,link);

                for(int a=0;a<1;a++) {
                    JSONObject obj3 = ary1.getJSONObject(a);

                    String url = obj3.getString("url");
                    MeiziFactory.imageUrls = insert(MeiziFactory.imageUrls,url);

//                    System.out.println(i+":"+link+"/"+source+"/"+title+"/"+ptime+"/"+url);
                }
            }
        }
    }

    /**
     * 字符串写入字符串数组
     * @param arr
     * @param str
     * @return
     */
    private static String[] insert(String[] arr, String str) {
        int size = arr.length;  //获取数组长度
        String[] tmp = new String[size + 1];  //新建临时字符串数组，在原来基础上长度加一
        for (int i = 0; i < size; i++){  //先遍历将原来的字符串数组数据添加到临时字符串数组
            tmp[i] = arr[i];
        }
        tmp[size] = str;  //在最后添加上需要追加的数据
        return tmp;  //返回拼接完成的字符串数组
    }
}
