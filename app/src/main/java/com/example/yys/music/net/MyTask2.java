package com.example.yys.music.net;

import android.graphics.Bitmap;
import android.os.AsyncTask;


/**
 * Created by pqrs8966 on 16/3/14.
 */
public class MyTask2 extends AsyncTask<String , Void, Bitmap> {
    MyListenner1 listenner;

    public MyTask2(MyListenner1 listenner){
        this.listenner=listenner;
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            Bitmap bm =NetUtil.getBitmapFormUrl(Constant.SERVER_URL + "/upfiles/" + params[0]);
            return bm;
        }catch(Exception e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bm) {
        listenner.listener(bm);
    }
    public interface MyListenner1{
        void listener(Bitmap bm);
    }
}
