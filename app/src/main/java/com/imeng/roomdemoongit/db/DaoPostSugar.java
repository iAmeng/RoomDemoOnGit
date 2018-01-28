package com.imeng.roomdemoongit.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import io.reactivex.Flowable;

/**
 * @Author : Administrator
 * @Date : 2018/1/28 17:25
 * @Version:
 */
@Dao
public interface DaoPostSugar {
    @Insert
    long insert(EntityPostSugar entityPostSugar);

    @Query("SELECT * FROM entitypostsugar order by id desc limit 0,1")
    Flowable<EntityPostSugar> rxLoadLastLineData();
}
