package com.example.chenm.rumian;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by 17637 on 2018/5/18.
 */

public class MyApplication extends Application {
    /**
     * 上下文
     */
    private static MyApplication instance;
    private static RealmConfiguration config;
    private static String key = "lee12345";

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 在Realm中Stetho需要配置
         */
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        Realm.init(this);
        instance = this;
        new SecureRandom().nextBytes(UIUtils.getRealmKey(key));
        config = new RealmConfiguration.Builder()
                .name("ShuYuan.realm")//指定数据库的名称。如不指定默认名为default。
//                .encryptionKey(UIUtils.getRealmKey(key))//指定数据库的密钥。
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()//声明版本冲突时自动删除原数据库，开发时候打开
//                .migration(new CustomMigration())//指定迁移操作的迁移类。
//                .inMemory()// 声明数据库只在内存中持久化
                .build();

//        mRealm = Realm.getDefaultInstance();
//        mRealm = Realm.getInstance(config);
    }

    public static Context getInstance() {
        return instance;
    }

    public static RealmConfiguration getRealmConfiguration() {
        return config;
    }

}
