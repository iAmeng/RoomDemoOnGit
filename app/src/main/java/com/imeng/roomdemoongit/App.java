package com.imeng.roomdemoongit;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.imeng.roomdemoongit.db.AppDataBase;

/**
 * @Author : Administrator
 * @Date : 2018/1/20 11:56
 * @Version:
 */
public class App extends Application{

    private AppDataBase mDb = null;

    private static  App singleInstance;

    public static App getInstance() {
        return singleInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleInstance = this;
        createDb();
    }

    private void createDb() {
        mDb = Room.databaseBuilder(this, AppDataBase.class, "database_name").build();
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDb = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "database-name").build();
            }
        }).start();
        */
        //暂时允许在mainthread 处理
        //mDb = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "database-name").allowMainThreadQueries().build();
    }

    public AppDataBase getDataBaseInstance() {
        return mDb;
    }


}
