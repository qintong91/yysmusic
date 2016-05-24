package com.example.yys.music.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by pqrs8966 on 16/4/11.
 */
public class NetUtil {
    final static String BOUNDARY="bdsfbdsjkfhjkdshfjksa";
    public static JSONObject post(String urlStr,Map<String,String> map) throws Exception{
        URL url = new URL(urlStr);
        // 使用HttpURLConnection打开连接
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        //因为这个是post请求,设立需要设置为true
        urlConn.setDoOutput(true);
        // 设置以POST方式
        urlConn.setDoInput(true);
        urlConn.setRequestMethod("POST");
        urlConn.setRequestProperty("Charset", "UTF-8");
        // Post 请求不能使用缓存
        urlConn.setUseCaches(false);
        urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConn.connect();

        if(map!=null) {
            StringBuilder param = new StringBuilder();
            boolean f = false;
            for (String key : map.keySet()) {
                if (f) param.append("&");
                param.append(key).append("=").append(map.get(key));
                f = true;
            }

            byte[] buf = param.toString().getBytes("utf-8");
            urlConn.getOutputStream().write(buf);// 输入参数
        }

        InputStream inStream = urlConn.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        String jsonStr=new String(data,"utf-8");
        JSONObject obj=new JSONObject(jsonStr);
        outStream.close();
        inStream.close();
        return obj;
    }
    public static JSONObject post1(String urlStr,Map<String,String> map,String name,String filename) throws Exception{
        URL url = new URL(urlStr);
        // 使用HttpURLConnection打开连接
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        //因为这个是post请求,设立需要设置为true
        urlConn.setDoOutput(true);
        // 设置以POST方式
        urlConn.setDoInput(true);
        urlConn.setRequestMethod("POST");
        urlConn.setRequestProperty("Charset", "UTF-8");
        // Post 请求不能使用缓存
        urlConn.setUseCaches(false);
        urlConn.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=" + BOUNDARY);
        urlConn.connect();
        DataOutputStream ds = new DataOutputStream(urlConn.getOutputStream());
        if (name != null) {
            File value = new File(filename);
            ds.writeBytes("--" + BOUNDARY + "\r\n");
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name
                    + "\"; filename=\"" + filename+ "\"\r\n");
            ds.writeBytes("Content-Type: " + getContentType(value) + "\r\n");
            ds.writeBytes("\r\n");
            ds.write(getBytes(value));
            ds.writeBytes("\r\n");
        }

        if(map!=null) {
            Set<String> keySet = map.keySet();
            for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
                String key = it.next();
                String value = map.get(key);

                ds.writeBytes("--" + BOUNDARY + "\r\n");
                ds.writeBytes("Content-Disposition: form-data; name=\"" + key
                        + "\"\r\n");
                ds.writeBytes("\r\n");
                ds.writeBytes(value + "\r\n");
            }
        }
        ds.writeBytes("--" + BOUNDARY + "--" + "\r\n");
        ds.writeBytes("\r\n");
        InputStream inStream = urlConn.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        String jsonStr=new String(data,"utf-8");
        JSONObject obj=new JSONObject(jsonStr);
        outStream.close();
        inStream.close();
        return obj;
    }
    //获取文件的上传类型，图片格式为image/png,image/jpg等。非图片为application/octet-stream
    private static String getContentType(File f) throws Exception {
        int lastDot = f.getName().lastIndexOf(".");
        if (lastDot < 0)
            return "application/octet-stream";
        String ext = f.getName().substring(lastDot + 1).toUpperCase();
        if (ext.equals("JPG")) return "image/jpeg";
        else if (ext.equals("JPG")) return "image/jpeg";
        else if (ext.equals("JPEG")) return "image/jpeg";
        else if (ext.equals("GIF")) return "image/gif";
        else if (ext.equals("PNG")) return "image/png";
        else return "application/octet-stream";
    }
    //把文件转换成字节数组
    private static byte[] getBytes(File f) throws Exception {
        FileInputStream in = new FileInputStream(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = in.read(b)) != -1) {
            out.write(b, 0, n);
        }
        in.close();
        return out.toByteArray();
    }
    public static Bitmap getBitmapFormUrl(String url) {
        Bitmap bitmap = null;
        HttpURLConnection con = null;
        try {
            URL mImageUrl = new URL(url);
            con = (HttpURLConnection) mImageUrl.openConnection();
            con.setConnectTimeout(10 * 1000);
            con.setReadTimeout(10 * 1000);
            con.setDoInput(true);
            con.setDoOutput(true);
            bitmap = BitmapFactory.decodeStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return bitmap;
    }
}
