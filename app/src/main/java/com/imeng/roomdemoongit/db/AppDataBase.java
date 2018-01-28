package com.imeng.roomdemoongit.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * @Author : Administrator
 * @Date : 2017/8/2 10:23
 * @Version:
 */

//@Database(entities = {User.class}, version = 1, exportSchema = false)
@Database(
        entities = {
                User.class,
                Score.class,
                EntityNibp.class,
                EntityPostSugar.class,
                EntityPreSugar.class,
                EntitySymptom.class,
                EntityWaist.class,
                EntityWalking.class,
                EntityWeight.class
        }, version = 3)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ScoreDao scoreDao();
    public abstract DaoNibp mDaoNibp();
    public abstract DaoPostSugar mDaoPostSugar();
    public abstract DaoPreSugar mDaoPreSugar();
    public abstract DaoSymptom mDaoSymptom();
    public abstract DaoWaist mDaoWaist();
    public abstract DaoWalking mDaoWalking();
    public abstract DaoWeight mDaoWeight();

}
