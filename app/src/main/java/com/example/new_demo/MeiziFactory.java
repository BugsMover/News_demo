package com.example.new_demo;

import java.util.ArrayList;
import java.util.List;

public class MeiziFactory {
    public static String[] imageUrls = {};
    public static String[] titles = {};
    public static String[] names = {};
    public static String[] favorites = {};
    public static String[] comments ={};
    public static String[] links={};

    public static List<Meizi> createMeizis(int num) {
        List<Meizi> meizis = new ArrayList<>();
        int arySize = imageUrls.length;
        for (int i = 0; i < num; i++) {
            int a = i % arySize;
            String url = imageUrls[a];
            String title = titles[a];
            String name = names[a];
            String favorites = MeiziFactory.favorites[a];
            String comments = MeiziFactory.comments[a];
            String links = MeiziFactory.links[a];
            meizis.add(new Meizi(url, title, name, favorites, comments,links));
        }

        return meizis;
    }
}
