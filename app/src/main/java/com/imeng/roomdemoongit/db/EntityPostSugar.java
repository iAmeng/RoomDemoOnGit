package com.imeng.roomdemoongit.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @Author : Administrator
 * @Date : 2018/1/28 17:24
 * @Version:
 */
@Entity
public class EntityPostSugar {
    @PrimaryKey(autoGenerate = true) //自增
    private int id;

    @ColumnInfo(name = "presugar")
    private String postsugar;

    @ColumnInfo(name = "time")
    private String time; //录入时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostsugar() {
        return postsugar;
    }

    public void setPostsugar(String postsugar) {
        this.postsugar = postsugar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
