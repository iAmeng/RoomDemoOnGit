package com.imeng.roomdemoongit.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @Author : Administrator
 * @Date : 2018/1/28 17:25
 * @Version:
 */
@Dao
public interface ScoreDao {
    @Insert
    long insertA(Score score);

    @Query("SELECT * FROM score")
    Flowable<List<Score>> rxLoadAllScores();

    @Query("SELECT * FROM score order by uid desc limit 0,1")
    Flowable<Score> rxLoadLastScores();



}
