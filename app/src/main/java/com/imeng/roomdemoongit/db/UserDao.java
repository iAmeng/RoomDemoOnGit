package com.imeng.roomdemoongit.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @Author : Administrator
 * @Date : 2017/8/2 10:22
 * @Version:
 *
 * http://www.goinbowl.com/?p=50
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);


    @Insert
    void insertAll(User... users);

    @Insert
    long insertA(User user);

    @Query("SELECT * FROM user")
    public User[] loadAllUsers();


    @Query("SELECT * FROM user")
    Flowable<List<User>> rxLoadAllUsers();

    @Delete
    void delete(User user);
}
