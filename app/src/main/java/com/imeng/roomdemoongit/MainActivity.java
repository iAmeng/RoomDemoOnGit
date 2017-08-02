package com.imeng.roomdemoongit;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.imeng.roomdemoongit.db.AppDataBase;
import com.imeng.roomdemoongit.db.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

    Button createDbBt = null;
    Button insertDbBt = null;
    Button deleteBt = null;
    Button queryBt = null;

    private AppDataBase mDb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
    }

    private void initView() {
        createDbBt = findViewById(R.id.crateDatabaseBt);
        insertDbBt = findViewById(R.id.insertDatabaseBt);
        deleteBt = findViewById(R.id.deleteDatabseBt);
        queryBt = findViewById(R.id.qureyDatabaseBt);

        createDbBt.setOnClickListener(this);
        insertDbBt.setOnClickListener(this);
        deleteBt.setOnClickListener(this);
        queryBt.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int vid = view.getId();
        switch (vid) {
            case R.id.crateDatabaseBt:
                createDb();
                break;
            case R.id.insertDatabaseBt:
                insertDb();
                break;
            case R.id.deleteDatabseBt:
                deleteData();
                break;
            case R.id.qureyDatabaseBt:
                queryData();
                break;
            default:
                break;
        }

    }

    private void createDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDb = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "database-name").build();
            }
        }).start();
        //暂时允许在mainthread 处理
        //mDb = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "database-name").allowMainThreadQueries().build();

    }

    private void insertDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, " insertDb: " + "");
                User user = new User();
                user.setUid((int) (System.currentTimeMillis() % 10000));
                user.setFirstName("First_" + System.currentTimeMillis());
                user.setLastName("Last_" + System.currentTimeMillis());
                if (!mDb.isOpen()) {
                    //Toast.makeText(MainActivity.this, "db is close", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, " thread  : db not open" + "");
                    //return;
                }
                /**
                 * 这几行是关键
                 */
                mDb.beginTransaction();
                //mDb.userDao().insertAll(user);
                mDb.userDao().insertA(user);
                //事务必须用这行么？？？
                mDb.setTransactionSuccessful();
                mDb.endTransaction();
                Log.d(TAG, " Insert Db suc !!!!!!!!!!!!!!!!!!! : " + "");
            }
        }).start();

        //mDb.close();
    }

    private void queryData() {
        Log.d(TAG, " onQueryData : " + "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                User[] users = null;
                users = mDb.userDao().loadAllUsers();
                for (User user : users) {
                    Log.e(TAG, user.getFirstName());
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDb.close();;
    }

    private void deleteData() {

    }
}
