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
public class EntityNibp {
    @PrimaryKey(autoGenerate = true) //自增
    private int id;

    @ColumnInfo(name = "high_press")
    private String highPress;

    @ColumnInfo(name = "low_press")
    private String lowPress;

    @ColumnInfo(name = "time")
    private String time; //录入时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHighPress() {
        return highPress;
    }

    public void setHighPress(String highPress) {
        this.highPress = highPress;
    }

    public String getLowPress() {
        return lowPress;
    }

    public void setLowPress(String lowPress) {
        this.lowPress = lowPress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
