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
public interface DaoWalking {
    @Insert
    long insert(EntityWalking entityWalking);

    @Query("SELECT * FROM entitywalking order by id desc limit 0,1")
    Flowable<EntityWalking> rxLoadLastLineData();
}
