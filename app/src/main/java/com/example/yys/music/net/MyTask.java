package com.example.yys.music.net;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.Map;


/**
 * Created by pqrs8966 on 16/3/14.
 */
public class MyTask extends AsyncTask<Object, Void, JSONObject> {
    String url;
    MyListenner listenner;

    public MyTask(String url, MyListenner listenner){
        this.url=url;
        this.listenner=listenner;
    }
    @Override
    protected JSONObject doInBackground(Object... params) {
        JSONObject obj=null;
        try {
            Object o=null;
            if(params.length==0)
                o=null;
            else
                o=params[0];
            Map<String,String> param=null;
            if(o!=null)
               param=(Map<String,String>)o;

            obj=NetUtil.post(url,param);
        }catch(Exception e){
            return null;
        }
        return obj;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {

        listenner.listener(jsonObject);
    }
    public interface MyListenner{
        void listener(JSONObject obj);
    }
}
