package com.example.yys.music.core;

import android.content.Context;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author bxbxbai
 */
public final class ZhuanLanApi {

    public static final int DEFAULT_COUNT = 10;

    public static final String KEY_POSTS = "/posts";
    public static final String KEY_LIMIT = "limit";
    public static final String KEY_OFFSET = "offset";
    public static final String KEY_RATING = "rating";

    public static final String ZHUAN_LAN_URL = "http://zhuanlan.zhihu.com";
    public static final String API_BASE = ZHUAN_LAN_URL + "/api/columns/%s";

    /**
     * slug, post id
     */
    public static final String API_POST_DETAIL = ZHUAN_LAN_URL + "/api/columns/%s/posts/%s";


    public static final class Url {
        private Url() {}

        public static final String ZHIHU_DAILY_BEFORE = "http://news.at.zhihu.com/api/3/news/before/";
        public static final String ZHIHU_DAILY_OFFLINE_NEWS = "http://news-at.zhihu.com/api/3/news/";
        public static final String ZHIHU_DAILY_PURIFY_HEROKU_BEFORE = "http://zhihu-daily-purify.herokuapp.com/raw/";
        public static final String ZHIHU_DAILY_PURIFY_SAE_BEFORE = "http://zhihudailypurify.sinaapp.com/raw/";
        public static final String SEARCH = "http://zhihudailypurify.sinaapp.com/search/";
    }

    /**
     * 知乎日报启动画面api（手机分辨率的长和宽）
     */
    public static final String API_START_IMAGE = "http://news-at.zhihu.com/api/4/start-image/%d*%d";
    public static final String API_RATING = API_BASE + KEY_POSTS + "{post_id}" + KEY_RATING;
    public static final String API_POST_LIST = API_BASE + KEY_POSTS;

    public static final String PIC_SIZE_XL = "xl";
    public static final String PIC_SIZE_XS = "xs";


    public static final String TEMPLATE_ID = "{id}";
    public static final String TEMPLATE_SIZE = "{size}";


    private static ZhuanLanApi instance = new ZhuanLanApi();

    private Retrofit retrofit;

    private ZhuanLanApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ZHUAN_LAN_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(createHttpClient())
                .build();
    }

    public static <T> T api(Class<T> clazz) {
        return instance.retrofit.create(clazz);
    }

    public static Api getZhuanlanApi() {
        return api(Api.class);
    }

    private static OkHttpClient createHttpClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(new Cache(context.getCacheDir(), 10 * 1024*1024));
        return builder.build();
    }
}
