package com.example.chenm.rumian;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.realm.Realm;

/**
 * Created by 17637 on 2018/5/15.
 */

public class UIUtils {

    private static Toast toast;

    /**
     * 静态吐司
     *
     * @param context
     * @param text
     */
    public static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }

    /**
     * 不需要上下文对象的  静态toast
     */
    public static void showToast(String text) {
        showToast(getContext(), text);
    }

    /**
     * 获取上下文对象
     *
     * @return
     */
    public static Context getContext() {
        return MyApplication.getInstance();
    }

    /**
     * 获得Realm实例
     *
     * @return
     */
    public static Realm getRealmInstance() {
        return Realm.getInstance(MyApplication.getRealmConfiguration());
    }

    /**
     * 获取Realm数据库64位秘钥
     *
     * @param key
     * @return
     */
    public static byte[] getRealmKey(String key) {
        String newKey = "";
        for (int i = 0; i < 4; i++) {
            newKey = newKey + key;
        }
        return newKey.getBytes();
    }

    /**
     * 从asset路径下读取对应文件转String输出
     *
     * @return
     */
    public static String getJson(String fileName) {
        StringBuilder sb = new StringBuilder();
        AssetManager am = getContext().getAssets();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(
                    am.open(fileName)));
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (IOException e) {
            e.printStackTrace();
            sb.delete(0, sb.length());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString().trim();
    }
}