package com.imeng.roomdemoongit.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * @Author : Administrator
 * @Date : 2017/8/2 10:23
 * @Version:
 */

//@Database(entities = {User.class}, version = 1, exportSchema = false)
@Database(entities = {User.class, Score.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract ScoreDao scoreDao();
}
